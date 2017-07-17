package cn.zdsoft.packager.task;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cn.zdsoft.common.FileUtil;
import cn.zdsoft.common.PathUtil;
import cn.zdsoft.common.ZipUtil;
import cn.zdsoft.common.base.ThreadBase;
import cn.zdsoft.packager.config.FilePackager;
import cn.zdsoft.packager.util.LogHelper;

/**
 * 定时组包的任务
 * @author 段江涛
 * @date 2017-07-17
 */
public class PackagerTask extends ThreadBase {
	/**
	 * 需要的打包的对象，包含打包的相关信息
	 */
	private FilePackager filePackager;

	public PackagerTask(FilePackager filePackager) {
		this.filePackager = filePackager;
	}

	/**
	 * 启动
	 */
	@Override
	public void Start() {
		super.Start();
		LogHelper.getLogger().info("组包:[" + filePackager.Name + "]开始启动");
		start();// 会自动调用run方法
	}

	/**
	 * 停止
	 */
	@Override
	public void Stop() {
		super.Stop();
		LogHelper.getLogger().info("组包:[" + filePackager.Name + "]停止");
	}

	/**
	 * 启动后会自动调用该方法
	 */
	@Override
	public void run() {
		while (running) {
			try {

				File[] files = null;
				if (filePackager.Filter.isEmpty()) {
					files = FileUtil.GetFiles(filePackager.Input);
				} else {
					files = FileUtil.GetFiles(filePackager.Input, filePackager.Filter);
				}

				// 大于指定文件个数才组包
				if (files.length >= filePackager.PackageSize) {
					// 按时间排序
					List<File> listFile = Arrays.asList(files);
					listFile.sort(new Comparator<File>() {
						@Override
						public int compare(File f1, File f2) {
							if (f1.lastModified() < f2.lastModified())
								return -1;// 表示f1排在前面
							else
								return 1;
						}
					});

					listFile = listFile.stream().limit(filePackager.PackageSize).collect(Collectors.toList());
					String zipName = new File(filePackager.Input).getName() + new Date().getTime() + ".zip";
					ZipUtil.Zip(listFile, filePackager.Output, zipName, filePackager.IncludeFolders);
					LogHelper.getLogger().info("[" + filePackager.Name + "]成功压缩文件:" + PathUtil.Combine(filePackager.Output, zipName));
					//压缩成功之后删除源文件
					for (File file : listFile) {
						FileUtil.DeleteFile(file.getAbsolutePath());
					}
				} else {
					LogHelper.getLogger().debug("目录[" + filePackager.Input + "]未达到指定文件个数[" + filePackager.PackageSize + "],不打包");
				}
			} catch (Exception e) {
				LogHelper.getLogger().error("组包程序，组包异常", e);
			} finally {
				try {
					Wait(filePackager.Interval);// 暂停
				} catch (InterruptedException e) {
					LogHelper.getLogger().error("组包程序，组包异常", e);
				}
			}

		}
	}

}

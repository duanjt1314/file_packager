package cn.zdsoft.packager.task;

import java.io.File;
import java.util.Date;

import cn.zdsoft.common.FileUtil;
import cn.zdsoft.common.base.ThreadBase;
import cn.zdsoft.packager.config.Delete;
import cn.zdsoft.packager.util.LogHelper;

/**
 * 定时删除文件任务
 * @author 段江涛
 * @date 2017-07-17
 * 功能描述：定时扫描指定文件夹，然后删除指定配置天数前的所有文件和文件夹(文件夹将递归删除内部文件)
 */
public class DeleteTask extends ThreadBase {
	private Delete delete;

	/**
	 * 实例化删除任务对象
	 * @param delete 删除任务的对象
	 */
	public DeleteTask(Delete delete) {
		this.delete = delete;
	}

	@Override
	public void Start() {
		super.Start();
		StringBuilder sb = new StringBuilder();
		sb.append("路径:" + delete.Directory);
		sb.append("保存天数:" + delete.SaveDay);
		sb.append("启动");
		LogHelper.getLogger().info(sb.toString());
		start();// 会自动调用run方法
	}

	@Override
	public void Stop() {
		LogHelper.getLogger().info("路径:" + delete.Directory + ",保存天数:" + delete.SaveDay + "\t停止");
		super.Stop();
	}

	/**
	 * 重写run方法，系统启动时就会自动调用该方法
	 */
	@Override
	public void run() {
		while (running) {
			try {
				if (!new File(delete.Directory).exists()) {
					LogHelper.getLogger().warn("文件路径：" + delete.Directory + "不存在,不会删除文件");
				} else {
					// 如果当前时间和创建时间的毫秒数大于 天数*24*60*60*1000就删除
					File[] files = FileUtil.GetFiles(delete.Directory);
					for (File file : files) {
						try {
							if (new Date().getTime() - file.lastModified() > delete.SaveDay * 24 * 60 * 60 * 1000) {
								if (file.isFile()) {
									file.delete();
									LogHelper.getLogger().debug("文件:" + file.getAbsolutePath() + "被删除");
								} else {
									FileUtil.DeleteDir(file.getAbsolutePath());
									LogHelper.getLogger().debug("文件夹:" + file.getAbsolutePath() + "被删除");
								}
							}
						} catch (Exception e) {
							LogHelper.getLogger().error("删除任务，处理文件:" + file.getAbsolutePath() + "失败", e);
						}
					}
				}
			} catch (Exception e) {
				LogHelper.getLogger().error("定时删除任务出现异常", e);
			} finally {
				try {
					Wait(60);//扫描不要太频繁,60秒扫描一次差不多
				} catch (InterruptedException e) {
					LogHelper.getLogger().error("暂停60秒出错", e);
				}
			}
		}
	}

}

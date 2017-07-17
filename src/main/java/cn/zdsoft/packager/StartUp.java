package cn.zdsoft.packager;

import java.util.ArrayList;
import java.util.List;

import cn.zdsoft.common.StringUtil;
import cn.zdsoft.common.base.ThreadBase;
import cn.zdsoft.packager.config.Delete;
import cn.zdsoft.packager.config.FilePackager;
import cn.zdsoft.packager.task.DeleteTask;
import cn.zdsoft.packager.task.PackagerTask;
import cn.zdsoft.packager.util.Config;
import cn.zdsoft.packager.util.LogHelper;

public class StartUp extends ThreadBase {
	/**
	 * 删除任务集合，方便停止
	 */
	private List<DeleteTask> deleteTasks = new ArrayList<DeleteTask>();
	/**
	 * 打包任务集合，方便停止
	 */
	private List<PackagerTask> packagerTasks = new ArrayList<PackagerTask>();

	/**
	 * 启动
	 */
	public void Start() {
		// 读取配置文件
		Config config = Config.getConfig();
		LogHelper.getLogger().info("读取配置文件的信息如下:"+StringUtil.GetJsonString(config));

		// 循环启动[删除任务]
		for (Delete delete : config.getDeletes()) {
			DeleteTask dtask=new DeleteTask(delete);
			dtask.Start();
			deleteTasks.add(dtask);
		}
		//循环启动[打包任务]
		for (FilePackager filePackager : config.getFilePackagers()) {
			if(filePackager.Enabled){
				PackagerTask packagerTask=new PackagerTask(filePackager);
				packagerTask.Start();
				packagerTasks.add(packagerTask);
			}
		}
	}

	/**
	 * 停止
	 */
	public void Stop() {
		LogHelper.getLogger().info("收到停止指令,程序停止中.");
		for (DeleteTask deleteTask : deleteTasks) {
			deleteTask.Stop();
		}
		for (PackagerTask packagerTask : packagerTasks) {
			packagerTask.Stop();
		}
		deleteTasks.clear();
		packagerTasks.clear();
	}
}

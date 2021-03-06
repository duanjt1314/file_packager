package cn.zdsoft.packager;

import cn.zdsoft.packager.util.LogHelper;

/**
 * 文件打包程序，启动类
 * @author 段江涛
 * @date 2017-07-14
 */
public class App {

	/**
	 * 主方法
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			StartUp startUp = new StartUp();
			LogHelper.getLogger().info("程序启动中...");
			startUp.Start();
			// 添加程序结束监听 ，用于释放系统资源
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					startUp.Stop();// 系统结束
				}
			});

			LogHelper.getLogger().info("程序启动成功");
		} catch (Exception ex) {
			LogHelper.getLogger().error("程序出现未识别的异常", ex);
		}
	}

}

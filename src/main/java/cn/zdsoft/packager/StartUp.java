package cn.zdsoft.packager;

import cn.zdsoft.packager.util.LogHelper;

public class StartUp extends Thread {
	/**
	 * 程序自动运行中
	 */
	private boolean running = false;

	/**
	 * 重写run方法，系统启动时就会自动调用该方法
	 */
	@Override
	public void run() {
		
	}
	
	/**
	 * 启动
	 */
	public void Start() {
		// 读取配置文件
		

		// 启动,会自动调用run方法
		running = true;
		start();
	}

	/**
	 * 停止
	 */
	public void Stop() {
		LogHelper.getLogger().info("收到停止指令,程序停止中.");
		running = false;
	}
}

package cn.zdsoft.packager.task;

import cn.zdsoft.common.base.ThreadBase;
import cn.zdsoft.packager.config.FilePackager;

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

	@Override
	public void Start() {
		super.Start();
	}

	@Override
	public void Stop() {
		super.Stop();

	}

}

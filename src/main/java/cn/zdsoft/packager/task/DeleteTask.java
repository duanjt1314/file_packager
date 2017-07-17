package cn.zdsoft.packager.task;

import cn.zdsoft.common.base.ThreadBase;
import cn.zdsoft.packager.config.Delete;

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
	public DeleteTask(Delete delete){
		this.delete=delete;
	}
	
	@Override
	public void Start(){
		
	}
	
	@Override
	public void Stop(){
		
	}
}

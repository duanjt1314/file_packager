package cn.zdsoft.packager.util;

import java.util.List;

import cn.zdsoft.common.PathUtil;
import cn.zdsoft.packager.config.Delete;
import cn.zdsoft.packager.config.FilePackager;

/**
 * 配置类,用于保存系统的相关配置
 * @author 段江涛
 * @date 2017-07-14
 */
public class Config {
	//局部静态变量，为了实现单例模式
	private static Config _config=null;
	/**
	 * 文件打包对象集合
	 */
	private List<FilePackager> filePackagers;
	/**
	 * 文件删除对象集合
	 */
	private List<Delete> deletes;
	
	/**
	 * 获取配置对象，单例模式
	 * @return
	 */
	public static Config getConfig(){
		if(_config==null){
			_config=new Config();
		}
		return _config;
	}
	
	public Config(){
		LoadConfig();
	}
	
	/**
	 * 获取文件打包对象集合
	 * @return
	 */
	public List<FilePackager> getFilePackagers(){
		return this.filePackagers;
	}
	
	/**
	 * 获取文件删除对象集合
	 * @return
	 */
	public List<Delete> getDeletes(){
		return this.deletes;
	}
	
	/**
	 * 加载配置文件
	 */
	private void LoadConfig(){
		String fileName=PathUtil.Combine(Helper.GetAppDir(),"config","config.xml");
		
	}
}

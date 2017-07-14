package cn.zdsoft.packager.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.zdsoft.common.PathUtil;
import cn.zdsoft.common.XmlUtil;
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
	private List<FilePackager> filePackagers=new ArrayList<FilePackager>();
	/**
	 * 文件删除对象集合
	 */
	private List<Delete> deletes=new ArrayList<Delete>();
	
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
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			try {
				// 解析xml文件
				SAXReader reader = new SAXReader();
				Document document = reader.read(file);
				Element ele = document.getRootElement();

				//读取文件打包配置
				for (Object obj : ele.elements("FSFilePackager")) {
					Element eleFilePackager=(Element)obj;
					FilePackager filePackager=new FilePackager();
					filePackager.Name=XmlUtil.GetXmlAttr(eleFilePackager, "Name").getText();
					filePackager.Enabled=XmlUtil.GetXmlAttr(eleFilePackager, "Enabled", true);
					filePackager.Input=XmlUtil.GetXmlAttr(eleFilePackager, "Input").getText();
					filePackager.Filter=XmlUtil.GetXmlAttr(eleFilePackager, "Filter", "");
					filePackager.IncludeFolders=XmlUtil.GetXmlAttr(eleFilePackager, "IncludeFolders", true);
					filePackager.Output=XmlUtil.GetXmlAttr(eleFilePackager, "Output").getText();
					filePackager.Interval=XmlUtil.GetXmlAttr(eleFilePackager, "Interval", 300);
					filePackager.PackageSize=XmlUtil.GetXmlAttr(eleFilePackager, "PackageSize", 500);
					this.filePackagers.add(filePackager);
				}
				
				//读取文件删除配置
				for (Object delete : ele.elements("Delete")) {
					Element eleDelete=(Element)delete;
					Delete d=new Delete();
					d.Directory=XmlUtil.GetXmlElement(eleDelete, "Directory").getText();
					d.SaveDay=Integer.parseInt(XmlUtil.GetXmlElement(eleDelete, "SaveDay").getText());
					this.deletes.add(d);
				}

			} catch (Exception e) {
				LogHelper.getLogger().error("xml配置文件解析错误,路径：" + fileName, e);
			}
		} else {
			LogHelper.getLogger().error("配置文件:" + fileName + " 没有找到,请配置正确路径");
		}
	}
}

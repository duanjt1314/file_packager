package cn.zdsoft.packager.config;

/**
 * 文件打包对象
 * @author 段江涛
 * @date 2017-07-14
 */
public class FilePackager {
	/**
	 * 获取或设置打包器名称
	 */
	public String Name;
	/**
	 * 获取或设置 是否启用打包器
	 */
	public String Enabled;
	/**
	 * 获取或设置 打包器待打包目录
	 */
	public String Input;
	/**
	 * 获取或设置 筛选条件，只筛选指定文件，为空代表所有文件。会通过indexOf筛选
	 */
	public String Filter;
	/**
	 * 获取或设置 打包时是否把文件夹打包到压缩文件里面
	 */
	public boolean IncludeFolders;
	/**
	 * 获取或设置 打包后的输出目录
	 */
	public String Output;
	/**
	 * 获取或设置 多久执行一次目录扫描，单位：毫秒
	 */
	public int Interval;
	/**
	 * 获取或设置 多少个文件组成一个压缩包
	 */
	public int PackageSize;
	
}

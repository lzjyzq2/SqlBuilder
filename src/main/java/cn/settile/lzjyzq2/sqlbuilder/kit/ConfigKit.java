package cn.settile.lzjyzq2.sqlbuilder.kit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import cn.settile.lzjyzq2.sqlbuilder.exception.ConfigInstanceException;
import cn.settile.lzjyzq2.sqlbuilder.lang3.ArrayUtils;
import cn.settile.lzjyzq2.sqlbuilder.lang3.StringUtils;

/**
 * 配置工具
 * @author Mr.cjd
 */
public class ConfigKit {

	private static ConfigKit me = null;

	/**
	 * 声明 sql存在的目录
	 */
	private List<String> folders;
	
	private String sqlMode;
	private String sqlFolders;
	private String sqlFolderBase;
	private String sqlMid;
	private String sqlDebug;

	/**
	 * private修饰,单例
	 */
	private ConfigKit(){
		InputStream userFis = null;
		InputStream defaultFis = null;
		// 获取 项目根路径
		this.sqlFolderBase = WebAppKit.getPath();
		// 加载 用户配置文件
		File userConfigFile = new File(this.sqlFolderBase, "sqlbuilder-config.properties");
		InputStream userConfigFileStream = null;
		if(!userConfigFile.exists()){
			userConfigFileStream = WebAppKit.getInnerFile("sqlbuilder-config.properties");
		}
		// 破解 打包在.jar内读取不到的办法
		defaultFis = WebAppKit.getInnerFile("sqlbuilder-config-default.properties");
		
		try {
			
			Properties user = new Properties();
			Properties defaults = new Properties();
			
			// 如果 存在则载入
			if (userConfigFile.exists()) {
				user.load(userFis = new FileInputStream(userConfigFile));
			}else if(userConfigFileStream!=null){
				user.load(userConfigFileStream);
			}
			
			// 装载 默认配置
			defaults.load(defaultFis);
			// 获取 模式
			this.sqlMode = user.getProperty("sqlMode", defaults.getProperty("sqlMode", "run"));
			// 获取 目录们
			this.sqlFolders = user.getProperty("sqlFolders", defaults.getProperty("sqlFolders", ""));
			// 获取 中间件
			this.sqlMid = user.getProperty("sqlMid", defaults.getProperty("sqlMid", ""));
			// 获取 调试模式
			this.sqlDebug = user.getProperty("sqlDebug", defaults.getProperty("sqlDebug", ""));
			
		} catch (IOException e) {
			new IOException("找不到配置文件!").printStackTrace();
		} finally {
			try {
				if (userFis != null) {
					userFis.close();
				}
				if (defaultFis != null) {
					defaultFis.close();
				}
			} catch (IOException e) {
				new RuntimeException("ConfigKit.me() 已成功获取配置，但无法关闭文件", e).printStackTrace();
			}
		}
	}

	/**
	 * 单例获取入口
	 * @return 当前实例
	 */
	public static ConfigKit me(){
		if (me == null) {
			me = new ConfigKit();
		}
		return me;
	}

	public List<String> getFolders() {
		// 用户有配置目录
		if (this.folders == null && StringUtils.isNotBlank(StringUtils.trimToEmpty(this.sqlFolders))) {
			String[] arrays = StringUtils.split(this.sqlFolders, ",");
			if (ArrayUtils.isNotEmpty(arrays)) {
				this.folders = new ArrayList<>(arrays.length + 1);
				this.folders.add(this.sqlFolderBase);
				for (String folder : arrays) {
					String userFolder = this.sqlFolderBase + StringUtils.trimToEmpty(folder).replaceAll("\\.", "/");
					this.folders.add(userFolder);
				}
			}
		}
		return folders;
	}

	/**
	 * 设置sql所在目录
	 * @param folders 文件夹目录
	 */
	public void setFolders(List<String> folders) {
		this.folders = folders;
	}

	/**
	 * 获取SqlBuilder工作模式
	 * @return run/debug
	 */
	public String getSqlMode() {
		return sqlMode;
	}

	/**
	 * 设置SqlBuilder工作模式
	 * @param sqlMode run/debug
	 */
	public void setSqlMode(String sqlMode) {
		this.sqlMode = sqlMode;
	}

	/**
	 * 获取Sql目录
	 * @return
	 */
	public String getSqlFolders() {
		return sqlFolders;
	}
	
	public void setSqlFolders(String sqlFolders) {
		this.sqlFolders = sqlFolders;
	}

	public String getSqlMid() {
		return sqlMid;
	}

	public void setSqlMid(String sqlMid) {
		this.sqlMid = sqlMid;
	}

	public boolean getSqlDebug() {
		if (StringUtils.isBlank(this.sqlDebug)) {
			return false;
		}
		return Boolean.parseBoolean(this.sqlDebug);
	}

	public void setSqlDebug(boolean sqlDebug) {
		this.sqlDebug = String.valueOf(sqlDebug);
	}
	
}
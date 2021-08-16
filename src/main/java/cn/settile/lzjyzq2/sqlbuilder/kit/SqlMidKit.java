package cn.settile.lzjyzq2.sqlbuilder.kit;

import cn.settile.lzjyzq2.sqlbuilder.lang3.StringUtils;
import cn.settile.lzjyzq2.sqlbuilder.middleware.SqlMiddleware;
import java.util.ArrayList;
import java.util.List;

/**
 * 中间件工具
 * @author Mr.cjd
 */
public class SqlMidKit {
	
	public static List<SqlMiddleware> list(){
		// 获取 用户选择了哪些中间件
		String userMids = ConfigKit.me().getSqlMid();
		// 没有中间件
		if (StringUtils.isBlank(userMids)) {
			return null;
		}
		String[] mids = StringUtils.split(userMids, ",");
		List<SqlMiddleware> midList = new ArrayList<>(mids.length);
		for (String mid : mids) {
			try {
				Class<?> clazz = Class.forName("cn.settile.lzjyzq2.sqlbuilder.middleware." + mid + "Mid");
				midList.add((SqlMiddleware) clazz.newInstance());
			} catch (ClassNotFoundException e) {
				new RuntimeException(mid + " 中间件未找到！", e).printStackTrace();
			} catch (InstantiationException e) {
				new RuntimeException(mid + " 中间件实例无法创建！", e).printStackTrace();
			} catch (IllegalAccessException e) {
				new RuntimeException(mid + " 中间件实例创建参数不正确！", e).printStackTrace();
			}
		} 
		return midList;
	}
	
}
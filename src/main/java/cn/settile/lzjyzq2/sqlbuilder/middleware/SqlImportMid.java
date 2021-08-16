package cn.settile.lzjyzq2.sqlbuilder.middleware;

import cn.settile.lzjyzq2.sqlbuilder.core.SqlBuilder;
import cn.settile.lzjyzq2.sqlbuilder.middleware.exception.SqlImportException;
import cn.settile.lzjyzq2.sqlbuilder.model.SqlBuilderPara;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * sql语句引入中间件
 * @author Mr.cjd
 */
public class SqlImportMid implements SqlMiddleware {
	
	private static final String MID_DES = "支持跨文件引入查询语句的函数";
	
	private String errorMsg(String callName, String content){
		return callName + " -> " + MID_DES + " -> " + content;
	}
	
	@Override
	public String render(String fileName, String sql, SqlBuilderPara... paras) {
		String regx = "sql\\('([^']+)'\\)";
		String parsed = sql;
		Matcher matcher = Pattern.compile(regx).matcher(parsed);
		while (matcher.find()) {
			// 获取 结果
			String result = matcher.group();
			// 获取 key
			String key = result.replaceAll(regx, "$1");
			// 获取 引入sql语句
			String importSql = SqlBuilder.render(key.indexOf('.') > -1 ? key : (fileName + "." + key), paras);
			
			try {
				// 替换
				parsed = parsed.replaceAll("sql\\('" + key + "'\\)", importSql);
			} catch (IllegalArgumentException e) {
				String errorMsg = e.getMessage();
				if (errorMsg.indexOf('{') > -1 || errorMsg.indexOf('}') > -1) {
					throw new SqlImportException(errorMsg(result, "请调用SqlBuilder.setEngine() 使全局引擎统一。"), e);
				}
				new RuntimeException(errorMsg(result, "引入发生错误，原因未知，请联系作者。"), e).printStackTrace();
				throw new SqlImportException();
			} catch (Exception e) {
				new RuntimeException(errorMsg(result, "引入发生错误，原因未知，请联系作者。"), e).printStackTrace();
				throw new SqlImportException();
			}
		}
		return parsed;
	}
	
}
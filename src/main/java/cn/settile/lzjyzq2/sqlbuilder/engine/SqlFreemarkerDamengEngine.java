package cn.settile.lzjyzq2.sqlbuilder.engine;

import cn.settile.lzjyzq2.sqlbuilder.model.SqlBuilderPara;
import java.util.regex.Pattern;

/**
 * freemarker 渲染引擎
 * 达梦数据库的sql需要大量的双引号，用这个处理器可以省去这个过程
 * @author Mr.cjd
 */
public class SqlFreemarkerDamengEngine extends SqlFreemarkerEngine {

	/**
	 * 预定义正则表达式
	 */
	private final static Pattern MATCHER = Pattern.compile("\"");

	@Override
	public String render(String sql, SqlBuilderPara... paras) {
		// 先转换一波
		String parsed = super.render(sql, paras);
		
		// 如果原来就有双引号就当做用户已经写成了达梦写法
		if (MATCHER.matcher(parsed).find()) {
			return parsed;
		}
		
		return parsed
		// 先统统小写
		.toLowerCase()
		// 替换别名
		.replaceAll("as\\s(\\w+)", "as \"$1\"")
		// 替换表名
		.replaceAll("from\\s(\\w+)", "from \"$1\"")
		// 替换联接
		.replaceAll("join\\s(\\w+)", "join \"$1\"")
		// 替换更新语句
		.replaceAll("update\\s(\\w+)", "update \"$1\"")
		// 替换字段
		.replaceAll("\\.(\\w+)", ".\"$1\"")
		// 修复别名
		.replaceAll("from\\s\"(\\w+)\"\\sas\\s\"(\\w+)\"", "from \"$1\" as $2")
		.replaceAll("join\\s?(\\(.+\\))\\s?as\\s\"(\\w+)\"", "join $1 as $2")
		.replaceAll("join\\s?(\"\\w+\")\\sas\\s\"(\\w+)\"", "from \"$1\" as $2")
		// 使用这个来解决一些正则很困难的问题
		.replaceAll("`", "");
	}
	
}

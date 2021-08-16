package cn.settile.lzjyzq2.sqlbuilder.engine;

import cn.settile.lzjyzq2.sqlbuilder.model.SqlBuilderPara;

public class SqlNoneEngine implements SqlRenderEngine {
	
	@Override
	public String render(String sql, SqlBuilderPara... paras) {
		return sql;
	}
	
}
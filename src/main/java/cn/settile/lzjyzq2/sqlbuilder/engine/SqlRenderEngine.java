package cn.settile.lzjyzq2.sqlbuilder.engine;

import cn.settile.lzjyzq2.sqlbuilder.model.SqlBuilderPara;

public interface SqlRenderEngine {
	
	public String render(String sql, SqlBuilderPara... paras);
	
}

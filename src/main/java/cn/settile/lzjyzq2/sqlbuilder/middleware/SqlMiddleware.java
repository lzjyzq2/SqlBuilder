package cn.settile.lzjyzq2.sqlbuilder.middleware;

import cn.settile.lzjyzq2.sqlbuilder.model.SqlBuilderPara;

public interface SqlMiddleware {
	
	String render(String fileName, String sql, SqlBuilderPara... paras);
	
}

package cn.settile.lzjyzq2.sqlbuilder.kit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.settile.lzjyzq2.sqlbuilder.exception.RenderException;
import cn.settile.lzjyzq2.sqlbuilder.lang3.StringUtils;
import cn.settile.lzjyzq2.sqlbuilder.middleware.SqlMiddleware;
import cn.settile.lzjyzq2.sqlbuilder.model.SqlBuilderPara;

public class SqlBuilderReader {
	
	private final static String ENTER = "\r\n";
	
	public final static String in(String sqlId, SqlBuilderPara... paras){
		String[] arrays = StringUtils.split(sqlId, ".", 2);
		if (arrays.length != 2) {
			new RuntimeException("错误的sqlId格式，示例 fileName.sqlName ").printStackTrace();
		}
		// 获取 各自信息
		String fileName = arrays[0];
		String sqlName = arrays[1];
		
		// 获取 用户配置
		String sqlMode = ConfigKit.me().getSqlMode();
		// 判断 是不是产品模式
		boolean sqlModeRun = StringUtils.isNotBlank(sqlMode) &&
				StringUtils.eqlsIgnoreCase(sqlMode, "run");
		
		if (sqlModeRun) {
			String cacheSql = SqlBuilderCache.sql(fileName, sqlName);
			
			if (StringUtils.isNotBlank(cacheSql)) {
				return cacheSql;
			}
		}
		
		// 声明 sqlMd文件类
		File sqlMdFile = null;
		try {
			sqlMdFile = SqlBuilderSearcher.search(ConfigKit.me().getFolders(), fileName + ".md");
		} catch (FileNotFoundException e) {
			new RuntimeException("错误的sqlId，无法找到" + fileName + ".md", e).printStackTrace();
		}
		
		FileReader fReader = null;
		BufferedReader bReader = null;
		List<String> sqlLines = new ArrayList<>();

		try {
			fReader = new FileReader(sqlMdFile);
			bReader = new BufferedReader(fReader);

			String sqlLine = null;
			String sqlLineTrim = null;

			// 是否已经找到sql
			boolean sqlFound = false;
			boolean sqlCode = false;
			while ((sqlLine = bReader.readLine()) != null) {
				// 处理前后空格
				sqlLineTrim = StringUtils.trimToEmpty(sqlLine);
				// 已经找到sql
				if (sqlFound) {
					// 已经到下一个sql域了，跳出
					if(RegexpKit.test("^-", sqlLineTrim)){
						sqlLines.clear();
						break;
					}else if (RegexpKit.test("^```.*$", sqlLineTrim)) {
						sqlLine = bReader.readLine();
						if(sqlCode){
							break;
						}else {
							sqlCode = true;
						}
					}
					if (sqlCode&&StringUtils.isNotBlank(sqlLine)) {
						// 插入 结果
						sqlLines.add(sqlLine);
					}
				} else {
					// 找sqlName
					sqlFound = RegexpKit.test("^- +" + sqlName + "$", sqlLineTrim);
				}
			}
		}
		// 因为已经提前预防不会出现这个错误，但是质量检测需要，那就加上吧
		catch (FileNotFoundException e) {
			new RuntimeException(fileName + ".md文件找不到噢", e).printStackTrace();
		}
		catch (IOException e) {
			new RuntimeException(fileName + ".md 读取过程发生错误", e).printStackTrace();
		}
		
		// 最后全要关闭
		finally {
			try {
				if (bReader != null) {
					bReader.close();
				}
				if (fReader != null) {
					fReader.close();
				}
			} catch (IOException e) {
				new RuntimeException(fileName + ".md 已读取完毕，但在关闭流的时候发生错误", e).printStackTrace();
			}
		}
		
		String sql = String.join(ENTER, sqlLines);
		
		if (StringUtils.isNotBlank(sql) && sqlModeRun) {
			SqlBuilderCache.sql(fileName, sqlName, sql);
		}
		
		// 获取 中间件
		List<SqlMiddleware> mids = SqlMidKit.list();
		if (mids != null && !mids.isEmpty()) {
			for (SqlMiddleware mid : mids) {
				sql = mid.render(fileName, sql, paras);
			}
		}
		
		return sql;
	}
	
}

package cn.settile.lzjyzq2.sqlbuilder.engine;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import cn.settile.lzjyzq2.sqlbuilder.exception.EngineException;
import cn.settile.lzjyzq2.sqlbuilder.lang3.ArrayUtils;
import cn.settile.lzjyzq2.sqlbuilder.model.SqlBuilderPara;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class SqlFreemarkerEngine implements SqlRenderEngine {

	private final static Configuration cfg = new Configuration(Configuration.VERSION_2_3_24);
	
	static {
		cfg.setDefaultEncoding("UTF-8");
		cfg.setLogTemplateExceptions(false);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        // 控制Freemarker数字格式化
        cfg.setNumberFormat("#");
	}
	
	@Override
	public String render(String sql, SqlBuilderPara... paras) {
        // 数据模型
        Map<String, Object> root = new HashMap<>();
        if (ArrayUtils.isNotEmpty(paras)) {
			for (SqlBuilderPara para : paras) {
				if (para != null) {
					root.put(para.getKey(), para.getValue());
				}
			}
		}
        
        // 输出结果
        Writer writer = null;
        
        try {
			Template template = new Template("SqlBuilder", new StringReader(sql), cfg);
			template.process(root, writer = new StringWriter());
		} catch (IOException e) {
			throw new EngineException(EngineException.FMK_INPUT_ERROR, e);
		} catch (TemplateException e) {
			throw new EngineException(EngineException.FMK_RENDER_ERROR, e);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				new IOException("Freemarker 渲染输出 writer 无法关闭，请检查原因！", e);
			}
		}
        
		return writer.toString();
	}

}

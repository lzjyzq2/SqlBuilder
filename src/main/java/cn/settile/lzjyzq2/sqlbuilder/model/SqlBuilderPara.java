package cn.settile.lzjyzq2.sqlbuilder.model;

public class SqlBuilderPara {
	
	private String key;
	private Object value;
	
	public SqlBuilderPara(String key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public static final String toJson(SqlBuilderPara... paras){
		if (paras == null || paras.length == 0) {
			return "[]";
		}
		StringBuilder builder = new StringBuilder("[");
		for (SqlBuilderPara para : paras) {
			builder.append(toJson(para));
			builder.append(" ,");
		}
		return builder.append("]").toString().replace(" ,]", "]");
	}
	
	public static final String toJson(SqlBuilderPara para){
		return "{\"" + para.getKey() + "\": \"" + para.getValue() + "\"}";
	}

}

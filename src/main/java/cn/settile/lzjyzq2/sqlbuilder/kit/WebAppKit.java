package cn.settile.lzjyzq2.sqlbuilder.kit;

import cn.settile.lzjyzq2.sqlbuilder.exception.PathException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.InputStream;

public class WebAppKit {
	
	public final static String getPath(){
		// 获取 地址
		URI uri = null;
		try {
			ClassLoader webLoader = WebAppKit.class.getClassLoader();
			URL url = webLoader.getResource("/");
			if (url == null) {
				url = WebAppKit.class.getResource("/");
				if (url == null) {
					System.err.println("WebAppKit.getPath() 无法正确获取到项目路径 ");
					throw new PathException();
				}
			}
			uri = url.toURI();
		} catch (URISyntaxException e) {
			new RuntimeException(PathException.GET_ERROR, e).printStackTrace();
		}
		if (uri == null) {
			new RuntimeException(PathException.NULL_ERROR).printStackTrace();
			// 质量检测
			return null;
		}
		return uri.getPath();
	}
	
	public final static InputStream getInnerFile(String fileName){
		return WebAppKit.class.getResourceAsStream("/" + fileName);
	}
	
}
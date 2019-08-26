package com.loong.server;

import org.eclipse.jetty.servlet.ServletHolder;
import com.loong.util.PropertiesUtil;

/**
 * 简单工厂类，根据requestUriPath不同生产不同的ServletHolder，达到定制响应内容的目的
 * @author Jay
 *
 */
public class ServletHolderSimpleFactory extends ServletHolder{
	private static ServletHolder shsf;
	private static String agentSecretKey = new PropertiesUtil().read("config.properties", "agentSecretKey");
	
	/**
	 * 根据requestUriPath不同生产不同的ServletHolder，达到定制响应内容的目的
	 * @param requestUriPath
	 * @return ServletHolder
	 */
	public static ServletHolder getServetHolder(String requestUriPath){
		if("/api/access/do".equals(requestUriPath)){
			shsf = new ServletHolder(new MyHttpServletResponse(agentSecretKey));
		}
		if("/test".equals(requestUriPath)){
			shsf = new ServletHolder(new MyHttpServletResponse2());
		}
		if("/getAgentKey".equals(requestUriPath)){
			shsf = new ServletHolder(new MyHttpServletResponse3());
		}
		if("/getAgentKeyNew".equals(requestUriPath)){
			shsf = new ServletHolder(new MyHttpServletResponse4());
		}
		return shsf;
	}
}

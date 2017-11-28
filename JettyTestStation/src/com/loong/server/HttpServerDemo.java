package com.loong.server;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.loong.util.PropertiesUtil;


public class HttpServerDemo {

	public static void main(String[] args) throws Exception {
		PropertiesUtil pu = new PropertiesUtil();
		String port = pu.read("config.properties", "port");
		String agentSecretKey = pu.read("config.properties", "agentSecretKey");
		Server server = new Server(Integer.parseInt(port));
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		//完整的请求是“http://localhost:10086/api/access/do”
		//截取请求“http://localhost:10086/api”
		//此处也可直接配置“/”
		context.setContextPath("/api");
		server.setHandler(context);
		//context.addServlet根据配置pathSpec串返回对应的响应内容，此处可配置多个context.addServlet
		//此处也可考虑简单工厂模式，根据传入的不同uri实例化不同的response对象，达到请求不同uri返回不同响应内容的结果
		//此处"/access/do"也可直接配置“/”
		context.addServlet(new ServletHolder(new MyHttpServletResponse(agentSecretKey)), "/access/do");
//		context.addServlet(new ServletHolder(new MyHttpServletResponse02()), "/access/do02");
		System.out.println("server start...");
		server.start();
		server.join();
	}

}

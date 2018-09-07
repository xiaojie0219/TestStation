package com.loong.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class MyHttpServletResponse2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(MyHttpServletResponse2.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException {
		// 读取请求内容
		InputStream is = request.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int temp = 0;
		while ((temp = is.read()) != -1) {
			baos.write(temp);
		}
		// 获取请求url中的参数名称和内容，注意非body体中的请求内容
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String name = paramNames.nextElement();
			String value = request.getParameter(name);
			logger.info(name + "=" + value);
		}
		// 获取post请求中body体内容并打印
		String reqBodyData;
		try {
			reqBodyData = new String(baos.toByteArray(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			reqBodyData = "";
		}
		if ("".equals(reqBodyData)) {
			logger.info("requestBody===>:你使用的是GET请求方式或POST请求Body体内容为空");
		} else {
			logger.info("requestBody===>:" + reqBodyData);
		}

		// 自定义响应内容
		OutputStream os = response.getOutputStream();
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		// 定制响应内容
		if ("".equals(reqBodyData)) {
			os.write("你使用的是GET请求方式或POST请求Body体内容为空".getBytes("UTF-8"));
		} else {
			os.write("success".getBytes("UTF-8"));
		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		doPost(request, response);
	}
}

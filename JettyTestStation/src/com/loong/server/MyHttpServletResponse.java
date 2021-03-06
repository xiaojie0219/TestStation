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

import com.loong.util.ThreeDES;

public class MyHttpServletResponse extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(MyHttpServletResponse.class); 
	
	private ThreeDES td;
	private String secretKey;
	public MyHttpServletResponse(String secretKey){
		this.secretKey = secretKey;
		td = new ThreeDES();
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
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
			// 解密请求body体，并打印到控制台
			reqBodyData = new String(td.decryptMode(baos.toByteArray(),	secretKey), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			reqBodyData = "post请求中body体内容为空";
		}
		logger.info("requestBody===>:" + reqBodyData);
		
		//自定义响应内容
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		OutputStream os = response.getOutputStream();
		//当请求body体为""时返回fail，否则返回success
		if("".equals(reqBodyData)){
			os.write(td.encryptMode("fail".getBytes("UTF-8"),secretKey));
		}else{
			os.write(td.encryptMode("success".getBytes("UTF-8"),secretKey));
		}
		
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		doPost(request,response);
	}
}

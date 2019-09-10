package com.loong.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.loong.util.GetAgentKey;

public class MyHttpServletResponse4 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(MyHttpServletResponse4.class);
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException {
		// 自定义响应内容
		OutputStream os = response.getOutputStream();
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		
		// 读取请求内容
		InputStream is = request.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int temp = 0;
		while ((temp = is.read()) != -1) {
			baos.write(temp);
		}

		// 获取post请求中body体内容并打印
		try {
			String reqBodyData = new String(baos.toByteArray(), "UTF-8");
			if("".equals(reqBodyData) || reqBodyData==null){
				logger.info("requestBody===>:POST请求Body体中的内容为空");
				os.write("POST请求Body体中的内容为空".getBytes("UTF-8"));
			}else{
				logger.info("requestBody===>:" + reqBodyData);
				String str[] = reqBodyData.split("&");
				Map<String,String> rmap = new HashMap<String,String>();
				for(int i=0;i<str.length;i++){
					String key=str[i].split("=")[0],value=str[i].split("=")[1];
					rmap.put(key, value);
				}
				GetAgentKey getAgentKey = new GetAgentKey(rmap.get("agentId"),rmap.get("agentInsertTime"));
				String resStr = getAgentKey.getPrivateKeyNew();
				os.write(resStr.getBytes("UTF-8"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 自定义响应内容
		OutputStream os = response.getOutputStream();
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);

		// 读取请求内容
		InputStream is = request.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int temp = 0;
		while ((temp = is.read()) != -1) {
			baos.write(temp);
		}

		// 获取请求url中的参数名称和内容，注意非body体中的请求内容
		Enumeration<String> paramNames = request.getParameterNames();
		if(!paramNames.hasMoreElements()){
			logger.info("GET请求中未传agentId和agentInsertTime参数");
			os.write("GET请求中未传agentId和agentInsertTime参数".getBytes("UTF-8"));
		}else{
			Map<String, String> rmap = new HashMap<String, String>();
			while (paramNames.hasMoreElements()) {
				String name = paramNames.nextElement();
				String value = request.getParameter(name);
				logger.info(name + "=" + value);
				rmap.put(name, value);
			}
			if(rmap.get("agentId")==null || rmap.get("agentInsertTime")==null){
				logger.info("你使用的是GET请求，请检查是否传入正确的agentId和agentInsertTime参数");
				os.write("你使用的是GET请求，请检查是否传入正确的agentId和agentInsertTime参数".getBytes("UTF-8"));
			}else{
				GetAgentKey getAgentKey = new GetAgentKey(rmap.get("agentId"),
						rmap.get("agentInsertTime"));
				String resStr = getAgentKey.getPrivateKeyNew();
				os.write(resStr.getBytes("UTF-8"));
			}
		}

	}

}

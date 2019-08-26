package com.loong.util;

import com.loong.util.MD5Security;

public class GetAgentKey {
	private String agentId;
	private String agentInsertTime;
	
	public GetAgentKey(String agentId, String agentInsertTime){
		this.agentId = agentId;
		this.agentInsertTime = agentInsertTime;
	}
	
	public String getPrivateKey() {
		String privateKye = null;
		try {
			privateKye = MD5Security.md5To24(agentId
					+ agentInsertTime.substring(0, 8) + "sino"
					+ agentInsertTime.substring(8, agentInsertTime.length()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return privateKye;
	}
	
	public String getPrivateKeyNew(){
		String privateKyeNew = null;
		try {
			privateKyeNew = MD5Security.md5To24Api(agentId
					+ agentInsertTime.substring(0, 8) + "sino"
					+ agentInsertTime.substring(8, agentInsertTime.length()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return privateKyeNew;
	}
	
	public static void main(String[] args) {
		String agentId = "00003";
		String insertTime = "20190815090921";
		try {
			String privateKye = MD5Security.md5To24(agentId + insertTime.substring(0, 8) + "sino" + insertTime.substring(8, insertTime.length()));
			System.out.println("oldGetAgentKeyMethod:" + privateKye);
			String privateKyeNew = MD5Security.md5To24Api(agentId + insertTime.substring(0, 8) + "sino" + insertTime.substring(8, insertTime.length()));
			System.out.println("newGetAgentKeyMethod:" + privateKyeNew);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

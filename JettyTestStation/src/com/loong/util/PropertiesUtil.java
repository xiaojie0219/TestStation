package com.loong.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

public class PropertiesUtil {
	/**
	 * 根据key读取value
	 * 
	 * @param fileName
	 *            属性文件名（文件应放在src目录下，如放在src下的包里，则应加上包的路径）
	 * @param key
	 *            属性名
	 * @return：属性值
	 */
	public String read(String fileName, String key) {
		Properties pp = new Properties();
		try {
			pp.load(this.getClass().getClassLoader()
					.getResourceAsStream(fileName));
			String n = pp.getProperty(key);
			System.out.println(n);
			return n;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 读取properties的全部信息，信息存放在枚举类型中
	 * 
	 * @param fileName
	 *            属性文件名（文件应放在src目录下，如放在src下的包里，则应加上包的路径）
	 */
	public void readProperties(String fileName) {
		Properties props = new Properties();
		try {
			props.load(this.getClass().getClassLoader()
					.getResourceAsStream(fileName));
			Enumeration<?> en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				System.out.println(key + ":" + Property);

			}
			// stringPropertyNames返回属性文件中的所有键值到set中
			Set<String> s = props.stringPropertyNames();
			for (int i = 0; i < s.size(); i++) {
				System.out.println("set的第" + i + "个元素是：" + s.toArray()[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		PropertiesUtil sp = new PropertiesUtil();
		sp.read("config.properties", "agentSecretKey");
		sp.readProperties("config.properties");
	}
}

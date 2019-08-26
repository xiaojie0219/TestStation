package com.loong.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Security
{
	private final static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	private static String bytesToHex(byte[] bytes)
	{
		StringBuffer sb = new StringBuffer();
		int t;
		for (int i = 0; i < 16; i++)
		{
			t = bytes[i];
			if (t < 0)
				t += 256;
			sb.append(hexDigits[(t >>> 4)]);
			sb.append(hexDigits[(t % 16)]);
		}
		return sb.toString();
	}

	public static String md5To24(String input) throws Exception
	{
		String tmp32 = md5(input);
		String tmp16 = md5To16(input);	
		String tmp = "";
		for(int i = 1; i <= 32; i++ )
		{
			if(i%4 == 0)
			{
				tmp = tmp + tmp32.substring(i-1, i);
			}			
		}
		return tmp16 + tmp;
	}
	
	public static String md5To24Api(String input) throws Exception
	{
		String tmp32 = md5(input);
		String tmp16 = md5To16(input);	
		String tmp = "";
		for(int i = 1; i <= 32; i++ )
		{
			if(i%4 == 0)
			{
				tmp = tmp + tmp32.substring(i-1, i);
			}			
		}
		return tmp + tmp16;
	}
	
	public static String md5To16(String input) throws Exception
	{
		return code(input, 16);
	}
	
	public static String md5(String input) throws Exception
	{
		return code(input, 32);
	}
	

	public static String code(	String input,
								int bit) throws Exception
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance(System.getProperty("MD5.algorithm", "MD5"));
			if (bit == 16)
				return bytesToHex(md.digest(input.getBytes("utf-8"))).substring(8, 24);
			return bytesToHex(md.digest(input.getBytes("utf-8")));
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			throw new Exception("Could not found MD5 algorithm.", e);
		}
	}

	public static String md5_3(String b) throws Exception
	{
		MessageDigest md = MessageDigest.getInstance(System.getProperty("MD5.algorithm", "MD5"));
		byte[] a = md.digest(b.getBytes());
		a = md.digest(a);
		a = md.digest(a);

		return bytesToHex(a);
	}
	
	public static void main(String[] arg)
	{
		try
		{
			String s = "0000120131206sino175016";
			System.out.println(MD5Security.md5(s));
			System.out.println(MD5Security.md5To16(s));
			System.out.println(MD5Security.md5To24(s));		
			String insertTime = "20131206175016";
			System.out.println(insertTime.substring(0, 8) + "sino" + insertTime.substring(8, insertTime.length()));
			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
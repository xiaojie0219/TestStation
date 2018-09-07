package com.loong.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.loong.server.HttpServerDemo;
import com.loong.util.PropertiesUtil;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jp;
	private String port;

	public MainFrame() {
		super("综合接入回调通知测试桩");
		jp = new JPanel();
		lanchPanel();
		add(jp);
		setBackground(Color.gray);
		setBounds(500, 400, 400, 150);
		setVisible(true);
		addWindowListener(new WindowsMonitor());

	}

	private void lanchPanel() {
		PropertiesUtil pu = new PropertiesUtil();
		port = pu.read("config.properties", "port");
		String agentSecretKey = pu.read("config.properties", "agentSecretKey");

		JLabel label1 = new JLabel("端口：" + port);
		JLabel label2 = new JLabel("密钥：" + agentSecretKey);
		JLabel label3 = new JLabel("注意：请核对启动端口和密钥，如有误请修改jar包配置文件");
		//label3.setFont(new java.awt.Font("Dialog",1,14));
		label3.setForeground(Color.red);
		
		jp.add(label1);
		jp.add(label2);
		jp.add(label3);
		jp.setBorder(new EmptyBorder(10, 10, 10, 10));//设置上左下右逆时针方法依次指定四个方向距离边框的空白像素
		jp.setLayout(new GridLayout(3, 0, 5, 5));
		jp.validate();

	}

	class WindowsMonitor extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent arg0) {
			System.out.println("window is exiting...");
			/*
			 * try { Runtime runtime = Runtime.getRuntime(); //查找进程号 Process p =
			 * runtime.exec("cmd /c netstat -ano | findstr \""+port+"\"");
			 * System.out.println("端口："+ port + "；进程号：" + p.getInputStream());
			 * runtime.exec("taskkill /F /pid "+ p +""); } catch (IOException e)
			 * { // TODO Auto-generated catch block e.printStackTrace(); }
			 */
			System.exit(0);
		}
	}

	public static void main(String[] args) throws Exception {

		// 使用一个标准的主函数，即SwingUtilities的函数invokeLater来保证GUI在事件分发线程中创建
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
			}
		});
		HttpServerDemo.startHttpServer();
	}
}

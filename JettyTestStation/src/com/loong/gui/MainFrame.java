package com.loong.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.loong.server.HttpServerDemo;
import com.loong.util.PropertiesUtil;


public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel jp;
	private String port; 
    
    public MainFrame(){
    	super("综合接入回调通知测试桩");
    	jp = new JPanel();
    	lanchPanel();
        add(jp);
        setBackground(Color.gray);
        setBounds(500, 400, 300, 150);
		setVisible(true);
        addWindowListener(new WindowsMonitor());
    }
    
    private void lanchPanel(){
    	PropertiesUtil pu = new PropertiesUtil();
    	port = pu.read("config.properties", "port");
    	String agentSecretKey = pu.read("config.properties", "agentSecretKey");
    	
        JLabel label1 = new JLabel("端口：" + port);
        JLabel label2 = new JLabel("密钥：" + agentSecretKey);
    	
        JButton button1 = new JButton("启动");
        button1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					HttpServerDemo.startHttpServer();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
        	
        });
        
    	jp.add(label1);
        jp.add(label2);
        jp.add(button1);
        jp.setBorder(new EmptyBorder(10,10,10,10));
        jp.setLayout(new GridLayout(3,0,5,5));
        jp.validate();
        
    }
    
    class WindowsMonitor extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent arg0) {
			System.out.println("window is exiting...");
			/*
			try {
				Runtime runtime = Runtime.getRuntime();
				//查找进程号
	            Process p = runtime.exec("cmd /c netstat -ano | findstr \""+port+"\"");
	            System.out.println("端口："+ port + "；进程号：" + p.getInputStream());
	            runtime.exec("taskkill /F /pid "+ p +"");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			System.exit(0);
		}
	}
    
	public static void main(String[] args) {
		/*
		// 使用一个标准的主函数，即SwingUtilities的函数invokeLater来保证GUI在事件分发线程中创建
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new MainFrame();
				}
			});*/
		new MainFrame();
	}
}

/**
 * @author Laud
 * @version 1.0.0
 * @since 2007-12-15
 */

package com.laud.major.traffic;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class TrafficFrame extends JFrame {
	
	/**
	 * 构造函数
	 * @param title 窗口标题
	 */
	public TrafficFrame(String title) {
		logger.debug("进入TrafficFrame(String title)构造函数. - " + TrafficFrame.class.getName());
		setTitle(title);
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	protected void createUI() {
		setBackground(Color.white);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setResizable(false);
		setVisible(true);
	}
	
	public void center() {
		SystemProperties sp = SystemProperties.getInstance();
		setLocation((sp.getScreenWidth() - getWidth()) / 2, (sp.getScreenHeight() - getHeight()) / 2);
	}
	
	static {
		DOMConfigurator.configure(TrafficDemo.class.getResource("/log.xml"));
	}
	
	public static final int DEFAULT_WIDTH = 1024;
	public static final int DEFAULT_HEIGHT = 768;
	
	private static Logger logger = Logger.getLogger(TrafficFrame.class);
}

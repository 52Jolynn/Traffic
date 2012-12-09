/**
 * @author Laud
 * @version 1.0.0
 * @since 2007-12-15
 */

package com.laud.major.traffic;

import java.awt.*;
import java.util.*;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class TrafficLight extends Observable implements Light, Runnable {

	public TrafficLight(Graphics2D g2) {
		logger.debug("进入TrafficLight(Graphics2D g2)构造函数, 构建TrafficLight实例. - " + TrafficLight.class.getName());
		this.g2 = g2;
		lgtVerticalRed = new ColorLight(g2, CLR_RED_ON);
		lgtVerticalGreen = new ColorLight(g2, CLR_GREEN_OFF);
		lgtHorizontalRed = new ColorLight(g2, CLR_RED_OFF);
		lgtHorizontalGreen = new ColorLight(g2, CLR_GREEN_ON);
	}

	public void drawLight() {
		drawLight(g2);
	}

	/**
	 * 关闭交通灯
	 */
	public void off() {
		lgtVerticalRed.off();
		lgtVerticalGreen.off();
		lgtHorizontalRed.off();
		lgtHorizontalGreen.off();
	}

	/**
	 * 打开交通灯
	 */
	public void on() {
		lgtVerticalRed.on();
		lgtVerticalGreen.on();
		lgtHorizontalRed.on();
		lgtHorizontalGreen.on();
	}

	public void run() {
		logger.debug("TrafficLight线程启动,现已进入其run方法. - " + TrafficLight.class.getName());
		while(true) {
			changeLight();
			
			setChanged();
			notifyObservers(movable);
			
			try {
				logger.debug("Sleep fot 5 seconds. - " + TrafficLight.class.getName());
				Thread.sleep(5000);		
			} catch (InterruptedException e) {
				logger.warn(e.getMessage() + " - " + TrafficLight.class.getName());
			}
			
			changeLight();
	
			setChanged();
			notifyObservers(movable);
			
			try {
				logger.debug("Sleep fot 5 seconds. - " + TrafficLight.class.getName());
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				logger.warn(e.getMessage() + " - " + TrafficLight.class.getName());
			}
		}
	}
	
	protected void changeLight() {
		if (verticalRedOn) {
			logger.debug("南北红灯状态: 红灯亮 - " + TrafficLight.class.getName());
			logger.debug("东西红灯状态: 红灯" + ((horizontalRedOn) ? "亮" : "熄") + " - " + TrafficLight.class.getName());
			lgtVerticalRed.setColor(CLR_WHITE);
			lgtVerticalRed.drawLight();
			lgtVerticalRed.setColor(CLR_RED_ON);
			lgtVerticalRed.drawLight();
			lgtHorizontalRed.setColor(CLR_WHITE);
			lgtHorizontalRed.drawLight();
			lgtHorizontalRed.setColor(CLR_RED_OFF);
			lgtHorizontalRed.drawLight();
			verticalRedOn = false;
			horizontalRedOn = true;	
		}
		else {
			logger.debug("南北红灯状态: 红灯熄 - " + TrafficLight.class.getName());
			logger.debug("东西红灯状态: 红灯" + ((horizontalRedOn) ? "亮" : "熄") + " - " + TrafficLight.class.getName());
			lgtVerticalRed.setColor(CLR_WHITE);
			lgtVerticalRed.drawLight();
			lgtVerticalRed.setColor(CLR_RED_OFF);
			lgtVerticalRed.drawLight();
			lgtHorizontalRed.setColor(CLR_WHITE);
			lgtHorizontalRed.drawLight();
			lgtHorizontalRed.setColor(CLR_RED_ON);
			lgtHorizontalRed.drawLight();
			verticalRedOn = true;
			horizontalRedOn = false;
		}
		
		if (verticalGreenOn) {
			logger.debug("南北绿灯状态: 绿灯亮 - " + TrafficLight.class.getName());
			logger.debug("东西绿灯状态: 绿灯" + ((horizontalGreenOn) ? "亮" : "熄") + " - " + TrafficLight.class.getName());
			lgtVerticalGreen.setColor(CLR_WHITE);
			lgtVerticalGreen.drawLight();
			lgtVerticalGreen.setColor(CLR_GREEN_ON);
			lgtVerticalGreen.drawLight();
			lgtHorizontalGreen.setColor(CLR_WHITE);
			lgtHorizontalGreen.drawLight();
			lgtHorizontalGreen.setColor(CLR_GREEN_OFF);
			lgtHorizontalGreen.drawLight();
			verticalGreenOn = false;
			horizontalGreenOn = true;
			movable = true; //true南北可通行
		}
		else {
			logger.debug("南北绿灯状态: 绿灯熄 - " + TrafficLight.class.getName());
			logger.debug("东西绿灯状态: 绿灯" + ((horizontalGreenOn) ? "亮" : "熄") + " - " + TrafficLight.class.getName());
			lgtVerticalGreen.setColor(CLR_WHITE);
			lgtVerticalGreen.drawLight();
			lgtVerticalGreen.setColor(CLR_GREEN_OFF);
			lgtVerticalGreen.drawLight();
			lgtHorizontalGreen.setColor(CLR_WHITE);
			lgtHorizontalGreen.drawLight();
			lgtHorizontalGreen.setColor(CLR_GREEN_ON);
			lgtHorizontalGreen.drawLight();
			verticalGreenOn = true;
			horizontalGreenOn = false;
			movable = false; //false东西可通行
		}
	}
	
	public void drawLight(Graphics2D g2) {
		logger.debug("drawLight(Graphics2D g2), 南北红灯. - " + TrafficLight.class.getName());
		lgtVerticalRed.drawLight(verticalRedX, verticalRedY, width, height);
		logger.debug("drawLight(Graphics2D g2), 南北绿灯. - " + TrafficLight.class.getName());
		lgtVerticalGreen.drawLight(verticalGreenX, verticalGreenY, width, height);
		logger.debug("drawLight(Graphics2D g2), 东西红灯. - " + TrafficLight.class.getName());
		lgtHorizontalRed.drawLight(horizontalRedX, horizontalRedY, width, height);
		logger.debug("drawLight(Graphics2D g2), 东西绿灯. - " + TrafficLight.class.getName());
		lgtHorizontalGreen.drawLight(horizontalGreenX, horizontalGreenY, width, height);
	}
	
	/**
	 * 得到南北红灯实例
	 * @return ColorLight
	 */
	public ColorLight getLgtVerticalRed() {
		return lgtVerticalRed;
	}

	/**
	 * 得到南北绿灯实例
	 * @return
	 */
	public ColorLight getLgtVerticalGreen() {
		return lgtVerticalGreen;
	}

	/**
	 * 得到东西红灯实例
	 * @return ColorLight
	 */
	public ColorLight getLgtHorizontalRed() {
		return lgtHorizontalRed;
	}

	/**
	 * 得到东西绿灯实例
	 * @return ColorLight
	 */
	public ColorLight getLgtHorizontalGreen() {
		return lgtHorizontalGreen;
	}

	/**
	 * 得到Graphics
	 * @return
	 */
	public Graphics2D getG2() {
		return g2;
	}

	/**
	 * 设置Graphics
	 * @param g2
	 */
	public void setG2(Graphics2D g2) {
		this.g2 = g2;
	}

	/**
	 * 得到南北红灯横坐标
	 * @return double
	 */
	public double getVerticalRedX() {
		return verticalRedX;
	}

	/**
	 * 设置南北红灯横坐标
	 * @param verticalRedX
	 */
	public void setVerticalRedX(double verticalRedX) {
		this.verticalRedX = verticalRedX;
	}

	/**
	 * 得到南北红灯纵坐标
	 * @return
	 */
	public double getVerticalRedY() {
		return verticalRedY;
	}

	/**
	 * 设置南北红灯纵坐标
	 * @param verticalRedY
	 */
	public void setVerticalRedY(double verticalRedY) {
		this.verticalRedY = verticalRedY;
	}

	/**
	 * 得到南北绿灯横坐标
	 * @return
	 */
	public double getVerticalGreenX() {
		return verticalGreenX;
	}

	/**
	 * 设置南北绿灯横坐标
	 * @param verticalGreenX
	 */
	public void setVerticalGreenX(double verticalGreenX) {
		this.verticalGreenX = verticalGreenX;
	}

	/**
	 * 得到南北绿灯纵坐标
	 * @return
	 */
	public double getVerticalGreenY() {
		return verticalGreenY;
	}

	/**
	 * 设置南北绿灯纵坐标
	 * @param verticalGreenY
	 */
	public void setVerticalGreenY(double verticalGreenY) {
		this.verticalGreenY = verticalGreenY;
	}

	/**
	 * 得到东西红灯横坐标
	 * @return
	 */
	public double getHorizontalRedX() {
		return horizontalRedX;
	}

	/**
	 * 设置东西红灯横坐标
	 * @param horizontalRedX
	 */
	public void setHorizontalRedX(double horizontalRedX) {
		this.horizontalRedX = horizontalRedX;
	}

	/**
	 * 得到东西红灯纵坐标
	 * @return
	 */
	public double getHorizontalRedY() {
		return horizontalRedY;
	}

	/**
	 * 设置东西红灯纵坐标
	 * @param horizontalRedY
	 */
	public void setHorizontalRedY(double horizontalRedY) {
		this.horizontalRedY = horizontalRedY;
	}

	/**
	 * 得到东西绿灯横坐标
	 * @return
	 */
	public double getHorizontalGreenX() {
		return horizontalGreenX;
	}

	/**
	 * 设置东西绿灯横坐标
	 * @param horizontalGreenX
	 */
	public void setHorizontalGreenX(double horizontalGreenX) {
		this.horizontalGreenX = horizontalGreenX;
	}

	/**
	 * 得到东西绿灯纵坐标
	 * @return
	 */
	public double getHorizontalGreenY() {
		return horizontalGreenY;
	}

	/**
	 * 设置东西绿灯纵坐标
	 * @param horizontalGreenY
	 */
	public void setHorizontalGreenY(double horizontalGreenY) {
		this.horizontalGreenY = horizontalGreenY;
	}

	/**通过画矩形的内接椭圆完成
	 * 得到灯的长度
	 * @return double
	 */
	public double getWidth() {
		return width;
	}

	/**通过画矩形的内接椭圆完成
	 * 设置灯的长度
	 * @param width
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**通过画矩形的内接椭圆完成
	 * 得到灯的高度
	 * @return
	 */
	public double getHeight() {
		return height;
	}

	/**通过画矩形的内接椭圆完成
	 * 设置灯的高度
	 * @param height
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	
	static {
		DOMConfigurator.configure(TrafficDemo.class.getResource("/log.xml"));
	}
	
	public static final Color CLR_RED_ON = new Color(255, 0, 0);
	public static final Color CLR_GREEN_ON = new Color(0, 255, 0);
	public static final Color CLR_RED_OFF = new Color(150, 0, 0);
	public static final Color CLR_GREEN_OFF = new Color(0, 150, 0);
	public static final Color CLR_WHITE = new Color(255, 255, 255);
	
	private ColorLight lgtVerticalRed = null; //南北方向的交通灯-红灯
	private ColorLight lgtVerticalGreen = null; //南北方向的交通灯-绿灯
	private ColorLight lgtHorizontalRed = null; //东西方向的交通灯-红灯
	private ColorLight lgtHorizontalGreen = null; //东西方向的交通灯-绿灯
	private Graphics2D g2 = null;
	
	private double verticalRedX = 880d;
	private double verticalRedY = 660d;
	private double verticalGreenX = 950d;
	private double verticalGreenY = 660d;
	private double horizontalRedX = 950d;
	private double horizontalRedY = 30d;
	private double horizontalGreenX = 950d;
	private double horizontalGreenY = 100d;
	private double width = 50d;
	private double height = 50d;
	
	private boolean verticalRedOn = true; //南北方向红灯状态
	private boolean verticalGreenOn = false; //南北方向绿灯状态
	private boolean horizontalRedOn = false; //东西方向红灯状态
	private boolean horizontalGreenOn = true; //东西方向绿灯状态
	private boolean movable = true; //true时南北可通行，否则东西可通行
	
	private static Logger logger = Logger.getLogger(TrafficLight.class); 
}

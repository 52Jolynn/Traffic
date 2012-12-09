/**
 * @author Laud
 * @version 1.0.0
 * @since 2007-12-15
 */

package com.laud.major.traffic;

import java.awt.*;
import java.awt.geom.*;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class ColorLight implements Light {

	/**
	 * 构造函数, 在使用drawLight之前,必须调用setColor方法，设置灯的颜色
	 */
	public ColorLight() {}
	
	/**
	 * 构造函数, 传入的参数指定灯的颜色
	 * @param color
	 */
	public ColorLight(Graphics2D g2, Color color) {
		logger.debug("进入ColorLight(Color color)构造函数. Color: " + color + " - " + ColorLight.class.getName());
		this.g2 = g2;
		this.color = color;
	}
	
	/**
	 * 画灯
	 */
	public void drawLight() {
		drawLight(x, y, width, height);
	}

	/**
	 * 关灯
	 */
	public void off() {
		STATE = OFF;
	}

	/**
	 * 开灯
	 */
	public void on() {
		STATE = ON;
	}

	/**
	 * 得到灯的颜色
	 * @return Color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * 设置灯的颜色
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * 得到灯的横坐标
	 * @return double
	 */
	public double getX() {
		return x;
	}

	/**
	 * 设置灯的横坐标
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * 得到灯的纵坐标
	 * @return double
	 */
	public double getY() {
		return y;
	}

	/**
	 * 设置灯的纵坐标
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * 得到灯的长度
	 * @return double
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * 设置灯的长度
	 * @param width
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * 得到灯的高度
	 * @return double
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * 设置灯的高度
	 * @param height
	 */
	public void setHeight(double height) {
		this.height = height;
	}

	/**
	 * 得到灯的状态
	 * @return int
	 * ColorLight.ON=0
	 * ColorLight.OFF=1
	 */
	public int getSTATE() {
		return STATE;
	}

	/**
	 * 设置灯的状态
	 * @param state
	 * ColorLight.ON=0
	 * ColorLight.OFF=0
	 */
	public void setSTATE(int state) {
		STATE = state;
	}
	
	/**
	 * 得到画图用的Graphics
	 * @return Graphics2D
	 */
	public Graphics2D getG2() {
		return g2;
	}

	/**
	 * 设置画图用的Graphics
	 * @param g2
	 */
	public void setG2(Graphics2D g2) {
		this.g2 = g2;
	}

	/**
	 * 画灯, 通过画矩形的内接椭圆完成
	 * @param g2 画灯使用的Graphics
	 * @param x 灯的横坐标
	 * @param y 灯的纵坐标
	 * @param width 灯的长度
	 * @param height 灯的高度
	 */
	public void drawLight(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		g2.setPaint(color);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.fill(new Ellipse2D.Double(x, y, width, height));
	}
	
	/**
	 * 画灯, 通过画矩形的内接椭圆完成
	 * @param g2 画灯使用的Graphics
	 * @param rec 矩形参照物
	 */
	public void drawLight(Rectangle2D rec) {
		this.x = rec.getX();
		this.y = rec.getY();
		this.width = rec.getWidth();
		this.height = rec.getHeight();
		g2.setPaint(color);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.fill(new Ellipse2D.Double(x, y, width, height));
	}
	
	static {
		DOMConfigurator.configure(TrafficDemo.class.getResource("/log.xml"));
	}

	private Graphics2D g2 = null; //画灯使用的Graphics
	private Color color = null; //灯的颜色
	private double x = 0d; //灯的横坐标
	private double y = 0d; //灯的纵坐标
	private double width = 0d; //灯的长度
	private double height = 0d; //灯的高度
	public static final int ON = 0; //灯开
	public static final int OFF = 1; //灯关
	private int STATE = OFF; //灯的状态
	
	private static Logger logger = Logger.getLogger(ColorLight.class);
}

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

public class TrafficDemo extends Canvas {
	/**
	 * 
	 */
	public TrafficDemo() {
		setBackground(Color.white);
	}
	
	@Override
	public void paint(Graphics g) {
		logger.debug("进入Traffic的paint方法." + TrafficDemo.class.getName());
		if (g != null) {
			light.drawLight();
			vCar110.drawCar();
			vCar120.drawCar();
			hCar119.drawCar();
			hCar999.drawCar();
			drawBackground();
		}
	}
	
	protected void drawBackground() {
		g2.setPaint(Color.black);
		//西边
		g2.drawLine(0, verticalUpEdge, horizontalLefEdge, verticalUpEdge);
		g2.drawLine(0, verticalDownEdge, horizontalLefEdge, verticalDownEdge);
		//东边
		g2.drawLine(horizontalRightEdge, verticalUpEdge, 1024, verticalUpEdge);
		g2.drawLine(horizontalRightEdge, verticalDownEdge, 1024, verticalDownEdge);
		//北边
		g2.drawLine(horizontalLefEdge, verticalUpEdge, 380, 0);
		g2.drawLine(horizontalRightEdge, verticalUpEdge, 630, 0);
		//南边
		g2.drawLine(horizontalLefEdge, verticalDownEdge, 314, 768);
		g2.drawLine(horizontalRightEdge, verticalDownEdge, 710, 768);
		//交通灯支持物
		g2.draw(new Rectangle(680, 146, 40, 100));
		g2.draw(new Rectangle(240, 507, 100, 40));
	}
	
	public static boolean tellVerticalCar() {
		boolean result = false;
		if (hCar999 instanceof HorizontalCar) {
			HorizontalCar car = (HorizontalCar)hCar999;	
			
			//判断路中间是否有车，并将结果告知南北方向的车
			if ((car.getX() + car.getWidth()) > TrafficDemo.horizontalLefEdge && car.getX() < TrafficDemo.horizontalRightEdge) {
				logger.debug("我正在通过东西路中间, 我的坐标:" + car.getX() + " - " + TrafficDemo.class.getName());
				result = true;
			}
			else
				result = false;
		}
		else if (hCar119 instanceof HorizontalCar) {
			HorizontalCar car = (HorizontalCar)hCar119;
			
			//判断路中间是否有车，并将结果告知南北方向的车
			if ((car.getX() + car.getWidth()) > TrafficDemo.horizontalLefEdge && car.getX() < TrafficDemo.horizontalRightEdge) {
				logger.debug("我正在通过东西路中间, 我的坐标:" + car.getX() + " - " + TrafficDemo.class.getName());
				result = true;
			}
			else
				result = false;
		}
		
		return result;
	}
	
	public static boolean tellHorizontalCar() {
		boolean result = false;
		if (vCar110 instanceof VerticalCar) {
			VerticalCar car = (VerticalCar)vCar110;
			
			//判断路中间是否有车，将结果告知东西方向的车
			if ((car.getY() + car.getHeight()) > TrafficDemo.verticalUpEdge && car.getY() < TrafficDemo.verticalDownEdge) {
				logger.debug("我正在通过南北路中间, 我的坐标:" + car.getY() + " - " + TrafficDemo.class.getName());
				result = true;	
			}
			else {
				result = false;
			}
		}
		else if (vCar120 instanceof VerticalCar) {
			VerticalCar car = (VerticalCar)vCar120;
			
			//判断路中间是否有车，将结果告知东西方向的车
			if ((car.getY() + car.getHeight()) > TrafficDemo.verticalUpEdge && car.getY() < TrafficDemo.verticalDownEdge) {
				logger.debug("我正在通过南北路中间, 我的坐标:" + car.getY() + " - " + TrafficDemo.class.getName());
				result = true;	
			}
			else {
				result = false;
			}
		}
		
		return result;
	}
	
	static {
		DOMConfigurator.configure(TrafficDemo.class.getResource("/log.xml"));
	}
	
	private static Graphics2D g2 = null; //画图使用的Graphics
	private static Car vCar110 = null; //110车
	private static Car vCar120 = null; //120车
	private static Car hCar119 = null; //119车
	private static Car hCar999 = null; //999车
	private static TrafficLight light = null; //交通灯
	
	public static final int verticalCenter = 379; //南北路中心
	public static final int horizontalCenter = 510; //东西路中心
	public static final int verticalUpEdge = 254; //南北路北路口边界
	public static final int verticalDownEdge = 504; //南北路南路口边界
	public static final int horizontalLefEdge = 350; //东西路西路口边界
	public static final int horizontalRightEdge = 670; //东西路东路口边界
	
	private static Logger logger = Logger.getLogger(TrafficDemo.class);
	
	/**
	 * 主函数
	 * @param args
	 */
	public static void main(String[] args) {
		TrafficFrame tf = new TrafficFrame("Traffic Demo");
		TrafficDemo td = new TrafficDemo();
		tf.add(td);
		tf.createUI();
		tf.center();
		
		g2 = (Graphics2D)td.getGraphics();
		
		//车110
		vCar110 = new VerticalCar(g2, td);
		
		//设置车110的属性
		if (vCar110 instanceof VerticalCar) {
			VerticalCar car = (VerticalCar)vCar110;
			car.setX(508);
			car.setY(- car.getHeight() / 2);
			car.setSpeed(60);
			car.setNorthToSouth(true);
		}
		
		//车120
		vCar120 = new VerticalCar(g2, td);
		
		//设置车120的属性
		if (vCar120 instanceof VerticalCar) {
			VerticalCar car = (VerticalCar)vCar120;
			car.setX(386);
			car.setY(TrafficFrame.DEFAULT_HEIGHT + car.getHeight() / 2);
			car.setSpeed(30);
			car.setNorthToSouth(false);
		}
		
		//车119
		hCar119 = new HorizontalCar(g2, td);
		
		//设置车119的属性
		if (hCar119 instanceof HorizontalCar) {
			HorizontalCar car = (HorizontalCar)hCar119;
			car.setX(- car.getWidth() / 2);
			car.setY(258);
			car.setSpeed(60);
			car.setLeftToRight(true);
		}
		
		//车999
		hCar999 = new HorizontalCar(g2, td);
		
		//设置车999的属性
		if (hCar999 instanceof HorizontalCar) {
			HorizontalCar car = (HorizontalCar)hCar999;
			car.setX(TrafficFrame.DEFAULT_WIDTH + car.getWidth() / 2);
			car.setY(382);
			car.setSpeed(30);
			car.setLeftToRight(false);
		}
		
		//交通灯
		light = new TrafficLight(g2);
		
		//设置交通灯的位置
		light.setWidth(36);
		light.setHeight(36);
		light.setVerticalRedX(683);
		light.setVerticalRedY(156);
		light.setVerticalGreenX(683);
		light.setVerticalGreenY(206);
		
		light.setHorizontalRedX(250);
		light.setHorizontalRedY(510);
		light.setHorizontalGreenX(300);
		light.setHorizontalGreenY(510);
		
		light.addObserver((Observer)vCar110);
		light.addObserver((Observer)vCar120);
		light.addObserver((Observer)hCar119);
		light.addObserver((Observer)hCar999);
		
		Thread thrVCar110 = new Thread((Runnable)vCar110, "car110");
		thrVCar110.start();
		
		Thread thrVCar120 = new Thread((Runnable)vCar120, "car120");
		thrVCar120.start();
		
		Thread thrHCar119 = new Thread((Runnable)hCar119, "car119");
		thrHCar119.start();
		
		Thread thrHCar999 = new Thread((Runnable)hCar999, "car999");
		thrHCar999.start();
		
		Thread thrLight = new Thread((Runnable)light);
		thrLight.start();
	}
}

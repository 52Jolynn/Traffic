/**
 * @author Laud
 * @version 1.0.0
 * @since 2007-12-15
 */

package com.laud.major.traffic;

import java.util.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class HorizontalCar implements Car, Runnable, Observer {

	public HorizontalCar(Graphics2D g2, Canvas canvas) {
		this.g2 = g2;
		buffer = canvas.createImage(width, 2 * height);
		bufferG2 = (Graphics2D)buffer.getGraphics();
		background = canvas.getBackground();
		carColor = color[random.nextInt(color.length)];
	}

	public void update(Observable o, Object arg) {
		movable = (!(Boolean)arg); //arg为真时南北可通行,假时东西可通行
	}

	public void drawCar() {
		move();
	}

	/**
	 * 画车
	 * @param g2
	 */
	public void drawCar(Graphics2D g2) {
		g2.fill(new Ellipse2D.Double(30, 0, 60, 60));
		g2.fill(new Rectangle(0, 30, 120, 60));
		g2.fill(new Ellipse2D.Double(15, 85, 30, 30));
		g2.fill(new Ellipse2D.Double(75, 85, 30, 30));
	}

	public void move() {	
		logger.debug("车的横坐标为: " + x + " - " + HorizontalCar.class.getName());
		//从东向西行驶
		if (leftToRight) {
			if (x >= TrafficFrame.DEFAULT_WIDTH) {
				bufferG2.setPaint(background);
				drawCar(bufferG2);
				g2.drawImage(buffer, (x - speed), y, null);
				x = - (width / 2);
			}
			bufferG2.setPaint(background);
			drawCar(bufferG2);
			g2.drawImage(buffer, x, y, null);
			bufferG2.setPaint(carColor);
			drawCar(bufferG2);
			x += speed;
			g2.drawImage(buffer, x, y, null);
		}
		//从西向东行驶
		else {
			if ((x + width) < 0) {
				bufferG2.setPaint(background);
				drawCar(bufferG2);
				g2.drawImage(buffer, (x + speed), y, null);
				x = TrafficFrame.DEFAULT_WIDTH + width / 2;
			}
			bufferG2.setPaint(background);
			drawCar(bufferG2);
			g2.drawImage(buffer, x, y, null);
			bufferG2.setPaint(carColor);
			drawCar(bufferG2);
			x -= speed;
			g2.drawImage(buffer, x, y, null);
		}
	}

	public void speedDown() {
		speed -= SPEEDINCREMENT;	
	}

	public void speedUp() {
		speed += SPEEDINCREMENT;
	}

	public void stop() {
		//从西向东行驶
		if (leftToRight) {
			int distance = TrafficDemo.horizontalLefEdge - x - width;
			if (x > TrafficDemo.horizontalLefEdge || distance >= width) {
				move();
			}
			else {
				bufferG2.setPaint(background);
				drawCar(bufferG2);
				g2.drawImage(buffer, x, y, null);
				bufferG2.setPaint(carColor);
				drawCar(bufferG2);
				x = TrafficDemo.horizontalLefEdge - width;
				g2.drawImage(buffer, x, y, null);
			}
		}
		//从东向西行驶
		else {
			int distance = x - TrafficDemo.horizontalRightEdge;
			if (x < TrafficDemo.horizontalRightEdge || distance >= width) {
				move();
			}
			else {
				bufferG2.setPaint(background);
				drawCar(bufferG2);
				g2.drawImage(buffer, x, y, null);
				bufferG2.setPaint(carColor);
				drawCar(bufferG2);
				x = TrafficDemo.horizontalRightEdge;
				g2.drawImage(buffer, x, y, null);
			}
		}
	}

	public void run() {
		if (g2 != null) {
			logger.debug("HorizontalCar线程启动, 现已进入其run方法. - " + HorizontalCar.class.getName());
			while (true) {
				logger.debug("东西方向车辆" + (movable ? "可通行" : "不可通行") + " - " + HorizontalCar.class.getName());
				if (movable) {
					if (TrafficDemo.tellHorizontalCar()) {
						logger.debug("东西路中间有车, 我不能通行. - " + VerticalCar.class.getName());
						try {
							logger.debug("Sleep for 0.1 second. - " + VerticalCar.class.getName());
							Thread.sleep(100);
							continue;
						} catch (InterruptedException e) {
							logger.warn(e.getMessage() + " - " + VerticalCar.class.getName());
						}
					}
					else {
						logger.debug("东西方向车辆前进一次. - " + HorizontalCar.class.getName());
						move();
					}
				}
				else {
					stop();
				}
				try {
					logger.debug("Sleep for 0.1 seconds. - " + HorizontalCar.class.getName());
					Thread.sleep(100);
				} catch (InterruptedException e) {
					logger.warn(e.getMessage() + " - " + HorizontalCar.class.getName());
				}
			}
		}
	}

	/**
	 * 得到车的加速度
	 * @return int
	 */
	public int getSPEEDINCREMENT() {
		return SPEEDINCREMENT;
	}

	/**
	 * 设置车的加速度
	 * @param speedincrement
	 */
	public void setSPEEDINCREMENT(int speedincrement) {
		SPEEDINCREMENT = speedincrement;
	}

	/**
	 * 得到车的速度
	 * @return int
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * 设置车的速度
	 * @param speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * 得到车的横坐标
	 * @return int
	 */
	public int getX() {
		return x;
	}

	/**
	 * 设置车的横坐标
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * 得到车的纵坐标
	 * @return int
	 */
	public int getY() {
		return y;
	}

	/**
	 * 设置车的纵坐标
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * 得到车的长度
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 得到车的高度
	 * @return
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * 是否从西向东行驶
	 * @return boolean
	 */
	public boolean isLeftToRight() {
		return leftToRight;
	}

	/**
	 * 设置从西向东或从东向西行驶, true为从西向东行驶, false为从东向西行驶
	 * @param leftToRight
	 */
	public void setLeftToRight(boolean leftToRight) {
		this.leftToRight = leftToRight;
	}

	static {
		DOMConfigurator.configure(TrafficDemo.class.getResource("/log.xml"));
	}

	private boolean movable = false; //为真时可通行;
	private Graphics2D g2 = null;
	private int SPEEDINCREMENT = 1;
	private int speed = 64;
	private int width = 120;
	private int height = 60;
	private int x = - (width / 2);
	private int y = 360;
	private boolean leftToRight = true; //指示车的方向,true为从西向东行驶,false为从东向西行驶.
	private Color carColor = null;
	private Image buffer = null; //用于双缓冲
	private Graphics2D bufferG2 = null;
	private Color background = null; //画布背景
	private Random random = new Random(System.currentTimeMillis());
	private final Color[] color = {Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray, Color.green, 
			Color.lightGray, Color.magenta, Color.orange, Color.pink, Color.red, Color.yellow};

	private static Logger logger = Logger.getLogger(HorizontalCar.class);
}

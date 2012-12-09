/**
 * @author Laud
 * @version 1.0.0
 * @since 2007-12-15
 */

package com.laud.major.traffic;

import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class VerticalCar implements Car, Runnable, Observer {

	public VerticalCar(Graphics2D g2, Canvas canvas) {
		this.g2 = g2;
		buffer = canvas.createImage(2 * width, height);
		bufferG2 = (Graphics2D)buffer.getGraphics();
		background = canvas.getBackground();
		carColor = color[random.nextInt(color.length)];
	}

	public void update(Observable o, Object arg) {
		movable = (Boolean)arg; //为真时可通行
	}

	/**
	 * 画车
	 */
	public void drawCar() {
		move();
	}

	/**
	 * 画车
	 * @param g2
	 */
	public void drawCar(Graphics2D g2) {
		g2.fill(new Ellipse2D.Double(30, 0, 60, 30));
		g2.fill(new Rectangle(0, 15, 120, 30));
		g2.fill(new Ellipse2D.Double(20, 42, 30, 15));
		g2.fill(new Ellipse2D.Double(70, 42, 30, 15));
	}

	/**
	 * 前进
	 */
	public void move() {
		logger.debug("车的纵坐标为: " + y + " - " + VerticalCar.class.getName());
		//从北向南行驶
		if (northToSouth) {
			if (y >= TrafficFrame.DEFAULT_HEIGHT) {
				bufferG2.setPaint(background);
				drawCar(bufferG2);
				g2.drawImage(buffer, x, (y - speed), null);
				y = - (height / 2);
			}
			bufferG2.setPaint(background);
			drawCar(bufferG2);
			g2.drawImage(buffer, x, y, null);
			bufferG2.setPaint(carColor);
			drawCar(bufferG2);
			y += speed;
			g2.drawImage(buffer, x, y, null);
		}
		//从南向北行驶
		else {
			if ((y + height) <= 0) {
				bufferG2.setPaint(background);
				drawCar(bufferG2);
				g2.drawImage(buffer, x, (y + speed), null);
				y = TrafficFrame.DEFAULT_HEIGHT + height / 2;
			}
			bufferG2.setPaint(background);
			drawCar(bufferG2);
			g2.drawImage(buffer, x, y, null);
			bufferG2.setPaint(carColor);
			drawCar(bufferG2);
			y -= speed;
			g2.drawImage(buffer, x, y, null);
		}
	}

	/**
	 * 减速
	 */
	public void speedDown() {
		speed -= SPEEDINCREMENT;
	}

	/**
	 * 加速
	 */
	public void speedUp() {
		speed += SPEEDINCREMENT;
	}

	/**
	 * 停车
	 */
	public void stop() {
		//由北向南行驶
		if (northToSouth) {
			int distance = TrafficDemo.verticalUpEdge - y - height;
			if (y > TrafficDemo.verticalUpEdge || distance >= height) {
				move();
			}
			else {
				bufferG2.setPaint(background);
				drawCar(bufferG2);
				g2.drawImage(buffer, x, y, null);
				bufferG2.setPaint(carColor);
				drawCar(bufferG2);
				y = TrafficDemo.verticalUpEdge - height;
				g2.drawImage(buffer, x, y, null);
			}
		}
		//由南向北行驶
		else {
			int distance = y - TrafficDemo.verticalDownEdge;
			if (y < TrafficDemo.verticalDownEdge || distance >= height) {
				move();
			}
			else {
				bufferG2.setPaint(background);
				drawCar(bufferG2);
				g2.drawImage(buffer, x, y, null);
				bufferG2.setPaint(carColor);
				drawCar(bufferG2);
				y = TrafficDemo.verticalDownEdge;
				g2.drawImage(buffer, x, y, null);
			}
		}
	}

	public void run() {
		if (g2 != null) {
			logger.debug("VerticalCar线程启动, 现已进入其run方法. - " + VerticalCar.class.getName());
			while (true) {
				logger.debug("南北方向车辆" + (movable ? "可通行" : "不可通行") + " - " + VerticalCar.class.getName());
				if (movable) {
					if (TrafficDemo.tellVerticalCar()) {
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
						logger.debug("南北方向车辆前进一次. - " + VerticalCar.class.getName());
						move();
					}
				}
				else {
					stop();
				}
				try {
					logger.debug("Sleep for 0.1 second. - " + VerticalCar.class.getName());
					Thread.sleep(100);
				} catch (InterruptedException e) {
					logger.warn(e.getMessage() + " - " + VerticalCar.class.getName());
				}
			}
		}
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
	 * 车辆是否从北向南行驶, 由北向南行驶返回真, 由南向北行驶返回假
	 * @return
	 */
	public boolean isNorthToSouth() {
		return northToSouth;
	}

	/**
	 * 设置车辆从北向南或者从南向北行驶, true表示从北向南, false表示从南向北
	 * @param northToSouth
	 */
	public void setNorthToSouth(boolean northToSouth) {
		this.northToSouth = northToSouth;
	}

	static {
		DOMConfigurator.configure(TrafficDemo.class.getResource("/log.xml"));
	}

	private boolean movable = false; //为真时可通行
	private Graphics2D g2 = null;
	private int SPEEDINCREMENT = 1;
	private int speed = 64;
	private int width = 60;
	private int height = 60;
	private int x = 480;
	private int y = - width / 2;
	private boolean northToSouth = true; //指示车辆从北到南为真, 从南到北为假
	private Color carColor = null;
	private Image buffer = null; //用于双缓冲
	private Graphics2D bufferG2 = null;
	private Color background = null; //画布背景
	private Random random = new Random(System.currentTimeMillis());
	private final Color[] color = {Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray, Color.green, 
			Color.lightGray, Color.magenta, Color.orange, Color.pink, Color.red, Color.yellow};

	private static Logger logger = Logger.getLogger(VerticalCar.class);

}

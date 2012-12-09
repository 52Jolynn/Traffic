/**
 * @author Laud
 * @version 1.0.0
 * @since 2007-12-15
 */

package com.laud.major.traffic;

public interface Car {

	/**
	 * 加速
	 */
	public void speedUp();
	
	/**
	 * 减速
	 */
	public void speedDown();
	
	/**
	 * 前进
	 */
	public void move();
	
	/**
	 * 停止
	 */
	public void stop();
	
	/**
	 * 画车
	 */
	public void drawCar();
}

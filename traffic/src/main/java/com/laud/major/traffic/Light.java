/**
 * @author Laud
 * @version 1.0.0
 * @since 2007-12-15
 */

package com.laud.major.traffic;

public interface Light {

	/**
	 * 灯开
	 */
	public void on();
	
	/**
	 * 灯关
	 */
	public void off();
	
	/**
	 * 画灯
	 */
	public void drawLight();
}

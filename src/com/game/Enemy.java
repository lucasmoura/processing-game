package com.game;

/*
 * Interface used to model an enemy expected behaviou
 */
public interface Enemy
{
	public static final int ASTEROID = 0;
	public static final int KODANCWCH = 1;
	public static final int KODANCYFLYM = 2;
	public static final int KODANRUTHR = 3;
	public static final int KODANTRWM = 4;
	public static final int KODANSWYN = 5;
	
	/*
	 * Method used to move the enemy through the screen
	 */
	public void move();
	
	/*
	 * Method used to allow the enemy to shoot, if that behaviour is expected
	 */
	public void shoot();
	
	/*
	 * Method used to check whether the enemy is alive
	 */
	public boolean isAlive();
}

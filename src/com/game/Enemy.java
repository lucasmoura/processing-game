package com.game;

public interface Enemy
{
	public static final int ASTEROID = 0;
	public static final int KODANCWCH = 1;
	public static final int KODANCYFLYM = 2;
	public static final int KODANRUTHR = 3;
	public static final int KODANTRWM = 4;
	
	public void move();
	public void shoot();
	public boolean isAlive();
}

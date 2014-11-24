package com.game;

import java.util.ArrayList;
import java.util.LinkedList;


public class PowerUpHolder
{
	private static PowerUpHolder instance = null;
	
	private LinkedList<PowerUp> powerUps;
	
	public static PowerUpHolder getInstance()
	{
		
		if(instance == null)
			instance = new PowerUpHolder();
		
		return instance;
		
	}
	
	private PowerUpHolder()
	{
		powerUps = new LinkedList<PowerUp>();
	}
	
	public void addPowerUp(PowerUp powerUp)
	{
		powerUps.add(powerUp);
	}
	
	public void clean()
	{
		for(PowerUp powerUp: powerUps)
			powerUp.clean();
		
		powerUps.clear();
	}
	
	public void removePowerUp(PowerUp powerUp)
	{
		powerUps.remove(powerUp);
	}
	
	public void update()
	{
		for(PowerUp powerUp: powerUps)
			powerUp.update();
	}
	
	public void drawObject()
	{
		for(PowerUp powerUp: powerUps)
			powerUp.drawObject();
	}
	
	public ArrayList<PowerUp> getActivePowerUps()
	{
		return new ArrayList<PowerUp>(powerUps);
	}
	

}

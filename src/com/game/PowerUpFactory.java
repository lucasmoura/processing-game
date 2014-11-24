package com.game;

public class PowerUpFactory 
{
	
	private static PowerUpFactory instance = null;
	
	public static PowerUpFactory getInstance()
	{
		if(instance == null)
			instance = new PowerUpFactory();
		
		return instance;
	}
	
	public PowerUp createPowerUp(int type, int x, int y)
	{
		switch(type)
		{
			case 0:
				return createProtonPowerUp(x, y);
			
			case 1:
				return createVulcanPowerUp(x, y);
			
			case 2:
				return createGammaPowerUp(x, y);
			
			case 3:
				return createHealthIncrease(x, y);
				
			case 4:
				return createSpeedBoost(x, y);
			
			default:
				return null;
		}
	}

	private PowerUp createSpeedBoost(int x, int y)
	{
		PowerUp power = new PowerUp(x, y, "powerup/speedboost.png", "speedboost", 4);
		PowerUpHolder.getInstance().addPowerUp(power);
		
		return power;
	}

	private PowerUp createHealthIncrease(int x, int y) 
	{
		PowerUp power = new PowerUp(x, y, "powerup/healthincrease.png", "healthincrease", 3);
		PowerUpHolder.getInstance().addPowerUp(power);
		
		return power;
	}

	private PowerUp createGammaPowerUp(int x, int y)
	{
		PowerUp power = new PowerUp(x, y, "powerup/gammaweapon.png", "gammaweapon", 2);
		PowerUpHolder.getInstance().addPowerUp(power);
		
		return power;
	}

	private PowerUp createVulcanPowerUp(int x, int y) 
	{
		PowerUp power = new PowerUp(x, y, "powerup/vulcanweapon.png", "vulcanweapon", 1);
		PowerUpHolder.getInstance().addPowerUp(power);
		
		return power;
	}

	private PowerUp createProtonPowerUp(int x, int y) 
	{
		PowerUp power = new PowerUp(x, y, "powerup/protonweapon.png", "protonweapon", 0);
		PowerUpHolder.getInstance().addPowerUp(power);
		
		return power;
	}

}

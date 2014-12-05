package com.game;


/*
 * This class implements the Factory and singleton design pattern to allow a single channel to create a power up
 * in the game. Therefore, every enemy in the game can create a power up by using the single instance of this class
 */
public class PowerUpFactory 
{
	
	private static PowerUpFactory instance = null;
	
	public static PowerUpFactory getInstance()
	{
		if(instance == null)
			instance = new PowerUpFactory();
		
		return instance;
	}
	
	/*
	 * Method used to create a power up in the game
	 * @param type: The power up type
	 * @param x: The power up x position
	 * @param y: the power up y position
	 * @return: The created Power up
	 */
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
	
	/*
	 * Every power up created is also stored in the power up holder, that hold and manages all power ups still
	 * present in the game
	 */

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

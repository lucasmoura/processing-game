package com.game;

import java.util.Random;

import com.engine.DestructableObject;
import com.engine.Processing;

public class EnemyFactory 
{
	
	public static EnemyFactory instance = null;
	
	public static EnemyFactory getInstance()
	{
		if(instance == null)
			instance = new EnemyFactory();
		
		return instance;
	}
	
	private EnemyFactory()
	{
		
	}
	
	public DestructableObject createEnemy(int enemy)
	{
		switch(enemy)
		{
			case Enemy.ASTEROID:
				return createAsteroid();
			
			case Enemy.KODANCWCH:
				return createKodancwch();
				
			case Enemy.KODANCYFLYM:
				return createKodancyflym();
				
			case Enemy.KODANRUTHR:
				return createKodanruthr();
				
			default:
				return null;
		}
	}
	
	private DestructableObject createAsteroid()
	{
		DestructableObject asteroid = new Asteroid(0, 0, 0, 0, "enemies/asteroidsMedium.png",
				"mediumAsteroid", 16);
		
		asteroid.setDamageDealt(30);
		asteroid.setHealth(30);
		asteroid.setSpeed(8);
		
		int width = Processing.getInstance().getParent().width;
		
		Random rand = new Random();
		int asteroidx = rand.nextInt(width - asteroid.getWidth());
		
		asteroid.setX(asteroidx);
		return asteroid;
	}
	
	private DestructableObject createKodancwch()
	{
		DestructableObject kodancwch = new BasicEnemy(0, 0, 0, 0,
				"enemyBlack1.png", "kodancwch", 1);
		
		createBasicEnemy(kodancwch, 40, 8, false);
		
		return kodancwch;
		
	}
	
	private DestructableObject createKodanruthr()
	{
		DestructableObject kodanruthr = new Kodanruthr(0, 0,
					"enemies/kodanruthr.png", "kodanruthr", 1);
		
		kodanruthr.setDamageDealt(0);
		kodanruthr.setHealth(30);
		kodanruthr.setSpeed(30);
		
		int enemyx = -1;
		
		if(new Random().nextInt(2)==0)
		{
			enemyx = 0;
			((Kodanruthr) kodanruthr).setStart(true);
		}	
		else
		{
			enemyx = Processing.getInstance().getParent().width - kodanruthr.getWidth();
			((Kodanruthr) kodanruthr).setStart(false);
		}	
		
		kodanruthr.setX(enemyx);
		
		return kodanruthr;
	}
	
	private DestructableObject createKodancyflym()
	{
		DestructableObject kodancyflym = new BasicEnemy(0, 0, 0, 0,
				"enemies/kodancyflym.png", "kodancyflym", 1);
		
		createBasicEnemy(kodancyflym, 50, 12, true);
		
		return kodancyflym;
		
	}
	
	private void createBasicEnemy(DestructableObject enemy, int health, int speed, boolean bulletType)
	{
		enemy.setDamageDealt(0);
		enemy.setHealth(health);
		enemy.setSpeed(speed);
		
		((BasicEnemy) enemy).setBulletType(bulletType);
		
		int enemyx = -1;
				
		if(new Random().nextInt(2)==0)
		{
			enemyx = 0;
			((BasicEnemy) enemy).setStart(true);
		}	
		else
		{
			enemyx = Processing.getInstance().getParent().width - enemy.getWidth();
			((BasicEnemy) enemy).setStart(false);
		}	
		
		enemy.setX(enemyx);
	}

}

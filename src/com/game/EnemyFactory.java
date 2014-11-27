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
				
			case Enemy.KODANTRWM:
				return createKodantrwm();
				
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
		
		createBasicEnemy(kodancwch, 20, 8);
		
		((BasicEnemy) kodancwch).setBulletType(false);
		
		if(kodancwch.getX() == 0)
			((BasicEnemy) kodancwch).setStart(true);
		else
			((BasicEnemy) kodancwch).setStart(false);
		
		return kodancwch;
		
	}
	
	private DestructableObject createKodanruthr()
	{
		DestructableObject kodanruthr = new Kodanruthr(0, 0,
					"enemies/kodanruthr.png", "kodanruthr", 1);
		
		kodanruthr.setDamageDealt(0);
		kodanruthr.setHealth(20);
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
		
		createBasicEnemy(kodancyflym, 50, 12);
		
		((BasicEnemy) kodancyflym).setBulletType(true);
		
		if(kodancyflym.getX() == 0)
			((BasicEnemy) kodancyflym).setStart(true);
		else
			((BasicEnemy) kodancyflym).setStart(false);
		
		return kodancyflym;
		
	}
	
	private DestructableObject createKodantrwm() 
	{
		DestructableObject kodantrwm = new Kodantrwm(0, 0, "enemies/kodantrwm.png",
				"kodantrwm", 1);
		
		createBasicEnemy(kodantrwm, 60, 5);
		
		((Kodantrwm) kodantrwm).setBulletType(true);
		
		if(kodantrwm.getX() == 0)
			((BasicEnemy) kodantrwm).setStart(true);
		else
			((BasicEnemy) kodantrwm).setStart(false);
		
		return kodantrwm;
	}
	
	private void createBasicEnemy(DestructableObject enemy, int health, int speed)
	{
		enemy.setDamageDealt(0);
		enemy.setHealth(health);
		enemy.setSpeed(speed);
		
		int enemyx = -1;
				
		if(new Random().nextInt(2)==0)
			enemyx = 0;
		else
			enemyx = Processing.getInstance().getParent().width - enemy.getWidth();
			
		
		enemy.setX(enemyx);
	}

}

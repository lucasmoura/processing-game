package com.game;

import java.util.Random;

import com.engine.CollidableObject;
import com.engine.Processing;

/*
 * This class implements the Factory and singleton design pattern to allow a single channel to create an enemy
 * in the game. It also allows an easer way to expand enemies in the game, since this is the only class that
 * allows enemy creation, therefore if a new enemy needs to be created, only this class need to be modified
 */
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
	
	/*
	 * Method used to create an enemy based on the enemy parameter
	 * @param enemy: enemy to be created
	 */
	public CollidableObject createEnemy(int enemy)
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
			
			case Enemy.KODANSWYN:
				return createKodanswyn();
				
			default:
				return null;
		}
	}
	
	/*
	 * All the downwards classes are just creating one of the six possible enemies in the game
	 * by instantiating the correct object and setting the appropriate parameters accordingly
	 */
	
	private CollidableObject createAsteroid()
	{
		CollidableObject asteroid = new Asteroid(0, 0, 0, 0, "enemies/asteroidsMedium.png",
				"mediumAsteroid", 16);
		
		asteroid.setDamageDealt(30);
		asteroid.setHealth(30);
		asteroid.setSpeed(8);
		
		int width = Processing.getInstance().getPApplet().width;
		
		Random rand = new Random();
		int asteroidx = rand.nextInt(width - asteroid.getWidth());
		
		asteroid.setX(asteroidx);
		return asteroid;
	}
	
	private CollidableObject createKodancwch()
	{
		CollidableObject kodancwch = new BasicEnemy(0, 0, 0, 0,
				"enemyBlack1.png", "kodancwch", 1);
		
		createBasicEnemy(kodancwch, 10, 8);
		
		((BasicEnemy) kodancwch).setBulletType(false);
		
		if(kodancwch.getX() == 0)
			((BasicEnemy) kodancwch).setStart(true);
		else
			((BasicEnemy) kodancwch).setStart(false);
		
		return kodancwch;
		
	}
	
	private CollidableObject createKodanruthr()
	{
		CollidableObject kodanruthr = new Kodanruthr(0, 0,
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
			enemyx = Processing.getInstance().getPApplet().width - kodanruthr.getWidth();
			((Kodanruthr) kodanruthr).setStart(false);
		}	
		
		kodanruthr.setX(enemyx);
		
		return kodanruthr;
	}
	
	private CollidableObject createKodancyflym()
	{
		CollidableObject kodancyflym = new BasicEnemy(0, 0, 0, 0,
				"enemies/kodancyflym.png", "kodancyflym", 1);
		
		createBasicEnemy(kodancyflym, 50, 12);
		
		((BasicEnemy) kodancyflym).setBulletType(true);
		
		if(kodancyflym.getX() == 0)
			((BasicEnemy) kodancyflym).setStart(true);
		else
			((BasicEnemy) kodancyflym).setStart(false);
		
		return kodancyflym;
		
	}
	
	private CollidableObject createKodantrwm() 
	{
		CollidableObject kodantrwm = new Kodantrwm(0, 0, "enemies/kodantrwm.png",
				"kodantrwm", 1);
		
		createBasicEnemy(kodantrwm, 60, 5);
		
		((Kodantrwm) kodantrwm).setBulletType(true);
		
		if(kodantrwm.getX() == 0)
			((BasicEnemy) kodantrwm).setStart(true);
		else
			((BasicEnemy) kodantrwm).setStart(false);
		
		return kodantrwm;
	}
	
	private CollidableObject createKodanswyn() 
	{
		CollidableObject kodanswyn;
		
		int enemyx = -1;
		
		if(new Random().nextInt(2)==0)	
			enemyx = 0;
		else
			enemyx = Processing.getInstance().getPApplet().width;
		
		
		
		if(enemyx == 0)
			kodanswyn = new Kodanswyn(0, 0, "enemies/kodanswyn_left.png", "kodanswynLeft", 1, 3);
		else
			kodanswyn = new Kodanswyn(0, 0, "enemies/kodanswyn_right.png", "kodanswynRight", 1, 3);
		
		kodanswyn.setDamageDealt(0);
		kodanswyn.setHealth(20);
		kodanswyn.setSpeed(5);

		if(enemyx != 0)
		{
			((Kodanswyn) kodanswyn).setStart(false);
			enemyx -= kodanswyn.getWidth();
		}
		else
			((Kodanswyn) kodanswyn).setStart(true);
		
		kodanswyn.setX(enemyx);
		
		return kodanswyn;
	}
	
	private void createBasicEnemy(CollidableObject enemy, int health, int speed)
	{
		enemy.setDamageDealt(0);
		enemy.setHealth(health);
		enemy.setSpeed(speed);
		
		int enemyx = -1;
				
		if(new Random().nextInt(2)==0)
			enemyx = 0;
		else
			enemyx = Processing.getInstance().getPApplet().width - enemy.getWidth();
			
		
		enemy.setX(enemyx);
	}

}

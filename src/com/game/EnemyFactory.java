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
				
			default:
				return null;
		}
	}
	
	private DestructableObject createAsteroid()
	{
		DestructableObject asteroid = new Asteroid(0, 0, 0, 0, "asteroidsMedium.png",
				"mediumAsteroid", 16);
		
		asteroid.setDamageDealt(30);
		asteroid.setHealth(30);
		
		int width = Processing.getInstance().getParent().width;
		
		Random rand = new Random();
		int asteroidx = rand.nextInt(width);
		
		asteroid.setX(asteroidx);
		return asteroid;
	}
	
	private DestructableObject createKodancwch()
	{
		DestructableObject kodancwch = new Kodancwch(0, 0, 0, 0,
				"enemyBlack1.png", "kodancwch", 1);
		
		kodancwch.setDamageDealt(0);
		kodancwch.setHealth(40);
		
		int kodancwchx = -1;
				
		if(new Random().nextInt(2)==0)
		{
			kodancwchx = 0;
			((Kodancwch) kodancwch).setStart(true);
		}	
		else
		{
			kodancwchx = Processing.getInstance().getParent().width - kodancwch.getWidth();
			((Kodancwch) kodancwch).setStart(false);
		}	
		
		kodancwch.setX(kodancwchx);
		return kodancwch;
		
	}

}

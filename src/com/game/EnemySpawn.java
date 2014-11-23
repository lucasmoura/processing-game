package com.game;

import java.util.ArrayList;
import java.util.Random;

import com.engine.DestructableObject;

public class EnemySpawn 
{
	
	private long enemySpawnCooldown;
	private float asteroidSpawnChance;
	private float kodancwchSpawnChance;
	private ArrayList<DestructableObject> enemies;
	
	public EnemySpawn()
	{
		enemySpawnCooldown = 17;
		asteroidSpawnChance = 80;
		kodancwchSpawnChance = 130;
		enemies = new ArrayList<DestructableObject>();
	}
	
	public ArrayList<DestructableObject> spawn(int numEnemies)
	{
		enemies.clear();
		
		if (System.currentTimeMillis() - enemySpawnCooldown >= 17) 
		{
	        enemySpawnCooldown = System.currentTimeMillis();
	 
	        if (numEnemies < 50) 
	        {
	            if (new Random().nextInt((int) asteroidSpawnChance) == 0)
	            {
	            	enemies.add(EnemyFactory.
	            			getInstance().createEnemy(Enemy.ASTEROID));
	            }
	            if (new Random().nextInt((int) kodancwchSpawnChance) == 0)
	            {
	            	enemies.add(EnemyFactory.
	            			getInstance().createEnemy(Enemy.KODANCWCH));
	            }
	        }
	        //increase Spawn Time
	        if (asteroidSpawnChance >= 1.1f) 
	            asteroidSpawnChance -= 0.005f;
	        
	        if(kodancwchSpawnChance >= 1.1f)
	        	kodancwchSpawnChance -= 0.009;
	    }
		
		return enemies;
	}
	
	

}

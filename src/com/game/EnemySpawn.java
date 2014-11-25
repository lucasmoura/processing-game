package com.game;

import java.util.ArrayList;
import java.util.Random;

import com.engine.DestructableObject;

public class EnemySpawn 
{
	
	private long enemySpawnCooldown;
	private float asteroidSpawnChance;
	private float kodancwchSpawnChance;
	private float kodancyflymSpawnChance;
	private ArrayList<DestructableObject> enemies;
	private Random rand;
	
	public EnemySpawn()
	{
		enemySpawnCooldown = 17;
		asteroidSpawnChance = 80;
		kodancwchSpawnChance = 130;
		kodancyflymSpawnChance = 190;
		enemies = new ArrayList<DestructableObject>();
		rand = new Random();
	}
	
	public ArrayList<DestructableObject> spawn(int numEnemies)
	{
		enemies.clear();
		
		if (System.currentTimeMillis() - enemySpawnCooldown >= 17) 
		{
	        enemySpawnCooldown = System.currentTimeMillis();
	 
	        if (numEnemies < 50) 
	        {
	            if (rand.nextInt((int) asteroidSpawnChance) == 0)
	            {
	            	enemies.add(EnemyFactory.
	            			getInstance().createEnemy(Enemy.ASTEROID));
	            }
	            if (rand.nextInt((int) kodancwchSpawnChance) == 0)
	            {
	            	enemies.add(EnemyFactory.
	            			getInstance().createEnemy(Enemy.KODANCWCH));
	            }
	            if (rand.nextInt((int) kodancyflymSpawnChance) == 0)
	            {
	            	enemies.add(EnemyFactory.
	            			getInstance().createEnemy(Enemy.KODANCYFLYM));
	            }
	            
	        }
	        //increase Spawn Time
	        if (asteroidSpawnChance >= 1.1f) 
	            asteroidSpawnChance -= 0.005f;
	        
	        if(kodancwchSpawnChance >= 1.1f)
	        	kodancwchSpawnChance -= 0.009;
	        
	        if(kodancwchSpawnChance >= 1.1f)
	        	kodancwchSpawnChance -= 0.0095;
	    }
		
		return enemies;
	}
	
	

}

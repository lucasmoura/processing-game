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
	private float kodanruthrSpawnChance;
	private float kodantrwmSpawnChance;
	private float kodanswynSpawnChance;
	private ArrayList<DestructableObject> enemies;
	private Random rand;
	
	public EnemySpawn()
	{
		enemySpawnCooldown = 19;
		asteroidSpawnChance = 100;
		kodancwchSpawnChance = 150;
		kodancyflymSpawnChance = 230;
		kodanruthrSpawnChance = 310;
		kodantrwmSpawnChance = 550;
		kodanswynSpawnChance = 420;
		enemies = new ArrayList<DestructableObject>();
		rand = new Random();
	}
	
	public ArrayList<DestructableObject> spawn(int numEnemies, boolean kodanswynStatus)
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
	            if (rand.nextInt((int) kodanruthrSpawnChance) == 0)
	            {
	            	enemies.add(EnemyFactory.
	            			getInstance().createEnemy(Enemy.KODANRUTHR));
	            }
	            if (rand.nextInt((int) kodantrwmSpawnChance) == 0)
	            {
	            	enemies.add(EnemyFactory.
	            			getInstance().createEnemy(Enemy.KODANTRWM));
	            }
	            if (!kodanswynStatus && rand.nextInt((int) kodanswynSpawnChance) == 0)
	            {
	            	enemies.add(EnemyFactory.
	            			getInstance().createEnemy(Enemy.KODANSWYN));
	            }
	            
	        }
	        //increase Spawn Time
	        if (asteroidSpawnChance >= 1.1f) 
	            asteroidSpawnChance -= 0.005f;
	        
	        if(kodancwchSpawnChance >= 1.1f)
	        	kodancwchSpawnChance -= 0.009;
	        
	        if(kodancwchSpawnChance >= 1.1f)
	        	kodancwchSpawnChance -= 0.0095;
	        
	        if(kodanruthrSpawnChance>= 1.1f)
	        	kodanruthrSpawnChance -= 0.0095;
	        
	        if(kodantrwmSpawnChance>= 1.1f)
	        	kodanruthrSpawnChance -= 0.002;
	        
	        if(kodanswynSpawnChance>= 1.1f)
	        	kodanswynSpawnChance -= 0.003;
	    }
		
		return enemies;
	}
	
	

}

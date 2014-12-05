package com.game;

import java.util.ArrayList;
import java.util.Random;

import com.engine.CollidableObject;

/*
 * This class is used to spawn enemies in the game
 */
public class EnemySpawn 
{
	//The timer it requires to spawn another enemy
	private long enemySpawnCooldown;
	
	/*
	 * The probability associated to deploy the six enemies of the game
	 */
	private float asteroidSpawnChance;
	private float kodancwchSpawnChance;
	private float kodancyflymSpawnChance;
	private float kodanruthrSpawnChance;
	private float kodantrwmSpawnChance;
	private float kodanswynSpawnChance;
	
	//The spawned enemies
	private ArrayList<CollidableObject> enemies;
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
		enemies = new ArrayList<CollidableObject>();
		rand = new Random();
	}
	
	/*
	 * Method used to spawn enemies
	 * @param numEnemies: The number of enemies in the screen
	 * @param kodanswynStatus: Used to allow that only one kodanswyn enemy can be present on the screen. (Improve)
	 * @return: An ArrayList containing all spawned enemies
	 */
	public ArrayList<CollidableObject> spawn(int numEnemies, boolean kodanswynStatus)
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
	        
	        /*
	         * Increase probability of spawning an enemy as the game progress, making it harder as time progress
	         */
	        
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

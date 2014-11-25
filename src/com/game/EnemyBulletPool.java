package com.game;

import java.util.ArrayList;

import com.engine.DestructableObject;

public class EnemyBulletPool 
{
	
	private static EnemyBulletPool instance = null;
	private BulletPool enemyBullets;
	private BulletPool redBullets;
	
	public static EnemyBulletPool getInstance()
	{
		if(instance == null)
			instance = new EnemyBulletPool();
		
		return instance;
	}
	
	private EnemyBulletPool()
	{
		enemyBullets = new BulletPool(60);
		redBullets = new BulletPool(60);
		
		enemyBullets.init("laserGreen10.png", "enemyLaser", Bullet.ENEMY_BULLET);
		redBullets.init("bullets/laserRed03.png", "redLaser", Bullet.ENEMY_BULLET);
	}

	public void update()
	{
		enemyBullets.update();
		redBullets.update();
	}
	
	public void getBullet(boolean bulletType, int x, int y, int speed, int damage)
	{
		if(!bulletType)
			enemyBullets.getBullet(x, y, speed, damage);
		else
			redBullets.getBullet(x, y, speed, damage);
	}
	
	public ArrayList<DestructableObject> getPool(boolean bulletType)
	{
		if(!bulletType)
			return enemyBullets.getPool();
		else
			return redBullets.getPool();
	}

	public void clean() 
	{
		instance = null;
		enemyBullets.clean();
		redBullets.clean();
		
	}

}

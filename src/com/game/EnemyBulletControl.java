package com.game;

import java.util.ArrayList;

import com.engine.DestructableObject;

public class EnemyBulletControl 
{
	
	private static EnemyBulletControl instance = null;
	private BulletPool enemyBullets;
	private BulletPool redBullets;
	private BulletPool chargeShoot;
	private int playerx;
	private int playery;
	
	public static EnemyBulletControl getInstance()
	{
		if(instance == null)
			instance = new EnemyBulletControl();
		
		return instance;
	}
	
	private EnemyBulletControl()
	{
		enemyBullets = new BulletPool(60);
		redBullets = new BulletPool(120);
		chargeShoot = new BulletPool(20);
		
		enemyBullets.init("laserGreen10.png", "enemyLaser", Bullet.ENEMY_BULLET);
		redBullets.init("bullets/laserRed03.png", "redLaser", Bullet.ENEMY_BULLET);
		chargeShoot.init("bullets/laserRed08.png", "chargeShoot", Bullet.ENEMY_BULLET);
	}

	public void update()
	{
		enemyBullets.update();
		redBullets.update();
		chargeShoot.update();
	}
	
	public void getBullet(boolean bulletType, int x, int y, int speed, int damage)
	{
		if(!bulletType)
			enemyBullets.getBullet(x, y, speed, damage, 0);
		else
			redBullets.getBullet(x, y, speed, damage, 0);
	}
	
	public void getThreeBullets(boolean bulletType, int x1, int x2, int x3,
				int y, int speed, int damage, int angle)
	{
		if(!bulletType)
			enemyBullets.getThreeBullets(x1, x2, x3, y, speed, damage, 0);
		else
			redBullets.getThreeBullets(x1, x2, x3, y, speed, damage, angle);
		
	}
	
	public ArrayList<DestructableObject> getPool(int bulletType)
	{
	
		if(bulletType == 0)
			return enemyBullets.getPool();
		else if(bulletType == 1)
			return redBullets.getPool();
		else
			return chargeShoot.getPool();
	}

	public void clean() 
	{
		instance = null;
		enemyBullets.clean();
		redBullets.clean();
		chargeShoot.clean();
		
	}
	
	

	public void setPlayerx(int playerx) 
	{
		this.playerx = playerx;
	}


	public void setPlayery(int playery)
	{
		this.playery = playery;
	}

	public void getChargeShoot(int x, int y, int speed, int damage)
	{
		chargeShoot.getBullet(x, y, speed, damage, getAngle(y, x));
	}
	
	
	public double getAngle(int y, int x)
	{
	    double angle = Math.toDegrees(Math.atan2(playery - y, playerx - x));

	    if(angle < 0)
	        angle += 360;
	    

	    return angle;
	}

}

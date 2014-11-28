package com.game;

import com.engine.DestructableObject;
import com.engine.SoundManager;

public class AdmiralShip extends DestructableObject
{
	
	private boolean moveRight;
	private boolean moveLeft;
	private int speedMovement;
	private int speedBoost;
	private int speedBoostCounter;
	private int width = 1814;
	private PlayerBulletControl bulletPool;
	private int counter;
	private int fireRate;
	private boolean alive;
	private boolean explode;
	private Explosion explosion;
	private int weapon;
	private int gravitationalMovement;
	
	public static int PROTON_WEAPON = 0;
	public static int GAMMA_WEAPON = 2;
	public static int VULCAN_WEAPON = 1;

	public AdmiralShip(int x, int y, int objectWidth, int objectHeight,
			String imagePath, String imageId, int numFrames)
	{
		super(x, y, objectWidth, objectHeight, imagePath, imageId, numFrames);
		
		moveLeft = moveRight = explode = false;
		fireRate =3;
		counter = fireRate;
		bulletPool = new PlayerBulletControl();
		gravitationalMovement = 0;
		speedMovement = 14;
		
		alive = true;
		explosion = new Explosion(0, 0, 0, 0, "explosion.png", "explosion", 17);
		setCollidable("mediumAsteroid");
		setCollidable("enemyLaser");
		setCollidable("vulcanweapon");
		setCollidable("protonweapon");
		setCollidable("gammaweapon");
		setCollidable("speedboost");
		setCollidable("healthincrease");
		setCollidable("redLaser");
		setCollidable("chargeShoot");
		
		weapon = PROTON_WEAPON;
		speedBoost = speedBoostCounter = 0;
	}
	
	public void drawObject()
	{
		if(!explode && alive)
		{
			bulletPool.getBulletPool(weapon).drawObject();
			super.drawObject();
		}
		else if(explode && alive)
			explode();
		
	}

	@Override
	public void update() 
	{
		
		counter++;
		speedBoostCounter++;
		
		if(explode)
			return;
		
		if(isColliding())
		{
			health -= damageReceived;
			isColliding = false;
			
			damageReceived = 0;
			
			if(health <= 0)
			{
				health =0;
				explode = true;
				return;
			}	
		}
		
		if(moveRight)
		{
			if(position.getX() + speedMovement <= width)
			{
				position.setX(position.getX() +speedMovement+speedBoost);
				moveRight = false;
			}
				
		}
		
		if(moveLeft)
		{
			if(position.getX() - speedMovement > 0)
			{
				position.setX(position.getX() -speedMovement - speedBoost + gravitationalMovement);
				moveLeft = false;
			}
		}
		
		if(gravitationalMovement>0)
		{
			if(position.getX()+gravitationalMovement <= width)
				position.setX(position.getX() + gravitationalMovement);
		}
		else
		{
			if(position.getX() + gravitationalMovement > 0)
				position.setX(position.getX() + gravitationalMovement);
		}
		
		bulletPool.getBulletPool(weapon).update();
		
		if(counter>=fireRate)
		{
			shoot();
			counter=0;
		}
		
		if(speedBoostCounter >= 300)
		{
			speedBoost = 0;
			speedBoostCounter = 0;
		}
		
		
	}
	
	public void moveRight()
	{
		moveRight = true;
	}
	
	public void moveLeft()
	{
		moveLeft = true;
	}
	
	private void shoot()
	{
		if(weapon == PROTON_WEAPON)
		{
			bulletPool.getBulletPool(weapon).getBullet(getX()+37, getY()-this.getHeight()+30, 15, 10, 0);
			SoundManager.getInstance().playSound("protonshoot", false);
		}	
		else if(weapon == VULCAN_WEAPON)
		{
			bulletPool.getBulletPool(weapon).getThreeBullets(getX() +27, getX()+37, getX()+47,
					getY()-this.getHeight()+30, 15, 5, 10);
			SoundManager.getInstance().playSound("vulcanshoot", false);
		}		
		else
		{
			bulletPool.getBulletPool(weapon).getBullet(getX()+37, getY()-this.getHeight()+30, 10, 15, 0);
			SoundManager.getInstance().playSound("gammashoot", false);
		}	
	}

	public BulletPool getBulletPool()
	{
		return bulletPool.getBulletPool(weapon);
	}
	
	private void explode()
	{
		explosion.setX(getX());
		explosion.setY(getY());
		
		if(!explosion.isOver())
		{
			explosion.update();
			explosion.drawObject();
		}	
		else
			alive = false;
	}
	
	public boolean isAlive()
	{
		return alive;
	}
	
	public void setWeapon(int weapon)
	{
		this.weapon = weapon;
	}

	public void setFireRate(int fireRate) 
	{
		this.fireRate = fireRate;
	}
	
	public void setGravitationalMovement(int gravitationalMovement)
	{
		this.gravitationalMovement = gravitationalMovement;
	}

	public void getPowerUp(PowerUp powerUp) 
	{
		switch(powerUp.getType())
		{
			case 0:
				
				if(weapon != PROTON_WEAPON)
					bulletPool.clear(weapon);
				
				weapon = PROTON_WEAPON;
				fireRate = 5;
				break;
			
			case 1:
				
				if(weapon != VULCAN_WEAPON)
					bulletPool.clear(weapon);
				
				weapon = VULCAN_WEAPON;
				fireRate = 3;
				break;
				
			case 2:
				
				if(weapon != GAMMA_WEAPON)
					bulletPool.clear(weapon);
				
				weapon = GAMMA_WEAPON;
				fireRate = 10;
				break;
				
			case 3:
				health += (health+20>100)?0: 20;
				break;
				
			case 4:
				
				if(speedBoost == 0)
					speedBoost = 3;
				
				speedBoostCounter =0;
				
				break;
				
		}
		
		SoundManager.getInstance().playSound("powerup", false);
	}
	
	public void clean()
	{
		bulletPool.clean();
		
		super.clean();
		
	}

}

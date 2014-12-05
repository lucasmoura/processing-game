package com.game;

import com.engine.CollidableObject;
import com.engine.SoundManager;

public class AdmiralShip extends CollidableObject
{
	//Verify is the ship is moving right
	private boolean moveRight;
	//Verify if the ship is moving left
	private boolean moveLeft;
	//The speed movement of the ship
	private int speedMovement;
	//The total speed spped boost at the current time
	private int speedBoost;
	//The amount of time allowed for the speed boost
	private int speedBoostCounter;
	private int width = 1814;
	//The bullet manager for the player ship
	private PlayerBulletControl bulletPool;
	//Variable used to control the fire rate
	private int counter;
	private int fireRate;
	//Variable that states if the player is alive
	private boolean alive;
	//Variable used to sinalize that the player should explode
	private boolean explode;
	//Explosion object associated with the player
	private Explosion explosion;
	//The ship current weapom
	private int weapon;
	//Value of gravitational field attracting the player
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
	
	/*
	 *
	 * @see com.engine.CollidableObject#drawObject()
	 */
	public void drawObject()
	{
		//If the ship is not exploding and still alive
		if(!explode && alive)
		{
			bulletPool.getBulletPool(weapon).drawObject();
			super.drawObject();
		}
		//If the ship has exploded
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
		
		//If a collision happens, receive damage from object and set health accordingly
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
		
		move();
		
		//update ship bullets position
		bulletPool.getBulletPool(weapon).update();
		
		//Mechanics used to alows that the player will always be shooting with a constant fire rate
		if(counter>=fireRate)
		{
			shoot();
			counter=0;
		}
		
		//Remove speed boost if timer is off
		if(speedBoostCounter >= 300)
		{
			speedBoost = 0;
			speedBoostCounter = 0;
		}
		
		
	}
	
	/*
	 * Method used to inform that the player is moving right
	 */
	public void moveRight()
	{
		moveRight = true;
	}
	
	/*
	 * Method used to inform that the player is moving left
	 */
	public void moveLeft()
	{
		moveLeft = true;
	}
	
	/*
	 *Method used to move the player through the bottom of the screen
	 */
	private void move()
	{
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
		
		/*
		 * If a gravitational field is pushing the ship, set the movement accordingly
		 */
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
	}
	
	/*
	 * Method used for the shot its bullet. It verifies what is the current weapon the player is using and shoots
	 * it accordingly
	 */
	private void shoot()
	{
		//Speed movement of the bullet
		int speed = 0;
		//Damage dealt by the bullet
		int damage = 0;
		//Angle used to shoot. Used only by the vulcan weapon
		double angle = 0;
		
		if(weapon == PROTON_WEAPON)
		{
			speed = 15;
			damage = 10;
			bulletPool.getBulletPool(weapon).getBullet(getX()+37, getY()-this.getHeight()+30, speed, damage, 0);
			SoundManager.getInstance().playSound("protonshoot", false);
		}	
		else if(weapon == VULCAN_WEAPON)
		{
			speed = 15;
			damage = 2;
			angle = 10;
			
			bulletPool.getBulletPool(weapon).getThreeBullets(getX() +27, getX()+37, getX()+47,
					getY()-this.getHeight()+30, speed, damage, (int)angle);
			SoundManager.getInstance().playSound("vulcanshoot", false);
		}		
		else
		{
			speed = 15;
			damage = 15;
			angle = 0;
			
			bulletPool.getBulletPool(weapon).getBullet(getX()+37, getY()-this.getHeight()+30,
						speed, damage, angle);
			SoundManager.getInstance().playSound("gammashoot", false);
		}	
	}

	/*
	 * Method used to return the ship bullet pool
	 * @return: The player's ship bullet pool
	 */
	public BulletPool getBulletPool()
	{
		return bulletPool.getBulletPool(weapon);
	}
	
	/*
	 * Method used to start the explosion animation for the player's ship
	 */
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
	
	/*
	 * Method used to verify if the player's ship is alive
	 * @return: A boolean stating if the player's ship is alive or not
	 */
	public boolean isAlive()
	{
		return alive;
	}
	
	/*
	 * Method used to set the player's ship current weapon
	 * @param weapon: The weapon that will become the current weapon of the player's ship
	 */
	public void setWeapon(int weapon)
	{
		this.weapon = weapon;
	}

	public void setFireRate(int fireRate) 
	{
		this.fireRate = fireRate;
	}
	
	/*
	 * Method used to set that a gravitational field is pushing the player
	 * @param graviatationalMovement: The gravitational push value that is attracting the player
	 */
	public void setGravitationalMovement(int gravitationalMovement)
	{
		this.gravitationalMovement = gravitationalMovement;
	}

	/*
	 * Method used when the player collide with a power up. It basically identifies which power up the ship
	 * has collided with and set the effects accordingly
	 */
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
				fireRate = 8;
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
	
	/*
	 * (non-Javadoc)
	 * @see com.engine.GameObject#clean()
	 */
	public void clean()
	{
		bulletPool.clean();
		
		super.clean();
		
	}

}

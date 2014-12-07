package com.game;

import java.util.Random;

import processing.core.PApplet;

import com.engine.CollidableObject;
import com.engine.Processing;
import com.engine.SoundManager;

/*
 * Class used to model enemies in the game. The methods move and willFire need to be overwritten if different
 * movement and shooting patterns are required
 */
public class BasicEnemy extends CollidableObject implements Enemy 
{

	//Variable used to represent if the enemy has reached its fixed location
	protected boolean reachedPosition;
	//Variable used to verify if the enemy is still alive
	private boolean alive;
	//Enemy width
	private int width;
	//Array containing the possible position the player can reach before moving horizontally throuhg the screen
	private int[] possibleFixedPositions;
	//Variable to control the shooting mechanics
	private int numberOfTicks;
	//The stop position the enemy must reach to starts moving horizontally or to start other defined moving pattern
	protected int stopPosition;
	//The enemy starting position
	private boolean startPosition;
	//If the player has started at the right corned of the screen
	protected boolean startRight;
	//Verify is the player is dead and is currently exploding
	protected boolean explode;
	//Object that is used to create the explosion animation
	private Explosion explosion;
	//Shooting probability threshold
	private double fireChance;
	//Probability of firing
	private double percentFire;
	//The enemy bullet type
	private boolean bulletType;
	
	private final int NUMBER_OF_FIXED_POSITIONS = 3;
	
	public BasicEnemy(int x, int y, int objectWidth, int objectHeight,
			String imagePath, String imageId, int numFrames)
	{
		super(x, y, objectWidth, objectHeight, imagePath, imageId, numFrames);
		
		reachedPosition = startPosition = explode = false;
		alive = true;
		
		PApplet applet = Processing.getInstance().getPApplet();
		this.width = applet.width - this.objectWidth;
		
		possibleFixedPositions = new int[NUMBER_OF_FIXED_POSITIONS];
		possibleFixedPositions[0] = applet.height/4;
		
		setFixedPosition();
		
		setCollidable("laser");
		setCollidable("vulcanbullet");
		setCollidable("gammabullet");
		
		numberOfTicks = 0;
		percentFire = 0.05f;
		explosion = new Explosion(0, 0, 0, 0, "explosions/explosion.png", "explosion", 17);
		
		points = 10;
		
	}
	
	/*
	 * Method used to find a stop position
	 */
	protected void setFixedPosition()
	{
		for(int i = 1; i<NUMBER_OF_FIXED_POSITIONS; i++)
			possibleFixedPositions[i] = possibleFixedPositions[i-1] + possibleFixedPositions[i-1]/2;
		
		stopPosition = possibleFixedPositions[new Random().nextInt(3)];
	}
	
	/*
	 * @see com.engine.CollidableObject#drawObject()
	 */
	public void drawObject()
	{
		if(!explode && alive)
			super.drawObject();	
		else if(explode && alive)
			explode();
	}

	/*
	 * @see com.game.Enemy#move()
	 */
	@Override
	public void move() 
	{
		if(!reachedPosition)
			reachDestination();
		else
			moveInStraightLine();
		
	}

	/*
	 * @see com.game.Enemy#shoot()
	 */
	@Override
	public void shoot() 
	{
		EnemyBulletControl.getInstance().getBullet(bulletType, getX()+37, getY()+this.getHeight()+30, 15, 10);	
		shootSound();
	}
	
	/*
	 * Method used to play a shooting effect when the enemy fire a bullet
	 */
	public void shootSound()
	{
		SoundManager.getInstance().playSound("enemyshoot", false);
	}

	/*
	 * @see com.game.Enemy#isAlive()
	 */
	@Override
	public boolean isAlive() 
	{
		return alive;
	}

	/*
	 * @see com.engine.CollidableObject#update()
	 */
	@Override
	public void update() 
	{
		if(explode)
			return;
		
		if(isColliding())
		{
			health -= damageReceived;
			damageReceived = 0;
			isColliding = false;
			
			if(health <= 0)
			{
				dropPowerUp();
				explode = true;
				return;
			}	
		}
		
		willFire();
		
		move();
		
	}
	
	/*
	 * Method used to set the fire mechanics of the enemy. The implemented method is a basic pattern of shooting
	 */
	protected void willFire()
	{
		//If the fire chance is smaller than a threshold, shoot
		fireChance = Math.floor(Math.random()*101);
		if (fireChance/100 < percentFire)
			shoot();
	}
	
	public void setStart(boolean start)
	{
		startRight = start;
	}
	
	/*
	 * Method used to see the enemy movement pattern. Start by using the sin function to reach the 
	 * stop position, to simulate a wave behaviour. 
	 */
	private void reachDestination()
	{
		
		if(getY() == stopPosition)
		{
			reachedPosition = true;
			numberOfTicks = 0;
			return;
		}
			
		int y = (int) (stopPosition * Math.sin((numberOfTicks * 0.5 * Math.PI)/60));
		
		if(y<0)
			y+= stopPosition;
	    
		if(startRight)
			setX(getX() + speed);
		else
			setX(getX() - speed);
		
		setY(y);
		
		numberOfTicks++;
		
	}
	
	/*
	 * Method used to set the horizontal movement of the enemy. Move normally until reaching an screen boundary,
	 * then start applying the sin function again to proportionate a smooth movement of the enemy when it reaches the
	 * screen boundaries
	 */
	private void moveInStraightLine()
	{
		
		if(startPosition == false)
		{
			if(getX()>= width || getX()<= 0)
			{
				startPosition = true;
				
				if(startRight)
					numberOfTicks = 80;
				else
					numberOfTicks = 240;
			}	
			else
			{
				if(startRight)
					setX(getX() + speed);
				else
					setX(getX() - speed);
			}
				
		}
		else
		{
			int x = (int) ((width/2*Math.sin((numberOfTicks++ * 0.5 * Math.PI)/80)) + width/2);
			setX(x);
		}
		
	}
	
	/*
	 * Method used to display a explode animation
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
	 * Method used to verify if the enemy will drop a power up once it is destroyed
	 */
	private void dropPowerUp()
	{
		int num = new Random().nextInt(100);
		
		//If the number is smaller than a threshold, a power up will be randomly created
		if(num <= 10)
		{
			int type = new Random().nextInt(5);
			PowerUpFactory.getInstance().createPowerUp(type, getX(), getY());	
		}
	}

	public void setBulletType(boolean bulletType)
	{
		this.bulletType = bulletType;
	}

	public void setPoints(int points) 
	{
		this.points = points;
		
	}
}

package com.game;

import java.util.Random;

import processing.core.PApplet;

import com.engine.DestructableObject;
import com.engine.Processing;

public class Kodancwch extends DestructableObject implements Enemy 
{

	private boolean reachedPosition;
	private boolean alive;
	private int width;
	private int[] possibleFixedPositions;
	private int numberOfTicks;
	private int stopPosition;
	private int speed = 10;
	private boolean startPosition;
	private boolean startRight;
	private boolean explode;
	private Explosion explosion;
	private double fireChance;
	private double percentFire;
	
	private final int NUMBER_OF_FIXED_POSITIONS = 3;
	
	public Kodancwch(int x, int y, int objectWidth, int objectHeight,
			String imagePath, String imageId, int numFrames)
	{
		super(x, y, objectWidth, objectHeight, imagePath, imageId, numFrames);
		
		reachedPosition = startPosition = explode = false;
		alive = true;
		
		possibleFixedPositions = new int[NUMBER_OF_FIXED_POSITIONS];
		PApplet applet = Processing.getInstance().getParent();
		
		this.width = applet.width - this.objectWidth;
		possibleFixedPositions[0] = applet.height/4;
		
		for(int i = 1; i<NUMBER_OF_FIXED_POSITIONS; i++)
			possibleFixedPositions[i] = possibleFixedPositions[i-1] + possibleFixedPositions[i-1]/2;
		
		stopPosition = possibleFixedPositions[new Random().nextInt(3)];
		setCollidable("laser");
		setCollidable("vulcanbullet");
		setCollidable("gammabullet");
		
		numberOfTicks = 0;
		percentFire = 0.05f;
		explosion = new Explosion(0, 0, 0, 0, "explosion.png", "explosion", 17);
		
		points = 20;
		
	}
	
	public void drawObject()
	{
		if(!explode && alive)
			super.drawObject();	
		else if(explode && alive)
			explode();
	}

	@Override
	public void move() 
	{
		if(!reachedPosition)
			reachDestination();
		else
			moveInStraightLine();
		
	}

	@Override
	public void shoot() 
	{
		EnemyBulletPool.getInstance().getBullet(getX()+37, getY()+this.getHeight()+30, 15, 10);
	}

	@Override
	public boolean isAlive() 
	{
		return alive;
	}

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
		
		fireChance = Math.floor(Math.random()*101);
		if (fireChance/100 < percentFire)
			shoot();
		
		move();
		
	}
	
	public void setStart(boolean start)
	{
		if(start)
			startRight = true;
		else
			startRight = false;
	}
	
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
	
	private void dropPowerUp()
	{
		int num = new Random().nextInt(100);
		
		if(num <= 80)
		{
			int type = new Random().nextInt(5);
			PowerUpFactory.getInstance().createPowerUp(type, getX(), getY());	
		}
	}

}

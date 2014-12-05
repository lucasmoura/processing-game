package com.game;

import com.engine.CollidableObject;
import com.engine.Processing;

public class Bullet extends CollidableObject
{
	
	//Variable used to state that the bullet is being used or is inside the screen boundaries
	private boolean active;
	//Variable used to check if the bullet is still in the screen boundaries
	private boolean isInScreen;
	//Bullet speed
	private int speed;
	//Bullet type
	private int type;
	//Bullet height
	private int height;
	//Bullet movement pattern
	private int movement;
	//Bullet angle
	private double angle;
	
	public static final int ENEMY_BULLET = 1;
	public static final int PLAYER_BULLET = 0;
	public static final int THREE_BULLETS = 0;
	public static final int STRAIGHT_LINE = 1;

	public Bullet(int x, int y, int objectWidth, int objectHeight,
			String imagePath, String imageId, int numFrames, int type) 
	{
		super(x, y, objectWidth, objectHeight, imagePath, imageId, numFrames);
		
		active = isInScreen = false;
		this.type = type;
		speed = 0;
		height = Processing.getInstance().getPApplet().displayHeight;
		movement = 0;
		angle = 0;
	}

	
	public void drawObject()
	{
		super.drawObject();
	}
	
	@Override
	public void update() 
	{
		//If a collsion happens, the bullet must be removed
		if(isColliding())
		{
			isInScreen = false;
			return;
		}
		
		//Update the bullet according to its type
		//If the bullet is from the player, the bullet must move upwards. else it must move downwards
		if(type == PLAYER_BULLET)
			updatePlayerBullet();
		else
			updateEnemyBullet();
		
	}
	
	private void updatePlayerBullet()
	{
		if(movement == STRAIGHT_LINE)
		{
			if(this.getY() - speed >= 0)
				this.setY(this.getY() - speed);
			else
				isInScreen = false;
		}
		else
		{
			double vx = speed*Math.cos(angle*Math.PI/180.0);
			double vy = speed*Math.sin(angle*Math.PI/180.0);
			
			if(this.getY() - speed >= 0)
			{
				this.setY(this.getY() - (int) vy);
				this.setX(this.getX() - (int) vx);
			}	
			else
				isInScreen = false;
		}
	}
	
	private void updateEnemyBullet()
	{
		if(movement == STRAIGHT_LINE)
		{
			if((this.position.getY() + speed) <= height)
				this.position.setY(this.position.getY() + speed);
			else
				isInScreen = false;
		}
		else
		{
			double vx = speed*Math.cos(angle*Math.PI/180.0);
			double vy = speed*Math.sin(angle*Math.PI/180.0);
			
			if(this.getY() + speed <= height)
			{
				this.setY(this.getY() + (int) vy);
				this.setX(this.getX() + (int) vx);
			}	
			else
				isInScreen = false;
		}
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
		this.isInScreen = active;
	}

	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int speed) 
	{
		this.speed = speed;
	}
	
	
	public boolean getIsInScreen() 
	{
		return isInScreen;
	}

	public void setNotInScreen(boolean notInScreen) 
	{
		this.isInScreen = notInScreen;
	}
	
	public void setMovement(int movement)
	{
		this.movement = movement;
	}
	
	public void setAngle(double angle)
	{
		this.angle = angle;
	}


	/*
	 * Method used to clear a bullet, reseting all its attributes
	 */
	public void clear()
	{
		isColliding = false;
		active = false;
		setX(0);
		setY(0);
		speed = 0;
		angle = 0;
		movement = 0;
	}
	
}

package com.game;

import com.engine.DestructableObject;
import com.engine.Processing;

public class Bullet extends DestructableObject
{
	
	private boolean active;
	private boolean isInScreen;
	private int speed;
	private int type;
	private int height;
	private int movement;
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
		height = Processing.getInstance().getParent().displayHeight;
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
		if(isColliding())
		{
			isInScreen = false;
			return;
		}
		
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

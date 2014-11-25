package com.engine;

import java.util.ArrayList;


public abstract class DestructableObject extends GameObject
{
	
	protected int damageDealt;
	protected int damageReceived;
	protected int health;
    private ArrayList<String> collidableWith;
    protected boolean isColliding;
    protected int points;
    protected int speed;

	public DestructableObject(int x, int y, int objectWidth, int objectHeight,
			String imagePath, String imageId, int numFrames) 
	{
		super(x, y, objectWidth, objectHeight, imagePath, imageId, numFrames);
		
		collidableWith = new ArrayList<String>();
		damageDealt = damageReceived = 0;
		health = 100;
	}
	

	public abstract void update();
	
	public void drawObject()
	{
		super.drawObject();
	}


	public int getDamageDealt() 
	{
		return damageDealt;
	}


	public void setDamageDealt(int damageDealt)
	{
		this.damageDealt = damageDealt;
	}


	public int getHealth()
	{
		return health;
	}


	public void setHealth(int health) 
	{
		this.health = health;
	}


	public int getDamageReceived()
	{
		return damageReceived;
	}


	public void setDamageReceived(int damageReceived) 
	{
		this.damageReceived = damageReceived;
	}
	
	public int getPoints()
	{
		return points;
	}
	
	public boolean isColliding() 
	{
		return isColliding;
	}

	public void setColliding(boolean isCollidable, int damageReceived) 
	{
		this.isColliding = isCollidable;
		this.damageReceived = damageReceived;
	} 
	
	public boolean isCollidableWith(GameObject object)
	{
		for(int i =0; i<collidableWith.size(); i++)
		{
			if(object.getImageId() == collidableWith.get(i))
				return true;
		}
		
		return false;
	}
	
	public void setCollidable(String object)
	{
		collidableWith.add(object);
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
}

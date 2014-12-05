package com.engine;

import java.util.ArrayList;

/*
 * Class used to represent any object that can collide with any other object
 */
public abstract class CollidableObject extends GameObject
{
	//The damage the object deals to other object when colliding
	protected int damageDealt;
	//The damage it receives from other objects when a collision happens
	protected int damageReceived;
	//The amount of health the object still has
	protected int health;
	//The list of entities that the object can collide with
    private ArrayList<String> collidableWith;
    //Variable used to state is a collision is happening
    protected boolean isColliding;
    //The number of points the object gives when destroyed
    protected int points;
    //The movement speed of the object
    protected int speed;

	public CollidableObject(int x, int y, int objectWidth, int objectHeight,
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

	/*
	 * Method used to return the amount of damage the object deals when a collision happens
	 * @return: The damage dealt by the object
	 */
	public int getDamageDealt() 
	{
		return damageDealt;
	}

	/*
	 * Method used to set the amount of damage the object deals when a collision happens
	 * @param damageDealt: The damage dealt by the object when a collision happens
	 */
	public void setDamageDealt(int damageDealt)
	{
		this.damageDealt = damageDealt;
	}


	/*
	 * Method used to get the current amount of health the object has
	 * @return: The current amount of health
	 */
	public int getHealth()
	{
		return health;
	}

	/*
	 * Method used to set the total amount of health the object has
	 * @param: The object maximum amount of health
	 */
	public void setHealth(int health) 
	{
		this.health = health;
	}

	/*
	 * Method used to get the damage received from another object at the current time
	 * @return: The amount of damage received by the object at the current time
	 */
	public int getDamageReceived()
	{
		return damageReceived;
	}

	/*
	 * Method used to set the damage received by the object at moment
	 * @param: The object damage received at the moment
	 */
	public void setDamageReceived(int damageReceived) 
	{
		this.damageReceived = damageReceived;
	}
	
	/*
	 * Method used to get the number of points the enemy gives when destroyed
	 * @return: The number of points the enemy gives when destroyed
	 */
	public int getPoints()
	{
		return points;
	}
	
	/*
	 * Method used to verify if the object is currently colliding with another object
	 * @return: A variable that states if the object is currently colliding with another object
	 */
	public boolean isColliding() 
	{
		return isColliding;
	}

	/*
	 * Method used to set that a collision has happened.
	 * @param isCollidable: Variable that set if a collision has happened
	 * @param damageReceived: The amount of damage the collision has caused to the object
	 */
	public void setColliding(boolean isCollidable, int damageReceived) 
	{
		this.isColliding = isCollidable;
		this.damageReceived = damageReceived;
	} 
	
	/*
	 * Method used to verify if the object is collidable with another game object
	 * @param: The game object in which the collision possibility will be verified on
	 * @return: a boolean variable stating if the object can collide with the game object or not
	 */
	public boolean isCollidableWith(GameObject object)
	{
		for(int i =0; i<collidableWith.size(); i++)
		{
			if(object.getImageId().equals(collidableWith.get(i)))
				return true;
		}
		
		
		return false;
	}
	
	/*
	 * Method used to add a game entity to the list of collidable objects
	 * @param object: The name of the game entity in which collision will be possible
	 */
	public void setCollidable(String object)
	{
		collidableWith.add(object);
	}
	
	/*
	 * Method used to set the speed of the object
	 * @param speed: The speed movement the object will possess
	 */
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
}

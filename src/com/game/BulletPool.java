package com.game;

import java.util.ArrayList;
import java.util.LinkedList;

import com.engine.CollidableObject;

/*
 * Method used to create a Object Pool of bullets. It is an implementation of the Object Pool design pattern
 */
public class BulletPool
{
	//Size of the object pool
	private int size;
	//All bullets in the pool
	private LinkedList<Bullet> bullets;
	
	public BulletPool(int size)
	{
		this.size = size;
		bullets = new LinkedList<Bullet>();
	}
	
	/*
	 * Method used to instantiate all bullets that will be used in the object pool
	 * @param bulletType: The type of the bullet
	 * @param id: The bullet id
	 * @param type: The bullet type (player or enemy)
	 * @return: A boolean variable stating that the objects were successfully created
	 */
	public boolean init(String bulletType, String id, int type)
	{
		for(int i =0; i<size; i++)
		{
			Bullet bullet = new Bullet(0, 0, 0, 0, bulletType, id,1, type);
			bullets.add(bullet);
		}
		
		return true;
	}
	
	/*
	 * Method use to use a bullet from the bullet pool. The Object Pool was designed to keep all
	 * active bullets on the front of the LinkedList and the inactive ones on the end of the LinkedList
	 * @param x: The bullet initial x coordinate
	 * @param y: The bullet initial y coordinate
	 * @param speed: The bullet speed
	 * @param damage: The bullet damage
	 * @param angle: The bullet angle
	 */
	public void getBullet(int x, int y, int speed, int damage, double angle)
	{
		
		if(!bullets.getLast().isActive())
		{
			bullets.getLast().setX(x);
			bullets.getLast().setY(y);
			bullets.getLast().setSpeed(speed);
			bullets.getLast().setDamageDealt(damage);
			
			if(angle == 0)
				bullets.getLast().setMovement(Bullet.STRAIGHT_LINE);
			else
			{
				bullets.getLast().setMovement(Bullet.THREE_BULLETS);
				bullets.getLast().setAngle(angle);
			}
			
			bullets.getLast().setActive(true);
			
			bullets.push(bullets.pollLast());
			
		}
	}
	
	private void setBullet(int position, int x, int y, int speed, int damage, double angle)
	{
		
		bullets.get(position).setX(x);
		bullets.get(position).setY(y);
		bullets.get(position).setSpeed(speed);
		bullets.get(position).setDamageDealt(damage);
		bullets.get(position).setAngle(angle);
		
		if(angle == 0)
			bullets.get(position).setMovement(Bullet.STRAIGHT_LINE);
		else
			bullets.get(position).setMovement(Bullet.THREE_BULLETS);
		
		bullets.get(position).setActive(true);
	}
	
	/*
	 * Method used to select three bullets at the same time. Similat to get bullet, but need
	 * to get the last three bullets from the LinkedList
	 */
	public void getThreeBullets(int x1, int x2, int x3, int y, int speed, int damage, int angle)
	{
		int size = bullets.size();
		
		if(!bullets.getLast().isActive() 
			&& !bullets.get(size-2).isActive()
			&& !bullets.get(size-3).isActive())
		{
			setBullet(size-1, x1, y, speed, damage, 90 - angle);
			setBullet(size-2, x2, y, speed, damage, 0);
			setBullet(size-3, x3, y, speed, damage, 90 + angle);
			
			bullets.push(bullets.pollLast());
			bullets.push(bullets.pollLast());
			bullets.push(bullets.pollLast());
		}
			
	}
	
	/*
	 * Method used to update the active bullets on the pool and move the recently inactive bullets to
	 * the back of the LinkedList, allowing objects to be recycled
	 */
	public void update()
	{
		
		int size = bullets.size();
		
		for(int i =0; i<size; i++)
		{
			if(bullets.get(i).isActive())
			{
				bullets.get(i).update();
				
				if(!bullets.get(i).getIsInScreen())
				{
					bullets.get(i).clear();
					bullets.addLast(bullets.get(i));
					bullets.remove(i);
				}
			
			}
		}
		
	}
	
	/*
	 * Method used to draw active bullets on the screen
	 */
	public void drawObject()
	{
		for(int i =0; i<bullets.size(); i++)
		{
			if(bullets.get(i).isActive())
				bullets.get(i).drawObject();
		}
	}
	
	/*
	 * Method used to get all bullets from the bullet pool
	 * @return: An ArrayList containing all the bullets objects
	 */
	public ArrayList<CollidableObject> getPool()
	{
		ArrayList<CollidableObject> objects = new ArrayList<CollidableObject>();
		for (int i = 0; i < size; i++) 
		{
			if (bullets.get(i).isActive())
				objects.add(bullets.get(i));
		}
		
		return objects;
	}
	
	/*
	 * Method used to clear all bullets inside the bullet poll
	 */
	public void clear()
	{
		for(Bullet bullet : bullets)
			bullet.clear();
	}
	
	/*
	 * Method used to clean all bullets inside the bullet poll
	 */
	public void clean()
	{
		for(Bullet bullet : bullets)
			bullet.clean();
	}

}

package com.game;

import java.util.ArrayList;
import java.util.LinkedList;

import com.engine.DestructableObject;

public class BulletPool
{
	private int size;
	private LinkedList<Bullet> bullets;
	
	public BulletPool(int size)
	{
		this.size = size;
		bullets = new LinkedList<Bullet>();
	}
	
	public boolean init(String bulletType, String id, int type)
	{
		for(int i =0; i<size; i++)
		{
			Bullet bullet = new Bullet(0, 0, 0, 0, bulletType, id,1, type);
			bullets.add(bullet);
		}
		
		return true;
	}
	
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
	
	public void getThreeBullets(int x1, int x2, int x3, int y, int speed, int damage, int angle)
	{
		int size = bullets.size();
		
		if(!bullets.getLast().isActive() 
			&& !bullets.get(size-2).isActive()
			&& !bullets.get(size-3).isActive())
		{
			//System.out.println("Shoot three bullets");
			setBullet(size-1, x1, y, speed, damage, 90 - angle);
			setBullet(size-2, x2, y, speed, damage, 0);
			setBullet(size-3, x3, y, speed, damage, 90 + angle);
			
			bullets.push(bullets.pollLast());
			bullets.push(bullets.pollLast());
			bullets.push(bullets.pollLast());
		}
			
	}
	
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
	
	public void drawObject()
	{
		for(int i =0; i<bullets.size(); i++)
		{
			if(bullets.get(i).isActive())
				bullets.get(i).drawObject();
		}
	}
	
	public ArrayList<DestructableObject> getPool()
	{
		ArrayList<DestructableObject> objects = new ArrayList<DestructableObject>();
		for (int i = 0; i < size; i++) 
		{
			if (bullets.get(i).isActive())
				objects.add(bullets.get(i));
		}
		
		return objects;
	}
	
	public void clear()
	{
		for(Bullet bullet : bullets)
			bullet.clear();
	}
	
	public void clean()
	{
		for(Bullet bullet : bullets)
			bullet.clean();
	}

}

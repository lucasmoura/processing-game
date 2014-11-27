package com.game;

import java.util.Random;

import com.engine.DestructableObject;
import com.engine.Processing;

public class Kodanruthr extends DestructableObject implements Enemy
{
	private boolean alive;
	private int height;
	private int stopPosition;
	private int numberOfTicks;
	private boolean startRight;
	private boolean explode;
	private Explosion explosion;

	public Kodanruthr(int x, int y, String imagePath, String imageId, int numFrames)
	{
		super(x, y, 0, 0, imagePath, imageId, numFrames);
		
		startRight = false;
		alive = true;
		
		height = Processing.getInstance().getParent().height;
		
		stopPosition = height/2;
		
		setCollidable("laser");
		setCollidable("vulcanbullet");
		setCollidable("gammabullet");
		
		explosion = new Explosion(0, 0, 0, 0, "explosion.png", "explosion", 17);
		
		numberOfTicks = 0;
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
	public void shoot() 
	{
		EnemyBulletControl.getInstance().getChargeShoot(getX()+37, getY()+this.getHeight()+30, 15, 20);	
	}

	@Override
	public void move()
	{	
		if(getY() == stopPosition)
			shoot();
		
		int y = (int) (stopPosition * Math.sin((numberOfTicks * 0.5 * Math.PI)/30));
	    
		if(startRight)
			setX(getX() + speed);
		else
			setX(getX() - speed);
		
		setY(y);
		
		if(getY()<0)
		{
			alive = false;
			points = 0;
		}	
		
		numberOfTicks++;
		
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
		
		move();
		
	}
	
	public void setStart(boolean start)
	{
		startRight = start;
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

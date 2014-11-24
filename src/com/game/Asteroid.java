package com.game;

import java.util.Random;

import com.engine.DestructableObject;
import com.engine.Processing;

import processing.core.PApplet;

public class Asteroid extends DestructableObject implements Enemy
{
	
	private int speed;
	private boolean alive;
	private boolean explode;
	private Explosion explosion;

	public Asteroid(int x, int y, int objectWidth, int objectHeight,
			String imagePath, String imageId, int numFrames) 
	{
		super(x, y, objectWidth, objectHeight, imagePath, imageId, numFrames);
		
		explosion = new Explosion(0, 0, 0, 0, "explosion.png", "explosion", 17);
	
		Random rand = new Random();
		PApplet pApplet = Processing.getInstance().getParent();
		
		setX(rand.nextInt(pApplet.width));
		explosion.setX(getX());
		
		setCollidable("laser");
		setCollidable("vulcanbullet");
		setCollidable("gammabullet");
		speed = 8;
		alive = true;
		explode = false;
		
		points = 10;
	}

	@Override
	public void move()
	{
		this.position.setY(this.position.getY()+speed);
		PApplet applet = Processing.getInstance().getParent();
		
		if ( position.getY() > applet.displayHeight)
			alive = false;
		
		int interval = 40;
		currentRow = (applet.millis()/interval)%numFrames;
		
	}

	@Override
	public void shoot() 
	{
		// TODO Auto-generated method stub
		
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
				damageDealt = 0;
				return;
			}	
		}
		
		move();
		
	}
	
	public void drawObject()
	{
		if(!explode && alive)
			super.drawObject();	
		else if(explode && alive)
			explode();
	}
	
	@Override
	public boolean isAlive()
	{
		return alive;
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
			int type = 2;
			PowerUpFactory.getInstance().createPowerUp(type, getX(), getY());	
		}
	}

}

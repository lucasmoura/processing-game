package com.game;

import com.engine.SoundManager;

/*
 * Class that implements enemy Kodanswyn. It extends a basic enemy changing the move and fire mechanics of it
 */
public class Kodanswyn extends BasicEnemy
{
	private int gravitationalFieldPower;
	private boolean gravitationalForce;

	
	public Kodanswyn(int x, int y, String imagePath, String imageId, int numFrames, 
			int gravitationalFieldPower)
	{
		super(x, y, 0, 0, imagePath, imageId, numFrames);
		
		this.gravitationalFieldPower = gravitationalFieldPower;
		gravitationalForce = false;
		points = 30;
	}
	
	
	@Override
	protected void setFixedPosition()
	{
		stopPosition = 500;	
	}
	
	@Override
	public void move()
	{
		if(getY() + speed >= stopPosition)
		{
			reachedPosition = true;
			return;
		}	
		else
			setY(getY()+speed);
	}
	
	public int getGravitationalFieldPower()
	{
		if(startRight)
			return gravitationalFieldPower*-1;
		else
			return gravitationalFieldPower;
	}
	
	@Override
	protected void willFire()
	{
		//Start gravitational push only when the enemy has reached its fixed position
		if(reachedPosition)
		{
			gravitationalForce = true;
			SoundManager.getInstance().playSound("gravityfield", false);
		}	
	}
	
	@Override
	public void update()
	{
		super.update();
		
		if(explode)
			gravitationalForce = false;
	}
	
	public boolean checkGravitationalForce()
	{
		return gravitationalForce;
	}
	
}

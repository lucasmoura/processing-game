package com.game;

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
		if(reachedPosition)
			gravitationalForce = true;
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

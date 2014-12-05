package com.game;

import com.engine.CollidableObject;
import com.engine.Processing;

public class PowerUp extends CollidableObject
{

	private int height;
	private int type;
	private int speed;
	
	public PowerUp(int x, int y, String imagePath, String imageId, int type) 
	{
		super(x, y, 0, 0, imagePath, imageId, 1);
		
		height = Processing.getInstance().getPApplet().height - objectHeight;
		speed = 10;
		this.type = type;
		
	}

	@Override
	public void update() 
	{
		if(getY()<= height)
			setY(getY() + speed);
		
	}

	public int getType() 
	{
		return type;
	}
	
	

}

package com.game;

import com.engine.DestructableObject;
import com.engine.Processing;

public class PowerUp extends DestructableObject
{

	private int height;
	private int type;
	private int speed;
	
	public PowerUp(int x, int y, String imagePath, String imageId, int type) 
	{
		super(x, y, 0, 0, imagePath, imageId, 1);
		
		height = Processing.getInstance().getParent().height - objectHeight;
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

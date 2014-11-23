package com.game;

import processing.core.PApplet;

import com.engine.GameObject;
import com.engine.Processing;

public class Explosion extends GameObject
{
	private int counter;
	private boolean over;

	public Explosion(int x, int y, int objectWidth, int objectHeight,
			String imagePath, String imageId, int numFrames)
	{
		super(x, y, objectWidth, objectHeight, imagePath, imageId, numFrames);
		
		counter = 0;
		over = false;
		
	}

	@Override
	public void update() 
	{
		int interval = 40;
		PApplet applet = Processing.getInstance().getParent();
		
		currentRow = (applet.millis()/interval)%numFrames;
		
		super.drawObject();
		
		if(counter++ == numFrames)
			over = true;
	}

	public boolean isOver() 
	{
		return over;
	}

}

package com.game;

import processing.core.PApplet;

import com.engine.GameObject;
import com.engine.Processing;
import com.engine.SoundManager;

public class Explosion extends GameObject
{
	private int counter;
	private boolean over;
	private boolean playSound;

	public Explosion(int x, int y, int objectWidth, int objectHeight,
			String imagePath, String imageId, int numFrames)
	{
		super(x, y, objectWidth, objectHeight, imagePath, imageId, numFrames);
		
		counter = 0;
		over = false;
		playSound = true;
		
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
		
		if(playSound)
		{
			SoundManager.getInstance().playSound("explosion", false);
			playSound = false;
		}
		
		
	}

	public boolean isOver() 
	{
		return over;
	}

}

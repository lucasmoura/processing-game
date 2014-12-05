package com.game;

import processing.core.PApplet;

import com.engine.GameObject;
import com.engine.Processing;
import com.engine.SoundManager;

/*
 * Class used to generate the explosion animation once a game entity is destroyed in the game
 */
public class Explosion extends GameObject
{
	//Interval used to change explosion sprites
	private int counter;
	//Variable used to check if the explosion animation is over
	private boolean over;
	//Used to see if it is required to play an explosion sound effect
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
		PApplet applet = Processing.getInstance().getPApplet();
		
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

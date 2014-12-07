package com.main;

import android.view.MotionEvent;

import com.engine.Processing;
import com.engine.SoundManager;
import com.game.Game;

import processing.core.*;

public class MainActivity extends PApplet  //PApplet in fact extends android.app.Activity
{
	int input = 0;
	int x;
	int y;
	int type;
	

    public void setup() 
    {
    	//Set the orientation of the screen
    	orientation(LANDSCAPE);
    	Processing.getInstance().setPApplet(this);
        Game.getInstance().init();
        //Set the game frame rate
        frameRate(30);
    }

    public void draw() 
    {
       //Reset canvas
       background(0);
     
       //Handle the game inputs
       if(input == 1)
       {
    	   Game.getInstance().handleInput(x, y, type);
    	   input = 0;
       }
       
       //Game loop that both update and renders the current state
       Game.getInstance().gameLoop();
    }
    
    /*
     * Method used to allow the application to handle multitouch events. This was used to allow the
     * player to change movement pattern while still pressing another movement button
     */
    @Override
    public boolean surfaceTouchEvent(MotionEvent event)
    {

    	input =1;
    	x = (int)event.getX(0);
    	y = (int)event.getY(0);
    	  
    	  if(event.getActionMasked() == MotionEvent.ACTION_UP)
    		  type = 0;
    	  else
    		  type = 1;
    	 
    	  return super.surfaceTouchEvent(event);
    	}
    
  
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	
    	/*
    	 * Clean the media player and sound pool associated with the game
    	 */
    	SoundManager.getInstance().clean();
    }
    
    @Override
    public void onStop()
    {
    	super.onStop();
    	//Stop the music being played
    	SoundManager.getInstance().stop();
    }
    
    @Override
    public void onRestart()
    {
    	super.onRestart();
    	//Resumes the music thas was being played when the activity stopped
    	SoundManager.getInstance().resume();
    }

}
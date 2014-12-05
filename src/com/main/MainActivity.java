package com.main;

import android.view.MotionEvent;

import com.engine.Processing;
import com.engine.SoundManager;
import com.lonesurvivor.Game;

import processing.core.*;

public class MainActivity extends PApplet  //PApplet in fact extends android.app.Activity
{
	int input = 0;
	int x;
	int y;
	int type;
	
    public void setup() 
    {
    	orientation(LANDSCAPE);
    	Processing.getInstance().setPApplet(this);
        Game.getInstance().init(this);
        frameRate(24);
    }

    public void draw() 
    {
       background(0);
       
       if(input == 1)
       {
    	   Game.getInstance().getStateMachine().handleInput(x, y, type);
    	   input = 0;
       }
       
       Game.getInstance().gameLoop();
    }
    
    @Override
    public boolean surfaceTouchEvent(MotionEvent event)
    {

    	input =1;
    	x = (int)event.getX(0);
    	y = (int)event.getY(0);
    	  
    	  if(event.getActionMasked() == MotionEvent.ACTION_UP)
    	  {
    		  type = 0;
    		  
    	  }
    	  else
    	  {
    		  type = 1;
    	  }

    	 
    	  return super.surfaceTouchEvent(event);
    	}
    
  
    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	
    	SoundManager.getInstance().clean();
    }
    
    @Override
    public void onStop()
    {
    	super.onStop();
    	SoundManager.getInstance().stop();
    }
    
    @Override
    public void onRestart()
    {
    	super.onRestart();
    	SoundManager.getInstance().resume();
    }
    
//    public void mouseReleased()
//    {
//    	int x = Processing.getInstance().getParent().mouseX;
// 	    int y = Processing.getInstance().getParent().mouseY;
//    	
//    	Game.getInstance().getStateMachine().handleInput(x, y, 0);
//    }
//    
//    public void mousePressed()
//    {
//    	int x = Processing.getInstance().getParent().mouseX;
// 	    int y = Processing.getInstance().getParent().mouseY;
//    	
//    	Game.getInstance().getStateMachine().handleInput(x, y, 1);
//    }
}
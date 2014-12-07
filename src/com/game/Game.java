package com.game;

import com.engine.GameStateMachine;
import com.engine.Processing;
import com.states.MenuState;

import processing.core.*;

/*
 * Class used to handle the game initial configurations and the game loop. This class was designed as a singleton
 * class to allow that any state could access the game state machine and change states accordingly
 */
public class Game 
{	    
	  private static Game instance = null; 
	  private boolean presentation01;
	  private boolean presentation02;
	  private boolean presentationOver;
	  private PApplet parent;
	  
	  final int WAIT_TIME = (int) (2.0 * 1000);
	  int startTime;
	 
	  private GameStateMachine gameStateMachine; 
	  boolean finish;
	  
	  private Game()
	  {
	    finish = false;  
	    
	  }

	  public static Game getInstance()
	  {
	    if(instance == null)
	      instance = new Game();
	    
	    return instance;
	  }  

	  /*
	   * Method used to init the necessary elements of the game
	   */
	  public void init()
	  {
		
	    gameStateMachine = new GameStateMachine();
	    presentation01 = false;
	    presentation02 = false;
	    presentationOver = false;
	    this.parent = Processing.getInstance().getPApplet();
	  }
	  
	  /*
	   * Method used to display the presentation screens of the game
	   */
	  public void presentation()
	  {
	    PImage presentation01 = parent.loadImage("screens/presentation.png");
	    presentation01.resize(1980, 1080);
	    presentation01.resize(1980, 1080);
	    presentation01.loadPixels();
	 
	    PImage presentation02 = parent.loadImage("screens/secondpresentation.png");
	    presentation02.resize(1980, 1080);
	    presentation02.loadPixels();
	    
	    if(this.presentation01 && this.presentation02)
	    {
	    	this.presentationOver = delay();
	    	gameStateMachine.changeState(new MenuState());
	    }	 
	    if(this.presentation01)
	     this.presentation02 = presentation02(presentation02);
	    else if(this.presentation01 == false)
	     this.presentation01 = presentation01(presentation01); 
	  }
	  
	  private boolean presentation01(PImage presentation01)
	  {
	    parent.background(0);
	    parent.image(presentation01, 0, 0);
	  
	    return true;    
	  
	  }

	  private boolean presentation02(PImage presentation02)
	  {
	    parent.background(0);
	    parent.image(presentation02, 0, 0);
	    
	    return delay();
	  
	  }
	  
	  private boolean delay()
	  {
		   startTime = parent.millis();
		    
		   while(hasFinished()==false);
		   
		   return true;
	  }
	  
	  public void gameLoop()
	  {
	
	   if(presentationOver==false)
		   presentation();
	   else
	   {
		   gameStateMachine.update();
		   gameStateMachine.render();
	   }
	    
	  }
	  
	  public GameStateMachine getStateMachine()
	  {
	    return gameStateMachine;
	  }  
	  
	  public boolean hasFinished()
	  {
		    return parent.millis() - startTime > WAIT_TIME;
	  }

	public void handleInput(int x, int y, int type)
	{
		if(presentationOver)
			gameStateMachine.handleInput(x, y, type);
		
	}
	  
}

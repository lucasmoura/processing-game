package com.states;

import java.util.ArrayList;

import com.engine.Button;
import com.engine.GameObject;
import com.engine.Processing;
import com.engine.TextureManager;
import com.lonesurvivor.Game;

import android.util.Log;

public class MenuState implements GameState 
{  
	  private String menuId = "MENU";
	  private Button playButton;
	  private Button creditButton;
	  private Button settingsButton;
	  private Button tutorialButton;
	  
	  private final String LOG_TAG = "MenuState";

	  private ArrayList<GameObject> menuObjects;
	  
	  public MenuState()
	  {
	    menuObjects = new ArrayList<GameObject>();
	  }
	  
	  private void createMenu()
	  {
	    int space = 300;
	    int verticalSpace = 300;
	    
	    playButton = new Button(0, 0, "play.png", "play", 2, true);
	    int playx = space;
	    int playy = Processing.getInstance().getParent().displayHeight/2;
	    playy = playy - playButton.getHeight()/2;
	    playButton.setPosition(playx, playy);
	    
	    settingsButton = new Button(0, 0, "settings.png", "settings", 2, true);
	    int settingsx = space;
	    int settingsy = Processing.getInstance().getParent().displayHeight/2;
	    settingsy = settingsy - settingsButton.getHeight()/2 + verticalSpace;
	    settingsButton.setPosition(settingsx, settingsy);
	    
	    tutorialButton = new Button(0, 0, "tutorial.png", "tutorial", 2, true);
	    int tutorialx = 1000;
	    int tutorialy = playy;
	    tutorialButton.setPosition(tutorialx, tutorialy);
	    
	    creditButton = new Button(0, 0, "credits.png", "credits", 2, true);
	    int creditsx = 1000;
	    int creditsy = settingsy;
	    creditButton.setPosition(creditsx, creditsy);
	    
	    menuObjects.add(playButton);
	    menuObjects.add(settingsButton);
	    menuObjects.add(tutorialButton);
	    menuObjects.add(creditButton);
	  }
	  
	  public void update()
	  {
	    for(int i =0; i<menuObjects.size(); i++)
	    {
	      //Log.v(LOG_TAG, "rendering: "+menuObjects.get(i).getClass().getName());
	      menuObjects.get(i).update();
	    }
	  }
	  
	  public void render()
	  {
	    
	   TextureManager.getInstance().drawObject("background", 0, 0);
	   TextureManager.getInstance().drawObject("title", 
			   TextureManager.getInstance().getPApplet().displayWidth/2 - 1338/2, 100);
	   
	    for(int i =0; i<menuObjects.size(); i++)
	      menuObjects.get(i).drawObject();
	    
	  }  
	  
	  public boolean onEnter()
	  {
	    if(TextureManager.getInstance().loadGameImage("space.jpg", "background") == false)
	      return false;
	    
	    TextureManager.getInstance().getImage("background").resize(1980, 1120);
	    TextureManager.getInstance().getImage("background").loadPixels();
	    
	    if(TextureManager.getInstance().loadGameImage("gametitle.png", "title") == false)
	      return false;
	    
	    createMenu();
	    return true;
	    
	  }
	  
	  public boolean onExit()
	  {
	    return true;
	  } 
	  
	  public void enable()
	  {
	    
	  } 
	  
	  public void disable()
	  {
	    
	  } 
	  
	  public String getStateId()
	  {
	    return menuId;
	  }
	  
	  public void menuToPlay()
	  {
		  Game.getInstance().getStateMachine().changeState(new PlayState());
	  }
	  
	  public void mouseReleased(int x, int y)
	  {
		  
//		 Log.i(LOG_TAG, "Mene mouse released");
//		 Log.i(LOG_TAG, String.valueOf(playButton.touchOnMe(x, y)));
//		 Log.i(LOG_TAG, "x: "+x + ", y: "+y);
		
	    if (playButton.touchOnMe(x, y))
	    {
	     menuToPlay();
	    }
	   
	  }
	  
	  public void mousePressed(int x, int y)
	  {
		  
	  }
	  
	}  
	  
	    

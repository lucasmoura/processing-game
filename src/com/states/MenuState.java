package com.states;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;

import android.content.SharedPreferences;

import com.engine.Button;
import com.engine.GameObject;
import com.engine.Processing;
import com.engine.SoundManager;
import com.engine.TextureManager;
import com.game.Game;

/*
 * Class used to represent the main menu of the game. From this state it is possible to move to the play state,
 * credit state and settings state.
 */
public class MenuState implements GameState 
{  
	  private String menuId = "MENU";
	  private Button playButton;
	  private Button creditButton;
	  private Button settingsButton;
	  private long bestScore;
	  private PFont bestScoreText;
	  private PApplet applet;
	  

	  private ArrayList<GameObject> menuObjects;
	  
	  public MenuState()
	  {
	    menuObjects = new ArrayList<GameObject>();
	  }
	  
	  private void createMenu()
	  {
	    int verticalSpace = 200;
	    
	    playButton = new Button(0, 0, "buttons/playMenu.png", "play", 2, true);
	    int playx = applet.width/2 - playButton.getWidth()/2;
	    int playy = applet.displayHeight/2 + verticalSpace;
	    playy = playy - playButton.getHeight()/2;
	    playButton.setPosition(playx, playy);
	    
	    settingsButton = new Button(0, 0, "buttons/settingsIcon.png", "settings", 2, true);
	    int settingsx = applet.width - settingsButton.getWidth();
	    int settingsy = applet.height - settingsButton.getHeight();
	    settingsButton.setPosition(settingsx, settingsy);
	    
	    creditButton = new Button(0, 0, "buttons/credits.png", "credits", 2, true);
	    int creditsx = playx;
	    int creditsy = playy + verticalSpace;
	    creditButton.setPosition(creditsx, creditsy);
	    
	    menuObjects.add(playButton);
	    menuObjects.add(settingsButton);
	    menuObjects.add(creditButton);
	  }
	  
	  public void update()
	  {
	    for(int i =0; i<menuObjects.size(); i++)
	      menuObjects.get(i).update();
	  }
	  
	  public void render()
	  {
	    
	   TextureManager.getInstance().drawObject("background", 0, 0);
	   TextureManager.getInstance().drawObject("title", applet.displayWidth/2 - 1338/2, 100);
	   TextureManager.getInstance().drawObject("bestscore", playButton.getX() - 150 , playButton.getY() - 250);
	   
	   applet.textFont(bestScoreText, 85);
	   applet.fill(255);
	   applet.text(String.valueOf(bestScore), playButton.getX() - 150 + 630 , playButton.getY() +  -170);
	   
	    for(int i =0; i<menuObjects.size(); i++)
	      menuObjects.get(i).drawObject();
	    
	  }  
	  
	  public boolean onEnter()
	  {
		 applet = Processing.getInstance().getPApplet();  
		  
	    if(TextureManager.getInstance().loadGameImage("backgrounds/space.jpg", "background") == false)
	      return false;
	    
	    TextureManager.getInstance().getImage("background").resize(1980, 1120);
	    TextureManager.getInstance().getImage("background").loadPixels();
	    
	    TextureManager.getInstance().loadGameImage("titles/bestscoretitle.png", "bestscore");
	    
	    if(TextureManager.getInstance().loadGameImage("titles/gametitle.png", "title") == false)
	      return false;
	    
	    createMenu();
	    loadSounds();
	    updateBestScore();
	    
	    bestScoreText = applet.createFont("Arial", 16, false);
	    
	    SoundManager.getInstance().playSound("menutheme", true);
	    
	    System.out.println("Passou aqui");
	    return true;
	    
	  }
	  
	  /*
	   * Method used to read the best score so far from the shared preferences file associated with the game
	   */
	  private void updateBestScore()
	  {
		  SharedPreferences score = Processing.getInstance().getPApplet().getSharedPreferences("score", 0);
		  long bestScore =  score.getLong("score", 0);
		  
		  this.bestScore = bestScore;

	  }
	  
	  private void loadSounds()
	  {
		  SoundManager.getInstance().addMusic("menutheme", "sound/music/Black_Vortex.ogg", true);
		  SoundManager.getInstance().addMusic("click", "sound/effect/mechanical_metallic_rattle-001.ogg", false);
	  }
	  
	  public boolean onExit()
	  {
		 for(GameObject object: menuObjects) 
			 object.clean();
		 
		 TextureManager.getInstance().clearFromTextureMap("background");
		 TextureManager.getInstance().clearFromTextureMap("title");
		 TextureManager.getInstance().clearFromTextureMap("bestscore");
		 
		 SoundManager.getInstance().stop();
		 SoundManager.getInstance().clearFromSoundManager("menutheme", true);
		 SoundManager.getInstance().clearFromSoundManager("click", false);
		 
	     return true;
	  } 
	  
	  public String getStateId()
	  {
	    return menuId;
	  }
	  
	  public void menuToPlay()
	  {
		  Game.getInstance().getStateMachine().changeState(new PlayState());
	  }
	  
	  public void menuToSettings()
	  {
		  Game.getInstance().getStateMachine().pushState(SettingsState.getInstance());
	  }
	  
	  public void menuToCredits()
	  {
		  Game.getInstance().getStateMachine().pushState(new CreditsState());
	  }
	  
	  public void mouseReleased(int x, int y)
	  {
		
	    if (playButton.touchOnMe(x, y))
	    {
	    	SoundManager.getInstance().playSound("click", false);
	    	menuToPlay();
	    	return;
	    }
	    
	    if(settingsButton.touchOnMe(x, y))
	    {
	    	SoundManager.getInstance().playSound("click", false);
	    	menuToSettings();
	    	settingsButton.setPressed(false);
	    	return;
	    }
	    
	    if(creditButton.touchOnMe(x, y))
	    {
	    	SoundManager.getInstance().playSound("click", false);
	    	menuToCredits();
	    	creditButton.setPressed(false);
	    	return;
	    }
	    
	   
	  }
	  
	  public void mousePressed(int x, int y)
	  {
		  
	  }
	  
	}  
	  
	    

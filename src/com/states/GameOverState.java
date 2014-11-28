package com.states;

import processing.core.PApplet;
import processing.core.PFont;

import com.engine.Button;
import com.engine.Processing;
import com.engine.SoundManager;
import com.engine.TextureManager;
import com.lonesurvivor.Game;

public class GameOverState implements GameState
{
	
	private String gameOverId = "GAMEOVER";
	private String gameOverTitle;
	private int width;
	private int height;
	private Button newgame;
	private Button menu;
	private long score;
	private PFont playerFinalScore;
	private PApplet applet;
	
	
	@Override
	public void update() 
	{
		
	}

	@Override
	public void render()
	{
		TextureManager.getInstance().drawObject("background", 0, 0);
		TextureManager.getInstance().drawObject(gameOverTitle, width/2 - 343, 100);
		TextureManager.getInstance().drawObject("scoreIcon", width/2 - 283, height/2-200-40);
		
		applet.textFont(playerFinalScore, 85);
		applet.fill(255);
		applet.text(String.valueOf(score), width/2 +40, height/2 -170);
		
		newgame.drawObject();
		menu.drawObject();
		
	}

	@Override
	public boolean onEnter()
	{
		applet = Processing.getInstance().getParent();
		width = applet.width;
		height = applet.height;
		
		 if(TextureManager.getInstance().loadGameImage("space.jpg", "background") == false)
		      return false;
		
		gameOverTitle = "gameOverTitle";
		TextureManager.getInstance().loadGameImage("gameOverTitle.png", gameOverTitle);
		TextureManager.getInstance().loadGameImage("score.png", "scoreIcon");
		
		newgame = new Button(0, 0, "buttons/newgame.png", "newgame", 2, true);
		int newgamex = width/2 - newgame.getWidth()/2;
		int newgamey = height/2 +50 - newgame.getHeight()/2;
		newgame.setX(newgamex);
		newgame.setY(newgamey);
		
		menu = new Button(0, 0, "buttons/menubutton.png", "menu", 2, true);
		int menux = width/2 - menu.getWidth()/2;
		int menuy = newgamey + 400 - menu.getHeight()/2;
		menu.setX(menux);
		menu.setY(menuy);
		
		playerFinalScore = applet.createFont("Arial", 16, false);
		
		return true;
	}

	@Override
	public boolean onExit() 
	{
		
		SoundManager.getInstance().stop();
		TextureManager.getInstance().clearFromTextureMap(gameOverTitle);
		TextureManager.getInstance().clearFromTextureMap("background");
		TextureManager.getInstance().clearFromTextureMap("scoreIcon");
		
		menu.clean();
		newgame.clean();
		
		return false;
	}

	@Override
	public void enable()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disable() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(int x, int y)
	{
		if(newgame.touchOnMe(x, y))
		{
			Game.getInstance().getStateMachine().changeState(new PlayState());
			return;
		}	
		
		if(menu.touchOnMe(x, y))
		{
			Game.getInstance().getStateMachine().changeState(new MenuState());
			return;
		}	
		
	}

	@Override
	public void mousePressed(int x, int y) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getStateId()
	{
		return gameOverId;
	}
	
	public void setScore(long score)
	{
		this.score = score;
	}

}

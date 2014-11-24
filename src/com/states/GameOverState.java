package com.states;

import com.engine.Button;
import com.engine.Processing;
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
	
	
	@Override
	public void update() 
	{
		
	}

	@Override
	public void render()
	{
		TextureManager.getInstance().drawObject("background", 0, 0);
		TextureManager.getInstance().drawObject(gameOverTitle, width/2 - 343, 100);
		
		newgame.drawObject();
		menu.drawObject();
		
	}

	@Override
	public boolean onEnter()
	{
		width = Processing.getInstance().getParent().width;
		height = Processing.getInstance().getParent().height;
		
		 if(TextureManager.getInstance().loadGameImage("space.jpg", "background") == false)
		      return false;
		
		gameOverTitle = "gameOverTitle";
		TextureManager.getInstance().loadGameImage("gameOverTitle.png", gameOverTitle);
		
		newgame = new Button(0, 0, "buttons/newgame.png", "newgame", 2, true);
		int newgamex = width/2 - newgame.getWidth()/2;
		int newgamey = height/2 - newgame.getHeight()/2;
		newgame.setX(newgamex);
		newgame.setY(newgamey);
		
		menu = new Button(0, 0, "buttons/menubutton.png", "menu", 2, true);
		int menux = width/2 - menu.getWidth()/2;
		int menuy = newgamey + 400 - menu.getHeight()/2;
		menu.setX(menux);
		menu.setY(menuy);
		
		return true;
	}

	@Override
	public boolean onExit() 
	{
		TextureManager.getInstance().clearFromTextureMap(gameOverTitle);
		TextureManager.getInstance().clearFromTextureMap("background");
		TextureManager.getInstance().clearFromTextureMap(newgame.getImageId());
		
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
	
	

}

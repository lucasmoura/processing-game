package com.states;

import com.engine.Processing;
import com.engine.TextureManager;

public class GameOverState implements GameState
{
	
	private String gameOverId = "MENU";
	private String gameOverTitle;
	private int width;
	private int height;
	
	
	@Override
	public void update() 
	{
		
	}

	@Override
	public void render()
	{
		TextureManager.getInstance().drawObject(gameOverTitle, width/2 - 343, height/2 - 55);
		
	}

	@Override
	public boolean onEnter()
	{
		width = Processing.getInstance().getParent().width;
		height = Processing.getInstance().getParent().height;
		
		gameOverTitle = "gameOverTitle";
		TextureManager.getInstance().loadGameImage("gameOverTitle.png", gameOverTitle);
		return false;
	}

	@Override
	public boolean onExit() 
	{
		TextureManager.getInstance().clearFromTextureMap(gameOverTitle);
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
		// TODO Auto-generated method stub
		
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

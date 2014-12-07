package com.states;

import com.engine.TextureManager;
import com.game.Game;

/*
 * Class that represent the credits screen in the game. It basically displays an image showing
 * the responsible for each part of the game
 */
public class CreditsState implements GameState
{
	
	private final String stateId = "CREDITS";

	@Override
	public void update()
	{
		
	}

	@Override
	public void render() 
	{
		TextureManager.getInstance().drawObject("creditsscreen", 0, 0);
	}

	@Override
	public boolean onEnter()
	{
		TextureManager.getInstance().loadGameImage("screens/creditsscreen.png", "creditsscreen");
		
		return false;
	}

	@Override
	public boolean onExit() 
	{
		TextureManager.getInstance().clearFromTextureMap("creditsscreen");
		return true;
	}

	@Override
	public void mouseReleased(int x, int y) 
	{
		Game.getInstance().getStateMachine().popState();
		
	}

	@Override
	public void mousePressed(int x, int y)
	{
		
	}

	@Override
	public String getStateId()
	{
		
		return stateId;
	}
	
	

}

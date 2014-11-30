package com.states;

import com.engine.TextureManager;
import com.lonesurvivor.Game;

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

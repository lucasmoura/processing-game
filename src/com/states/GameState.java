package com.states;

public interface GameState
{
	  public void update();
	  public void render();
	  
	  public boolean onEnter();
	  public boolean onExit();
	  
	  public void enable();
	  public void disable();
	  
	  public void mouseReleased(int x, int y);
	  public void mousePressed(int x, int y);
	  
	  public String getStateId();
	}


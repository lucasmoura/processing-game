package com.engine;

import java.util.ArrayList;

import com.states.GameState;

import android.util.Log;

public class GameStateMachine
{
  
  private ArrayList<GameState> gameStates;
  private final String LOG_TAG = "GameStateMachine";
  
  public GameStateMachine()
  {
    gameStates = new ArrayList<GameState>(); 
  }
  
  public void pushState(GameState state)
  {
    if(state != null && state.getStateId() != back().getStateId())
    {
      if(gameStates.size() > 0)
        back().disable();
        
      gameStates.add(state);
      back().onEnter();
      back().enable();
    } 
  }
  
  public void changeState(GameState state)
  {
    if(gameStates.isEmpty() == false)
    {
      if(back().getStateId() == state.getStateId() || state == null)
        return;
      
      popState();  
    }
    
    gameStates.add(state);
    System.out.println(back().onEnter());
    
  }
  
  public void popState()
  {
    if(gameStates.isEmpty() == false)
    {
      if(back().onExit() == true)
      {
        GameState temp = back();
        gameStates.remove(gameStates.size() - 1);
        
        if(gameStates.size() > 0)
          back().enable();
        
        temp = null;
      }
    }  
  }
  
  public void update()
  {
    if(gameStates.isEmpty() == false)
      back().update();
  }
  
  public void render()
  {
	  if(gameStates.isEmpty() == false)
	  {
		  //Log.v(LOG_TAG, "Render");
		  back().render();
	  }
  }
  
  public void clean()
  {
     if(gameStates.isEmpty() == false)
        gameStates.clear();
  }
  
  private GameState back()
  {
    if(gameStates.isEmpty() == false)
      return gameStates.get(gameStates.size() - 1);
    else
      return null;  
  }    
  
  public void handleInput(int x, int y, int type)
  {
	  if(type == 0)
		  back().mouseReleased(x, y);
	  else
		  back().mousePressed(x, y);
  }
  
}

package com.engine;

import java.util.ArrayList;

import com.states.GameState;

/*
 * Class used to represent the state machine used for screen transitions in the game. However, this is a
 * general state machine, that can be used for other purposes than screen transitions.
 */
public class GameStateMachine
{
  
  //ArrayList used to store all the states that currently stored in the state machine
  private ArrayList<GameState> gameStates;
  
  public GameStateMachine()
  {
    gameStates = new ArrayList<GameState>(); 
  }
  
  /*
   * Method used to push one state over the current one. Therefore, it doesn't remove the past state and put a
   * a new one on top.
   * @param state; The game state that will be pushed on top of the current game state
   */
  public void pushState(GameState state)
  {
	//Check if the state is not null or if the current state is the same kind as the state being pushed
    if(state != null && state.getStateId() != back().getStateId())
    {
//     if(gameStates.size() > 0)
//        back().disable();
//        
      gameStates.add(state);
      back().onEnter();
      //back().enable();
    } 
  }
  
  /*
   * Method used to change states in the state machine. Therefore, it removes the past state and put the new one
   * as the top state of the state machine
   * @param state: The new current state of the state machine
   */
  public void changeState(GameState state)
  {
    if(gameStates.isEmpty() == false)
    {
      if(back().getStateId() == state.getStateId() || state == null)
        return;
      
      //Removes the current state
      popState();  
    }
    
    gameStates.add(state);
    back().onEnter();
    
  }
  
  /*
   * Method used to remove one state from the state machine. It always removes the current state of the
   * state machine
   */
  public void popState()
  {
    if(gameStates.isEmpty() == false)
    {
      if(back().onExit() == true)
      {
    	//Remove the current state  
        gameStates.remove(gameStates.size() - 1);
        
//        if(gameStates.size() > 0)
//          back().enable();
        
      }
    }  
  }
  
  /*
   * Method used to update the current state of the state machine
   */
  public void update()
  {
    if(gameStates.isEmpty() == false)
      back().update();
  }
  
  /*
   * Method used to render the current state og the state machine
   */
  public void render()
  {
	  if(gameStates.isEmpty() == false)
		  back().render();
  }
  
  /*
   * Method used to clean the state machine
   */
  public void clean()
  {
     if(gameStates.isEmpty() == false)
        gameStates.clear();
  }
  
  /*
   * Method used to get the top element of the state machine
   */
  private GameState back()
  {
    if(gameStates.isEmpty() == false)
      return gameStates.get(gameStates.size() - 1);
    else
      return null;  
  }    
  
  /*
   * Method used to handle the user inputs for the current state of the state machine
   * @param x:    The x position of the input
   * @param y:    The y position of the input
   * @param type: The type of the input, 0 if is a mouseReleased input or 1 if a mousePressed one 
   */
  public void handleInput(int x, int y, int type)
  {
	  if(type == 0)
		  back().mouseReleased(x, y);
	  else
		  back().mousePressed(x, y);
  }
  
}

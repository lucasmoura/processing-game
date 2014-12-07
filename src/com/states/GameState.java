package com.states;

/*
 * Interface used to model the state used in the game. This was design with the fact the states in the game
 * would be screens.
 */
public interface GameState
{
	 /*
	  * Method used to update the game state
	  */
	  public void update();
	  
	  /*
	   * Method used to draw the objects associated with the game state on the screen
	   */
	  public void render();
	  
	  /*
	   * Load the necessary objects that are going to be used on the state
	   */
	  public boolean onEnter();
	  
	  /*
	   * Free the load objects that were used in the state
	   */
	  public boolean onExit();
	  
	  /*
	   * Methods used to handle inputs in the state. mouseReleased is used when the user release the screen
	   * after a click and mousePressed when the user is currently pressing the screen
	   * @param x: The x coordinate of the input event
	   * @param y: The y coordinate of the input event
	   */
	  public void mouseReleased(int x, int y);
	  public void mousePressed(int x, int y);
	  
	  /*
	   * Method used to get the state id of the state
	   * @return: A String representing the state id
	   */
	  public String getStateId();
	}


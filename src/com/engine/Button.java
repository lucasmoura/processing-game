package com.engine;



/*
 * Class used to model any button that will be present in the game
 */
public class Button extends GameObject
{
	  /*
	   * Variables used to represent the state of the button
	   * OUT - No click on the button
	   * CLICKED - The user has clicked the button, and if the button has more than one sprite, it should
	   * change accordingly
	   */
	  public static final int OUT = 0;
	  public static final int CLICKED = 1;
	  
	  //Variable used to verify if the button has more than one sprite and if it should be animated
	  private boolean animate;
	  //Variable used to verify is the button is being pressed
	  private boolean isPressed;
	  
	  public Button(int x, int y, String imagePath, String imageId,
			  int numFrames, boolean animate)
	  {
	    super(x, y, 0, 0, imagePath, imageId, numFrames);
	    currentFrame = OUT;
	    currentRow = OUT;
	    this.animate = animate;
	    
	    isPressed = false;
	    
	  	    
	  }
	  
	  public void drawObject()
	  {
	    super.drawObject();
	  }
	  
	  public void update()
	  {
	  }
	  
	  /*
	   * Method used to verify if the button has received a click input
	   * @param x: x coordinate of the click event
	   * @param y: y coordinate of the click event
	   * @return   a boolean variable stating if the click occurred on the button rectangle or not
	   */
	  public boolean touchOnMe(int x, int y)
	  {
	    
		//Check if the click event is inside the button rectangle
	    if(x < (position.getX() + getWidth()) &&
	        x > position.getX() &&
	        y < (position.getY() + getHeight()) &&
	        y > (position.getY()))
	    {
	        if(animate)
	           currentRow = CLICKED;
	           
	        return true;   
	         
	    }
	    else
	    {
	       if(animate)
	         currentRow = OUT;
	         
	       return false;  
	         
	    }
	  } 
	  
	  
	  public void clean()
	  {
	    TextureManager.getInstance().clearFromTextureMap(imageId);
	  }
	  
	  /*
	   * Method used to verify if the button is currently being pressed
	   * @return a boolean stating if the button is being pressed or not
	   */
	  public boolean isPressed()
	  {
		  return isPressed;
	  }
	  
	  /*
	   * Method used to set a button as pressed
	   * @param value: a boolean that states if the button is pressed o not
	   */
	  public void setPressed(boolean value)
	  {
		  //Set the current row of the sprite according to the value parameter
		  if(!value)
			  currentRow = OUT;
		  else
			  currentRow = CLICKED;
		  
		  isPressed = value;
	  }
	  
	}  
	  
	  

package com.engine;



public class Button extends GameObject
{
	  
	  public static final int OUT = 0;
	  public static final int CLICKED = 1;
	  
	  private boolean animate;
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
	  
	  public boolean touchOnMe(int x, int y)
	  {
	    
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
	  
	  public boolean isPressed()
	  {
		  return isPressed;
	  }
	  
	  public void setPressed(boolean value)
	  {
		  isPressed = value;
	  }
	  
	}  
	  
	  

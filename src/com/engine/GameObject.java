package com.engine;


import java.util.ArrayList;

import android.util.Log;

public abstract class GameObject
{
    protected Vector2D position;
    protected int objectWidth;
    protected int objectHeight;
    protected int numFrames;
    protected int currentRow;
    protected int currentFrame;
    protected String imageId;

    
    public GameObject(int x, int y, int objectWidth, int objectHeight, String imagePath, String imageId, int numFrames)
    {
      this.position = new Vector2D(x, y);
      this.objectWidth = objectWidth;
      this.objectHeight = objectHeight;
      this.imageId = imageId;
      
      this.currentRow = 0;
      this.currentFrame = 0;
      this.numFrames = numFrames;
      
      TextureManager.getInstance().loadGameImage(imagePath, imageId);
      
      if(objectHeight == 0)
    	  this.objectHeight = TextureManager.getInstance().getImage(imageId).height/numFrames;
      
      if(objectWidth == 0)
    	  this.objectWidth = TextureManager.getInstance().getImage(imageId).width;
      
    }
    
    public void drawObject()
    {
    	TextureManager.getInstance().drawFrame(imageId, position.getX(), position.getY(), objectWidth,
    		  objectHeight, currentRow, currentFrame);
     
     //Log.v(LOG_TAG, "Draw object: "+value);
    }
    
    public abstract void update();
    
    public void clean()
    {
      TextureManager.getInstance().clearFromTextureMap(imageId);
    }  
    
    public void setPosition(int x, int y)
    {
      position.setX(x);
      position.setY(y);
    }  
    
    public int getX()
    {
      return position.getX();
    }
    
    public void setX(int x)
    {
      position.setX(x);
    }  
    
    public int getY()
    {
      return position.getY();
    }
    
    public void setY(int y)
    {
      position.setY(y);
    } 
    
    public int getWidth()
    {
      return objectWidth;
    }
    
    public int getHeight()
    {
      return objectHeight;
    }

	public String getImageId() {
		return imageId;
	}

    
}

  
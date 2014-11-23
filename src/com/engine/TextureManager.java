package com.engine;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

public class TextureManager
{
	  private static TextureManager instance = null;
	  private HashMap<String, PImage> textureMap;
	  private HashMap<String, Integer> textureCount;
	  private PApplet parent;
	  
	  private final String LOG_TAG = "TextureManager";
	  
	  private TextureManager()
	  {
	    textureMap = new HashMap<String, PImage>();
	    textureCount = new HashMap<String, Integer>();
	  }
	  
	  public static TextureManager getInstance()
	  {
	    if(instance == null)
	      instance = new TextureManager();
	      
	     return instance;
	  }
	  
	  public boolean loadGameImage(String imagePath, String imageId)
	  {
	    if(getImage(imageId) == null)
	    {
	      PImage image = parent.loadImage(imagePath);
	      
	      if(image != null)
	      {
	        textureMap.put(imageId, image);
	        textureCount.put(imageId, 1);
	        return true;
	      } 
	      else
	        return false;
	    }
	    else
	        textureCount.put(imageId, textureCount.get(imageId) + 1);  
	      
	    return true;
	  }  
	  
	  public boolean drawObject(String imageId, int x, int y)
	  {
	    PImage image = textureMap.get(imageId);
	    
	    if(image == null)
	      return false;
	      
	    parent.image(image, x, y);
	    return true;
	  }  
	  
	  public boolean drawFrame(String imageId, int x, int y, int objectWidth, int objectHeight
			  , int currentRow, int currentFrame)
	  {
	    if(getImage(imageId) == null)
	      return false;
	      
	    //int framex = objectWidth * currentFrame;
	    int framey = objectHeight * currentRow; 
	    
	    PImage image = getImage(imageId);
	    //System.out.println(objectHeight);
	    
	    //System.out.println("Draw: "+objectHeight);
	    //System.out.println("Expected: "+image.height/2);
	    
	    //parent.image(image.get(0, framey, objectWidth, objectHeight), x, y);
	    parent.image(image.get(0,framey,objectWidth,objectHeight), x, y);
	    return true;
	  }
	  
	  public PImage getImage(String imageId)
	  {
	    
	    if(textureMap.get(imageId) == null)
	      return null;
	    else
	      return textureMap.get(imageId); 
	  }
	  
	  public void clearTextureMap()
	  {
	     for(PImage value : textureMap.values())
	       value = null;
	    
	    textureCount.clear();
	    textureMap.clear();
	  }
	  
	  public void clearFromTextureMap(String imageId)
	  {
	    Integer image = textureCount.get(imageId);
	    
	    if(image == null)
	      return;
	    else if(image == 1)
	    {
	       textureMap.remove(imageId);
	       image = null;
	       textureCount.remove(imageId);
	    }
	    else
	      textureCount.put(imageId, textureCount.get(imageId) - 1);
	   
	  }
	  
	  public void setPApplet(PApplet parent)
	  {
		  this.parent = parent;
	  }
	  
	  public PApplet getPApplet()
	  {
		  return this.parent;
	  }
	}  
	  
package com.engine;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

/*
 * Class used to handle images in the game. This class was designed as a singleton class in
 * order to allow a single way of access to all game images through a single channel
 */
public class TextureManager
{
      //Singleton instance
	  private static TextureManager instance = null;
	  //Map from image id to a PImage
	  private HashMap<String, PImage> textureMap;
	  //Map from the image id to the number of objects that are currently using that image
	  private HashMap<String, Integer> textureCount;
	  private PApplet parent;
	  
	  
	  private TextureManager()
	  {
	    textureMap = new HashMap<String, PImage>();
	    textureCount = new HashMap<String, Integer>();
	    parent = Processing.getInstance().getPApplet();
	  }
	  
	  public static TextureManager getInstance()
	  {
	    if(instance == null)
	      instance = new TextureManager();
	      
	     return instance;
	  }
	  
	  /*
	   * Method used to load an image on the texture map
	   * @param imagePath: The path of the image
	   * @param imageId  : The imageId associated with the image that will be loaded
	   * @return         : True if the image was loaded with success, false otherwise
	   */
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
	  
	  /*
	   * Method used to draw and object on the screen
	   * @param imageId: The image id that will be used to get the image from the texture map
	   * @param x      : The x coordinate that the image will be draw on
	   * @param y      : The y coordinate that the image will be draw on
	   * @return       : A boolean variable if the image was successfully drew or not
	   */
	  public boolean drawObject(String imageId, int x, int y)
	  {
	    PImage image = textureMap.get(imageId);
	    
	    if(image == null)
	      return false;
	      
	    parent.image(image, x, y);
	    return true;
	  }  
	  
	  /*
	   * Method used to draw an object that has more than one sprite
	   * @param imageId     : The image id that will be used to get the image from the texture map
	   * @param x           : The x coordinate that the image will be draw on
	   * @param y           : The y coordinate that the image will be draw on
	   * @param objectWidth : The object width
	   * @param objectHeight: The object height
	   * @param currentRow  : The currentRow that will be draw
	   * return             : A boolean variable if the image was successfully drew or not
	   */
	  public boolean drawFrame(String imageId, int x, int y, int objectWidth, int objectHeight
			  , int currentRow)
	  {
	    if(getImage(imageId) == null)
	      return false;
	      
	    int framey = objectHeight * currentRow; 
	    
	    PImage image = getImage(imageId);
	    parent.image(image.get(0,framey,objectWidth,objectHeight), x, y);
	    return true;
	  }
	  
	  /*
	   * Method used to retrieve an image from the texture map
	   * @param imageId: The image id that will be retrieved
	   * @return       : The PImage associated with the image id or null if an associated PImage was not found
	   */
	  public PImage getImage(String imageId)
	  {
	    
	    if(textureMap.get(imageId) == null)
	      return null;
	    else
	      return textureMap.get(imageId); 
	  }
	  
	  /*
	   * Method used to clear the texture map
	   */
	  public void clearTextureMap()
	  {
	    textureCount.clear();
	    textureMap.clear();
	  }
	  
	  /*
	   * Method used to remove one image from the texture map
	   * @param imageId: The image id associated with the image that will be removed from the texture map
	   */
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
	  
	}  
	  
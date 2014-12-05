package com.engine;

/*
 * Class used to model any game object in the game. It is the general class of the game, any other game object
 * should extend it, since it is also responsible for handling the game object sprites and drawing them on the
 * screen
 */
public abstract class GameObject
{
	//Variable used to represent the position of the game object on the screen
    protected Vector2D position;
    //The width of the object
    protected int objectWidth;
    //Varaible use to represent the game object height
    protected int objectHeight;
    //Variable used to represent the number of frames the game object sprite has
    protected int numFrames;
    //Variable used to represent the current row that is being used on the game spritesheet
    protected int currentRow;
    //Variable used to represent the current frame of the animation
    protected int currentFrame;
    //The id of the image which represent the game object
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
    
    /*
     * Method used to draw the object current animation frame on the screen
     */
    public void drawObject()
    {
    	TextureManager.getInstance().drawFrame(imageId, position.getX(), position.getY(), objectWidth,
    		  objectHeight, currentRow);
     
    }
    
    /*
     * Method used to update the status of a game object
     */
    public abstract void update();
    
    /*
     * Method used to clean the object from the memory. For the application, it basically remove the object 
     * referenced image from the image repository
     */
    public void clean()
    {
      TextureManager.getInstance().clearFromTextureMap(imageId);
    }  
    
    /*
     * Method used to set the position of the game object
     * @param x: The new x coordinate of the game object
     * @param y: THe new y coordinate of the game object
     */
    public void setPosition(int x, int y)
    {
      position.setX(x);
      position.setY(y);
    }  
    
    /*
     * Method used to get the horizontal coordinate of the game object
     * @return the x coordinate of the game object
     */
    public int getX()
    {
      return position.getX();
    }
    
    /*
     * Method used to set the horizontal coordinate of the game object
     * @param x: The new x coordinate of the game object
     */
    public void setX(int x)
    {
      position.setX(x);
    }  
    
    /*
     * Method used to get the vertical coordinate of the game object
     * @return the y coordinate of the game object
     */
    public int getY()
    {
      return position.getY();
    }
    
    /*
     * Method used to set the vertical coordinate of the game object
     * @param y: The new x coordinate of the game object
     */
    public void setY(int y)
    {
      position.setY(y);
    } 
    
    /*
     * Method used to get the object width
     * @return : The object width
     */
    public int getWidth()
    {
      return objectWidth;
    }
    
    /*
     * Method used to get the object height
     * @return : The object height
     */
    public int getHeight()
    {
      return objectHeight;
    }

    /*
     * Method used to get the image id correspondent to the game object
     * @return: The correspondent image id of the game object
     */
	public String getImageId() 
	{
		return imageId;
	}

    
}

  
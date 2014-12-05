package com.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.game.AdmiralShip;

/*
 * This class was used to better handle the collision detection bottleneck. It is based on the following tutorial
 * http://gamedevelopment.tutsplus.com/tutorials/quick-tip-use-quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374
 */
public class QuadTree
{
	//Variable used to represent the maximum amount of enemies allowed before the quadtree divides itself
	private int MAX_OBJECTS = 10;
	//Variable used to represent the maximum amount of leves the quadtree can reach
	private int MAX_LEVELS = 5;
	
	//Variable used to state the current level of the quadtree
	private int level;
	//The game objects inside the quadtree
	private List<CollidableObject> objects;
	//Variable used to represent the width of the quadtree
	private int width;
	//Variable used to represent the height of the quadtree
	private int height;
	//The x position that the quadtree will be placed
	private int x;
	//The y position that the quadtree will be placed
	private int y;
	//The nodes that the quadtree will be divided once the the number of objects is higher than the number of
	//object in the quadtree
	private QuadTree[] nodes;
	
	public QuadTree(int level, int x, int y, int width, int height)
	{
		this.level = level;
		objects = new ArrayList<CollidableObject>();
		this.width = width;
		this.height = height;
		nodes = new QuadTree[4];
	}
	
	/*
	 * Method used to split the quadtree in case the total amount of object is superior to the fixed amount
	 * of objects permited per level. The quadtree will be divided into four new quadrtree, divide it given
	 * (x,y, width, height) in four distinct regions with equals width and height, but different x and y 
	 * positions
	 */
	private void split()
	{
		int subWidth = (int)(width / 2);
		int subHeight = (int)(height/ 2);
		 
	   nodes[0] = new QuadTree(level+1, x + subWidth, y, subWidth, subHeight);
	   nodes[1] = new QuadTree(level+1,x, y, subWidth, subHeight);
	   nodes[2] = new QuadTree(level+1, x, y + subHeight, subWidth, subHeight);
	   nodes[3] = new QuadTree(level+1, x + subWidth, y + subHeight,
			   						subWidth, subHeight);
	}
	
	/*
	 * Method used to find the index of the object inside the quadtree. By index,
	 * it is meant one of the four distinct position allowed by the quadtree in the (x,y) plane
	 * @return: The object index inside the quadtree
	 */
	private int getIndex(GameObject object) 
	{
		   int index = -1;
		   double verticalMidpoint = this.x + ((double)this.width/2.0);
		   double horizontalMidpoint = this.y + ((double)this.height / 2.0);
		 
		   // Object is on the top quadrant
		   boolean topQuadrant = (object.getY() < horizontalMidpoint &&
				   object.getY() + object.getHeight()< horizontalMidpoint);
		   // Object is on the bottom quadrant
		   boolean bottomQuadrant = (object.getY() > horizontalMidpoint);
		 
		   // Object is in one of the left quadrants
		   if (object.getX()< verticalMidpoint && object.getX() + object.getWidth() < verticalMidpoint)
		   {
		      if (topQuadrant) 
		        index = 1;
		      else if (bottomQuadrant)
		        index = 2;
		    }
		    // Object is on one of the right quadrants
		    else if (object.getX() > verticalMidpoint) 
		    {
		     if (topQuadrant)
		       index = 0;
		     else if (bottomQuadrant)
		       index = 3;
		     
		   }
		    else
		    {
		    	/*
		    	 * Bug that index of the player ship not being identified when on the middle of the screen
		    	 * Ugly solution since engine now must talks with game package, therefore if time allows, it
		    	 * needs to be fixed
		    	 */
		    	if(object instanceof AdmiralShip && nodes[0] != null)
		    		index = 3;
		    }
		 
		   return index;
	}
	
	/*
	 * Method used to insert one object inside the quadtree. If the quadtree is full, the 
	 * quadtree will be splitted and the object will be inserted on a fitting new quadtree according
	 * to its index
	 * @param object: The object that will be inserted into the quadtree
	 */
	 public void insert(CollidableObject object)
	 {
		 
		 
		   if (nodes[0] != null) 
		   {
		     int index = getIndex(object);
		 
		     if (index != -1) 
		     {
		       nodes[index].insert(object);
		       return;
		     }
		   }
		 
		   objects.add(object);
		 
		   /*
		    * Divides the quadtree and place all its objects on the appropriate new quadtrees formed after
		    * spliting the current quadtree
		    */
		   if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS)
		   {
		      if (nodes[0] == null)
		         split();
		     
		    for (Iterator<CollidableObject> iterator = objects.iterator(); iterator.hasNext();)
			{
		    		CollidableObject o = iterator.next();
					int index = getIndex(o);
					
				    if(index != -1)
				    {
				    	nodes[index].insert(o);
				    	iterator.remove();
				    }
			}
		    
		   }
		   
	}
	 
	/*
	 * Method used to retrieve all objects that are collidable with the parameter object
	 * @param returnObjects: An empty List that will contain the collidable objects with the parameter
	 *                       object once the method finishes its execution
	 * @param object       : The object that the collision checking will be placed on
	 * @return             : A list containing the collidable objects with the parameter object                         
	 */
	 public List<CollidableObject> retrieve(List<CollidableObject> returnObjects,
			 											CollidableObject object) 
	 {
		   int index = getIndex(object);
		 		
		   
		   if (index != -1 && nodes[0] != null) 
		     nodes[index].retrieve(returnObjects, object);
		   
		 
		   returnObjects.addAll(objects);
		   
		   return returnObjects;
	 }
	 
	 /*
	  * Method used to get all objects inside the quadtree
	  * @param objects2: An empty ArrayList that will be populated with all the objects on the quadtree
	  * @return        : The populated ArrayList with all the objects inside the quadtree
	  */
	 public ArrayList<CollidableObject> getAllObjects(ArrayList<CollidableObject> objects2)
	 {
		 	if(nodes[0] == null)
		 	{
		 		objects2.addAll(objects);
		 		return objects2;
		 	}	
		 
		 	//Get the object for the division nodes as well, and keep calling this method recursivelly until
		 	//there is no more splitted nodes on the quadtree
			for (int i = 0; i < this.nodes.length; i++)
				this.nodes[i].getAllObjects(objects2);
			
			
			for (int i = 0, len = objects.size(); i < len; i++)
				objects2.add(objects.get(i));
			
			return objects2;
		}
	 
	/*
	 * Methos used to clear the quadtree. It basically clear every splitted node on the quadtree
	 */
	public void clear()
	{
		objects.clear();
		
		for(int i =0; i<nodes.length; i++)
		{
			if(nodes[i] != null)
			{
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}
	
	

}

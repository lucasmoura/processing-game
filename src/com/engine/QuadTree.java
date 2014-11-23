package com.engine;

import java.util.ArrayList;
import java.util.List;


//http://gamedevelopment.tutsplus.com/tutorials/quick-tip-use-quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374

public class QuadTree
{
	
	private int MAX_OBJECTS = 10;
	private int MAX_LEVELS = 5;
	
	private int level;
	private List<DestructableObject> objects;
	private int width;
	private int height;
	private int x;
	private int y;
	private QuadTree[] nodes;
	
	public QuadTree(int level, int x, int y, int width, int height)
	{
		this.level = level;
		objects = new ArrayList<DestructableObject>();
		this.width = width;
		this.height = height;
		nodes = new QuadTree[4];
	}
	
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
	
	private int getIndex(GameObject object) 
	{
		   int index = -1;
		   double verticalMidpoint = this.x + (this.width/ 2);
		   double horizontalMidpoint = this.y + (this.height / 2);
		 
		   // Object can completely fit within the top quadrants
		   boolean topQuadrant = (object.getY() < horizontalMidpoint &&
				   object.getY() + object.getHeight()< horizontalMidpoint);
		   // Object can completely fit within the bottom quadrants
		   boolean bottomQuadrant = (object.getY() > horizontalMidpoint);
		 
		   // Object can completely fit within the left quadrants
		   if (x < verticalMidpoint && object.getX() + object.getWidth() < verticalMidpoint)
		   {
		      if (topQuadrant) 
		        index = 1;
		      else if (bottomQuadrant)
		        index = 2;
		    }
		    // Object can completely fit within the right quadrants
		    else if (object.getX() > verticalMidpoint) 
		    {
		     if (topQuadrant)
		       index = 0;
		     else if (bottomQuadrant)
		       index = 3;
		     
		   }
		 
		   return index;
	}
	
	 public void insert(DestructableObject object)
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
		 
		   if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS)
		   {
		      if (nodes[0] == null)
		         split();
		 
		     int i = 0;
		     while (i < objects.size())
		     {
		       int index = getIndex(objects.get(i));
		       
		       if (index != -1)
		       {
		    	   
		    	   nodes[index].insert(objects.get(i));
		    	   objects.remove(i);
		    	}
		    	else {
		    	   i++;
		    	}
		       
		     }
		   }
		   
	}
	 
	 public List<DestructableObject> retrieve(List<DestructableObject> returnObjects,
			 											DestructableObject object) 
	 {
		   int index = getIndex(object);
		   
		   if (index != -1 && nodes[0] != null) 
		     nodes[index].retrieve(returnObjects, object);
		   
		 
		   returnObjects.addAll(objects);
		 
		   return returnObjects;
	 }
	 
	 public ArrayList<DestructableObject> getAllObjects(ArrayList<DestructableObject> objects2)
	 {
		 	if(nodes[0] == null)
		 	{
		 		objects2.addAll(objects);
		 		return objects2;
		 	}	
		 
			for (int i = 0; i < this.nodes.length; i++)
				this.nodes[i].getAllObjects(objects2);
			
			
			for (int i = 0, len = objects.size(); i < len; i++)
				objects2.add(objects.get(i));
			
			return objects2;
		}
	 
	
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

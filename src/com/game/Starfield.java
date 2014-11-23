package com.game;

import com.engine.Processing;

import processing.core.PApplet;

public class Starfield
{
	 private Star stars[];
	 private int count;
	 
	 public Starfield( int count ) 
	 {
		 this.count = count;
		 stars = new Star[count];
		 
		 PApplet parent = Processing.getInstance().getParent();
		 
		 for ( int i =0; i < count; i++) 
		 {
			 stars[i] = new Star( 
					 parent.random(parent.displayWidth),
					 parent.random(parent.displayHeight),
					 parent.random(10));
		 }
	 }
	 
	 public void drawObject() 
	 {
		 PApplet parent = Processing.getInstance().getParent();
		 parent.strokeWeight(8);
		 
		 for (int i =0; i < count; i++)
		 {
			 parent.stroke( stars[i].getZ() * 25 );
			 parent.point( stars[i].getX(), stars[i].getY() );
		 
			
		 }	 
	 
	}
	 
	 public void update()
	 {
		 PApplet parent = Processing.getInstance().getParent();
		 for (int i =0; i < count; i++)
		 { 
			 stars[i].setY(stars[i].getY()- stars[i].getZ());
			 
			 if (stars[i].getY() < 0) 
			 { 
				 stars[i].setX(parent.random(parent.displayWidth));
				 stars[i].setY(parent.random(parent.displayWidth));
				 stars[i].setZ(PApplet.sqrt(parent.random(100)));
			 }	 
		 }
	 }
}	 


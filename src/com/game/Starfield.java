package com.game;

import com.engine.Processing;

import processing.core.PApplet;

/*
 * This class was based on a class with the same name found on:
 * www.local-guru.net/projects/processing_tutorial/tutorial.pdf
 */

public class Starfield
{
	 private Star stars[];
	 private int count;
	 private PApplet applet;
	 
	 public Starfield( int count ) 
	 {
		 this.count = count;
		 stars = new Star[count];
		 
		 applet = Processing.getInstance().getPApplet();
		 
		 for ( int i =0; i < count; i++) 
		 {
			 stars[i] = new Star( 
					 applet.random(applet.displayWidth),
					 applet.random(applet.displayHeight),
					 applet.random(10));
		 }
	 }
	 
	 public void drawObject() 
	 {
		 PApplet parent = Processing.getInstance().getPApplet();
		 parent.strokeWeight(8);
		 
		 for (int i =0; i < count; i++)
		 {
			 parent.stroke( stars[i].getZ() * 25 );
			 parent.point( stars[i].getX(), stars[i].getY() );
		 
			
		 }	 
	 
	}
	 
	 public void update()
	 {
		 for (int i =0; i < count; i++)
		 { 
			 stars[i].setY(stars[i].getY()- stars[i].getZ());
			 
			 if (stars[i].getY() < 0) 
			 { 
				 stars[i].setX(applet.random(applet.displayWidth));
				 stars[i].setY(applet.random(applet.displayWidth));
				 stars[i].setZ(PApplet.sqrt(applet.random(100)));
			 }	 
		 }
	 }
}	 


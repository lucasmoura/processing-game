package com.engine;

import processing.core.PApplet;

public class Processing
{
	
	private static Processing instance = null;
	private PApplet parent;
	
	private Processing()
	{
		
	}
	
	public static Processing getInstance()
	{
		if(instance == null)
			instance = new Processing();
		
		return instance;
		
	}

	public PApplet getParent() 
	{
		return parent;
	}

	public void setParent(PApplet parent) 
	{
		this.parent = parent;
	}
	
	

}

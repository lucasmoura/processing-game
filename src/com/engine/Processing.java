package com.engine;

import processing.core.PApplet;

/*
 * Class used to access the PApplet variable which gives access to the processing methods.
 * This class was designed as a singleton class because there is only one instance of the PApplet object during
 * the application lifecycle, therefore this class is a single channel to access this variable.
 */
public class Processing
{
	//Singleton instance
	private static Processing instance = null;
	private PApplet applet;
	
	private Processing()
	{
		applet = null;
	}
	
	/*
	 * Method used to provide a single access channel to the class
	 * @return: The singleton instance
	 */
	public static Processing getInstance()
	{
		if(instance == null)
			instance = new Processing();
		
		return instance;
		
	}

	/*
	 * Method used to get the PApplet variable
	 * @return: The PApplet variable
	 */
	public PApplet getPApplet() 
	{
		return applet;
	}

	/*
	 * Method used to set the PApplet variable generate on the application main activity
	 * @param parent: the PApplet variable generated on the main activity
	 */
	public void setPApplet(PApplet applet) 
	{
		this.applet = applet;
	}
	
	

}

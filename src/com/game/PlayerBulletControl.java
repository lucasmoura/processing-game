package com.game;

/*
 * Class used to manage the different bullet pool associated with the player's ship. 
 * This class was designed with the singleton pattern to allow that the player would have a single access to its
 * different bullets. Therefore, this class handles the bullet pool for all player weapons, plasma, vulcan and gamma
 */
public class PlayerBulletControl 
{
	
	private BulletPool plasmaBullets;
	private BulletPool vulcanBullets;
	private BulletPool gammaBullets;
	
	public PlayerBulletControl()
	{
		plasmaBullets = new BulletPool(30);
		vulcanBullets = new BulletPool(90);
		gammaBullets = new BulletPool(30);
		
		plasmaBullets.init("laserBlue01.png", "laser", 0);
		vulcanBullets.init("laserRed01.png", "vulcanbullet", 0);
		gammaBullets.init("gamma.png", "gammabullet", 0);
	}
	
	public void clean()
	{
		
		plasmaBullets.clean();
		vulcanBullets.clean();
		gammaBullets.clean();
		
	}
	
	public void clear(int weapon)
	{
		if(weapon == 0)
			plasmaBullets.clear();
		else if(weapon == 1)
			vulcanBullets.clear();
		else
			gammaBullets.clear();
	}
	
	public BulletPool getBulletPool(int weapon)
	{
		if(weapon == 0)
			return plasmaBullets;
		else if(weapon == 1)
			return vulcanBullets;
		else
			return gammaBullets;
	}

}

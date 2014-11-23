package com.game;

public class PlayerBulletPool 
{
	
	private BulletPool plasmaBullets;
	private BulletPool vulcanBullets;
	private BulletPool gammaBullets;
	
	public PlayerBulletPool()
	{
		plasmaBullets = new BulletPool(30);
		vulcanBullets = new BulletPool(90);
		gammaBullets = new BulletPool(30);
		
		plasmaBullets.init("laserBlue01.png", "laser", 0);
		vulcanBullets.init("laserRed01.png", "vulcanbullet", 0);
		gammaBullets.init("gamma.png", "gammabullet", 0);
	}
	
	public void drawObject(int weapon)
	{
		if(weapon == 0)
			plasmaBullets.drawObject();
		else if(weapon == 1)
			vulcanBullets.drawObject();
	}
	
	public void update(int weapon)
	{
		if(weapon == 0)
			plasmaBullets.update();
		else if(weapon == 1)
			vulcanBullets.update();
	}
	
	public void clear(int weapon)
	{
		if(weapon == 0)
			plasmaBullets.clear();
		else if(weapon == 1)
			vulcanBullets.clear();
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

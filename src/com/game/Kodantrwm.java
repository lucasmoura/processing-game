package com.game;

import com.engine.Processing;

public class Kodantrwm extends BasicEnemy
{
	
	private boolean weaponType;
	private int fireCounter;
	private int secondFireCounter;

	public Kodantrwm(int x, int y, String imagePath, String imageId, int numFrames)
	{
		super(x, y, 0, 0, imagePath, imageId, numFrames);
		
		weaponType = true;
		fireCounter = secondFireCounter = 0;
		points = 50;
	}
	
	@Override
	protected void setFixedPosition()
	{
		int height = Processing.getInstance().getPApplet().height;
		
		stopPosition = height/4;
	}
	
	@Override
	public void shoot()
	{
		if(weaponType)
			EnemyBulletControl.getInstance().getChargeShoot(getX()+90, getY()+this.getHeight()-10,
					15, 30);
		else
			EnemyBulletControl.getInstance().getThreeBullets(true, getX() +70, getX() + 90,
						getX()+110, getY()+this.getHeight()-10, 30, 3, 40);
		
	}
	
	@Override
	protected void willFire()
	{
		
		/*
		 * Shooting pattern where it start by shooting a bullet that goes in the exactly player position
		 * and change to a three fast bullet firing pattern. This swap keeps happening until the enemy dies
		 */

		
		if(weaponType)
		{
			if(fireCounter == 40)
			{
				shoot();
				weaponType = false;
				fireCounter = 0;
			}	
			
			fireCounter++;
		}
		else
		{
			if(fireCounter == 40)
			{
				shoot();
				secondFireCounter++;
				
				if(secondFireCounter == 30)
				{
					weaponType = true;
					fireCounter = 0;
					secondFireCounter=0;
				}
			}
			else
				fireCounter++;
		}
		
		
		
	}
	

}

package com.states;

import java.util.ArrayList;
import java.util.Iterator;

import com.engine.Button;
import com.engine.DestructableObject;
import com.engine.GameObject;
import com.engine.Processing;
import com.engine.QuadTree;
import com.game.AdmiralShip;
import com.game.EnemyBulletPool;
import com.game.EnemySpawn;
import com.game.PowerUp;
import com.game.PowerUpHolder;
import com.game.Starfield;
import com.game.Enemy;
import com.game.PlayHUD;
import com.lonesurvivor.Game;

import processing.core.PApplet;
import processing.core.PFont;

public class PlayState implements GameState
{
	
	private Starfield starfield;
	private ArrayList<GameObject> playObjects;
	private ArrayList<DestructableObject> enemies;
	private EnemySpawn spawn;
	private final String playID = "PLAY";
	private Button leftButton; 
	private Button rightButton;
	private AdmiralShip admiralShip;
	private QuadTree quadTree;
	private PlayHUD playHUD;
	private PFont powerUpTitle;
	private boolean displayPowerUpText;
	private String powerUpText;
	private PApplet applet;
	private int powerUpTextCounter;

	@Override
	public void update() 
	{
		
		if(!admiralShip.isAlive())
		{
			gameOver();
			return;
		}
		
		handleQuadTree();
		int score = 0;
		
		for (Iterator<DestructableObject> iterator = enemies.iterator(); iterator.hasNext();)
		{
			DestructableObject object = iterator.next();
			
		    if (!((Enemy) object).isAlive())
		    {
		    	score += object.getPoints();
		        iterator.remove();
		    } 
		    else
		    	object.update();
		}
		
		if(displayPowerUpText)
		{
			if(powerUpTextCounter>=50)
			{
				displayPowerUpText = false;
				powerUpTextCounter = 0;
			}
			else
				powerUpTextCounter++;
		}
		
		starfield.update();
		
		enemies.addAll(spawn.spawn(enemies.size()));
		
		if(rightButton.isPressed())
			admiralShip.moveRight();
		else if(leftButton.isPressed())
			admiralShip.moveLeft();
		
		EnemyBulletPool.getInstance().update();
		admiralShip.update();
		playHUD.update(admiralShip.getHealth(), score);
		
		PowerUpHolder.getInstance().update();
		
	}

	@Override
	public void render() 
	{
		
		starfield.drawObject();
		admiralShip.drawObject();
		
		for(DestructableObject enemy: enemies)
			enemy.drawObject();
		
		for(GameObject object: playObjects)
			object.drawObject();
		
		for(DestructableObject bullet: EnemyBulletPool.getInstance().getPool(false))
			bullet.drawObject();
		
		for(DestructableObject bullet: EnemyBulletPool.getInstance().getPool(true))
			bullet.drawObject();

		PowerUpHolder.getInstance().drawObject();
		playHUD.drawObject();
		
		if(displayPowerUpText)
		{
			applet.textFont(powerUpTitle, 70);
			applet.fill(255);
			applet.text(String.valueOf(powerUpText), applet.width - 680,
					applet.height - rightButton.getHeight() - 30);
		}
		
	}

	@Override
	public boolean onEnter() 
	{
		applet = Processing.getInstance().getParent();
		playObjects = new ArrayList<GameObject>();
		enemies = new ArrayList<DestructableObject>();
		spawn = new EnemySpawn();
		
		starfield = new Starfield(20);
		rightButton = new Button(0, 0, "rightMove.png", "rightMove", 1, false);
		int rightx = applet.width - rightButton.getWidth();
		int righty = applet.height - rightButton.getHeight();
		rightButton.setX(rightx);
		rightButton.setY(righty);
		
		leftButton = new Button(0, 0, "leftMove.png", "leftMove", 1, false);
		int leftx = 0;
		int lefty = applet.height - leftButton.getHeight();
		leftButton.setX(leftx);
		leftButton.setY(lefty);
		
		admiralShip = new AdmiralShip(0, 0, 0, 0, "admiralship.png",
				"admiralship", 1);
		int shipx = applet.width/2 - admiralShip.getWidth()/2;
		int shipy = applet.height - admiralShip.getHeight();
		admiralShip.setX(shipx);
		admiralShip.setY(shipy);
		admiralShip.setDamageDealt(1000);
		admiralShip.setHealth(10);
		admiralShip.setFireRate(5);
		admiralShip.setWeapon(AdmiralShip.PROTON_WEAPON);
		
		displayPowerUpText = false;
		powerUpTitle = applet.createFont("Arial", 12, false);
		powerUpTextCounter = 0;
		
		playHUD = new PlayHUD();
		playHUD.init();
		
		quadTree = new QuadTree(0, 0, 0, applet.width, applet.height);
		
		playObjects.add(leftButton);
		playObjects.add(rightButton);
		
		return true;
	}

	@Override
	public boolean onExit() 
	{
		leftButton.clean();
		rightButton.clean();
		
		for(DestructableObject enemy : enemies)
			enemy.clean();
	
		PowerUpHolder.getInstance().clean();
		EnemyBulletPool.getInstance().clean();
		
		for(GameObject object: playObjects)
			object.clean();
		
		admiralShip.clean();
		
		starfield = null;
		return true;
	}
	
	private void detectCollision()
	{
		ArrayList<DestructableObject> objects = new ArrayList<DestructableObject>();
		ArrayList<DestructableObject> collision = new ArrayList<DestructableObject>();
		
		quadTree.getAllObjects(objects);
		
		for (int x = 0, len = objects.size(); x < len; x++)
		{
			collision.clear();
			quadTree.retrieve(collision, objects.get(x));
			
			for (int y = 0, length = collision.size(); y < length; y++)
			{

				if ( objects.get(x).isCollidableWith(collision.get(y)) &&
						objects.get(x).getX() < collision.get(y).getX() + collision.get(y).getWidth() &&
					     objects.get(x).getX() + objects.get(x).getWidth()  > collision.get(y).getX() &&
					     objects.get(x).getY() < collision.get(y).getY() + collision.get(y).getHeight() &&
						 objects.get(x).getY() + objects.get(x).getHeight() > collision.get(y).getY()) 
					{
						if(collision.get(y) instanceof PowerUp)
						{
							setPowerUpText(((PowerUp) collision.get(y)).getType());
							admiralShip.getPowerUp((PowerUp) collision.get(y));
							PowerUpHolder.getInstance().removePowerUp((PowerUp) collision.get(y));
						}	
							
						objects.get(x).setColliding(true, collision.get(y).getDamageDealt());
						collision.get(y).setColliding(true, objects.get(x).getDamageDealt());
					}
				}
			}
	}
	
	private void handleQuadTree()
	{
		quadTree.clear();
		
		for(int i =0; i<enemies.size(); i++)
			quadTree.insert(enemies.get(i));
		
		quadTree.insert(admiralShip);
		
		ArrayList<DestructableObject> bullets = admiralShip.getBulletPool().getPool();
		bullets.addAll(EnemyBulletPool.getInstance().getPool(false));
		bullets.addAll(EnemyBulletPool.getInstance().getPool(true));
		
		bullets.addAll(PowerUpHolder.getInstance().getActivePowerUps());
		
		for(int i =0; i<bullets.size(); i++)
			quadTree.insert(bullets.get(i));
		
		detectCollision();
	}
				
	@Override
	public void enable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		
	}
	
	private void setPowerUpText(int type)
	{
		displayPowerUpText = true;
		powerUpTextCounter = 0;
		
		switch(type)
		{
			case 0:
				powerUpText = "PROTON WEAPON !!!";
				break;
			
			case 1:
				powerUpText = "VULCAN WEAPON !!!";
				break;
				
			case 2:
				powerUpText = "GAMMA WEAPON !!!";
				break;
			
			case 3:
				powerUpText = "HEALTH BOOST !!!";
				break;
			
			case 4:
				powerUpText = "SPEED BOOST !!!";
				break;
				
		}
	}
	
	private void gameOver()
	{
		GameState gameOver = new GameOverState();
		((GameOverState) gameOver).setScore(playHUD.getScore());
		Game.getInstance().getStateMachine().changeState(gameOver);
	}

	
	public void mouseReleased(int x, int y) 
	{
		
		leftButton.setPressed(false);
		rightButton.setPressed(false);
		
	}
	
	public void mousePressed(int x, int y)
	{
		 if(leftButton.touchOnMe(x, y))
		 {
				if (leftButton.isPressed()==false) 
				{
				  leftButton.setPressed(true);
				  rightButton.setPressed(false);
				}
			
		 }
		 else
		 {
			 leftButton.setPressed(false);
		 }
		
		 if(rightButton.touchOnMe(x, y))
		 {
				if (rightButton.isPressed()==false) 
				{
				  rightButton.setPressed(true);
				  leftButton.setPressed(false);
				}
		 }
		 else
			 rightButton.setPressed(false);
	
	}

	@Override
	public String getStateId() 
	{
		return playID;
	}

}

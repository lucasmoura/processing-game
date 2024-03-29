package com.states;

import java.util.ArrayList;
import java.util.Iterator;

import com.engine.Button;
import com.engine.CollidableObject;
import com.engine.GameObject;
import com.engine.Processing;
import com.engine.QuadTree;
import com.engine.SoundManager;
import com.game.AdmiralShip;
import com.game.EnemyBulletControl;
import com.game.EnemySpawn;
import com.game.Game;
import com.game.Kodanswyn;
import com.game.PowerUp;
import com.game.PowerUpHolder;
import com.game.Starfield;
import com.game.Enemy;
import com.game.PlayHUD;

import processing.core.PApplet;
import processing.core.PFont;

/*
 * Class used to represent the actual game. This state can access the game over state and the pause state.
 */
public class PlayState implements GameState
{
	
	private Starfield starfield;
	private ArrayList<GameObject> playObjects;
	private ArrayList<CollidableObject> enemies;
	private EnemySpawn spawn;
	private boolean kodanswynStatus;
	private final String playID = "PLAY";
	private Button leftButton; 
	private Button rightButton;
	private Button pauseButton;
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
		
		//Update the enemy bullets
		EnemyBulletControl.getInstance().setPlayerx(admiralShip.getX());
		EnemyBulletControl.getInstance().setPlayery(admiralShip.getY());
		
		//Verify is the player's ship is still alive
		if(!admiralShip.isAlive())
		{
			gameOver();
			return;
		}
		
		handleQuadTree();
		int score = 0;
		
		//Update the enemies and remove form the enemies array the ones who left the screen or were destroyed
		for (Iterator<CollidableObject> iterator = enemies.iterator(); iterator.hasNext();)
		{
			CollidableObject object = iterator.next();
			
			if(object instanceof Kodanswyn)
			{
				/*
				 * If the enemy is a Kodanswyn, set a gravitational field push on the player's ship
				 * (Improve, since know playstate know about the existence of one specific enemy)
				 */
				if(((Kodanswyn) object).checkGravitationalForce())
					admiralShip.setGravitationalMovement(((Kodanswyn) object).getGravitationalFieldPower());
				
				kodanswynStatus = true;
			}
			
		    if (!((Enemy) object).isAlive())
		    {
		    
		    	/*
		    	 * Remove gravitational field from the player's ship. (Improve, same problem as above)
		    	 */
		    	if(object instanceof Kodanswyn)
				{
					if(!((Kodanswyn) object).checkGravitationalForce())
						admiralShip.setGravitationalMovement(0);
					
					kodanswynStatus = false;
				}
		    	
		    	score += object.getPoints();
		        iterator.remove();
		    } 
		    else
		    	object.update();
		}
		
		//If the player gets a power up, display a text indicating the power up type
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
		
		//Spawn enemies
		enemies.addAll(spawn.spawn(enemies.size(), kodanswynStatus));
		
		if(rightButton.isPressed())
			admiralShip.moveRight();
		else if(leftButton.isPressed())
			admiralShip.moveLeft();
		
		/*
		 * Update the player bullets and movement
		 */
		EnemyBulletControl.getInstance().update();
		admiralShip.update();
		playHUD.update(admiralShip.getHealth(), score);
		
		//Update the power ups positions in the screen
		PowerUpHolder.getInstance().update();
		
	}

	/*
	 * @see com.states.GameState#render()
	 */
	@Override
	public void render() 
	{
		
		starfield.drawObject();
		admiralShip.drawObject();
		
		for(CollidableObject enemy: enemies)
			enemy.drawObject();
		
		for(GameObject object: playObjects)
			object.drawObject();
		
		for(CollidableObject bullet: EnemyBulletControl.getInstance().getPool(0))
			bullet.drawObject();
		
		for(CollidableObject bullet: EnemyBulletControl.getInstance().getPool(1))
			bullet.drawObject();
		
		for(CollidableObject bullet: EnemyBulletControl.getInstance().getPool(2))
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
		applet = Processing.getInstance().getPApplet();
		playObjects = new ArrayList<GameObject>();
		enemies = new ArrayList<CollidableObject>();
		spawn = new EnemySpawn();
		
		kodanswynStatus = false;
		
		starfield = new Starfield(20);
		rightButton = new Button(0, 0, "buttons/rightMove.png", "rightMove", 1, false);
		int rightx = applet.width - rightButton.getWidth();
		int righty = applet.height - rightButton.getHeight();
		rightButton.setX(rightx);
		rightButton.setY(righty);
		
		leftButton = new Button(0, 0, "buttons/leftMove.png", "leftMove", 1, false);
		int leftx = 0;
		int lefty = applet.height - leftButton.getHeight();
		leftButton.setX(leftx);
		leftButton.setY(lefty);
		
		pauseButton = new Button(0, 0, "buttons/pause.png", "pause", 1, false);
		int pausex = Processing.getInstance().getPApplet().width - pauseButton.getWidth() - 30;
		int pausey = 10;
		pauseButton.setX(pausex);
		pauseButton.setY(pausey);
		
		admiralShip = new AdmiralShip(0, 0, 0, 0, "player/admiralship.png",
				"admiralship", 1);
		int shipx = applet.width/2 - admiralShip.getWidth()/2;
		int shipy = applet.height - admiralShip.getHeight();
		admiralShip.setX(shipx);
		admiralShip.setY(shipy);
		admiralShip.setDamageDealt(1000);
		admiralShip.setHealth(100);
		admiralShip.setFireRate(5);
		admiralShip.setWeapon(AdmiralShip.PROTON_WEAPON);
		
		displayPowerUpText = false;
		powerUpTitle = applet.createFont("Arial", 12, false);
		powerUpTextCounter = 0;
		
		playHUD = new PlayHUD();
		playHUD.init();
		
		quadTree = new QuadTree(0, 0, 0, applet.width, applet.height);
		
		//enemies.add(EnemyFactory.getInstance().createEnemy(Enemy.KODANRUTHR));
		//enemies.add(EnemyFactory.getInstance().createEnemy(Enemy.KODANSWYN));
		loadSounds();
		SoundManager.getInstance().playSound("playtheme", true);
		
		playObjects.add(leftButton);
		playObjects.add(rightButton);
		playObjects.add(pauseButton);
		
		return true;
	}
	
	private void loadSounds()
	{
		SoundManager.getInstance().addMusic("playtheme", "sound/music/Cephalopod.ogg", true);
		SoundManager.getInstance().addMusic("protonshoot", "sound/effect/sfx_laser2.ogg", false);
		SoundManager.getInstance().addMusic("powerup", "sound/effect/menu_pickup_treasure_coins-001.wav", false);
		SoundManager.getInstance().addMusic("gammashoot", "sound/effect/scifi_laser-003.wav", false);
		SoundManager.getInstance().addMusic("vulcanshoot", "sound/effect/scifi_laser_gun-001.wav", false);
		SoundManager.getInstance().addMusic("explosion", "sound/effect/explosion_medium_close-004.wav", false);
		SoundManager.getInstance().addMusic("enemyshoot", "sound/effect/scifi_laser_gun-003.wav", false);
		SoundManager.getInstance().addMusic("gravityfield", "sound/effect/scifi_forcefield-002.wav", false);
		SoundManager.getInstance().addMusic("impact", "sound/effect/impact_hollow_metal_hit-004.wav", false);
		
	}

	@Override
	public boolean onExit() 
	{
		
		leftButton.clean();
		rightButton.clean();
		
		for(CollidableObject enemy : enemies)
			enemy.clean();
	
		PowerUpHolder.getInstance().clean();
		EnemyBulletControl.getInstance().clean();
		
		for(GameObject object: playObjects)
			object.clean();
		
		admiralShip.clean();
		
		starfield = null;
		
		SoundManager.getInstance().clearFromSoundManager("playtheme", true);
		SoundManager.getInstance().clearFromSoundManager("protonshoot", false);
		SoundManager.getInstance().clearFromSoundManager("gammashoot", false);
		SoundManager.getInstance().clearFromSoundManager("vulcanshoot", false);
		SoundManager.getInstance().clearFromSoundManager("powerup", false);
		SoundManager.getInstance().clearFromSoundManager("explosion", false);
		SoundManager.getInstance().clearFromSoundManager("enemyshoot", false);
		SoundManager.getInstance().clearFromSoundManager("gravityfield", false);
		SoundManager.getInstance().clearFromSoundManager("impact", false);
		
		return true;
	}
	
	/*
	 * Method used to detect collision in the game. It basically gets all the collidable objects associated with
	 * the current object that collision is being checked on and see if they are actually colliding by verifying if
	 * the images rectangles are coliding. Using the quadtree, gurantees that the colidable objects are only going to
	 * be the ones which are on the same quadrant as the object which collision is being checked on
	 * 
	 */
	private void detectCollision()
	{
		ArrayList<CollidableObject> objects = new ArrayList<CollidableObject>();
		ArrayList<CollidableObject> collision = new ArrayList<CollidableObject>();
		
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
						/*
						 * If the collision is with a power up, the settings must be different, since
						 * the power up doesn't damage the player, but instead gives him a different
						 * weapon or boost
						 */
						if(collision.get(y) instanceof PowerUp)
						{
							//Display power up text on the screen
							setPowerUpText(((PowerUp) collision.get(y)).getType());
							//Indicate to the player's ship that it got a power up
							admiralShip.getPowerUp((PowerUp) collision.get(y));
							//Remove the power from the holder
							PowerUpHolder.getInstance().removePowerUp((PowerUp) collision.get(y));
						}	
							
						objects.get(x).setColliding(true, collision.get(y).getDamageDealt());
						collision.get(y).setColliding(true, objects.get(x).getDamageDealt());
					}
				}
			}
	}
	
	/*
	 * Method used to handle the quadtree. It basically clear the previous quadtree and start inserting
	 * all collidable objects back to the quadtree. Once that is done, collision detection can be started.
	 */
	private void handleQuadTree()
	{
		quadTree.clear();
		
		for(int i =0; i<enemies.size(); i++)
			quadTree.insert(enemies.get(i));
		
		quadTree.insert(admiralShip);
		
		ArrayList<CollidableObject> bullets = admiralShip.getBulletPool().getPool();
		bullets.addAll(EnemyBulletControl.getInstance().getPool(0));
		bullets.addAll(EnemyBulletControl.getInstance().getPool(1));
		bullets.addAll(EnemyBulletControl.getInstance().getPool(2));
		
		bullets.addAll(PowerUpHolder.getInstance().getActivePowerUps());
		
		for(int i =0; i<bullets.size(); i++)
			quadTree.insert(bullets.get(i));
		
		detectCollision();
	}
				
	/*
	 * Method used to select which text will be displayed once the player's ship get a power up
	 */
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
		
		if(pauseButton.touchOnMe(x, y))
		{
			Game.getInstance().getStateMachine().pushState(new PauseState());
		}
		
		/*
		 * When the player releases a button, guarantee that the space ship movement will stop
		 * if the player is not pressing any other button
		 */
		leftButton.setPressed(false);
		rightButton.setPressed(false);
		
		
	}
	
	/*
	 * @see com.states.GameState#mousePressed(int, int)
	 */
	public void mousePressed(int x, int y)
	{
		/*
		 * If the player is holding the left button, make it that the player's ship movement is constantly to
		 * the left
		 */
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
		
		 /*
			 * If the player is holding the right button, make it that the player's ship movement is
			 * constantly to the right
			 */
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

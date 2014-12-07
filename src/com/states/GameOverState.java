package com.states;

import processing.core.PApplet;
import processing.core.PFont;

import android.content.SharedPreferences;

import com.engine.Button;
import com.engine.Processing;
import com.engine.SoundManager;
import com.engine.TextureManager;
import com.game.Game;

/*
 * Class used to represent the game over screen in the game
 * It allows the user to begin a new game or go back to the main menu
 */
public class GameOverState implements GameState
{
	
	private String gameOverId = "GAMEOVER";
	private String gameOverTitle;
	private int width;
	private int height;
	private Button newgame;
	private Button menu;
	private long score;
	private PFont playerFinalScore;
	private PApplet applet;
	
	
	@Override
	public void update() 
	{
		
	}

	@Override
	public void render()
	{
		TextureManager.getInstance().drawObject("background", 0, 0);
		TextureManager.getInstance().drawObject(gameOverTitle, width/2 - 343, 100);
		TextureManager.getInstance().drawObject("scoreIcon", width/2 - 283, height/2-200-40);
		
		applet.textFont(playerFinalScore, 85);
		applet.fill(255);
		applet.text(String.valueOf(score), width/2 +40, height/2 -170);
		
		newgame.drawObject();
		menu.drawObject();
		
	}

	@Override
	public boolean onEnter()
	{
		applet = Processing.getInstance().getPApplet();
		width = applet.width;
		height = applet.height;
		
		 if(TextureManager.getInstance().loadGameImage("backgrounds/space.jpg", "background") == false)
		      return false;
		
		gameOverTitle = "gameOverTitle";
		TextureManager.getInstance().loadGameImage("titles/gameOverTitle.png", gameOverTitle);
		TextureManager.getInstance().loadGameImage("titles/score.png", "scoreIcon");
		
		newgame = new Button(0, 0, "buttons/newgame.png", "newgame", 2, true);
		int newgamex = width/2 - newgame.getWidth()/2;
		int newgamey = height/2 +50 - newgame.getHeight()/2;
		newgame.setX(newgamex);
		newgame.setY(newgamey);
		
		menu = new Button(0, 0, "buttons/menubutton.png", "menu", 2, true);
		int menux = width/2 - menu.getWidth()/2;
		int menuy = newgamey + 400 - menu.getHeight()/2;
		menu.setX(menux);
		menu.setY(menuy);
		
		playerFinalScore = applet.createFont("Arial", 16, false);
		
		setBestScore();
		
		return true;
	}

	/*
	 * Class used to save the best score of the player. It open the shared preferences file associated with
	 * the game and compares the score stored there with the player most recent one. If the most recent score
	 * is better than the old one, the shared preferences is then updated.
	 */
	private void setBestScore()
	{
		 SharedPreferences score = applet.getSharedPreferences("score", 0);
		 long bestScore = score.getLong("score", 0);
		 
		 if(bestScore < this.score)
		 {
			 SharedPreferences.Editor editor = score.edit();
			 editor.putLong("score", this.score);

			 editor.commit();
		 }	 
	      
	}

	@Override
	public boolean onExit() 
	{
		
		SoundManager.getInstance().stop();
		TextureManager.getInstance().clearFromTextureMap(gameOverTitle);
		TextureManager.getInstance().clearFromTextureMap("background");
		TextureManager.getInstance().clearFromTextureMap("scoreIcon");
		
		menu.clean();
		newgame.clean();
		
		return false;
	}

	@Override
	public void mouseReleased(int x, int y)
	{
		if(newgame.touchOnMe(x, y))
		{
			Game.getInstance().getStateMachine().changeState(new PlayState());
			return;
		}	
		
		if(menu.touchOnMe(x, y))
		{
			Game.getInstance().getStateMachine().changeState(new MenuState());
			return;
		}	
		
	}

	@Override
	public void mousePressed(int x, int y) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getStateId()
	{
		return gameOverId;
	}
	
	public void setScore(long score)
	{
		this.score = score;
	}

}

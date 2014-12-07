package com.states;

import processing.core.PApplet;
import processing.core.PFont;

import com.engine.Button;
import com.engine.Processing;
import com.engine.SoundManager;
import com.engine.TextureManager;
import com.game.Game;

/*
 * Class used to represent the settings state of the game. It allows the user to mute the music and the 
 * sound effects in the game. This state can only move back to the state that has created it. For example
 * if the settings state was created by the pause state, it can only move back to the pause state
 * 
 * This class was also designed using the singleton design pattern. This was done to guarantee that the state
 * of the buttons, which state if sound is enable or not, are going to be consistent no matter which state 
 * create the settings state
 */
public class SettingsState implements GameState
{
	
	private Button music;
	private Button soundEffect;
	private Button back;
	private int width;
	private int height;
	private PFont musicFont;
	private PFont effectFont;
	private boolean musicEnable;
	private boolean effectEnable;
	private PApplet applet;
	
	private final String stateId = "SETTINGS";
	private static SettingsState instance = null;
	
	public static SettingsState getInstance()
	{
		if(instance == null)
			instance = new SettingsState();
		
		return instance;
	}
	
	private SettingsState()
	{
		musicEnable = effectEnable = true;
	}

	@Override
	public void update()
	{
		
	}

	@Override
	public void render() 
	{
		TextureManager.getInstance().drawObject("background", 0, 0);
		TextureManager.getInstance().drawObject("settingstitle", width/2 - 338, 100);
		TextureManager.getInstance().drawObject("audiosettings", 0, 400);
		
		music.drawObject();
		soundEffect.drawObject();
		back.drawObject();
		
		applet.textFont(musicFont, 60);
		changeColor(musicEnable);
		applet.text("Music", music.getX() + music.getWidth()/4, music.getY() + music.getHeight()/2 + 15);
		
		applet.textFont(effectFont, 40);
		changeColor(effectEnable);
		applet.text("Sound Effects", soundEffect.getX() + 20, soundEffect.getY() + soundEffect.getHeight()/2 + 15);
		
	}
	
	private void changeColor(boolean check)
	{
		if(check)
			applet.fill(87, 222, 38);
		else
			applet.fill(240, 7, 7);
	}

	@Override
	public boolean onEnter() 
	{
		applet = Processing.getInstance().getPApplet();
		width = applet.width;
		height = applet.height;
		
		TextureManager.getInstance().loadGameImage("backgrounds/space.jpg", "background");
		TextureManager.getInstance().loadGameImage("titles/settingstitle.png", "settingstitle");
		TextureManager.getInstance().loadGameImage("hud/audioSettings.png", "audiosettings");
		
		music = new Button(0, 0, "hud/buttonBase.png", "musicBase", 1, false);
		int musicy = 520;
		music.setY(musicy);
		
		soundEffect = new Button(0, 0, "hud/buttonBase.png", "effectBase", 1, false);
		int effectx = music.getWidth() + 20;
		soundEffect.setX(effectx);
		soundEffect.setY(musicy);
		
		back = new Button(0, 0, "buttons/back.png", "back", 2, true);
		int backy = height - back.getHeight();
		back.setY(backy);
		
		musicFont = applet.createFont("Arial", 16, false);
		effectFont = applet.createFont("Arial", 16, false);
		
		return false;
	}

	@Override
	public boolean onExit() 
	{
		TextureManager.getInstance().clearFromTextureMap("background");
		TextureManager.getInstance().clearFromTextureMap("settingstitle");
		TextureManager.getInstance().clearFromTextureMap("audiosettings");
		
		music.clean();
		soundEffect.clean();
		back.clean();
		
		return true;
	}

	@Override
	public void mouseReleased(int x, int y) 
	{
		if(music.touchOnMe(x, y))
		{
			if(musicEnable)
				musicEnable = false;
			else
				musicEnable = true;
			
			SoundManager.getInstance().setMusicEnable(musicEnable);
			SoundManager.getInstance().playSound("click", false);
			
			return;
		}
		
		if(soundEffect.touchOnMe(x, y))
		{
			if(effectEnable)
				effectEnable = false;
			else
				effectEnable = true;
			
			SoundManager.getInstance().setEffectEnable(effectEnable);
			SoundManager.getInstance().playSound("click", false);
			
			return;
		}
		
		if(back.touchOnMe(x, y))
		{
			SoundManager.getInstance().playSound("click", false);
			Game.getInstance().getStateMachine().popState();
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
		return stateId;
	}
	
	

}

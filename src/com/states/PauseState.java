package com.states;

import processing.core.PApplet;

import com.engine.Button;
import com.engine.Processing;
import com.engine.SoundManager;
import com.engine.TextureManager;
import com.game.Game;

/*
 * Class used to represent the pause screen in the game. This state can only be accessed via the play state.
 * From the pause state, it is possible to move back to the play stateor go to the menu state or settings state.
 */
public class PauseState implements GameState
{	
	
	private Button play;
	private Button settings;
	private Button menu;
	private int width;
	private int height;
	
	private final String stateId = "PAUSE";

	public PauseState()
	{
		
	}

	@Override
	public void update()
	{
		
	}

	@Override
	public void render() 
	{
		TextureManager.getInstance().drawObject("background", 0, 0);
		TextureManager.getInstance().drawObject("pausetitle", width/2 - 231, 100);
		
		play.drawObject();
		menu.drawObject();
		settings.drawObject();
		
	}

	@Override
	public boolean onEnter()
	{
		PApplet applet = Processing.getInstance().getPApplet();
		width = applet.width;
		height = applet.height;
		
		 if(TextureManager.getInstance().loadGameImage("backgrounds/space.jpg", "background") == false)
		      return false;
		
		TextureManager.getInstance().loadGameImage("titles/pausetitle.png", "pausetitle");
		
		play = new Button(0, 0, "buttons/play.png", "play", 2, true);
		int playx = width/2 - play.getWidth()/2;
		int playy = height/2 +50 - play.getHeight()/2;
		play.setX(playx);
		play.setY(playy);
		
		menu = new Button(0, 0, "buttons/menubutton.png", "menu", 2, true);
		int menux = width/2 - menu.getWidth()/2;
		int menuy = playy + 400 - menu.getHeight()/2;
		menu.setX(menux);
		menu.setY(menuy);
		
		settings = new Button(0, 0, "buttons/settingsIcon.png", "settingsicon", 2, true);
		int settingsx = width - settings.getWidth();
		settings.setX(settingsx);
		
		SoundManager.getInstance().addMusic("click", "sound/effect/mechanical_metallic_rattle-001.ogg", false);
		
		return true;
	}

	@Override
	public boolean onExit() 
	{
		TextureManager.getInstance().clearFromTextureMap("pausetitle");
		TextureManager.getInstance().clearFromTextureMap("background");
		
		SoundManager.getInstance().clearFromSoundManager("click", false);
		
		menu.clean();
		play.clean();
		settings.clean();
		
		return true;
	}

	@Override
	public void mouseReleased(int x, int y)
	{
		if(play.touchOnMe(x, y))
		{
			SoundManager.getInstance().playSound("click", false);
			Game.getInstance().getStateMachine().popState();
			return;
		}	
		
		if(menu.touchOnMe(x, y))
		{
			SoundManager.getInstance().playSound("click", false);
			//Release both pause state and play state from the state machine
			Game.getInstance().getStateMachine().popState();
			Game.getInstance().getStateMachine().popState();
			Game.getInstance().getStateMachine().changeState(new MenuState());
			return;
		}
		
		if(settings.touchOnMe(x, y))
		{
			SoundManager.getInstance().playSound("click", false);
			Game.getInstance().getStateMachine().pushState(SettingsState.getInstance());
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


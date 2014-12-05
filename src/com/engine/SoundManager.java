package com.engine;

import java.io.IOException;
import java.util.HashMap;

import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import apwidgets.APMediaPlayer;

/*
 * Class used to handle music and sound effects in the game. This class was designed as a singleton class in
 * order to allow a single way of access to all game musics and sound effects
 */
public class SoundManager 
{
	//Singleton instance
	private static SoundManager instance = null;
	//Map used to map a music id to a store music
	private HashMap<String, String> music;
	//Map used to map a sound effect id to a stored sound effect
	private HashMap<String, Integer> soundEffect;
	//Variable used to play the game musics
	private APMediaPlayer player;
	//Variable used to play the different sound effects found on the game
	private SoundPool effect;
	//Variable used to get the stop position of an music in case the activity is paused
	private int stopPosition;
	//Variable used to access the assets where the musics and sound effects are stored
	private AssetManager assetManager;
	//Variable used to check if it is possible to play music
	private boolean musicEnable;
	//Variable used to check if it is possible to play a sound effect
	private boolean effectEnable;
	
	/*
	 * Method used to get the singleton instance
	 * @return: The singleton instance
	 */
	public static SoundManager getInstance()
	{
		if(instance == null)
			instance = new SoundManager();
		
		return instance;
	}
	
	private SoundManager()
	{
		
		music = new HashMap<String, String>();
		soundEffect = new HashMap<String, Integer>();
		
		player = new APMediaPlayer(Processing.getInstance().getPApplet());
		effect = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
		
		musicEnable = effectEnable = true;
		
		assetManager = Processing.getInstance().getPApplet().getAssets();
		
	}
	
	/*
	 * Method used to add a music to the SoundManager. If the isMusic parameter is true, the song will be
	 * store as a music, and if it is false, as a sound effect
	 * @param songId: The song id
	 * @param songPath: The path to reach the sound
	 * @param isMusic: Used to verify if the sound is a music or sound effect
	 */
	public void addMusic(String songId, String songPath, boolean isMusic)
	{
		if(isMusic)
			music.put(songId, songPath);
		else
		{	
			try {
				soundEffect.put(songId, effect.load(assetManager.openFd(songPath), 0));
			} catch (IOException e) 
			{
			} 
		}	
	}
	
	/*
	 * Method used to play a sound. If the isMusic parameter is true, a music will be played, and if
	 * it is false, a sound effect will be played
	 * @param soundId: The sound id to be played
	 * @param isMusic: The variable to verify if the sound is a music or sound effect
	 */
	public void playSound(String soundId, boolean isMusic)
	{
		if(isMusic)
		{
			try
			{
				player.setMediaFile(music.get(soundId));
				
				if(musicEnable)
				{
					player.start();
					player.setLooping(true);
				}
			}
			catch(Exception e)
			{
				
			}
		}	
		else
		{
			if(effectEnable)
				effect.play(soundEffect.get(soundId), 1, 1, 0, 0, 1);
		}	
	
	}
	
	/*
	 * Method used to remove a sound id from one of the map structures. If the isMusic parameter is true,
	 *  a music will be removed, and if it is false, a sound effect will be removed
	 * @param soundId: The sound id to be played
	 * @param isMusic: The variable to verify if the sound is a music or sound effect
	 */
	public void clearFromSoundManager(String soundId, boolean isMusic)
	{
		if(isMusic)
			music.remove(soundId);
		else
		{
			effect.unload(soundEffect.get(soundId));
			soundEffect.remove(soundId);
		}	
		
	}
	
	/*
	 * Method used to stop the current music
	 */
	public void stop()
	{
		stopPosition = player.getCurrentPosition();
		player.pause();
	}
	
	/*
	 *Method used to resume the current song if musicEnable is true
	 */
	public void resume()
	{
		if(musicEnable)
		{
			if(stopPosition != -1)
				player.seekTo(stopPosition);
			
			player.start();
		}
	}
	
	/*
	 * Method used to clean the SoundManager. Used when the activity is destroyed
	 */
	public void clean()
	{
		music.clear();
		soundEffect.clear();
		player.pause();
		effect.release();
		player.release();
	}
	
	/*
	 * Method used to enable the SoundManager to play musics or not
	 * @param musicEnable: Variable that state if the SoundManager can play a music or not
	 */
	public void setMusicEnable(boolean musicEnable)
	{
		this.musicEnable = musicEnable;
		
		if(!musicEnable)
			stop();
		else if(stopPosition != -1)
		{
			player.seekTo(stopPosition);
			player.start();
		}	 
	}
	
	/*
	 * Method used to enable the SoundManager to play sound effects or not
	 * @param effectEnable: Variable that state if the SoundManager can play a sound effect or not
	 */
	public void setEffectEnable(boolean effectEnable)
	{
		this.effectEnable = effectEnable;
	}

}

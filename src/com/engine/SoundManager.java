package com.engine;

import java.io.IOException;
import java.util.HashMap;

import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import apwidgets.APMediaPlayer;

public class SoundManager 
{
	
	private static SoundManager instance = null;
	private HashMap<String, String> music;
	private HashMap<String, Integer> soundEffect;
	private APMediaPlayer player;
	private SoundPool effect;
	private int stopPosition;
	private AssetManager assetManager;
	private boolean musicEnable;
	private boolean effectEnable;
	
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
		
		player = new APMediaPlayer(Processing.getInstance().getParent());
		effect = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
		
		musicEnable = effectEnable = true;
		
		assetManager = Processing.getInstance().getParent().getAssets();
		
	}
	
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
	
	public void stop()
	{
		stopPosition = player.getCurrentPosition();
		player.pause();
	}
	
	public void resume()
	{
		if(stopPosition != -1)
			player.seekTo(stopPosition);
		
		player.start();
	}
	
	public void clean()
	{
		music.clear();
		soundEffect.clear();
		player.pause();
		effect.release();
		player.release();
	}
	
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
	
	public void setEffectEnable(boolean effectEnable)
	{
		this.effectEnable = effectEnable;
	}

}

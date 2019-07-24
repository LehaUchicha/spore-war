package ru.game.spore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameInstance {
	
	public int difficultyConfig = 0;
	public int factoryHealthConfig = 0;
	public int antiAliasConfig = 0;	

	public static GameInstance instance;

	public static GameInstance getInstance() {
		if (instance == null) {
			instance = new GameInstance();
		}
		return instance;
	}
	
	public void resetGame() {
		Preferences prefs = Gdx.app.getPreferences("paxbritannica");
		GameInstance.getInstance().difficultyConfig  = prefs.getInteger("difficulty",0);
		GameInstance.getInstance().factoryHealthConfig  = prefs.getInteger("factoryHealth",0);
		GameInstance.getInstance().antiAliasConfig  = prefs.getInteger("antiAliasConfig",1);
	}

}
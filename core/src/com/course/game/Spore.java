package com.course.game;

import com.badlogic.gdx.Game;
import com.course.game.screens.MainMenu;


public class Spore extends Game {
	@Override 
	public void create () {
		setScreen(new MainMenu(this));
	}
}

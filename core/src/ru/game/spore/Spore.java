package ru.game.spore;

import com.badlogic.gdx.Game;
import ru.game.spore.screens.MainMenu;

public class Spore extends Game {

	@Override 
	public void create () {
		setScreen(new MainMenu(this));
	}
}

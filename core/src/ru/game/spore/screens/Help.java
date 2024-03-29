package ru.game.spore.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import ru.game.spore.resources.background.BackgroundFXRenderer;

import ru.game.spore.resources.Resources;
import ru.game.spore.GameInstance;

public class Help extends DefaultScreen implements InputProcessor {
	
	Sprite back;
	
	BoundingBox collisionBack = new BoundingBox();

	BoundingBox collisionMusic = new BoundingBox();

	Sprite fighter;

	Sprite bomber;

	Sprite frigate;

	Sprite upgrade;
	
	BackgroundFXRenderer backgroundFX = new BackgroundFXRenderer();

	Sprite blackFade;

	OrthographicCamera cam;
	
	BitmapFont font;
	
	SpriteBatch titleBatch;

	SpriteBatch fadeBatch;
	
	Ray collisionRay;

	boolean finished = false;
	
	float time = 0;

	float fade = 1.0f;

	private int width = 800;

	private int height = 480;

	public Help(Game game) {
		super(game);
		Gdx.input.setCatchBackKey( true );
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void show() {		
		GameInstance.getInstance().resetGame();

		back = Resources.getInstance().back;
		back.setPosition(20, 010);
		back.setColor(1, 1, 1, 0.5f);
		collisionBack.set(new Vector3(back.getVertices()[0], back.getVertices()[1], -10), new Vector3(back.getVertices()[10], back.getVertices()[11], 10));

		blackFade = Resources.getInstance().blackFade;

		fighter =  new Sprite(new Texture(Gdx.files.internal("data/sprites/vir3.png")));
		fighter.setRotation(0);
		bomber = new Sprite(new Texture(Gdx.files.internal("data/sprites/vir2.png")));
		bomber.setRotation(0);
		
		titleBatch = new SpriteBatch();
		titleBatch.getProjectionMatrix().setToOrtho2D(0, 0, 800, 480);
		fadeBatch = new SpriteBatch();
		fadeBatch.getProjectionMatrix().setToOrtho2D(0, 0, 2, 2);
		
		font = new BitmapFont();
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		time += delta;

		if (time < 1f)
			return;

		backgroundFX.render();

		titleBatch.begin();
		
		back.draw(titleBatch);
		
		font.draw(titleBatch, "Welcome to our game", 90, 460);
		font.draw(titleBatch, "Select a player and start .", 90, 440);
		font.draw(titleBatch, "Your choice of 4 models , provided each player with their own features and characteristics", 90, 400);
		font.draw(titleBatch, "To control your character , simply attach the finger to the point on the screen where the character must move", 90, 380);
		font.draw(titleBatch, "Your task is to defeat enemies and collect food cells that would grow in levels", 90, 360);
		font.draw(titleBatch, "The maximum level - 10.This a couple of examples of characters:", 90, 340);
		fighter.setPosition(1, 90);
		fighter.draw(titleBatch);
		font.draw(titleBatch, "Tom: has high speed and attack power, but loses in the amount of health remaining characters", 130, 300);
		bomber.setPosition(1, 199);
		bomber.draw(titleBatch);
		font.draw(titleBatch, "Glock : increased health, average speed and attack power reduced", 130, 180);

		font.draw(titleBatch, "You can play as one, and to network with other players .", 90, 80);
		font.draw(titleBatch, "We hope you enjoy a good game !", 90, 60);

		titleBatch.end();

		if (!finished && fade > 0) {
			fade = Math.max(fade - Gdx.graphics.getDeltaTime() / 2.f, 0);
			fadeBatch.begin();
			blackFade.setColor(blackFade.getColor().r, blackFade.getColor().g, blackFade.getColor().b, fade);
			blackFade.draw(fadeBatch);
			fadeBatch.end();
		}

		if (finished) {
			fade = Math.min(fade + Gdx.graphics.getDeltaTime() / 2.f, 1);
			fadeBatch.begin();
			blackFade.setColor(blackFade.getColor().r, blackFade.getColor().g, blackFade.getColor().b, fade);
			blackFade.draw(fadeBatch);
			fadeBatch.end();
			if (fade >= 1) {
				game.setScreen(new MainMenu(game));
			}
		}

	}
	
	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		if (width == 480 && height == 320) {
			cam = new OrthographicCamera(700, 466);
			this.width = 700;
			this.height = 466;
		} else if (width == 320 && height == 240) {
			cam = new OrthographicCamera(700, 525);
			this.width = 700;
			this.height = 525;
		} else if (width == 400 && height == 240) {
			cam = new OrthographicCamera(800, 480);
			this.width = 800;
			this.height = 480;
		} else if (width == 432 && height == 240) {
			cam = new OrthographicCamera(700, 389);
			this.width = 700;
			this.height = 389;
		} else if (width == 960 && height == 640) {
			cam = new OrthographicCamera(800, 533);
			this.width = 800;
			this.height = 533;
		}  else if (width == 1366 && height == 768) {
			cam = new OrthographicCamera(1280, 720);
			this.width = 1280;
			this.height = 720;
		} else if (width == 1366 && height == 720) {
			cam = new OrthographicCamera(1280, 675);
			this.width = 1280;
			this.height = 675;
		} else if (width == 1536 && height == 1152) {
			cam = new OrthographicCamera(1366, 1024);
			this.width = 1366;
			this.height = 1024;
		} else if (width == 1920 && height == 1152) {
			cam = new OrthographicCamera(1366, 854);
			this.width = 1366;
			this.height = 854;
		} else if (width == 1920 && height == 1200) {
			cam = new OrthographicCamera(1366, 800);
			this.width = 1280;
			this.height = 800;
		} else if (width > 1280) {
			cam = new OrthographicCamera(1280, 768);
			this.width = 1280;
			this.height = 768;
		} else if (width < 800) {
			cam = new OrthographicCamera(800, 480);
			this.width = 800;
			this.height = 480;
		} else {
			cam = new OrthographicCamera(width, height);
		}
		cam.position.x = 400;
		cam.position.y = 240;
		cam.update();	
		backgroundFX.resize(width, height);
		titleBatch.getProjectionMatrix().set(cam.combined);
		
		back.setPosition(20 - ((this.width-800)/2), 10- ((this.height-480)/2));
		collisionBack.set(new Vector3(back.getVertices()[0], back.getVertices()[1], -10),new Vector3(back.getVertices()[10], back.getVertices()[11], 10));
	
	}

	@Override
	public void hide() {
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.BACK) {
			finished = true;
		}
		
		if(keycode == Input.Keys.ESCAPE) {
			finished = true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		collisionRay = cam.getPickRay(x, y);
		
		if (Intersector.intersectRayBoundsFast(collisionRay, collisionBack)) {
			finished = true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
}
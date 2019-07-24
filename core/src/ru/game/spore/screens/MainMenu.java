package ru.game.spore.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import ru.game.spore.GameInstance;
import ru.game.spore.Resources;
import ru.game.spore.background.BackgroundFXRenderer;


public class MainMenu extends DefaultScreen implements InputProcessor {
    Sprite title;
    Sprite credits;
    Sprite settings;
    Sprite singleGame;
    Sprite multiplayer;

    OrthographicCamera cam;

    Sprite help;
    Sprite musicOnOff;
    BoundingBox collisionHelp = new BoundingBox();
    BoundingBox collisionMusic = new BoundingBox();
    BoundingBox collisionSettings = new BoundingBox();
    BoundingBox collisionSingleGame = new BoundingBox();
    BoundingBox collisionMultiplayer = new BoundingBox();

    BackgroundFXRenderer backgroundFX = new BackgroundFXRenderer();
    Sprite blackFade;

    SpriteBatch titleBatch;
    SpriteBatch fadeBatch;

    float time = 0;
    float fade = 1.0f;
    int cnt = 0;
    int changeToScreen = -1;

    Ray collisionRay;

    private int width = 800;
    private int height = 480;

    public MainMenu(Game game) {
        super(game);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {
        Resources.getInstance().reInit();

        GameInstance.getInstance().resetGame();

        changeToScreen = -1;

        backgroundFX = new BackgroundFXRenderer();

        title = Resources.getInstance().title;
        credits = Resources.getInstance().credits;
        settings = Resources.getInstance().settings;
        singleGame = Resources.getInstance().singleGame;
        multiplayer = Resources.getInstance().multiplayer;
        blackFade = Resources.getInstance().blackFade;

        musicOnOff = Resources.getInstance().musicOnOff;
        musicOnOff.setPosition(20, 10);
        musicOnOff.setColor(1, 1, 1, 0.5f);
        collisionMusic.set(new Vector3(musicOnOff.getVertices()[0], musicOnOff.getVertices()[1], -10), new Vector3(musicOnOff.getVertices()[10], musicOnOff.getVertices()[11], 10));

        help = Resources.getInstance().help;
        help.setPosition(75, 10);
        help.setColor(1, 1, 1, 0.5f);
        collisionHelp.set(new Vector3(help.getVertices()[0], help.getVertices()[1], -10), new Vector3(help.getVertices()[10], help.getVertices()[11], 10));

        settings = Resources.getInstance().settings;
        settings.setPosition(135, 8);
        settings.setColor(1, 1, 1, 0.5f);
        collisionSettings.set(new Vector3(settings.getVertices()[0], settings.getVertices()[1], -10), new Vector3(settings.getVertices()[10], settings.getVertices()[11], 10));

        singleGame = Resources.getInstance().singleGame;
        singleGame.setPosition(200, 10);
        singleGame.setColor(1, 1, 1, 0.5f);
        collisionSingleGame.set(new Vector3(singleGame.getVertices()[0], singleGame.getVertices()[1], -10), new Vector3(singleGame.getVertices()[10], singleGame.getVertices()[11], 10));

        multiplayer = Resources.getInstance().multiplayer;
        multiplayer.setPosition(620, 10);
        multiplayer.setColor(1, 1, 1, 0.5f);
        collisionMultiplayer.set(new Vector3(multiplayer.getVertices()[0], multiplayer.getVertices()[1], -10), new Vector3(multiplayer.getVertices()[10], multiplayer.getVertices()[11], 10));

        titleBatch = new SpriteBatch();
        titleBatch.getProjectionMatrix().setToOrtho2D(0, 0, 800, 480);
        fadeBatch = new SpriteBatch();
        fadeBatch.getProjectionMatrix().setToOrtho2D(0, 0, 2, 2);

        Preferences prefs = Gdx.app.getPreferences("paxbritannica");
        if (prefs.getBoolean("music") == true) {
            if (Resources.getInstance().music == null) Resources.getInstance().reInit();
            if (!Resources.getInstance().music.isPlaying()) {
                Resources.getInstance().music.play();
                Resources.getInstance().music.setLooping(true);
            }
            musicOnOff.setColor(1, 1, 1, 0.5f);
        } else {
            Resources.getInstance().music.stop();
            musicOnOff.setColor(1, 1, 1, 0.1f);
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
        } else if (width == 1366 && height == 768) {
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

        musicOnOff.setPosition(20 - ((this.width - 800) / 2), 10 - ((this.height - 480) / 2));
        help.setPosition(75 - ((this.width - 800) / 2), 10 - ((this.height - 480) / 2));
        settings.setPosition(135 - ((this.width - 800) / 2), 8 - ((this.height - 480) / 2));
        singleGame.setPosition(200 - ((this.width - 800) / 2), 8 - ((this.height - 480) / 2));
        multiplayer.setPosition(650 - ((this.width - 800) / 2), 8 - ((this.height - 480) / 2));

        collisionMusic.set(new Vector3(musicOnOff.getVertices()[0], musicOnOff.getVertices()[1], -10), new Vector3(musicOnOff.getVertices()[10], musicOnOff.getVertices()[11], 10));
        collisionHelp.set(new Vector3(help.getVertices()[0], help.getVertices()[1], -10), new Vector3(help.getVertices()[10], help.getVertices()[11], 10));
        collisionSettings.set(new Vector3(settings.getVertices()[0], settings.getVertices()[1], -10), new Vector3(settings.getVertices()[10], settings.getVertices()[11], 10));
        collisionSingleGame.set(new Vector3(singleGame.getVertices()[0], singleGame.getVertices()[1], -10), new Vector3(singleGame.getVertices()[10], singleGame.getVertices()[11], 10));
        collisionMultiplayer.set(new Vector3(multiplayer.getVertices()[0], multiplayer.getVertices()[1], -10), new Vector3(multiplayer.getVertices()[10], multiplayer.getVertices()[11], 10));

    }

    @Override
    public void render(float delta) {
        delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());

        time += delta;

        if (time < 1f)
            return;

        backgroundFX.render();

        titleBatch.begin();

        musicOnOff.draw(titleBatch);
        help.draw(titleBatch);
        settings.draw(titleBatch);
        singleGame.draw(titleBatch);
        multiplayer.draw(titleBatch);

        titleBatch.draw(title, 85f, 320f, 0, 0, 512, 64f, 1.24f, 1.24f, 0);
        titleBatch.draw(credits, 595f, 50f);


        titleBatch.end();

        if (fade >= 1 && cnt < 1) {
            if (changeToScreen == 0) {
                game.setScreen(new Help(game));
            }
            if (changeToScreen == 1) {
                game.setScreen(new Settings(game));
            }
            if (changeToScreen == 2) {
                game.setScreen(new SingleGame(game));
            }
            if (changeToScreen == 3) {
                game.setScreen(new Multiplayer(game));
            }
        }
    }

    @Override
    public void hide() {
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
            boolean exit = true;

            if (exit) {
                if (!(Gdx.app.getType() == ApplicationType.Applet)) {
                    Gdx.app.exit();
                }
            }
        }

        if (keycode == Input.Keys.M) {
            if (cnt >= 1)
                return false;
            Preferences prefs = Gdx.app.getPreferences("paxbritannica");
            prefs.putBoolean("music", !prefs.getBoolean("music"));
            prefs.flush();
            if (prefs.getBoolean("music")) {
                if (Resources.getInstance().music == null) Resources.getInstance().reInit();
                if (!Resources.getInstance().music.isPlaying()) {
                    Resources.getInstance().music.play();
                    Resources.getInstance().music.setLooping(true);
                }
                musicOnOff.setColor(1, 1, 1, 0.5f);
            } else {
                Resources.getInstance().music.stop();
                musicOnOff.setColor(1, 1, 1, 0.1f);
            }
        }

        if (keycode == Input.Keys.F1) {
            if (cnt >= 1)
                return false;
            changeToScreen = 0;
        }

        if (keycode == Input.Keys.S) {
            if (cnt >= 1)
                return false;
            changeToScreen = 1;
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

        if (Intersector.intersectRayBoundsFast(collisionRay, collisionMusic)) {
            if (cnt >= 1)
                return false;
            Preferences prefs = Gdx.app.getPreferences("paxbritannica");
            prefs.putBoolean("music", !prefs.getBoolean("music"));
            prefs.flush();
            if (prefs.getBoolean("music")) {
                if (Resources.getInstance().music == null) Resources.getInstance().reInit();
                if (!Resources.getInstance().music.isPlaying()) {
                    Resources.getInstance().music.play();
                    Resources.getInstance().music.setLooping(true);
                }
                musicOnOff.setColor(1, 1, 1, 0.5f);
            } else {
                Resources.getInstance().music.stop();
                musicOnOff.setColor(1, 1, 1, 0.1f);
            }
        }

        if (Intersector.intersectRayBoundsFast(collisionRay, collisionHelp)) {
            if (cnt >= 1)
                return false;
            changeToScreen = 0;
        }

        if (Intersector.intersectRayBoundsFast(collisionRay, collisionSettings)) {
            if (cnt >= 1)
                return false;
            changeToScreen = 1;
        }

        if (Intersector.intersectRayBoundsFast(collisionRay, collisionSingleGame)) {
            if (cnt >= 1)
                return false;
            changeToScreen = 2;
        }

        if (Intersector.intersectRayBoundsFast(collisionRay, collisionMultiplayer)) {
            if (cnt >= 1)
                return false;
            changeToScreen = 3;
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

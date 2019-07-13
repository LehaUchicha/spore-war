package com.course.game.screens;

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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;

import com.course.game.Resources;
import com.course.game.GameInstance;
import com.course.game.background.BackgroundFXRenderer;

public class SingleGame extends DefaultScreen implements InputProcessor {
    Sprite back;
    Sprite title;

    FactorySelector p1;
    FactorySelector p2;
    FactorySelector p3;
    FactorySelector p4;
    Countdown countdown;

    OrthographicCamera cam;

    BoundingBox collisionBack = new BoundingBox();
    BoundingBox collisionPlay = new BoundingBox();
    BackgroundFXRenderer backgroundFX = new BackgroundFXRenderer();
    Sprite blackFade;
    Sprite playButton;

    SpriteBatch titleBatch;
    SpriteBatch fadeBatch;
    SpriteBatch plButtonBatch;

    float time = 0;
    float fade = 1.0f;

    int cnt = 0;
    int oldCnt = 0;
    int changeToScreen = -1;
    boolean start = false;
    Array<Integer> playerList;
    Array<Integer> cpuList;

    Ray collisionRay;

    private int width = 800;
    private int height = 480;

    public SingleGame(Game game) {
        super(game);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {
        Resources.getInstance().reInit();

        GameInstance.getInstance().resetGame();

        changeToScreen = -1;

        backgroundFX = new BackgroundFXRenderer();

        title = Resources.getInstance().title;
        blackFade = Resources.getInstance().blackFade;
        playButton = Resources.getInstance().playButton;
        playButton.setSize(120, 120);
        playButton.setPosition(450, 020);
        collisionPlay.set(new Vector3(playButton.getX() - playButton.getHeight(), playButton.getY(), -10), new Vector3(playButton.getX(), playButton.getY() + playButton.getHeight(), 10));

        back = Resources.getInstance().back;
        back.setPosition(20, 010);
        back.setColor(1, 1, 1, 0.5f);
        collisionBack.set(new Vector3(back.getVertices()[0], back.getVertices()[1], -10), new Vector3(back.getVertices()[10], back.getVertices()[11], 10));

        p1 = new FactorySelector(new Vector2(125f, 150f), 1);
        p2 = new FactorySelector(new Vector2(250f, 150f), 2);
        p3 = new FactorySelector(new Vector2(375f, 150f), 3);
        p4 = new FactorySelector(new Vector2(500f, 150f), 4);

        countdown = new Countdown(new Vector2(380f, 7f));

        titleBatch = new SpriteBatch();
        titleBatch.getProjectionMatrix().setToOrtho2D(0, 0, 800, 480);
        fadeBatch = new SpriteBatch();
        fadeBatch.getProjectionMatrix().setToOrtho2D(0, 0, 2, 2);
        plButtonBatch = new SpriteBatch();

        Preferences prefs = Gdx.app.getPreferences("paxbritannica");
        if (prefs.getBoolean("music") == true) {
            if (Resources.getInstance().music == null) Resources.getInstance().reInit();
            if (!Resources.getInstance().music.isPlaying()) {
                Resources.getInstance().music.play();
                Resources.getInstance().music.setLooping(true);
            }
        } else {
            Resources.getInstance().music.stop();
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

        back.setPosition(20 - ((this.width - 800) / 2), 10 - ((this.height - 480) / 2));
        collisionBack.set(new Vector3(back.getVertices()[0], back.getVertices()[1], -10), new Vector3(back.getVertices()[10], back.getVertices()[11], 10));
    }

    @Override
    public void render(float delta) {
        delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());

        time += delta;

        if (time < 1f)
            return;

        backgroundFX.render();

        titleBatch.begin();

        back.draw(titleBatch);
        titleBatch.draw(title, 85f, 320f, 0, 0, 512, 64f, 1.24f, 1.24f, 0);

        p1.draw(titleBatch);
        p2.draw(titleBatch);
        p3.draw(titleBatch);
        p4.draw(titleBatch);

        cnt = 1;
        if (p1.playerSelect)
            cnt = 2;
        if (p2.playerSelect)
            cnt++;
        if (p3.playerSelect)
            cnt++;
        if (p4.playerSelect)
            cnt++;
        if (cnt > 1) {
            countdown.draw(titleBatch);
        }
        if (cnt != oldCnt) {
            countdown.reset();
            oldCnt = cnt;
        }

        if ((p1.picked && (p1.playerSelect)) || (p2.picked && (p2.playerSelect)) || (p3.picked
                && (p3.playerSelect)) || (p4.picked && (p4.playerSelect))) {
            countdown.reset();
            plButtonBatch.begin();
            playButton.draw(plButtonBatch);
            plButtonBatch.end();
        }

        titleBatch.end();

        if (!countdown.finished && fade > 0) {
            fade = Math.max(fade - delta / 2.f, 0);
            fadeBatch.begin();
            blackFade.setColor(blackFade.getColor().r, blackFade.getColor().g, blackFade.getColor().b, fade);
            blackFade.draw(fadeBatch);
            fadeBatch.end();
        }

        if (countdown.finished && start) {
            fade = Math.min(fade + delta / 2.f, 1);
            fadeBatch.begin();
            blackFade.setColor(blackFade.getColor().r, blackFade.getColor().g, blackFade.getColor().b, fade);
            blackFade.draw(fadeBatch);
            fadeBatch.end();
            if (fade >= 1 && cnt >= 2) {
                playerList = new Array<Integer>();
                if (p1.playerSelect == true) {
                    playerList.add(1);
                }
                if (p2.playerSelect == true) {
                    playerList.add(2);
                }
                if (p3.playerSelect == true) {
                    playerList.add(3);
                }
                if (p4.playerSelect == true) {
                    playerList.add(4);
                }
                cpuList = new Array<Integer>();

                game.setScreen(new GameScreen(game, playerList, cpuList));
            } else if (fade >= 1 && cnt < 1) {
                if (changeToScreen == 1) {
                    game.setScreen(new Settings(game));
                } else {
                    game.setScreen(new Help(game));
                }
            }
        }

    }

    @Override
    public void hide() {
        collisionPlay.clr();
    }

    private void diactivatePlayers() {
        p1.reset();
        p2.reset();
        p3.reset();
        p4.reset();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
            boolean exit = true;
            if (p1.picked) {
                p1.reset();
                exit = false;
            }
            if (p2.picked) {
                p2.reset();
                exit = false;
            }
            if (p3.picked) {
                p3.reset();
                exit = false;
            }
            if (p4.picked) {
                p4.reset();
                exit = false;
            }

            if (exit) {
                if (!(Gdx.app.getType() == ApplicationType.Applet)) {
                    Gdx.app.exit();
                }
            }
        }

        if (keycode == Input.Keys.A) {
            if (!p1.picked) {
                p1.picked = true;
            } else {
                p1.playerSelect = true;
                p1.cpuSelect = false;
            }
        }
        if (keycode == Input.Keys.F) {
            if (!p2.picked) {
                p2.picked = true;
            } else {
                p2.playerSelect = true;
                p2.cpuSelect = false;
            }
        }
        if (keycode == Input.Keys.H) {
            if (!p3.picked) {
                p3.picked = true;
            } else {
                p3.playerSelect = true;
                p3.cpuSelect = false;
            }
        }
        if (keycode == Input.Keys.L) {
            if (!p4.picked) {
                p4.picked = true;
            } else {
                p4.playerSelect = true;
                p4.cpuSelect = false;
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
            } else {
                Resources.getInstance().music.stop();
            }
        }

        if (keycode == Input.Keys.F1) {
            if (cnt >= 1)
                return false;
            countdown.finished = true;
            changeToScreen = 0;
        }

        if (keycode == Input.Keys.S) {
            if (cnt >= 1)
                return false;
            countdown.finished = true;
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

        if (Intersector.intersectRayBoundsFast(collisionRay, collisionBack)) {
            game.setScreen(new MainMenu(game));
        }

        if (Intersector.intersectRayBoundsFast(collisionRay, collisionPlay)) {
            start = true;
            countdown.finished = true;
            System.out.println("play");

            if (countdown.finished && start) {

                playerList = new Array<Integer>();
                if (p1.playerSelect == true) {
                    playerList.add(1);
                }
                if (p2.playerSelect == true) {
                    playerList.add(2);
                }
                if (p3.playerSelect == true) {
                    playerList.add(3);
                }
                if (p4.playerSelect == true) {
                    playerList.add(4);
                }
                cpuList = new Array<Integer>();

            }
            game.setScreen(new GameScreen(game, playerList, cpuList));
        }


        if (cnt > 4 || countdown.finished)
            return false;

        if (Intersector.intersectRayBoundsFast(collisionRay, p1.collision) && !p1.picked) {
            diactivatePlayers();
            p1.picked = true;
            p1.playerSelect = true;
            System.out.println("1 pick");
        } else if (Intersector.intersectRayBoundsFast(collisionRay, p2.collision) && !p2.picked) {
            diactivatePlayers();
            p2.picked = true;
            p2.playerSelect = true;
            System.out.println("2 pick");
        } else if (Intersector.intersectRayBoundsFast(collisionRay, p3.collision) && !p3.picked) {
            diactivatePlayers();
            p3.picked = true;
            p3.playerSelect = true;
            System.out.println("3 pick");
        } else if (Intersector.intersectRayBoundsFast(collisionRay, p4.collision) && !p4.picked) {
            diactivatePlayers();
            p4.picked = true;
            p4.playerSelect = true;
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
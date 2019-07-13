package com.course.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.course.game.*;
import com.course.game.gameManagers.EnemyFactory;
import com.course.game.gameManagers.FoodGenerator;
import com.course.game.gameManagers.HeroAnimation;
import com.course.game.gameObject.Enemy;
import com.course.game.gameObject.Hero;

import java.util.ArrayList;
import java.util.Iterator;


public class GameScreen extends DefaultScreen implements InputProcessor {


    private OrthographicCamera cam;
    private SpriteBatch batch;
    float stateTime = 0f;

    private Sprite foodImage;
    private Sprite mapSprite;

    private Sprite weapon;
    private BitmapFont font;

    BoundingBox collisionGenomAbility = new BoundingBox();
    private float rotationSpeed;
    private FoodGenerator foodGenerator;
    private EnemyFactory enemyFactory;

    private float fade = 1.0f;
    private Sprite blackFade;

    private Hero player;

    SpriteBatch fadeBatch;

    private boolean gameOver = false;
    private float gameOverTimer = 5;

    int numPlayers = 0;

    Ray collisionRay;

    private int width = 1024;
    private int height = 1024;

    public GameScreen(Game game, Array<Integer> playerList, Array<Integer> cpuList) {
        super(game);

        rotationSpeed = 0.5f;
        foodImage = new Sprite(Resources.getInstance().food);
        foodImage.setSize(4, 4);


        mapSprite = new Sprite(new Texture(Gdx.files.internal("data/sprites/background2.png")));
        mapSprite.setPosition(0, 0);
        mapSprite.setSize(width, height);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        font = createFont();

        cam = new OrthographicCamera(240, 240 * (h / w));
        cam.position.set(cam.viewportWidth, cam.viewportHeight, 0);
        cam.update();

        foodGenerator = new FoodGenerator(width, height);
        foodGenerator.generate();

        enemyFactory = new EnemyFactory(width, height);
        enemyFactory.generate();
        player = new Hero();


        numPlayers = playerList.size;
        if (playerList.size > 0 && playerList.get(0) == 1) {
            player.setSprite(Resources.getInstance().factoryP1);
            player.setPosition(cam.viewportWidth, cam.viewportHeight);
            player.animation = new HeroAnimation().create("data/sprites/vir1sp.png");
        } else if (playerList.size > 0 && playerList.get(0) == 2) {
            player.setSprite(Resources.getInstance().factoryP2);
            player.setPosition(cam.viewportWidth, cam.viewportHeight);
            player.animation = new HeroAnimation().create("data/sprites/vir2sp.png");
        } else if (playerList.size > 0 && playerList.get(0) == 3) {
            player.setSprite(Resources.getInstance().factoryP3);
            player.setPosition(cam.viewportWidth, cam.viewportHeight);
            player.animation = new HeroAnimation().create("data/sprites/vir3sprite.png");
        } else if (playerList.size > 0 && playerList.get(0) == 4) {
            player.setSprite(Resources.getInstance().factoryP3);
            player.setPosition(cam.viewportWidth, cam.viewportHeight);
            player.animation = new HeroAnimation().create("data/sprites/vir4s.png");
        }
        player.setSize(12, 12);
        // Fade
        blackFade = Resources.getInstance().blackFade;
        fadeBatch = new SpriteBatch();
        fadeBatch.getProjectionMatrix().setToOrtho2D(0, 0, 2, 2);

        cam.update();
        batch = new SpriteBatch();
    }

    private BitmapFont createFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        return font12;
    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = 240f;
        cam.viewportHeight = 240f * height / width;
        cam.update();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        stateTime += Gdx.graphics.getDeltaTime();
        handleInput();
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        batch.begin();

        mapSprite.draw(batch);
        if (player.animation != null) {
            player.setSprite(player.animation, stateTime);

            //змена спрайта по анимации
        }

        player.getSprite().draw(batch);
        for (Enemy enemy : enemyFactory.getEnemyList()) {
            enemy.setSprite(enemy.getH1(), stateTime);
            enemy.moveToTarget(player);
        }
        CharSequence str = "Lvl: " + player.getCurrentLevel().ordinal() + " " + player.getExpirience() + "/" + player.getCurrentLevel().getMaxExperience();
        CharSequence playerBar = "Hp: " + (int) player.getHealth() + " Armor: " + player.getArmory() + " Atk: " + player.getAttack();
        font.draw(batch, str, cam.position.x - cam.viewportWidth / 2 - 10, cam.position.y + cam.viewportHeight / 2 + 8);
        font.draw(batch, playerBar, cam.position.x - cam.viewportWidth / 2 - 10, cam.position.y - cam.viewportHeight / 2 - 10);

        for (Rectangle food : foodGenerator.getFoodList()) {
            batch.draw(foodImage, food.x, food.y, foodImage.getWidth() * 2, foodImage.getHeight() * 2);

        }

        for (Enemy enemy : enemyFactory.getEnemyList()) {
            batch.draw(enemy.getSprite(), enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());

        }
        batch.end();
        cam.update();

        Iterator<Rectangle> iter = foodGenerator.getFoodList().iterator();
        while (iter.hasNext()) {
            Rectangle food = iter.next();
            if (food.y + 64 < 0) iter.remove();
            if (food.overlaps(player.gerRect())) {
                player.setPosition(cam.position.x, cam.position.y);
                if (player.getHealth() < player.getCurrentLevel().getMaxHP()) {
                    player.setHealth(player.getHealth() + 1);
                }
                if (player.getExpirience() < player.getCurrentLevel().getMaxExperience()) {
                    player.increseExpBy(1);
                } else
                    player.increaseLevel();
                iter.remove();
            }
        }

        Iterator<Rectangle> foodIter = foodGenerator.getFoodList().iterator();
        while (foodIter.hasNext()) {
            Rectangle food = foodIter.next();
            for (int i = 0; i < enemyFactory.getEnemyList().size; i++) {
                Enemy en = enemyFactory.getEnemyList().get(i);
                if (food.y + 64 < 0) foodIter.remove();
                if (food.overlaps(en.gerRect())) {
                    if (en.getHealth() < en.getCurrentLevel().getMaxHP()) {
                        en.setHealth(en.getHealth() + 1);
                    }
                    if (en.getExpirience() < en.getCurrentLevel().getMaxExperience()) {
                        en.increseExpBy(1);
                    } else
                        en.increaseLevel();
                    foodIter.remove();
                }
            }
        }

        if (TimeUtils.nanoTime() - foodGenerator.getLastGenerateTime() > 100000000)
            foodGenerator.generate();
        if (TimeUtils.nanoTime() - enemyFactory.getLastGenerateTime() > 100000000)
            enemyFactory.generate();

        Iterator<Enemy> enemyIter = enemyFactory.getEnemyList().iterator();
        while (enemyIter.hasNext()) {
            Enemy enemy = enemyIter.next();
            if (player.gerRect().overlaps(enemy.gerRect())) {
                if (TimeUtils.nanoTime() - player.getLastAttackTime() > 1000000000)
                    player.attack(enemy);
                if (enemy.getHealth() < 0) {
                    enemyIter.remove();
                    player.increseExpBy(10);
                }
            }
            if (enemy.gerRect().overlaps(player.gerRect())) {
                if (TimeUtils.nanoTime() - enemy.getLastAttackTime() > 1000000000)
                    enemy.attack(player);
                if (player.getHealth() < 0)
                    gameOver = true;
            }
        }

        for (int i = 0; i < enemyFactory.getEnemyList().size; i++) {
            Enemy enem = enemyFactory.getEnemyList().get(i);
            for (Enemy anotherEnemy : enemyFactory.getEnemyList()) {
                if (!enem.equals(anotherEnemy)) {
                    if (enem.gerRect().overlaps(anotherEnemy.gerRect())) {
                        if (TimeUtils.nanoTime() - enem.getLastAttackTime() > 1000000000)
                            enem.attack(anotherEnemy);
                        if (enem.getHealth() < 0) {
                            ArrayList<Enemy> delEnemy = new ArrayList<Enemy>();
                            delEnemy.add(anotherEnemy);
                            enemyFactory.getEnemyList().removeValue(anotherEnemy, true);
                            enem.increseExpBy(10);
                        }
                    }
                }
            }
        }

        delta = Math.min(0.06f, delta);

        if (!gameOver && fade > 0 && fade < 100) {
            fade = Math.max(fade - delta / 2.f, 0);
            fadeBatch.begin();
            blackFade.setColor(blackFade.getColor().r, blackFade.getColor().g, blackFade.getColor().b, fade);
            blackFade.draw(fadeBatch);
            fadeBatch.end();
        }

        if (player.getHealth() <= 0) {
            fade = Math.min(fade + delta / 2.f, 1);
            fadeBatch.begin();
            blackFade.setColor(blackFade.getColor().r, blackFade.getColor().g, blackFade.getColor().b, fade);
            blackFade.draw(fadeBatch);
            fadeBatch.end();
            if (fade >= 1) game.setScreen(new MainMenu(game));
        }

        if (gameOver) {
            game.setScreen(new MainMenu(game));
        }
        if (gameOver && gameOverTimer <= 0) {
            fade = Math.min(fade + delta / 2.f, 1);
            fadeBatch.begin();
            blackFade.setColor(blackFade.getColor().r, blackFade.getColor().g, blackFade.getColor().b, fade);
            blackFade.draw(fadeBatch);
            fadeBatch.end();
            game.setScreen(new MainMenu(game));
        }
    }

    private void handleInput() {

        if (Gdx.input.isTouched()) {

            Vector3 coord = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            collisionRay = cam.getPickRay(coord.x, coord.y);

            if (Intersector.intersectRayBoundsFast(collisionRay, collisionGenomAbility)) {
                System.out.print("genom");
            }

            float curX = player.getX();
            float curY = player.getY();

            Vector3 gameCoordXY = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            Vector3 touchPosition = cam.unproject(gameCoordXY);

            float widthK = Math.abs(touchPosition.x - curX);
            float heightK = Math.abs(touchPosition.y - curY);
            float gipotenuza = (float) Math.sqrt(widthK * widthK + heightK * heightK);
            float sin = widthK / gipotenuza;
            float cos = heightK / gipotenuza;
            if (curX < touchPosition.x && curY < touchPosition.y) {
                cam.translate(player.getSpeed() * sin, player.getSpeed() * cos, 0);
                player.setPosition(cam.position.x, cam.position.y);
            }
            if (curX > touchPosition.x && curY < touchPosition.y) {
                cam.translate(-player.getSpeed() * sin, player.getSpeed() * cos, 0);
                player.setPosition(cam.position.x, cam.position.y);
            }
            if (curX > touchPosition.x && curY > touchPosition.y) {
                cam.translate(-player.getSpeed() * sin, -player.getSpeed() * cos, 0);
                player.setPosition(cam.position.x, cam.position.y);
            }
            if (curX < touchPosition.x && curY > touchPosition.y) {
                cam.translate(player.getSpeed() * sin, -player.getSpeed() * cos, 0);
                player.setPosition(cam.position.x, cam.position.y);
            }

        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.rotate(-rotationSpeed, 0, 0, 1);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            cam.rotate(rotationSpeed, 0, 0, 1);
        }

        cam.zoom = MathUtils.clamp(cam.zoom, 1.5f, 1024 / cam.viewportWidth);

        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 1024 - effectiveViewportWidth / 2f);
        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 1024 - effectiveViewportHeight / 2f);
    }

    @Override
    public void hide() {
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            gameOver = true;
            gameOverTimer = 0;
        }

        if (keycode == Input.Keys.ESCAPE) {
            gameOver = true;
            gameOverTimer = 0;
        }
        cam.update();
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        collisionRay = cam.getPickRay(x, y);
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

}

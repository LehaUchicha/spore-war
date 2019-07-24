package ru.game.spore.gameManagers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import ru.game.spore.Resources;
import ru.game.spore.gameObject.Enemy;

/**
 * Created by Alex on 28.04.2016.
 */
public class EnemyFactory {

    private Array<Enemy> enemies;
    private float width, height;
    private long lastDropTime;

    public EnemyFactory(float width, float height){
        this.width = width;
        this.height = height;
        enemies = new Array<Enemy>();
    }
    
    public void generate(){
        if(enemies.size < 10) {
            float x = MathUtils.random(150, width);
            float y = MathUtils.random(150, height);

            Enemy enemy = new Enemy();
            enemy.setSprite(Resources.getInstance().enemy);
            enemy.setSize(16, 16);
            enemy.setPosition(x, y);

            enemies.add(enemy);
            lastDropTime = TimeUtils.nanoTime();
        }
    }

    public Array<Enemy> getEnemyList(){
        return enemies;
    }

    public long getLastGenerateTime(){
        return lastDropTime;
    }
}

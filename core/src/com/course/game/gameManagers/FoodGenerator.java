package com.course.game.gameManagers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Alex on 28.04.2016.
 */
public class FoodGenerator {

    private Array<Rectangle> foods;
    private float width, height;
    private long lastDropTime;
    
    public FoodGenerator(float width, float height){
        this.width = width;
        this.height = height;
        foods = new Array<Rectangle>();
    }
    
    public void generate(){
        Rectangle food = new Rectangle();
        food.x = MathUtils.random(120, width - 120);
        food.y = MathUtils.random(120, height - 120);
        food.width = 6;
        food.height = 6;
        foods.add(food);
        lastDropTime = TimeUtils.nanoTime();
    }

    public Array<Rectangle> getFoodList(){
        return foods;
    }

    public long getLastGenerateTime(){
        return lastDropTime;
    }
}

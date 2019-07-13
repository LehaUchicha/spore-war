package com.course.game.gameObject;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.course.game.gameManagers.HeroAnimation;

/**
 * Created by Alex on 04.05.2016.
 */
public class Enemy extends Cell{
    private Animation h1;
    private Rectangle radar;

    public Enemy(Sprite imageSrc, HeroAnimation an1){
        this.imageSrc = imageSrc;
    }

    public Enemy() {
        setSpeed(0.4f);
        setHealth(100);
        setArmory(5);
        setAttack(8);
        name = "Enemy";
        position = new Vector2();
        rect = new Rectangle();
        radar = new Rectangle();
        radar.setSize(50f, 50f);
        level = Level.ZERO;
        this.h1=new HeroAnimation().create("data/sprites/enemySprite2.png");
    }

    public void moveToTarget(Cell cell){
        float curX = getX();
        float curY = getY();
        System.out.println("Enemy x:"+curX+" Enemy y:"+curY);

        Vector3 targetPosition = new Vector3(cell.getX(), cell.getY(), 0);

        System.out.println("Hero x:"+targetPosition.x+" Hero y:"+targetPosition.y);
        float widthK = Math.abs(targetPosition.x - curX);
        float heightK = Math.abs(targetPosition.y - curY);
        float gipotenuza = (float)Math.sqrt(widthK * widthK + heightK * heightK);
        float sin = widthK / gipotenuza;
        float cos = heightK / gipotenuza;
        if(curX < targetPosition.x && curY < targetPosition.y ){
            setPosition(getX()+ getSpeed() * sin, getY()+ getSpeed() * cos);
        }
        if(curX > targetPosition.x && curY < targetPosition.y){
            setPosition(getX()-getSpeed() * sin, getY()+getSpeed() * cos);
        }
        if(curX > targetPosition.x && curY > targetPosition.y){
            setPosition(getX()-getSpeed() * sin, getY()-getSpeed() * cos);
        }
        if(curX < targetPosition.x && curY > targetPosition.y){
            setPosition(getX()+getSpeed() * sin, getY()-getSpeed() * cos);
        }
    }

    public Animation getH1() {
        return h1;
    }

    public void setH1(Animation h1) {
        this.h1 = h1;
    }
}

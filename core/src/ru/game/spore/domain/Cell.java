package ru.game.spore.domain;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Alex on 05.05.2016.
 */
public class Cell {
    protected Vector2 position;
    protected float speed = 0.8f;
    protected float attack;
    protected float health;
    protected float armory;
    protected String name;
    protected Rectangle rect;
    protected Sprite imageSrc;
    protected Level level;
    protected int expirience;
    protected long lastAttackTime;
    public com.badlogic.gdx.graphics.g2d.Animation animation;

    public void setPosition(float x, float y){
        position.x = x;
        position.y = y;
        if(imageSrc != null){
            rect.setPosition(x, y);
            imageSrc.setPosition(position.x, position.y);
        }
    }

    public Rectangle gerRect(){
        return rect;
    }

    public float getWidth(){
        return imageSrc.getWidth();
    }

    public void increseExpBy(int count){
        expirience+=count;
    }


    public int getExpirience(){
        return expirience;
    }

    public float getHeight(){
        return imageSrc.getHeight();
    }

    public void setSize(float w, float h){
        imageSrc.setSize(w, h);
        rect.setSize(w, h);
    }

    public void setLevel(Level level){
        this.level = level;
    }

    public void increaseLevel(){
        switch(level.ordinal()) {
            case 0: {
                setLevel(Level.ONE);
                increaseCharacteristics();
                health = level.getMaxHP();
                break;
            }
            case 1: {
                setLevel(Level.TWO);
                increaseCharacteristics();
                health = level.getMaxHP();
                break;
            }
            case 2: {
                setLevel(Level.THRE);
                increaseCharacteristics();
                health = level.getMaxHP();
                break;
            }
            case 3: {
                setLevel(Level.FOUR);
                increaseCharacteristics();
                health = level.getMaxHP();
                break;
            }
            case 4: {
                setLevel(Level.FIVE);
                increaseCharacteristics();
                health = level.getMaxHP();
                break;
            }
            case 5: {
                setLevel(Level.SIX);
                increaseCharacteristics();
                health = level.getMaxHP();
                break;
            }
            case 6: {
                setLevel(Level.SEVEN);
                increaseCharacteristics();
                health = level.getMaxHP();
                break;
            }
            case 7: {
                setLevel(Level.EIGHT);
                increaseCharacteristics();
                health = level.getMaxHP();
                break;
            }
            case 8: {
                setLevel(Level.NINE);
                increaseCharacteristics();
                health = level.getMaxHP();
                break;
            }
            case 9: {
                setLevel(Level.TEN);
                increaseCharacteristics();
                health = level.getMaxHP();
                break;
            }
        }
    }

    private void increaseCharacteristics(){
        speed+=0.075f;
        armory+=5;
        attack+=2;
        setSize(imageSrc.getWidth()+1f, imageSrc.getHeight()+1f);
    }

    public Level getCurrentLevel(){
        return level;
    }

    public float getX(){
        return position.x;
    }

    public float getY(){
        return position.y;
    }

    public void setSprite(com.badlogic.gdx.graphics.g2d.Animation an1,float time1){
        this.imageSrc.setRegion((TextureRegion) an1.getKeyFrame(time1, true)) ;
    }
    public void setSprite(Sprite imageSrc1){
        this.imageSrc=imageSrc1 ;
    }

    public Sprite getSprite(){

        return imageSrc;
    }

    public void attack(Cell enemy){
        lastAttackTime = TimeUtils.nanoTime();
        enemy.setHealth(enemy.getHealth() - this.attack + enemy.getArmory() * 0.01f);
        if(getX()< enemy.getX() && getY() < enemy.getY())
            enemy.setPosition(enemy.getX()+45, enemy.getY()+45);
        if(getX()< enemy.getX() && getY() > enemy.getY())
            enemy.setPosition(enemy.getX()+45, enemy.getY()-45);
        if(getX()> enemy.getX() && getY() < enemy.getY())
            enemy.setPosition(enemy.getX()-45, enemy.getY()+45);
        if(getX()> enemy.getX() && getY() > enemy.getY())
            enemy.setPosition(enemy.getX()-45, enemy.getY()-45);

    }

    public long getLastAttackTime(){
        return lastAttackTime;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getArmory() {
        return armory;
    }

    public void setArmory(int armory) {
        this.armory = armory;
    }

    public float getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}

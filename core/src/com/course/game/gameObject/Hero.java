package com.course.game.gameObject;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.course.game.gameManagers.HeroAnimation;
import com.course.game.inventoryObject.Inventory;

/**
 * Created by 1 on 17.04.2016.
 */
public class Hero extends Cell {

    protected Inventory inventory;

    public Hero(Sprite imageSrc, HeroAnimation an1){
        this.imageSrc = imageSrc;
    }

    public Hero() {
        setSpeed(0.6f);
        setHealth(100);
        setArmory(5);
        setAttack(8);
        name = "Hero";
        position = new Vector2();
        rect = new Rectangle();
        rect.setSize(16, 16);
        level = Level.ZERO;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }


}
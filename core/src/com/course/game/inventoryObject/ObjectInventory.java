package com.course.game.inventoryObject;

import com.badlogic.gdx.graphics.Texture;
import com.course.game.gameObject.Hero;

/**
 * Created by 1 on 17.04.2016.
 */
public class ObjectInventory {
    private Texture texture;
    private String name;
    private String description;
    private int deltaspeed;
    private int deltaforcebite;
    private int deltahealth;
    private int deltaarmory;

    ObjectInventory() {
        name = "inv";
        description = "descr";
        deltaarmory = 0;
        deltaforcebite = 0;
        deltahealth = 0;
        deltaspeed = 0;
    }

    public void action(Hero h1) {
        h1.setSpeed(h1.getSpeed() + this.deltaspeed);
        h1.setHealth(h1.getHealth() + this.deltahealth);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDeltaspeed() {
        return deltaspeed;
    }

    public void setDeltaspeed(int deltaspeed) {
        this.deltaspeed = deltaspeed;
    }

    public int getDeltaforcebite() {
        return deltaforcebite;
    }

    public void setDeltaforcebite(int deltaforcebite) {
        this.deltaforcebite = deltaforcebite;
    }

    public int getDeltahealth() {
        return deltahealth;
    }

    public void setDeltahealth(int deltahealth) {
        this.deltahealth = deltahealth;
    }

    public int getDeltaarmory() {
        return deltaarmory;
    }

    public void setDeltaarmory(int deltaarmory) {
        this.deltaarmory = deltaarmory;
    }
}

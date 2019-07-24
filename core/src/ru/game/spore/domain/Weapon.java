package ru.game.spore.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Alex on 24.04.2016.
 */
public class Weapon {
    public Sprite image = new Sprite(new Texture(Gdx.files.internal("data/sprites/weapon.png")));;

    public Sprite getSprite() {
        return image;
    }
}

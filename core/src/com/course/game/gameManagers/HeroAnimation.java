package com.course.game.gameManagers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by 1 on 23.04.2016.
 */
public class HeroAnimation {
    private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 2;

    Animation walkAnimation;
    Texture walkSheet;
    TextureRegion[] walkFrames;

    public Animation create(String src) {
        walkSheet = new Texture(Gdx.files.internal(src));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.100f, walkFrames);

        return walkAnimation;
    }

}

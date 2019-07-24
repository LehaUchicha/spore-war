package ru.game.spore.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import ru.game.spore.Resources;


public class FactorySelector extends Sprite {

    public boolean picked = false;
    public boolean playerSelect = false;
    public boolean cpuSelect = false;

    public BoundingBox collision = new BoundingBox();
    public BoundingBox collisionPlayerSelect = new BoundingBox();
    public BoundingBox collisionCPUSelect = new BoundingBox();
    public Vector3 collisionMinVector = new Vector3();
    public Vector3 collisionMaxVector = new Vector3();

    private float fade = 0.2f;
    private float fadeButton = 0.0f;

    private float pulse_time = 0;

    private Sprite button;
    private Sprite aPlayerButton;


    float delta;

    private Vector2 position = new Vector2();

    public FactorySelector(Vector2 position, int id) {
        super();
        this.position = position;
        this.setPosition(position.x, position.y);

        switch (id) {
            case 1:
                this.set(Resources.getInstance().factoryP1);
                break;
            case 2:
                this.set(Resources.getInstance().factoryP2);
                break;
            case 3:
                this.set(Resources.getInstance().factoryP3);
                break;
            default:
                this.set(Resources.getInstance().factoryP4);
                break;
        }
        setRotation(90);
        this.setPosition(position.x, position.y);
        this.setColor(0, 0, 0, 1);

        button = new Sprite(Resources.getInstance().aButton);
        button.setPosition(position.x + 35f, position.y + 85.f);

        aPlayerButton = new Sprite(Resources.getInstance().playerButton);
        aPlayerButton.setPosition(position.x, position.y + 85.f);

        float pulse = (1 + MathUtils.cos((pulse_time / 180.f) * 2.f * MathUtils.PI)) / 2.f;
        float color = fade * pulse + 1 * (1 - pulse);
        this.setColor(color, color, color, 1);
        button.setColor(color, color, color, 1);
    }

    public void reset() {
        picked = false;
        cpuSelect = false;
        playerSelect = false;

        fade = 0.2f;
        fadeButton = 0.0f;

        pulse_time = 0;
        float pulse = (1 + MathUtils.cos((pulse_time / 180.f) * 2.f * MathUtils.PI)) / 2.f;
        float color = fade * pulse + 1 * (1 - pulse);
        this.setColor(color, color, color, 1);
        button.setColor(color, color, color, 1);
    }

    @Override
    public void draw(Batch batch) {


        delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());

        super.draw(batch);

        collisionMinVector.set(this.getVertices()[0], this.getVertices()[1], -10);
        collisionMaxVector.set(this.getVertices()[10], this.getVertices()[11], 10);
        collision.set(collisionMinVector, collisionMaxVector);

        collisionMinVector.set(this.getVertices()[0], this.getVertices()[1], -10);
        collisionMaxVector.set(this.getVertices()[10], this.getVertices()[11], 10);
        collisionMinVector.y += ((this.getVertices()[11] - this.getVertices()[1]) / 2);
        collisionPlayerSelect.set(collisionMinVector, collisionMaxVector);

        collisionMinVector.set(this.getVertices()[0], this.getVertices()[1], -10);
        collisionMaxVector.set(this.getVertices()[10], this.getVertices()[11], 10);
        collisionMaxVector.y -= ((this.getVertices()[11] - this.getVertices()[1]) / 2);
        collisionCPUSelect.set(collisionMinVector, collisionMaxVector);


        pulse_time += Gdx.graphics.getDeltaTime();

        float pulse = (1 + MathUtils.cos((pulse_time / 5.f) * 2.f * MathUtils.PI)) / 2.f;
        float color = fade * pulse + 1 * (1 - pulse);

        if (picked && !(playerSelect || cpuSelect)) {
            button.draw(batch);
            button.setColor(0.2f, 0.2f, 0.2f, 1);
        } else {
            if (playerSelect) {
                aPlayerButton.draw(batch);
                aPlayerButton.setColor(color, color, color, 1);
            } else {
                button.draw(batch);
                button.setColor(color, color, color, 1);
            }
        }

        if (picked && !(playerSelect || cpuSelect)) {
            fade = 0.2f;
            this.setColor(fade, fade, fade, 1);

            fadeButton = Math.min(fadeButton + delta, 1);

        } else if (playerSelect || cpuSelect) {
            fade = Math.min(fade + delta, 1);
            this.setColor(fade, fade, fade, 1);

            fadeButton = Math.max(fadeButton - delta, 0);

        }
    }
}
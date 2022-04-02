package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Scrollable {
    private Texture texture;
    private Vector2 position,velocity;
    private int width,height;

    public Scrollable(Texture texture, float x, float y,
                      int width, int height, float scrollSpeed){
        this.texture = texture;
        this.position = new Vector2(0,0);
        this.velocity = new Vector2(scrollSpeed,0);
        this.width = width;
        this.height = height;
    }

    public void update(float delta){
        position.add(velocity.cpy().scl(delta));

        if(position.x + width < 0){
            position.x += width;
        }
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture,position.x,0,width,height);
        batch.draw(texture,position.x + width,0,width,height);
    }
}

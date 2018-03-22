package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class CheesePlease extends Game {

    private SpriteBatch batch;
    private Sprite mouseySprite;
    private Sprite cheeseSprite;
    private Sprite floorSprite;
    private Sprite winTextSprite;
    private boolean win;

    public void create() {
        batch = new SpriteBatch();

        mouseySprite = new Sprite(new Texture(Gdx.files.internal("assets/mouse.png")));
        mouseySprite.setPosition(20,20);

        cheeseSprite = new Sprite(new Texture(Gdx.files.internal("assets/cheese.png")));
        cheeseSprite.setPosition(400,300);

        floorSprite = new Sprite(new Texture(Gdx.files.internal("assets/tiles.jpg")));
        floorSprite.setPosition(0,0);

        winTextSprite = new Sprite(new Texture(Gdx.files.internal("assets/you-win.png")));
        winTextSprite.setPosition(170,60);

        win = false;
    }

    public void render() {

        // Process input
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            mouseySprite.translateX(-1);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            mouseySprite.translateX(1);
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            mouseySprite.translateY(1);
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            mouseySprite.translateY(-1);

        // Check win condition
        Rectangle cheeseRectangle = cheeseSprite.getBoundingRectangle();
        Rectangle mouseyRectangle = mouseySprite.getBoundingRectangle();

        if(cheeseRectangle.contains(mouseyRectangle))
            win = true;

        // Draw graphics
        Gdx.gl.glClearColor(0.8f, 0.8f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        floorSprite.draw(batch);
        cheeseSprite.draw(batch);
        mouseySprite.draw(batch);
        if(win)
            winTextSprite.draw(batch);
        batch.end();
    }
}

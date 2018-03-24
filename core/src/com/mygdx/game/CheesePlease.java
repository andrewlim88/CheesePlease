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
import com.badlogic.gdx.scenes.scene2d.Stage;

public class CheesePlease extends Game {

    public Stage mainStage;
    private BaseActor mousey;
    private BaseActor cheese;
    private BaseActor floor;
    private BaseActor winText;

    public void create() {
        mainStage = new Stage();

        floor = new BaseActor();
        floor.setTexture(new Texture(Gdx.files.internal("assets/tiles.jpg")));
        floor.setPosition(0,0);
        mainStage.addActor(floor);

        cheese = new BaseActor();
        cheese.setTexture(new Texture(Gdx.files.internal("assets/cheese.png")));
        cheese.setPosition(400,300);
        mainStage.addActor(cheese);

        mousey = new BaseActor();
        mousey.setTexture(new Texture(Gdx.files.internal("assets/mouse.png")));
        mousey.setPosition(20,20);
        mainStage.addActor(mousey);

        winText = new BaseActor();
        winText.setTexture(new Texture(Gdx.files.internal("assets/you-win.png")));
        winText.setPosition(170,60);
        winText.setVisible(false);
        mainStage.addActor(winText);
    }

    public void render() {

        // Process input
        mousey.velocityX = 0;
        mousey.velocityY = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            mousey.velocityX -= 100;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            mousey.velocityX += 100;
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            mousey.velocityY += 100;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            mousey.velocityY -= 100;



        // Update
        float dt = Gdx.graphics.getDeltaTime();
        mainStage.act(dt);

        // Check win condition
        Rectangle cheeseRectangle = cheese.getBoundingRectangle();
        Rectangle mouseyRectangle = mousey.getBoundingRectangle();

        if(cheeseRectangle.contains(mouseyRectangle))
            winText.setVisible(true);

        // Draw Graphics
        Gdx.gl.glClearColor(0.8f, 0.8f, 1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainStage.draw();
    }
}
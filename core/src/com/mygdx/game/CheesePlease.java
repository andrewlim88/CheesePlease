package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class CheesePlease extends Game {

    // Game world dimensions
    final int mapWidth = 800;
    final int mapHeight = 800;

    // Window dimensions
    final int viewWidth = 640;
    final int viewHeight = 480;

    public Stage mainStage;
    public Stage uiStage;
    private AnimatedActor mousey;
    private BaseActor cheese;
    private BaseActor floor;
    private BaseActor winText;
    private Boolean win;
    private float timeElapsed;
    private Label timeLabel;

    public void create() {
        mainStage = new Stage();
        uiStage = new Stage();
        win = false;
        timeElapsed = 0;

        BitmapFont font = new BitmapFont();
        String text = "Time: 0";
        Label.LabelStyle style = new Label.LabelStyle(font, Color.NAVY);
        timeLabel = new Label(text, style);
        timeLabel.setFontScale(2);
        timeLabel.setPosition(500,440);
        uiStage.addActor(timeLabel);


        floor = new BaseActor();
        floor.setTexture(new Texture(Gdx.files.internal("assets/tiles-800-800.jpg")));
        floor.setPosition(0,0);
        mainStage.addActor(floor);

        cheese = new BaseActor();
        cheese.setTexture(new Texture(Gdx.files.internal("assets/cheese.png")));
        cheese.setPosition(400,300);
        mainStage.addActor(cheese);

        TextureRegion[] frames = new TextureRegion[4];
        for(int n = 0; n < 4; n++) {
            String fileName = "assets/mouse" + n + ".png";
            Texture tex = new Texture(Gdx.files.internal(fileName));
            tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            frames[n] = new TextureRegion(tex);
        }
        Array<TextureRegion> framesArray = new Array<TextureRegion>(frames);
        Animation anim = new Animation(0.1f,framesArray, Animation.PlayMode.LOOP_PINGPONG);

        mousey = new AnimatedActor();
        mousey.setAnimation(anim);
        mousey.setOrigin(mousey.getWidth()/2, mousey.getHeight()/2);
        mousey.setPosition(20,20);
        mainStage.addActor(mousey);

        winText = new BaseActor();
        winText.setTexture(new Texture(Gdx.files.internal("assets/you-win.png")));
        winText.setPosition(170,60);
        winText.setVisible(false);
        uiStage.addActor(winText);
    }

    public void render() {

        // Process input
        mousey.velocityX = 0;
        mousey.velocityY = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            mousey.velocityX -= 200;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            mousey.velocityX += 200;
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            mousey.velocityY += 200;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            mousey.velocityY -= 200;



        // Update
        float dt = Gdx.graphics.getDeltaTime();
        mainStage.act(dt);
        uiStage.act(dt);

        mousey.setX(MathUtils.clamp(mousey.getX(),0,mapWidth - mousey.getWidth()));
        mousey.setY(MathUtils.clamp(mousey.getY(),0,mapHeight - mousey.getHeight()));

        if (!win) {
            timeElapsed += dt;
            timeLabel.setText("Time: " + (int)timeElapsed);
        }

        // Check win condition
        Rectangle cheeseRectangle = cheese.getBoundingRectangle();
        Rectangle mouseyRectangle = mousey.getBoundingRectangle();

        if (!win && cheeseRectangle.contains(mouseyRectangle)) {

            Action spinShrinkFadeOut = Actions.parallel(
                Actions.alpha(1),   // Set transparency
                Actions.rotateBy(360, 1),   // Rotation amount, duration
                Actions.scaleTo(0,0,1),             // x amount, y amount, duration
                Actions.fadeOut(1)                        // Duration of fade out
            );



            Action fadeInColorCycleForever = Actions.sequence(
                    //Actions.alpha(1),
                    Actions.show(),
                    //Actions.fadeIn(2),
                    Actions.forever(
                            Actions.sequence(
                                    Actions.color(new Color(1,0,0,1),1),
                                    Actions.color(new Color(0,0,1,1),1)
                            )
                    )
            );
            winText.addAction(fadeInColorCycleForever);
            cheese.addAction(spinShrinkFadeOut);


        }

        // Draw Graphics
        Gdx.gl.glClearColor(0.8f, 0.8f, 1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Camera
        Camera cam = mainStage.getCamera();
        cam.position.set(mousey.getX() + mousey.getOriginX(),mousey.getY() + mousey.getOriginY(),0);
        cam.position.x = MathUtils.clamp(cam.position.x, viewWidth/2, mapWidth - viewWidth/2);
        cam.position.y = MathUtils.clamp(cam.position.y, viewHeight/2, mapHeight - viewHeight/2);
        cam.update();


        mainStage.draw();
        uiStage.draw();
    }
}
package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

/**
 * @author Andrew
 */

public class AnimatedActor extends BaseActor{

    public float elapsedTime;
    public Animation<TextureRegion> anim;

    public AnimatedActor() {
        super();
        elapsedTime = 0;
    }

    public void setAnimation(Animation<TextureRegion> a) {
        Texture t = a.getKeyFrame(0).getTexture();
        setTexture(t);
        anim = a;
    }

    public void act(float dt) {
        super.act(dt);
        elapsedTime += dt;
        if(velocityX != 0 || velocityY != 0)
            setRotation(MathUtils.atan2(velocityY, velocityX) * MathUtils.radiansToDegrees);
    }

    public void draw(Batch batch, float parentAlpha) {
        region.setRegion(anim.getKeyFrame(elapsedTime));
        super.draw(batch, parentAlpha);
    }
}

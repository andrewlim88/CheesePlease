package com.mygdx.game;

import com.badlogic.gdx.Game;

/**
 * @author Andrew
 */

public class CheeseGame extends Game{
    public void create() {
        CheeseLevel cl = new CheeseLevel(this);
        setScreen(cl);
    }
}
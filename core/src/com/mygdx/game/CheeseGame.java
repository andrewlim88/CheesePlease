package com.mygdx.game;

import com.badlogic.gdx.Game;

/**
 * @author Andrew
 */

public class CheeseGame extends Game{
    public void create() {
        CheeseMenu cm = new CheeseMenu(this);
        setScreen(cm);
    }
}
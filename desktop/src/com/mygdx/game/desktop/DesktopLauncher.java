package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.GameLauncher;

/**
 * Launches the game in order.
 *
 * @author biGgEsT yEeT: tHe fiNaL fOrM
 */ 
public class DesktopLauncher {
 
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        LwjglApplication gameLauncher = new LwjglApplication(new GameLauncher(), config);

    }
}

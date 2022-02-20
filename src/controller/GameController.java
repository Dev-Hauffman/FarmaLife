package controller;

import java.awt.event.KeyEvent;

import game.Game;
import input.Input;

public class GameController {
    private Input input;

    public GameController(Input input) {
        this.input = input;
    }
    
    public void update(Game game) {
        if (input.isToggled(KeyEvent.VK_F2)) {
            game.getSettings().toggleDebugMode();
        }

        if (input.isToggled(KeyEvent.VK_ADD)) {
            game.getSettings().increaseGameSpeed();
        }

        if (input.isToggled(KeyEvent.VK_SUBTRACT)) {
            game.getSettings().decreaseGameSpeed();
        }
    }
    
}

package controller;

import input.Input;
import java.awt.event.KeyEvent;

public class KeyboardController {
    private Input input;
    
    public KeyboardController(Input input) {
        this.input = input;
    }

    public boolean isRequestingEnter() {
        return input.isToggled(KeyEvent.VK_ENTER);
    }
}

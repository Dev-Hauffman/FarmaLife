/*
* Controller is responsible for the keyboard keys used in the game, here in the implementation we define which keys are used
*/

package controller;
import java.awt.event.KeyEvent;
import input.Input;

public class PlayerController implements IController{

    private Input input;
    
    public PlayerController(Input input) {
        this.input = input;
    }

    @Override
    public boolean isRequestingUp() {
        return input.isPressed(KeyEvent.VK_UP);        
    }

    @Override
    public boolean isRequestingDown() {
        return input.isPressed(KeyEvent.VK_DOWN);
    }

    @Override
    public boolean isRequestingLeft() {
        return input.isPressed(KeyEvent.VK_LEFT);
    }

    @Override
    public boolean isRequestingRight() {
        return input.isPressed(KeyEvent.VK_RIGHT);
    }

    @Override
    public boolean isRequestingMindAccess() {
        return input.isPressed(KeyEvent.VK_SLASH);
    }

    @Override
    public boolean isRequestingAnalysis() {
        return input.isPressed(KeyEvent.VK_SHIFT);
    }

    @Override
    public boolean isRequestingExit() {
        return input.isPressed(KeyEvent.VK_ESCAPE);
    }    
    
}

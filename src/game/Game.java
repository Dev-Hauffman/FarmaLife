/*
* Game is the class that holds the window, the display to which things will be drawn
* Holds the inputs
* It's like a central hub with everything? Like a handler?
*/

package game;

import controller.GameController;
import core.Size;
import display.Display;
import game.settings.GameSettings;
import input.Input;
import state.State;
import state.menu.MenuState;

public class Game {

    public static int SPRITE_SIZE = 64;

    private Display display;
    private Input input;
    private State state;
    private GameSettings settings;
    private GameController gameController;

    public Game(int width, int height){
        input = new Input();
        display = new Display(width, height, input); 
        settings = new GameSettings(true);
        state = new MenuState(new Size(width, height), input, settings);
        gameController = new GameController(input);
    }
    
    public void update(){
        state.update(this);
        gameController.update(this);
    }

    public void render() {
        display.render(state, settings.isDebugMode());
    }

    public GameSettings getSettings() {
        return settings;
    }

    public void enterState(State nextState) {
        state.cleanUp();
        state = nextState;
    }

}

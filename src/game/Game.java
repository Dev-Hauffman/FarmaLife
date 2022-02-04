/*
* Game is the class that holds the window, the display to which things will be drawn
* Holds the inputs
* It's like a central hub with everything? Like a handler?
*/

package game;

import display.Display;
import game.state.GameState;
import game.state.State;
import input.Input;

public class Game {

    public static int SPRITE_SIZE = 64;

    private Display display;
    private Input input;
    private State state;

    public Game(int width, int height){
        input = new Input();
        display = new Display(width, height, input); 
        state = new GameState(input);
    }
    
    public void update(){
        state.update();
    }

    public void render() {
        display.render(state);
    }

}

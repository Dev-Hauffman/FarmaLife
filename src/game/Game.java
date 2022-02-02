/*
* Game is the class that holds the window, the display to which things will be drawn
* Holds the inputs
* It's like a central hub with everything? Like a handler?
*/

package game;
import java.util.ArrayList;
import java.util.List;

import controller.PlayerController;
import display.Display;
import entity.GameObject;
import entity.Player;
import gfx.SpriteLibrary;
import input.Input;

public class Game {

    public static int SPRITE_SIZE = 64;

    private Display display;
    private List<GameObject> gameObject;
    private Input input;
    private SpriteLibrary spriteLibrary;

    public Game(int width, int height){
        input = new Input();
        display = new Display(width, height, input);
        gameObject =  new ArrayList<>();
        spriteLibrary = new SpriteLibrary();
        gameObject.add(new Player(new PlayerController(input), spriteLibrary));
    }
    
    public void update(){
        gameObject.forEach(gameObject -> gameObject.update());
    }

    public void render() {
        display.render(this);
    }

    public List<GameObject> getGameObject() {
        return gameObject;
    }
}

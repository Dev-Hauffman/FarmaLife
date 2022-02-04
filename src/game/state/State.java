package game.state;

import java.util.ArrayList;
import java.util.List;

import entity.GameObject;
import gfx.SpriteLibrary;
import input.Input;
import map.GameMap;

public abstract class State {    
    protected List<GameObject> gameObject;
    protected SpriteLibrary spriteLibrary;
    protected Input input;
    protected GameMap gameMap;

    public State(Input input) {
        this.input = input;
        gameObject =  new ArrayList<>();
        spriteLibrary = new SpriteLibrary();        
    }

    public void update() {
        gameObject.forEach(gameObject -> gameObject.update());
    }

    public List<GameObject> getGameObject() {
        return gameObject;
    }

    public GameMap getGameMap() {
        return gameMap;
    }
    
}

package game.state;

import java.util.ArrayList;
import java.util.List;

import core.Size;
import display.Camera;
import entity.GameObject;
import gfx.SpriteLibrary;
import input.Input;
import map.GameMap;

public abstract class State {    
    protected List<GameObject> gameObject;
    protected SpriteLibrary spriteLibrary;
    protected Input input;
    protected GameMap gameMap;
    protected Camera camera;

    public State(Size windowSize, Input input) {
        this.input = input;
        gameObject =  new ArrayList<>();
        spriteLibrary = new SpriteLibrary();
        camera = new Camera(windowSize);
    }

    public void update() {
        gameObject.forEach(gameObject -> gameObject.update());
        camera.update(this);
    }

    public List<GameObject> getGameObject() {
        return gameObject;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public Camera getCamera() {
        return camera;
    }   
    
}

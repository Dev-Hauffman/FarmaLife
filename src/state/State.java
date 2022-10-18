package state;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import audio.AudioPlayer;
import core.Position;
import core.Size;
import display.Camera;
import entity.GameObject;
import game.Game;
import game.Time;
import game.settings.GameSettings;
import gamespace.GameSpace;
import gamespace.IGameSpace;
import gfx.SpriteLibrary;
import input.Input;
import text.Text;
import ui.UIContainer;

public abstract class State {  
    
    protected GameSettings gameSettings;
    protected AudioPlayer audioPlayer;
    protected List<GameObject> gameObjects;
    protected List<UIContainer> uiContainers;
    protected SpriteLibrary spriteLibrary;
    protected Input input;
    protected IGameSpace gameSpace;
    protected Camera camera;
    protected Time time;
    protected Text text; //DELETE

    protected Size windowSize;

    private State nextState;

    public State(Size windowSize, Input input, GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        this.windowSize = windowSize;
        this.input = input;
        audioPlayer = new AudioPlayer(gameSettings.getAudioSettings());
        gameObjects =  new ArrayList<>();
        uiContainers = new ArrayList<>();
        spriteLibrary = new SpriteLibrary();
        text = new Text("Another Test", spriteLibrary, "testFont", 50, 50, 64); //DELETE
        gameSpace = new GameSpace(new Size(windowSize.getWidth(), windowSize.getHeight()));
        camera = new Camera(windowSize);
        time = new Time();
    }

    public void update(Game game) {
        audioPlayer.update();
        time.update();
        sortObjectByPosition();
        updateGameObjects();
        List.copyOf(uiContainers).forEach(uiContainers -> uiContainers.update(this));
        camera.update(this);
        handleMouseInput();

        if (nextState != null) {
            game.enterState(nextState);
        }
    }

    private void handleMouseInput() {

        input.clearMouseClick();
    }

    private void updateGameObjects() {
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).update(this);
        }
    }

    private void sortObjectByPosition() {
        gameObjects.sort(Comparator.comparing(GameObject::getRenderOrder).thenComparing(gameObject -> gameObject.getPosition().getY()));
    }

    public List<GameObject> getGameObject() {
        return gameObjects;
    }

    public IGameSpace getGameSpace() {
        return gameSpace;
    }

    public Camera getCamera() {
        return camera;
    }

    public Time getTime() {
        return time;
    }

    public Position getRandomPosition() {
        return gameSpace.getRandomPosition();
    }

    public List<GameObject> getCollidingGameObjects(GameObject gameObject) {
        return gameObjects.stream()
            .filter(other -> other.doesCollidesWith(gameObject)).collect(Collectors.toList());
    }

    public List<UIContainer> getUiContainers() {
        return uiContainers;
    }

    public SpriteLibrary getSpriteLibrary() {
        return spriteLibrary;
    }
    
    public void spawn (GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public Input getInput() {
        return input;
    }

    public void setNextState(State nextState) {
        this.nextState = nextState;
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public void cleanUp() {
        audioPlayer.clear();
    }    
    
    public Text getText(){ // DELETE
        return text;
    }
}

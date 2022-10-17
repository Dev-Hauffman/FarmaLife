package state.game;

import controller.CameraController;
import core.Position;
import core.Size;
import display.Camera;
import entity.Scenery;
import entity.patient.Patient;
import game.settings.GameSettings;
import gamespace.GameSpace;
import input.Input;
import state.State;
import text.Font;
import text.SingleCharacter;

public class WorkCounterState extends State{

    Patient patient;
    int lowestObject;

    public WorkCounterState(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize, input, gameSettings);
        gameSpace = new GameSpace(new Size(windowSize.getWidth(), 1354));
        patient = new Patient(spriteLibrary);
        Font font = new Font(spriteLibrary); // DELETE
        text.add(new SingleCharacter(0, 0, 'a', "testFont", font, 0)); // DELETE
        initializeObjects();
        camera = new Camera(windowSize, new CameraController(input));
    }

    private void initializeObjects() {
        gameObjects.add(new Scenery("backwall", new Size(1600, 861), new Position(0,0), spriteLibrary, 1));
        gameObjects.add(new Scenery("counter", new Size(1600, 812), new Position(0,542), spriteLibrary, 12));
        //patient.getBodyParts().forEach((key, value) -> gameObjects.add(value));
    }
    
}

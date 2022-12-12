package state.counter;

import catalog.medicine.MedicineStock;
import controller.CameraController;
import core.Position;
import core.Size;
import display.Camera;
import entity.Scenery;
import entity.StaticObject;
import entity.patient.Patient;
import game.Game;
import game.settings.GameSettings;
import gamespace.GameSpace;
import input.Input;
import state.State;
import state.counter.pc.PCState;
import state.counter.pc.states.CallNextPCState;

public class WorkCounterState extends State{

    private Patient activePatient;
    private PCState computer;
    private MedicineStock stock;
    public static int patientsCounter = 0;


    public WorkCounterState(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize, input, gameSettings);
        gameSpace = new GameSpace(new Size(windowSize.getWidth(), 1354));
        computer = new CallNextPCState(this);
        activePatient = new Patient(spriteLibrary);
        stock = new MedicineStock();
        initializeObjects();
        camera = new Camera(windowSize, new CameraController(input));
    }

    private void initializeObjects() {
        gameObjects.add(new Scenery("backwall", new Size(1600, 861), new Position(0,0), spriteLibrary, 1));
        gameObjects.add(new Scenery("counter", new Size(1600, 812), new Position(0,542), spriteLibrary, 3));
        gameObjects.add(new StaticObject("computercase", new Position(980, 500), spriteLibrary, 4));
        //patient.getBodyParts().forEach((key, value) -> gameObjects.add(value));
        computer.getObjects().forEach(value -> gameObjects.add(value));
    }

    public void changePCState(PCState nextState) {
        computer.cleanUp(this);
        computer = nextState;
        computer.getObjects().forEach(value -> gameObjects.add(value));
    }
    
    @Override
    public void update(Game game) {
        super.update(game);
        computer.update(this);
    }

    public PCState getComputer() {
        return computer;
    }

    public MedicineStock getStock() {
        return stock;
    }

    public void setComputer(PCState computer) {
        this.computer = computer;
    }

    public Patient getActivePatient() {
        return activePatient;
    }
}

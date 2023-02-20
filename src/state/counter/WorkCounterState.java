package state.counter;

import catalog.medicine.MedicineStock;
import controller.CameraController;
import core.Position;
import core.Size;
import display.Camera;
import entity.AnimatedObject;
import entity.Scenery;
import entity.StaticObject;
import entity.patient.Patient;
import entity.player.PlayerSpeech;
import game.Game;
import game.settings.GameSettings;
import gamespace.GameSpace;
import input.Input;
import lines.LinesCatalog;
import state.GameOverState;
import state.State;
import state.counter.pc.PCState;
import state.counter.pc.states.CallNextPCState;
import state.counter.ui.SpeechDisplay;
import state.counter.ui.QuickAnswerManager;

public class WorkCounterState extends State{

    private Patient activePatient;
    private PlayerSpeech playerChoices;
    private SpeechDisplay speechDisplay;
    private PCState computer;
    private MedicineStock stock;
    private LinesCatalog linesCatalog;
    private QuickAnswerManager quickAnswers;
    private Score score;
    public static int patientsCounter = 0;


    public WorkCounterState(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize, input, gameSettings);
        gameSpace = new GameSpace(new Size(windowSize.getWidth(), 920));
        computer = new CallNextPCState(this);
        stock = new MedicineStock();
        linesCatalog = new LinesCatalog();
        playerChoices = new PlayerSpeech(this);
        speechDisplay = new SpeechDisplay(new Position(700, 125), this, 6);
        quickAnswers = new QuickAnswerManager(this);
        score = new Score();
        initializeObjects();
        camera = new Camera(windowSize, new CameraController(input));
    }

    private void initializeObjects() {
        gameObjects.add(new Scenery("backwall", new Size(1600, 861), new Position(0,0), spriteLibrary, 1));
        gameObjects.add(new Scenery("counter", new Size(1600, 812), new Position(0,542), spriteLibrary, 3));
        gameObjects.add(new StaticObject("computercase", new Position(680, 400), spriteLibrary, 4));
        computer.getObjects().forEach(value -> gameObjects.add(value));
        gameObjects.add(speechDisplay);
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
        if (activePatient != null) {
            activePatient.update(this);
        }
        quickAnswers.update(this);
        speechDisplay.update(this);
        if (patientsCounter > 5) {
            patientsCounter = 0;
            setNextState(new GameOverState(windowSize, input, gameSettings));
        }
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

    public void spawnPatient(){
        activePatient = new Patient(this);
        activePatient.getBodyParts().forEach((key, value) -> gameObjects.add(value));
        activePatient.start();
    }

    public PlayerSpeech getPlayerChoices() {
        return playerChoices;
    }

    public LinesCatalog getLinesCatalog() {
        return linesCatalog;
    }

    public SpeechDisplay getSpeechDisplay() {
        return speechDisplay;
    }

    public QuickAnswerManager getQuickAnswers() {
        return quickAnswers;
    }

    public static int getPatientsCounter() {
        return patientsCounter;
    }

    public Score getScore() {
        return score;
    }

    public void setActivePatient(Patient activePatient) {
        this.activePatient = activePatient;
    }

    public void setPlayerChoices(PlayerSpeech playerChoices) {
        this.playerChoices = playerChoices;
    }

    public void setSpeechDisplay(SpeechDisplay speechDisplay) {
        this.speechDisplay = speechDisplay;
    }

    public void setStock(MedicineStock stock) {
        this.stock = stock;
    }

    public void setLinesCatalog(LinesCatalog linesCatalog) {
        this.linesCatalog = linesCatalog;
    }

    public void setQuickAnswers(QuickAnswerManager quickAnswers) {
        this.quickAnswers = quickAnswers;
    }

    public static void setPatientsCounter(int patientsCounter) {
        WorkCounterState.patientsCounter = patientsCounter;
    }

    public void despawnPatient(){
        gameObjects.removeAll(activePatient.getBodyParts().values());
    }

}

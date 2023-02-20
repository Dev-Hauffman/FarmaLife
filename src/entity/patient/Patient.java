package entity.patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ai.lines.AIResponse;
import ai.manager.AIManager;
import ai.manager.StageAIManager;
import core.Position;
import core.Rotation;
import core.Size;
import entity.AnimatedObject;
import entity.DynamicObject;
import entity.GameObject;
import gfx.SpriteLibrary;
import state.State;
import state.counter.StagesEnum;
import state.counter.WorkCounterState;

public class Patient {
    private Map<String, GameObject> bodyParts;
    private StageAIManager aiManager;
    private List<String> cart;
    private CurrentStatus currentStatus;
    private AIResponse aiResponse;
    private Objective objective;
    private NPCAction action;
    private StagesEnum stage;

    public Patient(WorkCounterState state) {
        cart = new ArrayList<>();
        bodyParts = new HashMap<>();
        createBody(state.getSpriteLibrary());
        aiManager = new StageAIManager();
        objective = new Objective(state.getStock().getMedicineList());
        currentStatus = new CurrentStatus();
        aiResponse = new AIResponse();
        action = new NPCAction();
        stage = StagesEnum.GREETING;
    }

    private void createBody(SpriteLibrary spriteLibrary) {
        bodyParts.put("body", new AnimatedObject("body", getRandomBody(), new Position(-821, 0), new Size(821, 675), spriteLibrary, Rotation.SIDE_RIGHT, 2));
        AnimatedObject basehead = new AnimatedObject("basehead", getRandomHead(), new Position(-821, 0), new Size(821, 675), spriteLibrary, Rotation.SIDE_RIGHT, 2);
        bodyParts.put("basehead", basehead);
        AnimatedObject eyebrow = new AnimatedObject("eyebrow", getRandoEyebrow(), new Position(0, 0), new Size(821, 675), spriteLibrary, Rotation.SIDE_RIGHT, 5);
        AnimatedObject eyelids = new AnimatedObject("eyelids", getRandomEyelids(), new Position(0, 0), new Size(821, 675), spriteLibrary, Rotation.SIDE_RIGHT, 4);
        AnimatedObject mouth = new AnimatedObject("mouth", getRandomJaw(), new Position(0, 0), new Size(821, 675), spriteLibrary, Rotation.SIDE_RIGHT, 3);
        AnimatedObject nose = new AnimatedObject("nose", getRandomNose(), new Position(0, 0), new Size(821, 675), spriteLibrary, Rotation.SIDE_RIGHT, 5);
        AnimatedObject iris = new AnimatedObject("iris", getRandomIris(), new Position(0, 0), new Size(821, 675), spriteLibrary, Rotation.SIDE_RIGHT, 4);
        iris.parent(basehead);
        iris.setFollowParent(true);
        eyelids.parent(basehead);
        eyelids.setFollowParent(true);
        nose.parent(basehead);
        nose.setFollowParent(true);
        mouth.parent(basehead);
        mouth.setFollowParent(true);
        eyebrow.parent(basehead);
        eyebrow.setFollowParent(true);
        bodyParts.put("eyebrow", eyebrow);
        bodyParts.put("eyelids", eyelids);
        bodyParts.put("nose", nose);
        bodyParts.put("iris", iris);
        bodyParts.put("mouth", mouth);
    }

    private String getRandomIris() {
        Random random = new Random();
        int chosenNumber = random.nextInt(2);
        switch (chosenNumber) {
            case 0:
                return "irisone";
                
            case 1:
                return "iristwo";

            default:
                return "irisTest";                
        }
    }

    private String getRandomNose() {
        Random random = new Random();
        int chosenNumber = random.nextInt(3);
        switch (chosenNumber) {
            case 0:
                return "noseone";
                
            case 1:
                return "nosetwo";
                
            case 2:
                return "nosethree";

            default:
                return "noseTest";                
        }
    }

    private String getRandomJaw() {
        Random random = new Random();
        int chosenNumber = random.nextInt(3);
        switch (chosenNumber) {
            case 0:
                return "mouthone";
                
            case 1:
                return "mouthtwo";
                
            case 2:
                return "mouththree";

            default:
                return "mouthTest";                
        }
    }

    private String getRandomEyelids() {
        Random random = new Random();
        int chosenNumber = random.nextInt(3);
        switch (chosenNumber) {
            case 0:
                return "eyelidsone";
                
            case 1:
                return "eyelidstwo";
                
            case 2:
                return "eyelidsthree";

            default:
                return "eyelidsTest";                
        }
    }

    private String getRandoEyebrow() {
        Random random = new Random();
        int chosenNumber = random.nextInt(3);
        switch (chosenNumber) {
            case 0:
                return "eyebrowone";
                
            case 1:
                return "eyebrowthree";
                
            case 2:
                return "eyebrowtwo";

            default:
                return "eyebrowTest";                
        }
    }

    private String getRandomBody() {
        Random random = new Random();
        int chosenNumber = random.nextInt(3);
        switch (chosenNumber) {
            case 0:
                return "greensuit";
                
            case 1:
                return "tiewhiteshirt";
                
            case 2:
                return "whiteshirt";

            default:
                return "bodyTest";                
        }
    }

    private String getRandomHead() {
        Random random = new Random();
        int chosenNumber = random.nextInt(6);
        switch (chosenNumber) {
            case 0:
                return "earonehairone";
                
            case 1:
                return "earonehairthree";
                
            case 2:
                return "earonehairtwo";
                
            case 3:
                return "eartwohairone";
                
            case 4:
                return "eartwohairthree";
                
            case 5:
                return "eartwohairtwo";

            default:
                return "baseHeadTest";                
        }
    }

    public Map<String, GameObject> getBodyParts() {
        return bodyParts;
    }

    public List<String> getCart() {
        return cart;
    }

    public void start(){
        aiManager.startRoutine();
    }

    public void update(State state){
        aiManager.update(state, this);
    }

    public CurrentStatus getCurrentStatus() {
        return currentStatus;
    }

    public AIResponse getAiResponse() {
        return aiResponse;
    }

    public Objective getObjective() {
        return objective;
    }

    public NPCAction getAction() {
        return action;
    }

    public StagesEnum getStage() {
        return stage;
    }

    public void setStage(StagesEnum stage) {
        this.stage = stage;
    }
    
}

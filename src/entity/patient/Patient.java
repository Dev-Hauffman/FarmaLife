package entity.patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        bodyParts.put("body", new AnimatedObject("body", "bodyTest", new Position(-821, 0), new Size(821, 675), spriteLibrary, Rotation.SIDE_RIGHT, 2));
        AnimatedObject basehead = new AnimatedObject("basehead", "baseHeadTest", new Position(-821, 0), new Size(821, 675), spriteLibrary, Rotation.SIDE_RIGHT, 2);
        bodyParts.put("basehead", basehead);
        AnimatedObject eyebrow = new AnimatedObject("eyebrow", "eyebrowTest", new Position(0, 0), new Size(821, 675), spriteLibrary, Rotation.SIDE_RIGHT, 5);
        AnimatedObject eyelids = new AnimatedObject("eyelids", "eyelidsTest", new Position(0, 0), new Size(821, 675), spriteLibrary, Rotation.SIDE_RIGHT, 4);
        AnimatedObject mouth = new AnimatedObject("mouth", "mouthTest", new Position(0, 0), new Size(821, 675), spriteLibrary, Rotation.SIDE_RIGHT, 3);
        AnimatedObject nose = new AnimatedObject("nose", "noseTest", new Position(0, 0), new Size(821, 675), spriteLibrary, Rotation.SIDE_RIGHT, 5);
        AnimatedObject iris = new AnimatedObject("iris", "irisTest", new Position(0, 0), new Size(821, 675), spriteLibrary, Rotation.SIDE_RIGHT, 4);
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

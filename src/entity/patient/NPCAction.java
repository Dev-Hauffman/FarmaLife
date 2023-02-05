package entity.patient;

import core.Rotation;
import entity.AnimatedObject;
import state.counter.StagesEnum;
import state.counter.WorkCounterState;
import state.counter.pc.states.CartPCState;

public class NPCAction {
    private ActionEnum action;

    public NPCAction(){
        action = ActionEnum.NONE;
    }

    public void setAction(String actionName){
        switch (actionName) {
            case "leave_scene":
                action = ActionEnum.LEAVE_SCENE;
                break;
        
            default:
                break;
        }
    }

    public void playAction(WorkCounterState state){
        switch (action) {
            case LEAVE_SCENE:
                AnimatedObject head = (AnimatedObject)state.getActivePatient().getBodyParts().get("basehead");
                head.setRotation(Rotation.SIDE_RIGHT);
                state.getActivePatient().setStage(StagesEnum.LEAVING);
                break;
        
            default:
                break;
        }
    }

    public enum ActionEnum{
        NONE, LEAVE_SCENE;
    }
}

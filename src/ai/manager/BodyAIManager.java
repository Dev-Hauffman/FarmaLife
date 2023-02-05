package ai.manager;

import ai.state.basehead.MoveToEnd;
import ai.state.basehead.MoveToStart;
import entity.patient.Patient;
import state.State;

public class BodyAIManager extends AIManager{
    private BehaviourAIManager parent;

    @Override
    public void update(State state, Patient patient) {
        if (currentAIState != null) {
            currentAIState.update(state, patient.getBodyParts().get("body"));            
        }
        
    }

    @Override
    protected void transitionTo(IState nextState) {
        switch ((BodyEnum)nextState) {
            case MOVING_TO_END:
                currentAIState = new MoveToEnd();
                break;

            case MOVE_TO_START:
                currentAIState = new MoveToStart();
                break;
        
            default:
                break;
        }
        
    }

    public enum BodyEnum implements IState{
        FACE_LEFT, FACE_RIGHT, FACE_FOWARD, FACE_HALF_RIGHT, FACE_HALF_LEFT, MOVE_TO_START, IDLE, MOVING_TO_END;
    }

}

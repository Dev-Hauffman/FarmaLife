package ai.manager;

import ai.state.basehead.MoveToStart;
import entity.patient.Patient;
import state.State;

public class BaseHeadAIManager extends AIManager{

    @Override
    public void update(State state, Patient patient) {        
        if (currentAIState != null) {
            currentAIState.update(state, patient.getBodyParts().get("basehead"));            
        }
    }

    @Override
    protected void transitionTo(IState nextState) {
        switch ((BaseHeadEnum)nextState) {
            case FACE_RIGHT:
                
                break;

            case MOVE_TO_START:
                currentAIState = new MoveToStart();
                break;
        
            default:
                break;
        }
        
    }

    public enum BaseHeadEnum implements IState{
        FACE_LEFT, FACE_RIGHT, FACE_FOWARD, FACE_HALF_RIGHT, FACE_HALF_LEFT, MOVE_TO_START;
    }
}

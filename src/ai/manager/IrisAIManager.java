package ai.manager;

import ai.state.basehead.MoveToStart;
import entity.patient.Patient;
import state.State;

public class IrisAIManager extends AIManager{
    private BehaviourAIManager parent;

    @Override
    public void update(State state, Patient patient) {
        if (currentAIState != null) {
            currentAIState.update(state, patient.getBodyParts().get("iris"));            
        }
        
    }

    @Override
    protected void transitionTo(IState nextState) {
        switch ((IrisEnum)nextState) {
            case FACE_RIGHT:
                
                break;

            case MOVE_TO_START:
                currentAIState = new MoveToStart();
                break;
        
            default:
                break;
        }
        
    }

    public enum IrisEnum implements IState{
        FACE_LEFT, FACE_RIGHT, FACE_FOWARD, FACE_HALF_RIGHT, FACE_HALF_LEFT, IDLE, FOCUS_PLAYER, MOVE_TO_START;
    }
}

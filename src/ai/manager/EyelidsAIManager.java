package ai.manager;

import ai.state.eyelids.Blink;
import entity.patient.Patient;
import state.State;

public class EyelidsAIManager extends AIManager {
    private BehaviourAIManager parent;

    @Override
    public void update(State state, Patient patient) {
        if (currentAIState != null) {
            currentAIState.update(state, patient.getBodyParts().get("eyelids"));            
        }
        
    }

    @Override
    protected void transitionTo(IState nextState) {
        switch ((EyelidEnum)nextState) {
            case FACE_RIGHT:
                
                break;

            case BLINK:
                currentAIState = new Blink();
                break;
        
            default:
                break;
        }
        
    }

    public enum EyelidEnum implements IState{
        FACE_LEFT, FACE_RIGHT, FACE_FOWARD, FACE_HALF_RIGHT, FACE_HALF_LEFT, BLINK;
    }
}

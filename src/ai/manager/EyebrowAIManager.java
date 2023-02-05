package ai.manager;

import ai.state.basehead.MoveToStart;
import entity.patient.Patient;
import state.State;

public class EyebrowAIManager extends AIManager{
    private BehaviourAIManager parent;
    private EyebrowEnum currentState;

    @Override
    public void update(State state, Patient patient) {
        if (currentAIState != null) {
            currentAIState.update(state, patient.getBodyParts().get("eyebrow"));            
        }
    }

    @Override
    protected void transitionTo(IState nextState) {
        switch ((EyebrowEnum)nextState) {
            case IDLE:
                currentState = EyebrowEnum.IDLE;
                break;

            case MOVE_TO_START:
                currentAIState = new MoveToStart();
                break;
        
            default:
                break;
        }
        
    }

    public EyebrowEnum getCurrentState() {
        return currentState;
    }

    public enum EyebrowEnum implements IState{
        FACE_LEFT, FACE_RIGHT, FACE_FOWARD, FACE_HALF_RIGHT, FACE_HALF_LEFT, MOVE_TO_START, IDLE;
    }
}

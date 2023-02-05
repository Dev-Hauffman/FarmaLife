package ai.manager;

import ai.manager.BehaviourAIManager.BehaviorEnum;
import ai.state.AIState;
import entity.GameObject;
import entity.patient.Patient;
import state.State;

public class SubStageAIManager extends AIManager{
    private AIState currentAIState;
    private StageAIManager parent;
    private BehaviourAIManager child;

    public SubStageAIManager(){
        child = new BehaviourAIManager();
        child.setParent(this);
    }

    @Override
    protected void transitionTo(IState nextState) {
        switch ((SubStagesEnum)nextState) {
            case ENTERING:
                child.transitionTo(BehaviorEnum.MOVING_TO_START);
                break;
        
            case LEAVING:
                child.transitionTo(BehaviorEnum.MOVING_TO_END);
                break;

            default:
                break;
        }
    }

    @Override
    public void update(State state, Patient patient) {
        child.update(state, patient);
    }

    public enum SubStagesEnum implements IState{
        ENTERING, GREETING, ORDERING, QUESTIONING, LEAVING;
    }
}

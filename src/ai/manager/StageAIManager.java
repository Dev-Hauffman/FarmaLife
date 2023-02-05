package ai.manager;

import ai.manager.SubStageAIManager.SubStagesEnum;
import ai.state.AIState;
import ai.state.stage.EnterScene;
import ai.state.stage.Interactive;
import ai.state.stage.LeaveScene;
import entity.GameObject;
import entity.patient.Patient;
import state.State;

public class StageAIManager extends AIManager{
    private AIState currentAIState;

    public StageAIManager(){
        child = new SubStageAIManager();
        child.setParent(this);
    }

    public void startRoutine() {
        transitionTo(AIStagesEnum.ENTER_SCENE);
    }

    @Override
    public void update(State state, Patient patient) {
        child.update(state, patient);
        if (currentAIState != null) {
            if (currentAIState.shouldTransition(state, patient)) {
                transitionTo(currentAIState.getNextState());
            }
            currentAIState.update(state, null);
        }
    }

    @Override
    protected void transitionTo(IState nextState) {
        switch ((AIStagesEnum)nextState) {
            case ENTER_SCENE:
                child.transitionTo(SubStagesEnum.ENTERING);
                currentAIState = new EnterScene();
                break;
        
            case INTERACTIVE:
                currentAIState = new Interactive();
                break;

            case LEAVE_SCENE:
                currentAIState = new LeaveScene();
                child.transitionTo(SubStagesEnum.LEAVING);
                break;

            default:
                break;
        }
    }

    public enum AIStagesEnum implements IState{
        ENTER_SCENE, INTERACTIVE, LEAVE_SCENE;
    }
}

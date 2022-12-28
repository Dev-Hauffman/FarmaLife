package ai.manager;

import ai.manager.SubStageAIManager.SubStagesEnum;
import ai.state.AIState;
import ai.state.stage.EnterScene;
import ai.state.stage.Interactive;
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
        transitionTo(StagesEnum.ENTER_SCENE);
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
        switch ((StagesEnum)nextState) {
            case ENTER_SCENE:
                child.transitionTo(SubStagesEnum.ENTERING);
                currentAIState = new EnterScene();
                break;
        
            case INTERACTIVE:
                currentAIState = new Interactive();
                break;

            case LEAVE_SCENE:
                
                break;

            default:
                break;
        }
    }

    public enum StagesEnum implements IState{
        ENTER_SCENE, INTERACTIVE, LEAVE_SCENE;
    }
}

package ai.state.stage;

import ai.AITransition;
import ai.manager.StageAIManager.AIStagesEnum;
import ai.state.AIState;
import entity.GameObject;
import state.State;

public class LeaveScene extends AIState{

    @Override
    protected AITransition initializeTransition() {
        return new AITransition(AIStagesEnum.INTERACTIVE, ((state, currentPatient) -> {
            return false;
        })
    );
    }

    @Override
    public void update(State state, GameObject currentCharacter) {
        // TODO Auto-generated method stub
        
    }
    
}

package ai.state.stage;

import ai.AITransition;
import ai.manager.StageAIManager.AIStagesEnum;
import ai.state.AIState;
import entity.GameObject;
import state.State;

public class EnterScene extends AIState{

    @Override
    protected AITransition initializeTransition() {
        return new AITransition(AIStagesEnum.INTERACTIVE, ((state, currentPatient) -> {
                return currentPatient.getBodyParts().get("body").getPosition().getIntX() == 0;
            })
        );
    }

    @Override
    public void update(State state, GameObject currentCharacter) {
                
    }
}

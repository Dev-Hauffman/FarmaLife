package ai.state.stage;

import ai.AITransition;
import ai.manager.StageAIManager.StagesEnum;
import ai.state.AIState;
import entity.GameObject;
import state.State;
import state.counter.WorkCounterState;

public class Interactive extends AIState{
    private boolean hasSetSpeak;

    @Override
    protected AITransition initializeTransition() {
        return new AITransition(StagesEnum.LEAVE_SCENE, ((state, currentPatient) -> {
            return false;
        })
    );
    }

    @Override
    public void update(State state, GameObject currentCharacter) {
        if (!hasSetSpeak) {
            if (state instanceof WorkCounterState) {
                ((WorkCounterState)state).getPlayerChoices().setCanSpeak(true);
                hasSetSpeak = true;
            }
        }
        
    }
    
}

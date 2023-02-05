package ai.state.stage;

import ai.AITransition;
import ai.manager.StageAIManager.AIStagesEnum;
import ai.state.AIState;
import entity.GameObject;
import state.State;
import state.counter.StagesEnum;
import state.counter.WorkCounterState;

public class Interactive extends AIState{
    private boolean hasSetSpeak;

    @Override
    protected AITransition initializeTransition() {
        return new AITransition(AIStagesEnum.LEAVE_SCENE, ((state, currentPatient) -> {
            return currentPatient.getStage() == StagesEnum.LEAVING;
        })
    );
    }

    @Override
    public void update(State state, GameObject currentCharacter) {
        if (!hasSetSpeak) {
            if (state instanceof WorkCounterState) {
                ((WorkCounterState)state).getPlayerChoices().getFirstLines(((WorkCounterState)state), StagesEnum.GREETING);
                ((WorkCounterState)state).getQuickAnswers().addQuickAnswers(state);
                hasSetSpeak = true;
            }
        }
        
    }
    
}

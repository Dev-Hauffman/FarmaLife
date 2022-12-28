package ai.state.stage;

import ai.AITransition;
import ai.manager.StageAIManager.StagesEnum;
import ai.state.AIState;
import core.Position;
import entity.AnimatedObject;
import entity.GameObject;
import entity.patient.Patient;
import state.State;
import state.counter.WorkCounterState;

public class EnterScene extends AIState{

    @Override
    protected AITransition initializeTransition() {
        return new AITransition(StagesEnum.INTERACTIVE, ((state, currentPatient) -> {
                return currentPatient.getBodyParts().get("body").getPosition().getIntX() == 0;
            })
        );
    }

    @Override
    public void update(State state, GameObject currentCharacter) {
                
    }
}

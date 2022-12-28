package ai;

import ai.manager.IState;
import entity.GameObject;
import entity.NPC;
import entity.patient.Patient;
import state.State;

public class AITransition {
    private IState nextState;
    private IAICondition condition;
    public AITransition(IState nextState, IAICondition condition) {
        this.nextState = nextState;
        this.condition = condition;
    }

    // public boolean shouldTransition(State state, GameObject currentCharacter) {
    //     return condition.isMet(state, currentCharacter);
    // }

    public boolean shouldTransition(State state, Patient currentPatient) {
        return condition.isMet(state, currentPatient);
    }

    public IState getNextState() {
        return nextState;
    }
    
}

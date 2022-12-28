package ai.state;

import ai.AITransition;
import ai.manager.IState;
import entity.GameObject;
import entity.NPC;
import entity.patient.Patient;
import state.State;

public abstract class AIState {
    private AITransition transition;

    public AIState() {
        this.transition = initializeTransition();
    }

    protected abstract AITransition initializeTransition();

    public abstract void update(State state, GameObject currentCharacter);

    // public boolean shouldTransition(State state, GameObject currentCharacter) {
    //     return transition.shouldTransition(state, currentCharacter);
    // }

    public boolean shouldTransition(State state, Patient currentPatient) {
        return transition.shouldTransition(state, currentPatient);
    }

    public IState getNextState() {
        return transition.getNextState();
    }
}

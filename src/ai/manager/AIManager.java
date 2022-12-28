package ai.manager;

import ai.state.AIState;
import ai.state.Stand;
import ai.state.Wander;
import entity.GameObject;
import entity.NPC;
import entity.patient.Patient;
import state.State;

public abstract class AIManager {
    protected AIState currentAIState;
    protected AIManager child;
    protected AIManager parent;

    public AIManager() {
        // transitionTo("stand");
    }

    // public void update(State state, NPC currentCharacter) {
    //     currentAIState.update(state, currentCharacter);

    //     if (currentAIState.shouldTransition(state, currentCharacter)) {
    //         transitionTo(currentAIState.getNextState());
    //     }
    // }

    public void update(State state, Patient patient){
        child.update(state, patient);
    }

    protected abstract void transitionTo(IState nextState);

    // protected void transitionTo(String nextState) {
        // switch (nextState) {
        //     case "wander":
        //         currentAIState = new Wander();
        //         return;
        //     case "stand":        
        //     default:
        //         currentAIState = new Stand();
        //         break;
        // }
    // }

    public void setParent(AIManager parent) {
        this.parent = parent;
    }

}

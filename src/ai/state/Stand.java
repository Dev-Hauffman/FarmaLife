package ai.state;

import ai.AITransition;
import entity.GameObject;
import entity.NPC;
import entity.patient.Patient;
import state.State;

public class Stand extends AIState{
    private int updatesAlive;
    
    @Override
    protected AITransition initializeTransition() {
        // return new AITransition("wander", ((state, currentCharacter) -> updatesAlive >= state.getTime().getUpdatesFromSeconds(3)));
        return null;
    }

    @Override
    public void update(State state, GameObject currentCharacter) {
        updatesAlive++;
        
    }
    
}

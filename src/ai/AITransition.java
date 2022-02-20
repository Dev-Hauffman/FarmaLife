package ai;

import entity.NPC;
import state.State;

public class AITransition {
    private String nextState;
    private IAICondition condition;
    public AITransition(String nextState, IAICondition condition) {
        this.nextState = nextState;
        this.condition = condition;
    }

    public boolean shouldTransition(State state, NPC currentCharacter) {
        return condition.isMet(state, currentCharacter);
    }

    public String getNextState() {
        return nextState;
    }
    
}

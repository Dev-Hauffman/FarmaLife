package ai;

import entity.NPC;
import state.State;

public interface IAICondition {
    public boolean isMet(State state, NPC currentCharacter);
}

package ai;

import entity.GameObject;
import entity.NPC;
import entity.patient.Patient;
import state.State;

public interface IAICondition {
    // public boolean isMet(State state, GameObject currentCharacter);

    public boolean isMet(State state, Patient currentPatient);
}

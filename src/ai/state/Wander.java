package ai.state;

import java.util.ArrayList;
import java.util.List;

import ai.AITransition;
import controller.NPCController;
import core.Position;
import entity.GameObject;
import entity.NPC;
import entity.patient.Patient;
import state.State;

public class Wander extends AIState{
    private List<Position> targets;

    public Wander() {
        super();
        targets = new ArrayList<>();
    }

    // @Override
    // protected AITransition initializeTransition() {
    //     return new AITransition("stand", ((state, currentCharacter) -> arrived(currentCharacter)));
    // }

    @Override
    public void update(State state, GameObject currentCharacter) {
        if (targets.isEmpty()) {
            targets.add(state.getRandomPosition());
        }

        // NPCController controller = (NPCController) currentCharacter.getEntityController();
        // controller.moveToTarget(targets.get(0), currentCharacter.getPosition());

        // if (arrived(currentCharacter)) {
        //     controller.stop();
        // }
    }

    private boolean arrived(GameObject currentCharacter) {
        return currentCharacter.getPosition().isInRangeOf(targets.get(0));
    }

    @Override
    protected AITransition initializeTransition() {
        // TODO Auto-generated method stub
        return null;
    }
    
}

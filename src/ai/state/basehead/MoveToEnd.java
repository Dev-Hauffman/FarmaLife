package ai.state.basehead;

import ai.AITransition;
import ai.state.AIState;
import core.Rotation;
import entity.AnimatedObject;
import entity.GameObject;
import state.State;
import state.counter.WorkCounterState;
import state.counter.pc.states.CartPCState;

public class MoveToEnd extends AIState{
    @Override
    protected AITransition initializeTransition() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(State state, GameObject currentCharacter) {
        if (currentCharacter instanceof AnimatedObject) {
            if (((AnimatedObject)currentCharacter).getName().equals("basehead")) {
                if (((AnimatedObject)currentCharacter).getAnimationManager().hasFinishedRotation()) {
                    if (state instanceof WorkCounterState) {
                        AnimatedObject body = (AnimatedObject)((WorkCounterState)state).getActivePatient().getBodyParts().get("body");
                        body.setRotation(Rotation.SIDE_RIGHT);
                    }
                }
            }
            if (currentCharacter.getPosition().getIntX() < state.getWindowSize().getWidth() + currentCharacter.getSprite().getWidth(null) - 1000){
                currentCharacter.setPosX(currentCharacter.getPosition().getIntX() + 7);
                currentCharacter.setPosY((int)(Math.sin(currentCharacter.getPosition().getIntX()*10)*2));
                // System.out.println((int)(Math.sin(currentCharacter.getPosition().getIntX()*10)*2));
            }else{
                currentCharacter.setPosX(state.getWindowSize().getWidth() + currentCharacter.getSprite().getWidth(null));
                currentCharacter.setPosY(state.getWindowSize().getWidth() + currentCharacter.getSprite().getWidth(null));
                if (state instanceof WorkCounterState) {
                    ((WorkCounterState)state).despawnPatient();                    
                    if (((WorkCounterState)state).getComputer() instanceof CartPCState) {
                    ((CartPCState)((WorkCounterState)state).getComputer()).getFinishButton().setDisabled(false);
                }
                }
            }
        }
        
    }
}

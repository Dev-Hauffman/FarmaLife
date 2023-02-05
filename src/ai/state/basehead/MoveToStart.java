package ai.state.basehead;

import ai.AITransition;
import ai.state.AIState;
import core.Position;
import core.Rotation;
import entity.AnimatedObject;
import entity.GameObject;
import state.State;
import state.counter.WorkCounterState;

public class MoveToStart extends AIState {

    @Override
    protected AITransition initializeTransition() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(State state, GameObject currentCharacter) {
        if (currentCharacter instanceof AnimatedObject) {
            if (currentCharacter.getPosition().getIntX() < 0){
                currentCharacter.setPosX(currentCharacter.getPosition().getIntX() + 7);
                currentCharacter.setPosY((int)(Math.sin(currentCharacter.getPosition().getIntX()*10)*2));
                // System.out.println((int)(Math.sin(currentCharacter.getPosition().getIntX()*10)*2));
                if (currentCharacter.getPosition().getIntX() > -125 && ((AnimatedObject)currentCharacter).getName().equals("body")) {
                    ((AnimatedObject)currentCharacter).setRotation(Rotation.FRONT);
                }
                if (currentCharacter.getPosition().getIntX() > -250 && ((AnimatedObject)currentCharacter).getName().equals("basehead")) {
                    ((AnimatedObject)currentCharacter).setRotation(Rotation.FRONT);
                }
            }else{
                currentCharacter.setPosX(0);
                currentCharacter.setPosY(0);
            }
        }
        
    }
    
}

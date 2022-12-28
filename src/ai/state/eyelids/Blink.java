package ai.state.eyelids;

import java.util.Random;

import ai.AITransition;
import ai.state.AIState;
import entity.AnimatedObject;
import entity.GameObject;
import state.State;

public class Blink extends AIState{
    private int waitTime = 20;
    private int blinkThreshhold = 20;

    @Override
    protected AITransition initializeTransition() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(State state, GameObject currentCharacter) {
        waitTime--;
        if (waitTime < 0) {
            waitTime = 20;
            Random rand = new Random();
            int chanceToBlink = rand.nextInt(101);
            if (chanceToBlink < blinkThreshhold) {
                if (currentCharacter instanceof AnimatedObject) {
                    ((AnimatedObject)currentCharacter).getAnimationManager().playAnimation("eyelidsTest", true);
                }
            }
        }
    }
    
}

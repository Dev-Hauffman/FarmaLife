package entity.humanoid.action;

import entity.humanoid.Humanoid;
import game.GameLoop;
import state.State;

public class Cough extends Action{

    private int lifeSpanInSeconds;

    public Cough() {
        lifeSpanInSeconds = GameLoop.UPDATES_PER_SECOND;
    }

    @Override
    public void update(State state, Humanoid performer) {
        lifeSpanInSeconds--;
    }

    @Override
    public boolean isDone() {
        return lifeSpanInSeconds <= 0;
    }

    @Override
    public String getAnimationName() {
        return "cough";
    }
    
}

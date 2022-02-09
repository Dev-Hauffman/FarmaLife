package entity;

import ai.AIManager;
import controller.IController;
import game.state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

public class NPC extends MovingEntity {
    private AIManager aiManager;
    public NPC(IController controller, SpriteLibrary spriteLibrary) {
        super(controller, spriteLibrary);
        animationManager = new AnimationManager(spriteLibrary.getUnit("matt"));
        aiManager = new AIManager();
    }
    
    @Override
    public void update(State state) {
        super.update(state);
        aiManager.update(state, this);
    }
}
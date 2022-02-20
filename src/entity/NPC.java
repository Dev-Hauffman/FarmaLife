package entity;

import ai.AIManager;
import controller.IEntityController;
import entity.humanoid.Humanoid;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import state.State;

public class NPC extends Humanoid {
    private AIManager aiManager;
    public NPC(IEntityController controller, SpriteLibrary spriteLibrary) {
        super(controller, spriteLibrary);
        animationManager = new AnimationManager(spriteLibrary.getSpriteSets("matt"));
        aiManager = new AIManager();
    }
    
    @Override
    public void update(State state) {
        super.update(state);
        aiManager.update(state, this);
    }

    @Override
    protected void handleCollision(GameObject other) {
        if (other instanceof Player) {
            motion.stop(willCollideX(other), willCollideY(other));
        }        
    }
}

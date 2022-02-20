package entity;

import controller.IEntityController;
import entity.humanoid.Humanoid;
import gfx.SpriteLibrary;

public class Player extends Humanoid{

    public Player(IEntityController controller, SpriteLibrary spriteLibrary){
        super(controller, spriteLibrary);
    }

    @Override
    protected void handleCollision(GameObject other) {
    }
    
}

package entity;

import controller.IController;
import gfx.SpriteLibrary;

public class Player extends MovingEntity{

    public Player(IController controller, SpriteLibrary spriteLibrary){
        super(controller, spriteLibrary);
    }

    @Override
    public void update() {
        super.update();
    }    
}

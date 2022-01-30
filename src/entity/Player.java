package entity;
import java.awt.Image;

import controller.IController;

public class Player extends MovingEntity{

    public Player(IController controller){
        super(controller);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public Image getSprite() {
        // TODO Auto-generated method stub
        return null;
    }
    
}

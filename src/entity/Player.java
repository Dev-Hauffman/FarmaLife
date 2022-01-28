package entity;
import java.awt.Image;

import controller.IController;
import core.Position;

public class Player extends GameObject{

    private IController controller;

    public Player(IController controller){
        super();
        this.controller = controller;
    }

    @Override
    public void update() {
        int deltaX = 0;
        int deltaY = 0;

        if (controller.isRequestingUp()) {
            deltaY--;
        }

        if (controller.isRequestingDown()) {
            deltaY++;
        }

        if (controller.isRequestingLeft()) {
            deltaX--;
        }

        if (controller.isRequestingRight()) {
            deltaX++;
        }

        position = new Position(position.getX() + deltaX, position.getY() + deltaY);
    }

    @Override
    public Image getSprite() {
        // TODO Auto-generated method stub
        return null;
    }
    
}

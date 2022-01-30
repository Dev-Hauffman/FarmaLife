package entity;

import controller.IController;
import core.Movement;

public abstract class MovingEntity extends GameObject {
    
    private IController controller;
    private Movement movement;

    public MovingEntity(IController controller) {
        super();
        this.controller = controller;
        this.movement =  new Movement(2);
    }

    @Override
    public void update() {
        movement.update(controller);
        position.apply(movement);
    }
    
}

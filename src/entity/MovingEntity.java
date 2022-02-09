package entity;

import controller.IController;
import core.Direction;
import core.Motion;
import game.state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

import java.awt.Image;

public abstract class MovingEntity extends GameObject {
    
    protected IController controller;
    protected Motion motion;
    protected AnimationManager animationManager;
    protected Direction direction;

    public MovingEntity(IController controller, SpriteLibrary spriteLibrary) {
        super();
        this.controller = controller;
        this.motion =  new Motion(2);
        this.direction = Direction.S;
        this.animationManager = new AnimationManager(spriteLibrary.getUnit("dave"));
    }

    @Override
    public void update(State state) {
        motion.update(controller);
        position.apply(motion);
        manageDirection();
        decideAnimation();
        animationManager.update(direction);
    }

    private void decideAnimation() {
        if (motion.isMoving()) {
            animationManager.playAnimation("walk");
        } else {
            animationManager.playAnimation("stand");
        }
    }

    private void manageDirection() {
        if (motion.isMoving()) {
            this.direction = Direction.fromMotion(motion);
        }
    }

    @Override
    public Image getSprite() {
        return animationManager.getSprite();
    }
    
}

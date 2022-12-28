package entity;

import controller.IEntityController;
import core.CollisionBox;
import core.Direction;
import core.Motion;
import core.Position;
import core.Size;
import game.Game;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import state.State;

import java.awt.*;

public abstract class MovingEntity extends GameObject {
    
    protected IEntityController entityController;
    protected Motion motion;
    protected AnimationManager animationManager;
    protected Direction direction;

    protected Size collisionBoxSize;

    public MovingEntity(IEntityController controller, SpriteLibrary spriteLibrary) {
        super();
        this.entityController = controller;
        this.motion =  new Motion(2);
        this.direction = Direction.S;
        // this.animationManager = new AnimationManager(spriteLibrary.getSpriteSets("dave"));
    }

    @Override
    public void update(State state) {
        motion.update(entityController);
        handleMotion();
        // animationManager.update(direction);

        handleCollisions(state);
        // animationManager.playAnimation(decideAnimation());
       
        apply(motion);
    }

    private void handleCollisions(State state) {
        state.getCollidingGameObjects(this).forEach(this::handleCollision);
    }

    protected abstract void handleCollision(GameObject other);

    protected abstract void handleMotion();
    
    protected abstract String decideAnimation();

    private void manageDirection(Motion motion) {
        if (motion.isMoving()) {
            this.direction = Direction.fromMotion(motion);
        }
    }

    @Override
    public CollisionBox getCollisionBox() {
        Position positionWithMotion = Position.copyOf(getPosition());
        positionWithMotion.apply(motion); // this is to avoid collisionbox overlapping by calculating where the NPC's collisionbox will be before commiting
        positionWithMotion.subtract(collisionBoxOffset);
        return new CollisionBox (
            new Rectangle(
                positionWithMotion.getIntX(),
                positionWithMotion.getIntY(),
                collisionBoxSize.getWidth(),
                collisionBoxSize.getHeight()
            )
        );
    }

    @Override
    public Image getSprite() {
        return animationManager.getSprite(Game.SPRITE_SIZE, Game.SPRITE_SIZE);
    }    

    public IEntityController getEntityController() {
        return entityController;
    }

    public boolean willCollideX(GameObject other) {
        CollisionBox otherBox = other.getCollisionBox();
        Position positionWithXApplied = Position.copyOf(position);
        positionWithXApplied.applyX(motion);
        positionWithXApplied.subtract(collisionBoxOffset);

        return CollisionBox.of(positionWithXApplied, collisionBoxSize).collidesWith(otherBox);
    }

    public boolean willCollideY(GameObject other) {
        CollisionBox otherBox = other.getCollisionBox();
        Position positionWithYApplied = Position.copyOf(position);
        positionWithYApplied.applyY(motion);
        positionWithYApplied.subtract(collisionBoxOffset);

        return CollisionBox.of(positionWithYApplied, collisionBoxSize).collidesWith(otherBox);
    }

    public void apply(Motion motion) {         
        manageDirection(motion);
        position.apply(motion);
    }
    
}

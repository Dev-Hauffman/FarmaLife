package entity;
/* 
* class from each all objects in the game will derive from
*/
import java.awt.*;

import core.CollisionBox;
import core.Position;
import core.Size;
import display.Camera;
import state.State;

public abstract class GameObject {
    protected Position position;
    protected Position renderOffset;
    protected Position collisionBoxOffset;
    protected Size size;    
    protected Size collisionBoxSize;

    protected int renderOrder;

    protected GameObject parent;

    public GameObject() {
        position = new Position(0, 0);
        size = new Size(64, 64);
        renderOffset = new Position(0, 0);
        collisionBoxOffset = new Position(0, 0);
        this.collisionBoxSize = new Size(size.getWidth(), size.getHeight());
        renderOrder = 5;
    }

    public abstract void update(State state);

    public abstract Image getSprite();

    public CollisionBox getCollisionBox() {
        return new CollisionBox(new Rectangle());
    }

    public boolean doesCollidesWith(GameObject other) {
        return getCollisionBox().collidesWith(other.getCollisionBox());
    }

    public Position getPosition() {
        Position finalPosition = Position.copyOf(position);
        return finalPosition;
    }    

    public void setPosition(Position position) {
        this.position = position;
    }

    public Size getSize() {
        return size;
    }

    public void parent(GameObject parent) {
        this.parent = parent;
    }

    public Position getRenderPosition(Camera camera) {
        return new Position(
            getPosition().getX() - camera.getPosition().getX() - renderOffset.getX(),
            getPosition().getY() - camera.getPosition().getY() - renderOffset.getY()
        );
    }

    public int getRenderOrder() {
        return renderOrder;
    }

    public Position getRenderOffset() {
        return renderOffset;
    }

    public void setRenderOrder(int renderOrder) {
        this.renderOrder = renderOrder;
    }

    public void setPosX(int posX) {
        this.position.setX(posX);
    }

    public void setPosY(int posY) {
        this.position.setY(posY);
    }

    public GameObject getParent() {
        return parent;
    }
    
}

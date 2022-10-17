package core;

import java.awt.*;

public class CollisionBox {

    private Rectangle bounds;

    public CollisionBox(Rectangle bounds) {
        this.bounds = bounds;
    }

    public boolean collidesWith(CollisionBox other) {
        return bounds.intersects(other.getBounds());
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public static CollisionBox of(Position position, Size size) {
        return new CollisionBox(
            new Rectangle(
                position.getIntX(),
                position.getIntY(),
                size.getWidth(),
                size.getHeight()
            )
        );
    }
    
}

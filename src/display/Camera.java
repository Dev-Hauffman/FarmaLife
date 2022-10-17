package display;

import java.util.Optional;

import controller.IEntityController;
import core.Motion;
import core.Position;
import core.Size;
import entity.GameObject;
import game.Game;
import state.State;

import java.awt.*;

public class Camera {
    private static final int SAFETY_SPACE = 2 * Game.SPRITE_SIZE;
    private Position position;
    private Size windowSize;
    private Motion motion;
    private IEntityController entityController;

    private Rectangle viewBounds;

    private Optional<GameObject> objectWithFocus;

    public Camera(Size windowSize) {
        this.position = new Position(0, 0);
        this.entityController = null;
        this.motion =  new Motion(2);
        this.windowSize = windowSize;
        this.objectWithFocus = Optional.empty();
        calculateViewBounds();
    }

    public Camera(Size windowSize, IEntityController controller) {
        this.position = new Position(0, 0);
        this.entityController = controller;
        this.motion =  new Motion(5);
        this.windowSize = windowSize;
        this.objectWithFocus = Optional.empty();
        calculateViewBounds();
    }

    private void calculateViewBounds() {
        viewBounds = new Rectangle(
            position.getIntX(),
            position.getIntY(),
            windowSize.getWidth() + SAFETY_SPACE,
            windowSize.getHeight() + SAFETY_SPACE
        );
    }

    public void focusOn(GameObject object) {
        this.objectWithFocus = Optional.of(object);
    }

    public void update(State state) {
        if (objectWithFocus.isPresent()) {
            Position objectPosition = objectWithFocus.get().getPosition();
            this.position.setX(objectPosition.getX() - windowSize.getWidth() / 2);
            this.position.setY(objectPosition.getY() - windowSize.getHeight() / 2);
        }else if (entityController != null) {
            motion.update(entityController);
            apply(motion);
        }
        clampWithinBounds(state);
        calculateViewBounds();
    }

    private void clampWithinBounds(State state) {
        if (position.getX() < 0) {
            position.setX(0);
        }

        if (position.getY() < 0) {
            position.setY(0);
        }
        // checking if the right border of the screen is beyond the gamespace right border, considering that the right border of the gamespace is biger than the screen width (or right border)
        if (position.getX() + windowSize.getWidth() > state.getGameSpace().getWidth() && state.getGameSpace().getWidth() > windowSize.getWidth()) {
            position.setX(state.getGameSpace().getWidth() - windowSize.getWidth());
        }
        // does not allow the camera to move on the x axis if the gamespace if shorter than the screen size
        if (state.getGameSpace().getWidth() < windowSize.getWidth()) {
            position.setX(0);
        }

        if (position.getY() + windowSize.getHeight() > state.getGameSpace().getHeight() && state.getGameSpace().getHeight() > windowSize.getHeight()) {
            position.setY(state.getGameSpace().getHeight() - windowSize.getHeight());
        }

        if (state.getGameSpace().getHeight() < windowSize.getHeight()) {
            position.setY(0);
        }
    }

    public Position getPosition() {
        return position;
    }

    public boolean isInView(GameObject gameObject) {
        return viewBounds.intersects(
            gameObject.getPosition().getIntX(),
            gameObject.getPosition().getIntY(),
            gameObject.getSize().getWidth(),
            gameObject.getSize().getHeight()
        );
    }

    public Size getSize() {
        return windowSize;
    }

    public void apply(Motion motion) {
        position.apply(motion);
    }

}

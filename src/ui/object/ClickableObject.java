package ui.object;

import java.awt.*;

import core.Position;
import display.Camera;
import state.State;

public abstract class ClickableObject extends UIObject{
    
    public ClickableObject(String name, Position position, State state, int renderOrder) {
        super(name, position, state, renderOrder);
    }

    protected boolean hasFocus;
    protected boolean isPressed;

    @Override
    public void update(State state) {
        Position mousePosition = state.getInput().getMousePosition();
        boolean previousFocus = hasFocus;

        hasFocus = getBounds(state.getCamera()).contains(mousePosition.getIntX(), mousePosition.getIntY());

        isPressed = hasFocus && state.getInput().isMousePressed();

        if (hasFocus && state.getInput().isMouseClicked()) {
            onClick(state);
        }

        if (hasFocus && state.getInput().isMousePressed()) {
            onDrag(state);
        }

        if (!previousFocus && hasFocus) {
            onFocus(state);
        }
        
    };

    protected abstract void onFocus(State state);

    protected abstract void onDrag(State state);

    protected abstract void onClick(State state);

    private Rectangle getBounds(Camera camera) {
        return new Rectangle(
            getPosition().getIntX() - camera.getPosition().getIntX(),
            getPosition().getIntY() - camera.getPosition().getIntY(),
            sprite.getWidth(null),
            sprite.getHeight(null)
        );
    }

    public boolean hasFocus() {
        return hasFocus;
    }
    
}

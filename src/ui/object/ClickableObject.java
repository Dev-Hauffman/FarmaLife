package ui.object;

import java.awt.*;

import core.Position;
import gfx.SpriteLibrary;
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

        hasFocus = getBounds().contains(mousePosition.getIntX(), mousePosition.getIntY());

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
        
    }

    @Override
    public Image getSprite(){
        return super.getSprite();
    };

    protected abstract void onFocus(State state);

    protected abstract void onDrag(State state);

    protected abstract void onClick(State state);

    private Rectangle getBounds() {
        return new Rectangle(
            getPosition().getIntX(),
            getPosition().getIntY(),
            sprite.getWidth(null),
            sprite.getHeight(null)
        );
    }
    
}

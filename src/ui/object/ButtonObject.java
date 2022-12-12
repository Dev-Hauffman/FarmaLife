package ui.object;

import java.awt.Image;

import core.Position;
import entity.GameObject;
import gfx.SpriteLibrary;
import state.State;
import text.GameText;
import ui.clickable.IClickAction;

public class ButtonObject extends ClickableObject{
    private GameText text;
    private IClickAction clickAction;
    private SpriteLibrary spriteLibrary;
    private String clickedName;
    private String unclickedName;
    private boolean disabled;
    private boolean disableOnClick;
    private boolean wasClicked;
    private int timer = 20;

    public ButtonObject(String label, String unclickedButtonName, String clickedButtonName, State state, Position position, int renderOrder, boolean disableOnClick, IClickAction clickEvent){
        super(unclickedButtonName, position, state, renderOrder);
        clickAction = clickEvent;
        spriteLibrary = state.getSpriteLibrary();
        this.clickedName = clickedButtonName;
        this.unclickedName = unclickedButtonName;
        this.position = position;
        this.disableOnClick = disableOnClick;
        if (label != null) {
            text = new GameText(label, state, "testFont", new Position(0, 0), 24, 7);
            text.setPosX((sprite.getWidth(null)/2)-((text.getStringSpriteWidth())/2));
            text.setPosY((getSprite().getHeight(null)/2) - (text.getStringSpriteHeight()/2));
            addChildren(text);
        }
    }

    public ButtonObject(String label, String unclickedButtonName, String clickedButtonName, State state, Position position, int renderOrder, boolean disableOnClick, GameObject parent,  IClickAction clickEvent){
        this(label, unclickedButtonName, clickedButtonName, state, position, renderOrder, disableOnClick, clickEvent);
        parent(parent);
    }

    @Override
    protected void onFocus(State state) {
        state.getAudioPlayer().playSound("button.wav");
        
    }

    @Override
    protected void onDrag(State state) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void onClick(State state) {
        wasClicked = true;
        if (!disabled) {
            spriteName = clickedName;
            loadGraphics(spriteLibrary);
            clickAction.execute(state);            
        }
        if (disableOnClick) {
            disabled = true;            
        }
    }

    @Override
    public void setPosition(Position position) {
        for (GameObject gameObject : children) {
            gameObject.setPosX(gameObject.getPosition().getIntX() + position.getIntX());
            gameObject.setPosY(gameObject.getPosition().getIntY() + position.getIntY());
        }
        if (parent != null) {
            position = (new Position(getPosition().getIntX() + position.getIntX(), getPosition().getIntY() + position.getIntY()));
        }
        super.setPosition(position);
    }

    public GameText getText() {
        return text;
    }

    public void setTextPosX(int posX){
        text.setPosX(posX);
    }
    
    @Override
    public void update(State state) {
        super.update(state);
        if (!disableOnClick && wasClicked) {
            timer--;
            if (timer < 0) {
                spriteName = unclickedName;
                loadGraphics(spriteLibrary);
                wasClicked = false;
                timer = 20;
            }
        }
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean WasClicked() {
        return wasClicked;
    }
}

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
    private boolean disabled;

    public ButtonObject(String label, String unclickedButtonName, String clickedButtonName, State state, Position position, int renderOrder, IClickAction clickEvent){
        super(unclickedButtonName, position, state, renderOrder);
        clickAction = clickEvent;
        spriteLibrary = state.getSpriteLibrary();
        this.clickedName = clickedButtonName;
        this.position = position;       
    }

    public ButtonObject(String label, String unclickedButtonName, String clickedButtonName, State state, Position position, int renderOrder, GameObject parent,  IClickAction clickEvent){
        this(label, unclickedButtonName, clickedButtonName, state, position, renderOrder, clickEvent);
        parent(parent);
        text = new GameText(label, state, "testFont", new Position(0, 0), 24, 7);
        text.setPosX((sprite.getWidth(null)/2)-(text.getStringSpriteWidth()/2));
        text.setPosY((getSprite().getHeight(null)/2) - (text.getStringSpriteHeight()/2));
        addChildren(text);
    }

    @Override
    public void update(State state) {
        super.update(state);
    }

    @Override
    public Image getSprite() {
        return super.getSprite();
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
        if (!disabled) {
            spriteName = clickedName;
            loadGraphics(spriteLibrary);
            clickAction.execute(state);            
        }
        disabled = true;
    }
    
}

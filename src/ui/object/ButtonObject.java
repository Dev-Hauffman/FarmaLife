package ui.object;

import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import core.Position;
import core.Size;
import entity.GameObject;
import gfx.ImageUtils;
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
        this.children = new ArrayList<>();
        this.position = position;
        this.renderOrder = renderOrder;
        this.state = state;
        clickAction = clickEvent;
        spriteLibrary = state.getSpriteLibrary();
        this.clickedName = clickedButtonName;
        this.unclickedName = unclickedButtonName;
        this.spriteName = unclickedButtonName;
        this.position = position;
        this.disableOnClick = disableOnClick;
        loadGraphics(spriteLibrary);
        if (label != null) {
            text = new GameText(label, state, "testFont", new Position(0, 0), 24, 7);
            if (sprite != null) {
                text.setPosX((sprite.getWidth(null)/2)-((text.getStringSpriteWidth())/2));
                text.setPosY((getSprite().getHeight(null)/2) - (text.getStringSpriteHeight()/2));                
            }
            addChildren(text);
        }
    }

    public ButtonObject(String label, Size size, State state, Position position, int renderOrder, boolean disableOnClick, IClickAction clickEvent){
        this.children = new ArrayList<>();
        this.position = position;
        this.renderOrder = renderOrder;
        this.size = size;
        this.state = state;
        clickAction = clickEvent;
        spriteLibrary = state.getSpriteLibrary();
        this.clickedName = null;
        this.unclickedName = null;
        this.spriteName = null;
        this.position = position;
        this.disableOnClick = disableOnClick;
        if (label != null) {
            text = new GameText(label, state, "testFont", new Position(0, 0), 24, 7);
            text.setPosX((size.getWidth()/2)-((text.getStringSpriteWidth())/2));
            text.setPosY((size.getHeight()/2) - (text.getStringSpriteHeight()/2));
            addChildren(text);
        }
        loadGraphics(spriteLibrary);
    }

    public ButtonObject(String label, String unclickedButtonName, String clickedButtonName, State state, Position position, int renderOrder, boolean disableOnClick, GameObject parent,  IClickAction clickEvent){
        this(label, unclickedButtonName, clickedButtonName, state, position, renderOrder, disableOnClick, clickEvent);
        parent(parent);
    }

    @Override
    public void loadGraphics(SpriteLibrary spriteLibrary) {
        if(spriteName != null){
            sprite = spriteLibrary.getImage(spriteName);
            size = new Size(sprite.getWidth(null), sprite.getHeight(null));
        }
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_BITMASKED);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.RED);
        graphics.drawRect(position.getIntX(), position.getIntY(), size.getWidth() - 1, size.getHeight() - 1);
        if (sprite != null) {
            graphics.drawImage(this.sprite, 0, 0, null);
        }
        for (GameObject gameObject : children) {
            if (gameObject.getClass().getSimpleName().equals("GameText")) {
                graphics.drawImage(
                    gameObject.getSprite(),
                    ((GameText)gameObject).getRenderPosition().getIntX(),
                    ((GameText)gameObject).getRenderPosition().getIntY(),
                    null
                );
            }
            else{
                graphics.drawImage(
                    gameObject.getSprite(),
                    gameObject.getPosition().getIntX() - getPosition().getIntX(),
                    gameObject.getPosition().getIntY() - getPosition().getIntY(),
                    null
                );
            }
        }
        graphics.dispose();
        Image result = image;
        sprite = result;
        if (parent != null) {
            if (parent instanceof UIObject) {
                ((UIObject)parent).loadGraphics(spriteLibrary);
            }
        }
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
        if (!wasClicked) {
            if (disabled) {
                spriteName = clickedName;
                loadGraphics(spriteLibrary);
            }else{
                spriteName = unclickedName;
                loadGraphics(spriteLibrary);
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

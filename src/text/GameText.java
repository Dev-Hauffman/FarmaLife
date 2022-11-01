package text;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;

import core.Position;
import core.Size;
import entity.GameObject;
import gfx.ImageUtils;
import gfx.SpriteLibrary;
import state.State;
import ui.object.UIObject;

public class GameText extends UIObject{
    protected List<SingleCharacter> characters;
    protected String fontName;
    protected int fontSize;
    protected String text;
    protected int renderOrder;
    protected int spacing;

    public GameText(String text, State state, String fontName, Position position, int fontSize, int renderOrder){
        this.position = position;
        this.fontName = fontName;
        this.fontSize = fontSize;
        this. renderOrder = renderOrder;
        this.state = state;
        this.children = new ArrayList<>();
        this.characters = new ArrayList<>();
        int spacing = fontSize/2 + (fontSize/3);
        this. spacing = spacing;
        stringToImage(text, state.getSpriteLibrary(), fontName, spacing, fontSize, renderOrder);
        this.size = new Size(getStringSpriteWidth(), getStringSpriteHeight());
        loadGraphics(state.getSpriteLibrary());
    }

    private void stringToImage(String text, SpriteLibrary spriteLibrary, String fontName, int spacing, int fontSize, int renderOrder){
        for (int i = 0; i < text.length(); i++) {
            SingleCharacter currentChar = new SingleCharacter(position.getIntX(), position.getIntY(), text.charAt(i), fontName, spriteLibrary, i, spacing, fontSize, renderOrder);
            characters.add(currentChar);
        }
    }

    @Override
    public void loadGraphics(SpriteLibrary spriteLibrary) {
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(new Size(getStringSpriteWidth(), getStringSpriteHeight()), ImageUtils.ALPHA_BITMASKED);
        Graphics2D graphics = image.createGraphics();
        for (GameObject gameObject : characters) {
            graphics.drawImage(
                gameObject.getSprite(),
                gameObject.getPosition().getIntX() - getPosition().getIntX(),
                gameObject.getPosition().getIntY() - getPosition().getIntY(),
                null
            );
        }
        graphics.dispose();
        Image result = image;
        sprite = result;
    }
    
    @Override
    public Image getSprite() {
        return sprite;
    }

    public List<SingleCharacter> getCharacterSprites() {
        return characters;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public void parent(GameObject parent) {
        super.parent(parent);
        for (SingleCharacter singleCharacter : characters) {
            singleCharacter.setPosX(parent.getPosition().getIntX() + position.getIntX());
            singleCharacter.setPosY(parent.getPosition().getIntY() + position.getIntY());
        }
    }

    public int getStringSpriteWidth(){
        int width = 0;
        for (SingleCharacter singleCharacter : characters) {
            width += singleCharacter.getSpriteWidth();
        }
        return width;
    }

    public int getStringSpriteHeight(){
        return characters.get(0).getSpriteHeight();
    }

    public void setPosX(int posX) {
        if(parent != null){
            this.position.setX(parent.getPosition().getIntX() + posX);
        } else {
            this.position.setX(posX);
        }
        for (SingleCharacter singleCharacter : characters) {
            if(parent != null){
                singleCharacter.setPosX(parent.getPosition().getIntX() + posX);
            } else {
                singleCharacter.setPosX(posX);
            }
        }
    }

    public void setPosY(int posY) {
        if(parent != null){
            this.position.setY(parent.getPosition().getIntX() + posY);
        } else {
            this.position.setY(posY);
        }
        for (SingleCharacter singleCharacter : characters) {
            if(parent != null){
                singleCharacter.setPosY(parent.getPosition().getIntY() + posY);
            } else {
                singleCharacter.setPosY(posY);
            }
        }
    }

    public void setText(String text) {
        this.text = text;
        characters.clear();
        stringToImage(text, state.getSpriteLibrary(), fontName, spacing, fontSize, renderOrder);
        loadGraphics(state.getSpriteLibrary());
        if (parent instanceof UIObject) {
            ((UIObject)parent).loadGraphics(state.getSpriteLibrary());
        }
    }

    @Override
    public void update(State state) {
        super.update(state);
    } 

}

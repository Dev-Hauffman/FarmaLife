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
import java.awt.Color;

public class GameText extends UIObject{
    protected List<SingleCharacter> characters;
    protected String fontName;
    protected int fontSize;
    protected String text;
    protected int renderOrder;
    protected int spacing;
    protected Position renderPosition;

    public GameText(String text, State state, String fontName, Position position, int fontSize, int renderOrder){
        this.position = position;
        this.renderPosition = position;
        this.fontName = fontName;
        this.fontSize = fontSize;
        this. renderOrder = renderOrder;
        this.state = state;
        this.children = new ArrayList<>();
        this.characters = new ArrayList<>();
        int spacing = fontSize/2 + (fontSize/3) + (fontSize/9);
        this. spacing = spacing;
        if (text != null) {
            this.text = text;
            stringToImage(text, state.getSpriteLibrary(), fontName, spacing, fontSize, renderOrder);            
            this.size = new Size(getStringSpriteWidth(), getStringSpriteHeight());
            loadGraphics(state.getSpriteLibrary());
        }
    }

    private void stringToImage(String text, SpriteLibrary spriteLibrary, String fontName, int spacing, int fontSize, int renderOrder){
        for (int i = 0; i < text.length(); i++) {
            SingleCharacter currentChar = new SingleCharacter(position.getIntX(), position.getIntY(), text.charAt(i), fontName, spriteLibrary, i, spacing, fontSize, renderOrder);
            characters.add(currentChar);
        }
    }

    @Override
    public void loadGraphics(SpriteLibrary spriteLibrary) {
        if (getStringSpriteWidth() > 0 && getStringSpriteHeight() > 0) {
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
            if (parent != null) {
                if (parent instanceof UIObject) {
                    ((UIObject)parent).loadGraphics(spriteLibrary);
                }
            }
        }
    }
    
    @Override
    public Image getSprite() {
        return sprite;
    }

    public List<SingleCharacter> getCharacterSprites() {
        return characters;
    }

    @Override
    public void parent(GameObject parent) {
        super.parent(parent);
    }

    public int getStringSpriteWidth(){        
        return getCharacters().size() * getSpacing();
    }

    public int getStringSpriteHeight(){
        return characters.get(0).getSpriteHeight();
    }

    public void setText(String text) {
        this.text = text;
        characters.clear();
        stringToImage(text, state.getSpriteLibrary(), fontName, spacing, fontSize, renderOrder);
        loadGraphics(state.getSpriteLibrary());
    }

    @Override
    public void update(State state) {
        super.update(state);
    }

    public List<SingleCharacter> getCharacters() {
        return characters;
    }

    public String getText() {
        return text;
    }

    public int getSpacing() {
        return spacing;
    }

    public boolean isEmpty(){
        if (text == null) {
            return true;
        }
        return characters.isEmpty();
    }

    public Position getRenderPosition() {
        return renderPosition;
    }

}

package text;
import java.awt.image.BufferedImage;

import entity.GameObject;

import java.awt.Image;
import game.Game;
import gfx.SpriteLibrary;
import state.State;

public class SingleCharacter extends GameObject{
    private Image sprite;
    private int index;
    private int spacing;

    public SingleCharacter(int posX, int posY, char character, String fontName, SpriteLibrary spriteLibrary, int index, int spacing, int fontSize, int renderOrder){
        this.index = index;
        this.spacing = spacing;
        this.renderOrder = renderOrder;
        setPosition(posX, posY, index, spacing);
        setSprite(spriteLibrary, fontName, character, fontSize);
    }

    private void setPosition(int posX, int posY, int index, int spacing) {
        this.position.setX(posX + index * spacing);
        this.position.setY(posY);
    }

    private void setSprite(SpriteLibrary spriteLibrary, String fontName, char character, int fontSize) {
        int charValue = (int)character;
        BufferedImage fullImage = (BufferedImage) spriteLibrary.getImage(fontName);
        int indexX = (charValue - 32) % 15;
        int indexY = (charValue - 32) / 15;
        if (indexX > 14) {
            indexX = 0;
        }
        if (indexY > 7) {
            indexY = 0;
        }
        BufferedImage sizeableImage = fullImage.getSubimage(indexX*Game.SPRITE_SIZE, indexY*Game.SPRITE_SIZE, Game.SPRITE_SIZE, Game.SPRITE_SIZE);
        Image result = sizeableImage.getScaledInstance(fontSize, fontSize, 0);
        this.sprite = result;
    }

    public Image getSprite(){
        return sprite;
    }

    public int getPosX() {
        return this.position.getIntX();
    }

    public int getPosY() {
        return this.position.getIntY();
    }
    
    public int getSpriteWidth(){
        return sprite.getWidth(null);
    }

    public int getSpriteHeight(){
        return sprite.getHeight(null);
    }

    @Override
    public void update(State state) {}

    @Override
    public void setPosX(int posX){
        this.position.setX(posX + (index * spacing));
    }
}

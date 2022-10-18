package text;
import java.awt.image.BufferedImage;
import java.awt.Image;
import game.Game;
import gfx.SpriteLibrary;

public class SingleCharacter {
    private int posX;
    private int posY;
    private Image sprite;

    public SingleCharacter(int posX, int posY, char character, String fontName, SpriteLibrary spriteLibrary, int index, int spacing, int fontSize){
        setPosition(posX, posY, index, spacing);
        setSprite(spriteLibrary, fontName, character, fontSize);
    }

    private void setPosition(int posX, int posY, int index, int spacing) {
        this.posX = posX + index * spacing;
        this.posY = posY;
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
        return posX;
    }

    public int getPosY() {
        return posY;
    }    
}

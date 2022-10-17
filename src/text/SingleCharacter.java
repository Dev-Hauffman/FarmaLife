package text;
import java.awt.image.BufferedImage;
import java.awt.Image;
import game.Game;

public class SingleCharacter {
    private int posX;
    private int posY;
    private Image sprite;

    public SingleCharacter(int posX, int posY, char character, String fontName, Font font, int index){
        setPosition(posX, posY, index);
        setSprite(font, fontName, character);
    }

    public void setPosition(int posX, int posY, int index) {
        this.posX = posX + index * Game.SPRITE_SIZE;
        this.posY = posY + index * Game.SPRITE_SIZE;
    }

    private void setSprite(Font font, String fontName, char character) {
        System.out.println(character); //DELETE
        int charValue = (int)character;
        System.out.println(charValue);// DELETE
        BufferedImage fullImage = (BufferedImage) font.getImage("testFont");
        int indexX = (charValue - 32) % 15;
        System.out.println(indexX); // DELETE
        int indexY = (charValue - 32) / 15;
        System.out.println(indexY); // DELETE
        if (indexX > 14) {
            indexX = 0;
        }
        if (indexY > 7) {
            indexY = 0;
        }
        Image result = fullImage.getSubimage(indexX*Game.SPRITE_SIZE, indexY*Game.SPRITE_SIZE, Game.SPRITE_SIZE, Game.SPRITE_SIZE);
        this.sprite = result;
    }

    public Image getSprite(){
        return sprite;
    }
}

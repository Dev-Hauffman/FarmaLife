package text;

import java.util.ArrayList;
import java.util.List;

import gfx.SpriteLibrary;
import ui.UIComponent;

public class Text {
    private List<SingleCharacter> stringImage;
    private String string;
    private int posX;
    private int posY;
    private UIComponent parent;

    public Text(String string, SpriteLibrary spriteLibrary, String fontName, int posX, int posY, int fontSize){
        this.posX = posX;
        this.posY = posY;
        stringImage = new ArrayList<>();
        int spacing = fontSize/2 + (fontSize/3);
        stringToImage(string, spriteLibrary, fontName, spacing, fontSize);
    }

    private void stringToImage(String string, SpriteLibrary spriteLibrary, String fontName, int spacing, int fontSize){
        for (int i = 0; i < string.length(); i++) {
            SingleCharacter currentChar = new SingleCharacter(posX, posY, string.charAt(i), fontName, spriteLibrary, i, spacing, fontSize);
            stringImage.add(currentChar);
        }
    }

    public List<SingleCharacter> getCharacterSprites() {
        return stringImage;
    }
}

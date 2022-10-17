package text;

import gfx.SpriteLibrary;
import java.awt.Image;

public class Font {
    SpriteLibrary spriteLibrary;

    public Font(SpriteLibrary spriteLibrary){
        this.spriteLibrary = spriteLibrary;
    }

    public Image getImage(String name){
        return spriteLibrary.getImage(name);
    }
}

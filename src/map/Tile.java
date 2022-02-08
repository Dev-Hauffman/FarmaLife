package map;

import java.awt.*;

import gfx.SpriteLibrary;

public class Tile {

    private Image sprite;

    public Tile (SpriteLibrary spriteLibrary) {
        this.sprite = spriteLibrary.getTile("woodfloor");
    }

    public Image getSprite() {
        return sprite;
    }

}

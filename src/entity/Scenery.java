package entity;

import java.awt.Image;

import core.Position;
import core.Size;
import gfx.SpriteLibrary;
import state.State;

public class Scenery extends GameObject{

    private Image sprite;
    private String name;
    private boolean walkable;

    public Scenery(String name, Size size, Position position, SpriteLibrary spriteLibrary, int renderOrder) {
        this.name = name;
        this.size = size;
        this.position = position;
        this.renderOrder = renderOrder;

        loadGraphics(spriteLibrary);
    }

    private void loadGraphics(SpriteLibrary spriteLibrary) {
        sprite = spriteLibrary.getImage(name);
    }

    @Override
    public void update(State state) {}

    @Override
    public Image getSprite() {
        return sprite;
    }
    
}

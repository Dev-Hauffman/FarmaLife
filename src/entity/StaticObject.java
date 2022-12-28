package entity;

import java.awt.Image;

import core.Position;
import gfx.SpriteLibrary;
import state.State;

public class StaticObject extends GameObject{

    private Image sprite;
    private String name;

    public StaticObject(String name,Position position, SpriteLibrary spriteLibrary, int renderOrder){
        this.name = name;
        this.position = position;
        this.renderOrder = renderOrder;

        loadGraphics(spriteLibrary);
    }

    public StaticObject(String name,Position position, SpriteLibrary spriteLibrary, int renderOrder, GameObject parent){
        this.name = name;
        this.position = position;
        this.renderOrder = renderOrder;
        parent(parent);
        loadGraphics(spriteLibrary);
    }

    private void loadGraphics(SpriteLibrary spriteLibrary) {
        sprite = spriteLibrary.getImage(name);
    }

    @Override
    public void update(State state) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Image getSprite() {
        return sprite;
    }

    @Override
    public Position getPosition() {
        Position finalPosition = Position.copyOf(position);

        if (parent != null) {
            finalPosition.add(parent.getPosition());
        }
        return finalPosition;
    }
    
}

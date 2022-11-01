package ui.object;

import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import core.Position;
import core.Size;
import entity.GameObject;
import gfx.ImageUtils;
import gfx.SpriteLibrary;
import state.State;

public class UIObject extends GameObject{
    protected List<GameObject> children;
    protected Image sprite;
    protected State state;
    protected String spriteName;

    public UIObject(){
        this.children = new ArrayList<>();
    }

    public UIObject(String spriteName, Position position, State state, int renderOrder){
        this.children = new ArrayList<>();
        this.position = position;
        this.renderOrder = renderOrder;
        this.state = state;
        this.spriteName = spriteName;

        loadGraphics(state.getSpriteLibrary());
    }

    public UIObject(String spriteName, Position position, State state, int renderOrder, GameObject parent){
        this(spriteName, position, state, renderOrder);
        parent(parent);
    }

    @Override
    public void update(State state) {
        children.forEach(component -> component.update(state));        
    }

    public void loadGraphics(SpriteLibrary spriteLibrary) {
        sprite = spriteLibrary.getImage(spriteName);
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(new Size(sprite.getWidth(null), sprite.getHeight(null)), ImageUtils.ALPHA_BITMASKED);
        Graphics2D graphics = image.createGraphics();
        // graphics.setColor(Color.blue);
        // graphics.fillRect(0, 0, size.getWidth(), size.getHeight());
        graphics.drawImage(this.sprite, 0, 0, null);
        // children.sort(Comparator.comparing(GameObject::getRenderOrder)
        for (GameObject gameObject : children) {
            graphics.drawImage(
                gameObject.getSprite(),
                gameObject.getPosition().getIntX(),
                gameObject.getPosition().getIntY(),
                null
            );
            // graphics.setColor(Color.blue);
            // graphics.drawRect(100, 100, gameObject.getSprite().getWidth(null), gameObject.getSprite().getHeight(null));
        }
        graphics.dispose();
        Image result = image;
        sprite = result;
    }

    @Override
    public Image getSprite() {
        return sprite;
    }

    public void addChildren(GameObject gameObject) {
        children.add(gameObject);
        gameObject.parent(this);
        loadGraphics(state.getSpriteLibrary());
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }

}

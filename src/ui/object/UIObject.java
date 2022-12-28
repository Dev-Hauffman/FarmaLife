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
import text.GameText;

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
        if (spriteName != null) {
            loadGraphics(state.getSpriteLibrary());
        }
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
        graphics.drawImage(this.sprite, 0, 0, null);
        // if (getClass().getSimpleName().equals("UIObject")) {
        //     graphics.setColor(Color.red);
        //     graphics.fillRect(0, 0, size.getWidth(), size.getHeight());
        // }
        // if (getClass().getSimpleName().equals("SearchBar")) {
        //     graphics.setColor(Color.blue);
        //     System.out.println(getPosition().getIntX());
        //     System.out.println(getPosition().getIntY());
        //     graphics.fillRect(0, 0, size.getWidth(), size.getHeight());
        // }
        // children.sort(Comparator.comparing(GameObject::getRenderOrder)
        for (GameObject gameObject : children) {
            // if (gameObject.getClass().getSimpleName().equals("SearchBar")) {
            //     graphics.setColor(Color.blue);
            //     graphics.drawRect(0, 0, gameObject.getSprite().getWidth(null), gameObject.getSprite().getHeight(null));
            //     System.out.println(gameObject.getParent().getPosition().getIntX());
            //     System.out.println(gameObject.getParent().getPosition().getIntY());
            //     System.out.println(gameObject.getPosition().getIntX());
            //     System.out.println(gameObject.getPosition().getIntY());
                
            //     graphics.drawImage(
            //         gameObject.getSprite(),
            //         gameObject.getPosition().getIntX() - getPosition().getIntX(),
            //         gameObject.getPosition().getIntY() - getPosition().getIntY(),
            //         null
            //     );
            // }else{
                // if (gameObject.getParent().getClass().getSimpleName().equals("ButtonObject")) {
                //     System.out.println(gameObject.getParent().getPosition().getIntX());
                //     System.out.println(gameObject.getParent().getPosition().getIntY());
                //     if (gameObject.getClass().getSimpleName().equals("GameText")) {
                //         System.out.println(gameObject.getPosition().getIntX());
                //         System.out.println(gameObject.getPosition().getIntY());
                //     }
                // }
                // if (gameObject.getParent().getClass().getSimpleName().equals("SearchBar")) {
                //     System.out.println(gameObject.getParent().getPosition().getIntX());
                //     System.out.println(gameObject.getParent().getPosition().getIntY());
                //     System.out.println(gameObject.getPosition().getIntX());
                //     System.out.println(gameObject.getPosition().getIntY());
                // }
                // System.out.println(gameObject.getPosition().getIntX() - getPosition().getIntX());
                // System.out.println(gameObject.getPosition().getIntY() - getPosition().getIntY());
                if (gameObject.getClass().getSimpleName().equals("GameText")) {
                    graphics.drawImage(
                        gameObject.getSprite(),
                        ((GameText)gameObject).getRenderPosition().getIntX(),
                        ((GameText)gameObject).getRenderPosition().getIntY(),
                        null
                    );
                }
                // else if (gameObject.getClass().getSimpleName().equals("ButtonObject")) {
                //     graphics.drawImage(
                //         gameObject.getSprite(),
                //         ((ButtonObject)gameObject).getRenderPosition().getIntX(),
                //         ((ButtonObject)gameObject).getRenderPosition().getIntY(),
                //         null
                //     );
                // }
                else{
                    graphics.drawImage(
                        gameObject.getSprite(),
                        gameObject.getPosition().getIntX() - getPosition().getIntX(),
                        gameObject.getPosition().getIntY() - getPosition().getIntY(),
                        null
                    );
                }
                // if (gameObject instanceof UIObject) {
                //     if(((UIObject)gameObject).getChildren().size() > 0){
                //         ((UIObject)gameObject).loadGraphics(spriteLibrary);
                //     }
                // }
            // }
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
        return position;
    }

    @Override
    public void parent(GameObject parent) { //NEEDS WORK?
        super.parent(parent);
        setPosition(new Position(parent.getPosition().getIntX() + getPosition().getIntX(), parent.getPosition().getIntY() + getPosition().getIntY()));
        if (parent instanceof UIObject) { // solution for input gamText from disappearing in searchBar, also related to ButtonObject GameText Disappearing
            for (GameObject gameObject : children) {
                gameObject.parent(this);
            }
        }
    }

    public List<GameObject> getChildren() {
        return children;
    }

}

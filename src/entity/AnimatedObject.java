/* Animated object is an object that move (independent from screen) and has a animation on loop or not
* Most body parts are AnimatedObjects
*/

package entity;

import java.awt.Image;

import controller.IEntityController;
import controller.NPCController;
import core.Position;
import core.Rotation;
import core.Size;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import state.State;

public class AnimatedObject extends GameObject {
    protected IEntityController entityController;
    protected AnimationManager animationManager;
    private Rotation rotation;
    private String name;
    private boolean followParent;

    public AnimatedObject(String name, Position position, Size size, SpriteLibrary spriteLibrary, int renderOrder) {
        entityController = new NPCController();
        this.name = name;
        this.animationManager = new AnimationManager(spriteLibrary.getSpriteSets(name), this);
        this.position = position;
        this.renderOrder = renderOrder;
        this.size = size;
    }

    public AnimatedObject(String name, String startSpriteset, Position position, Size size, SpriteLibrary spriteLibrary, Rotation initialRotation, int renderOrder) {
        this(name, position, size, spriteLibrary, renderOrder);
        this.rotation = initialRotation;
        animationManager.setSprite(startSpriteset, 0, rotation);
    }

    @Override
    public void update(State state) {
        if (parent != null) {
            if (parent instanceof AnimatedObject) {
                Rotation parentRotation = ((AnimatedObject)parent).getRotation();
                if (rotation != (parentRotation)) {
                    setRotation(parentRotation);
                }
            }
        }
        animationManager.update();
    }

    @Override
    public Image getSprite() {
        return animationManager.getSprite(size.getWidth(), size.getHeight());
    }

    public AnimationManager getAnimationManager() {
        return animationManager;
    }

    public String getName() {
        return name;
    }

    public IEntityController getEntityController() {
        return entityController;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
        if (name.equals("eyelids")) { // to blink everytime the head rotates (eyelids rotate with it)
            animationManager.playAnimation("eyelidsTest", true);
        }
        animationManager.setFinishedRotation(false);
        animationManager.rotate(rotation);
    }
    
    @Override
    public Position getPosition() {
        if (followParent) {
            int sumX = parent.getPosition().getIntX() + position.getIntX();
            int sumY = parent.getPosition().getIntY() + position.getIntY();
            return new Position(sumX, sumY);
        }
        return position;
    }

    public void setFollowParent(boolean followParent) {
        this.followParent = followParent;
    }

}

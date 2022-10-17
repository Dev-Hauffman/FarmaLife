/* Animated object is an object that move (independent from screen) and has a animation on loop or not
* Most body parts are AnimatedObjects
*/

package entity;

import java.awt.Image;

import gfx.AnimationManager;
import state.State;

public class AnimatedObject extends GameObject {

    protected AnimationManager animationManager;

    public AnimatedObject(AnimationManager animationManager) {
        this.animationManager = animationManager;
    }

    @Override
    public void update(State state) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Image getSprite() {
        // TODO Auto-generated method stub
        return null;
    }
    
}

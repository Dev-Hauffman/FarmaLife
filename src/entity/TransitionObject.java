package entity;

import java.awt.Image;
import java.awt.image.BufferedImage;

import core.Position;
import core.Size;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import gfx.SpriteSet;
import state.State;
import state.counter.WorkCounterState;
import state.mind.MindState;

public class TransitionObject extends GameObject{
    protected AnimationManager animationManager;
    private SpriteSet spriteSet;
    private int frameIndex;
    private BufferedImage animationSheet;
    private int updatesPerFrame;
    private int currentFrameTime;
    private boolean invertedAnimation;
    private boolean playAnimaton;

    public TransitionObject(SpriteLibrary spriteLibrary, int renderOrder) {
        this.animationManager = new AnimationManager(spriteLibrary.getSpriteSets("eyesclosing"), this);
        this.position = new Position(0, 0);
        spriteSet = spriteLibrary.getSpriteSets("eyesclosing");
        this.updatesPerFrame = 2;
        this.frameIndex = 0;
        this.currentFrameTime = 0;
        this.renderOrder = renderOrder;
        this.animationSheet = (BufferedImage) spriteSet.getOrGetDefault("eyesclosing");
    }

    @Override
    public void update(State state) {
        currentFrameTime++;
        if (currentFrameTime >= updatesPerFrame) {
            currentFrameTime = 0;
            if (playAnimaton) {
                int animationSize = animationSheet.getWidth() / 1600;
                if(invertedAnimation){
                    frameIndex --;
                    if (frameIndex <= 0) {
                        invertedAnimation = false;
                        frameIndex = 0;
                        playAnimaton = false;
                        state.getGameObjects().remove(this);
                    }
                }else{
                    frameIndex++;
                    if (frameIndex >= animationSize) {
                        frameIndex = animationSize - 1;
                        playAnimaton = false;
                        if (state instanceof WorkCounterState) {
                            state.setNextState(new MindState(state.getWindowSize(), state.getInput(), state.getGameSettings(), (WorkCounterState)state));
                        }
                    }
                }
            }
        }
    }

    @Override
    public Image getSprite() {
        return animationSheet.getSubimage(frameIndex * 1600,
        0 * 900,
        1600,
        900
        );
    }

    public void playAnimation(boolean invertedAnimation) {        
        frameIndex = 0;
        if (invertedAnimation) {
            frameIndex = (animationSheet.getWidth() / 1600) -1;
            this.invertedAnimation = true;
        }
        playAnimaton = true;
    }
    
}

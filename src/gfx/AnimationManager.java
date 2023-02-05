package gfx;

import java.awt.image.BufferedImage;

import core.Direction;
import core.Rotation;
import entity.AnimatedObject;
import entity.GameObject;
import game.Game;

import java.awt.*;

public class AnimationManager {
    private SpriteSet spriteSet;
    private String currentAnimationName;
    private BufferedImage currentAnimationSheet;
    private int updatesPerFrame; // determine how many frames a sprite lives
    private int currentFrameTime;  // a counter to tell how long a sprite has already lived
    private int frameIndex; // tells which frame(sprite?) we're on
    private int rotationIndex;
    private boolean looping;
    private boolean invertedAnimation;
    private boolean loopBack;
    private boolean reverseAnimation;
    private boolean increaseFrame;
    private boolean playAnimaton;
    private boolean playRotation;
    private boolean finishedRotation;
    private Rotation targetRotation;
    private GameObject owner;

    public AnimationManager(SpriteSet spriteSet, GameObject owner) {
        this(spriteSet, false, owner);
    }

    public AnimationManager(SpriteSet spriteSet, boolean looping, GameObject owner) {
        this.spriteSet = spriteSet;
        this.updatesPerFrame = 2;
        this.frameIndex = 0;
        this.currentFrameTime = 0;
        this.rotationIndex = 0;
        this.looping = looping;
        this.owner = owner;
        currentAnimationName = "";
        // playAnimation("stand");
    }

    public Image getDefaultSprite() {
        return null;
    }

    public Image getSprite(int spriteWidth, int spriteHeight) {
        return currentAnimationSheet.getSubimage(frameIndex * spriteWidth,
        rotationIndex * spriteHeight,
        spriteWidth,
        spriteHeight
        );
    }

    public void setSprite(String animationName, int frameIndex, Rotation rotationIndex){
        if (!animationName.equals(currentAnimationName)) {
            this.currentAnimationSheet = (BufferedImage) spriteSet.getOrGetDefault(animationName);
            currentAnimationName = animationName;
        }
        this.frameIndex = frameIndex;
        this.rotationIndex = rotationIndex.getFrame();
    }

    // public void update(Direction direction) {
    //     currentFrameTime++;
    //     directionIndex = direction.getAnimationRow();
        
    //     if (currentFrameTime >= updatesPerFrame) {
    //         currentFrameTime = 0;
    //         frameIndex++;
    //         int animationSize = currentAnimationSheet.getWidth() / Game.SPRITE_SIZE;
    //         if (frameIndex >= animationSize) {
    //             frameIndex = looping ? 0 : animationSize - 1;
    //         }
    //     }
    // }

    public void update() {
        currentFrameTime++;
        if (currentFrameTime >= updatesPerFrame) {
            currentFrameTime = 0;
            if (playRotation) {
                if (rotationIndex > targetRotation.getFrame()) {
                    if (rotationIndex != targetRotation.getFrame()) {
                        rotationIndex--;
                    }else{
                        playRotation = false;
                        finishedRotation = true;
                    }
                }else{
                    if (rotationIndex != targetRotation.getFrame()) {
                        rotationIndex++;
                    }else{
                        playRotation = false;
                        finishedRotation = true;
                    }
                }
            }
            if (playAnimaton) {
                int animationSize = currentAnimationSheet.getWidth() / owner.getSize().getWidth();
                if (loopBack) {
                    if (increaseFrame) {
                        frameIndex ++;
                        if (frameIndex >= animationSize) {
                            reverseAnimation = true;
                            increaseFrame = false;
                        }
                    }
                    if (reverseAnimation) {
                        frameIndex --;
                        if (frameIndex <= 0) {
                            reverseAnimation = false;
                            loopBack = false;
                            frameIndex = 0;
                            playAnimaton = false;
                        }
                    }
                }else if(invertedAnimation){
                    frameIndex --;
                    if (frameIndex <= 0) {
                        invertedAnimation = false;
                        frameIndex = 0;
                        playAnimaton = false;
                    }
                }else{
                    frameIndex++;
                    if (frameIndex >= animationSize) {
                        frameIndex = animationSize - 1;
                        playAnimaton = false;
                    }
                }
            }

        }
    }

    public void playAnimation(String name, boolean playLoopBack) {
        if (!name.equals(currentAnimationName)) {
            this.currentAnimationSheet = (BufferedImage) spriteSet.getOrGetDefault(name);
            currentAnimationName = name;
            frameIndex = 0;
        }
        if (playLoopBack) {
            loopBack = true;
            if (!reverseAnimation) {
                increaseFrame = true;
            }
        }
        playAnimaton = true;
    }

    public void playAnimation(boolean invertedAnimation, String name) {
        if (!name.equals(currentAnimationName)) {
            this.currentAnimationSheet = (BufferedImage) spriteSet.getOrGetDefault(name);
            currentAnimationName = name;
            frameIndex = 0;
        }
        if (invertedAnimation) {
            frameIndex = currentAnimationSheet.getWidth() / owner.getSize().getWidth();
        }
        playAnimaton = true;
    }

    public void rotate(Rotation rotationTarget){
        if (targetRotation != rotationTarget) {
            playRotation = true;
            this.targetRotation = rotationTarget;
        }
    }

    public boolean hasFinishedRotation() {
        return finishedRotation;
    }

    public void setFinishedRotation(boolean finishedRotation) {
        this.finishedRotation = finishedRotation;
    }

}

package gfx;

import java.awt.image.BufferedImage;

import core.Direction;
import game.Game;

import java.awt.*;

public class AnimationManager {
    private SpriteSet spriteSet;
    private String currentAnimationName;
    private BufferedImage currentAnimationSheet;
    private int updatesPerFrame; // determine how many frames a sprite lives
    private int currentFrameTime;  // a counter to tell how long a sprite has already lived
    private int frameIndex; // tells which frame(sprite?) we're on
    private boolean looping;

    private int directionIndex;

    public AnimationManager(SpriteSet spriteSet) {
        this(spriteSet, false);
    }

    public AnimationManager(SpriteSet spriteSet, boolean looping) {
        this.spriteSet = spriteSet;
        this.updatesPerFrame = 20;
        this.frameIndex = 0;
        this.currentFrameTime = 0;
        this.directionIndex = 0;
        this.looping = looping;
        currentAnimationName = "";
        // playAnimation("stand");
    }

    public Image getDefaultSprite() {
        return null;
    }

    public Image getSprite() {
        return currentAnimationSheet.getSubimage(frameIndex * Game.SPRITE_SIZE,
        directionIndex * Game.SPRITE_SIZE,
        Game.SPRITE_SIZE,
        Game.SPRITE_SIZE
        );
    }

    public void update(Direction direction) {
        currentFrameTime++;
        directionIndex = direction.getAnimationRow();
        
        if (currentFrameTime >= updatesPerFrame) {
            currentFrameTime = 0;
            frameIndex++;
            int animationSize = currentAnimationSheet.getWidth() / Game.SPRITE_SIZE;
            if (frameIndex >= animationSize) {
                frameIndex = looping ? 0 : animationSize - 1;
            }
        }
    }

    public void playAnimation(String name) {
        if (!name.equals(currentAnimationName)) {
            this.currentAnimationSheet = (BufferedImage) spriteSet.getOrGetDefault(name);
            currentAnimationName = name;
            frameIndex = 0;
        }
    }

}

package gfx;

import java.awt.image.BufferedImage;

import game.Game;

import java.awt.*;

public class AnimationManager {
    private SpriteSet spriteSet;
    private BufferedImage currentAnimationSheet;
    private int updatesPerFrame; // determine how many frames a sprite lives
    private int currentFrameTime;  // a counter to tell how long a sprite has already lived
    private int frameIndex; // tells which frame(sprite?) we're on

    public AnimationManager(SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
        this.updatesPerFrame = 20;
        this.frameIndex = 0;
        this.currentFrameTime = 0;
        playAnimation("stand");
    }

    public Image getSprite() {
        return currentAnimationSheet.getSubimage(frameIndex * Game.SPRITE_SIZE,
        0,
        Game.SPRITE_SIZE,
        Game.SPRITE_SIZE
        );
    }

    public void update() {        
        currentFrameTime++;
        if (currentFrameTime >= updatesPerFrame) {
            currentFrameTime = 0;
            frameIndex++;

            if (frameIndex >= currentAnimationSheet.getWidth() / Game.SPRITE_SIZE - 1) {
                frameIndex = 0;
            }
        }
    }

    public void playAnimation(String name) {
        this.currentAnimationSheet = (BufferedImage) spriteSet.get(name);
    }

}

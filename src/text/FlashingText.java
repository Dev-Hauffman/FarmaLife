package text;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;

import core.Position;
import core.Size;
import entity.GameObject;
import gfx.ImageUtils;
import gfx.SpriteLibrary;
import state.State;
import ui.object.UIObject;

public class FlashingText extends GameText{
    private boolean flashing;
    private int flashDuration;

    public FlashingText(String text, State state, String fontName, Position position, int fontSize, int renderOrder){
        super(text, state, fontName, position, fontSize, renderOrder);
        this.flashing = false;
        this.flashDuration = 0;
    }

    @Override
    public void update(State state) {
        super.update(state);
        if (flashing) {
            flashRoutine(state);
        }
    }

    private void flashRoutine(State state) {
        if (flashDuration % 20 == 0) {
            sprite = setSpriteAlpha(0.0f); 
            if (parent instanceof UIObject) {
                ((UIObject)parent).loadGraphics(state.getSpriteLibrary());
            }               
        }else if (flashDuration % 20 == 10) {
            sprite = setSpriteAlpha(1.0f);
            if (parent instanceof UIObject) {
                ((UIObject)parent).loadGraphics(state.getSpriteLibrary());
            }
        }
        flashDuration--;
        if (flashDuration < 0) {
            flashDuration = 0;
            flashing = false;
            sprite = setSpriteAlpha(1.0f);
            if (parent instanceof UIObject) {
                ((UIObject)parent).loadGraphics(state.getSpriteLibrary());
            }
        }
    }

    private Image setSpriteAlpha(float opacity) {
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(new Size(getStringSpriteWidth(), getStringSpriteHeight()), ImageUtils.ALPHA_BITMASKED);
        Graphics2D graphics = image.createGraphics();
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        for (GameObject gameObject : characters) {
            graphics.drawImage(
                gameObject.getSprite(),
                gameObject.getPosition().getIntX() - getPosition().getIntX(),
                gameObject.getPosition().getIntY() - getPosition().getIntY(),
                null
            );
        }
        graphics.dispose();
        Image result = image;
        return result;
    }

    public void flashText(int flashDuration) {
        flashing = true;
        this.flashDuration = flashDuration;
    }

    public boolean isFlashing() {
        return flashing;
    }
}

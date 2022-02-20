package ui;

import java.awt.*;
import java.awt.image.BufferedImage;

import core.Size;
import gfx.ImageUtils;
import state.State;

public class UIText extends UIComponent {

    private String text;
    private int fontSize;
    private int fontStyle;
    private String fontFamily;
    private Color color;

    private boolean dropShadow;
    private int dropShadowOffset;
    private Color shadowColor;

    private Font font;

    public UIText(String text) {
        this.text = text;
        this.fontSize = 24;
        this.fontStyle = Font.PLAIN;
        this.fontFamily = "Helvetica";
        this.color = Color.WHITE;

        this.dropShadow = false;
        this.dropShadowOffset = 2;
        this.shadowColor = new Color(140, 140, 140);
    }

    @Override
    public Image getSprite() {
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_BITMASKED);
        Graphics2D graphics = image.createGraphics();
        graphics.setFont(font);

        if (dropShadow) {
            graphics.setColor(shadowColor);
            graphics.drawString(text, padding.getLeft() + dropShadowOffset, fontSize + padding.getTop() + dropShadowOffset);
        }

        graphics.setColor(color);
        graphics.drawString(text, padding.getLeft(), fontSize + padding.getTop());

        graphics.dispose();

        return image;
    }

    @Override
    public void update(State state) {
        createFont();
        calculateSize();
    }

    private void calculateSize() {
        FontMetrics fontMetrics = new Canvas().getFontMetrics(font);
        int width = fontMetrics.stringWidth(text) + padding.getHorizontal();
        int height = fontMetrics.getHeight() + padding.getVertical();

        if(dropShadow) {
            width += dropShadowOffset;
        }
        /*
        * This if is here because if the update happens before the rendering, there will be problems with the createCompatibleImage
        * method call, because it doesn't accept values below or equal to 0, considering that the string size is 0
        */
        if (width <= 0) {
            width = 1;
        }

        if (height <= 0) {
            height = 1;
        }

        size = new Size(width, height);
    }

    private void createFont() {
        font = new Font(fontFamily, fontStyle, fontSize);
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    
    
}

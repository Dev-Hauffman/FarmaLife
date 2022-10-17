package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import core.Position;
import core.Size;
import gfx.ImageUtils;
import state.State;

public abstract class UIContainer extends UIComponent{

    protected boolean centerChildren;
    protected Color backgroundColor;

    protected List<UIComponent> children;

    protected Alignment alignment;
    protected Size windowSize;

    protected Size fixedSize;

    public UIContainer(Size windowSize) {
        super();
        this.windowSize = windowSize;
        centerChildren = false;
        alignment = new Alignment(Alignment.Position.END, Alignment.Position.START);
        backgroundColor = Color.RED;
        margin = new Spacing(5);
        padding = new Spacing(5);
        children = new ArrayList<>();
        calculateSize();
        calculatePosition();
    }

    protected abstract Size calculateContentSize();

    protected abstract void calculateContentPosition();

    private void calculateSize() {
        Size calculatedContentSize = calculateContentSize();
        size = fixedSize != null 
            ? fixedSize 
            : new Size(
                padding.getHorizontal() + calculatedContentSize.getWidth(),
                padding.getVertical() + calculatedContentSize.getHeight()
            );
    }

    private void calculatePosition() {        
        int x = margin.getLeft();
        if (alignment.getHorizontal().equals(Alignment.Position.CENTER)) {
            x = windowSize.getWidth() / 2 - size.getWidth() / 2;
        }
        if (alignment.getHorizontal().equals(Alignment.Position.END)) {
            x = windowSize.getWidth() - size.getWidth() - margin.getRight();
        }

        int y = margin.getTop();
        if (alignment.getVertical().equals(Alignment.Position.CENTER)) {
            y = windowSize.getHeight() / 2 - size.getHeight() / 2;
        }
        if (alignment.getVertical().equals(Alignment.Position.END)) {
            y = windowSize.getHeight() - size.getHeight() - margin.getBottom();
        }

        this.relativePosition = new Position(x, y);
        if (parent == null) {
            this.absolutePosition = new Position(x, y);
        }
        calculateContentPosition();
    }
    
    @Override
    public Image getSprite() {  
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_BITMASKED);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, size.getWidth(), size.getHeight());

        for (UIComponent uiComponent : children) {
            graphics.drawImage(
                uiComponent.getSprite(),
                uiComponent.getRelativePosition().getIntX(),
                uiComponent.getRelativePosition().getIntY(),
                null
            );
        }

        graphics.dispose();
        return image;
    }

    @Override
    public void update(State state) {
        children.forEach(component -> component.update(state));
        calculateSize();
        calculatePosition();
    }

    public void addUIComponent(UIComponent uiComponent) {
        children.add(uiComponent);
        uiComponent.setParent(this);
    }

    public void setBackgroundColor(Color color) {
        backgroundColor = color;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public void setFixedSize(Size fixedSize) {
        this.fixedSize = fixedSize;
    }

    public void setCenterChildren(boolean centerChildren) {
        this.centerChildren = centerChildren;
    }    
    
}

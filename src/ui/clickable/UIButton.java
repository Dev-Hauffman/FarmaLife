package ui.clickable;

import java.awt.Image;

import core.Size;
import state.State;
import ui.UIContainer;
import ui.UIText;
import ui.VerticalContainer;

import java.awt.*;

public class UIButton extends UIClickable{

    private UIContainer container;
    private UIText label;

    private IClickAction clickAction;

    public UIButton(String label, IClickAction clickEvent) {
        this.label = new UIText(label);
        this.clickAction = clickEvent;

        container = new VerticalContainer(new Size(0, 0));
        container.addUIComponent(this.label);
        container.setFixedSize(new Size(150, 30));
    }

    @Override
    public void update(State state) {
        super.update(state);
        container.update(state);
        size = container.getSize();

        Color color = Color.GRAY;

        if (hasFocus) {
            color = Color.LIGHT_GRAY;
        }

        if (isPressed) {
            color = Color.DARK_GRAY;
        }

        container.setBackgroundColor(color);
    }

    @Override
    protected void onClick(State state) {
        clickAction.execute(state);
    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }

    @Override
    protected void onFocus(State state) {
        state.getAudioPlayer().playSound("button.wav");
    }

    @Override
    protected void onDrag(State state) {
        // TODO Auto-generated method stub
        
    }
    
}

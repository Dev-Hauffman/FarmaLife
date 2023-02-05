package state.mind.ui;

import java.util.List;
import java.util.Random;
import java.awt.*;

import core.Position;
import entity.GameObject;
import lines.PlayerLine;
import state.State;
import state.mind.MindState;
import text.GameText;
import ui.clickable.IClickAction;
import ui.object.ClickableWord;

public class PoolItem extends ClickableWord{
    private int ttl;
    private Rectangle area;
    private int indexNumber;
    private boolean count;
    private boolean stop;
    private AnswerPool answerPool;
    private PlayerLine line;
    protected boolean clicked;    

    public PoolItem(Position position, int answerNumber, State state, List<String> phrase, int initialOpacity, int renderOrder, IClickAction clickEvent) {
        super(position, answerNumber, state, phrase, initialOpacity, renderOrder, clickEvent);
        area = new Rectangle(position.getIntX(), position.getIntY(), size.getWidth(), size.getHeight());
        Random rand = new Random();
        ttl = rand.nextInt(200) + 100;
    }

    public Rectangle getArea() {
        return area;
    }

    public int getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(int indexNumber) {
        this.indexNumber = indexNumber;
    }

    public void setArea(Rectangle area) {
        this.area = area;
    }

    public void updatedPosX(int posX){
        position = new Position(posX, position.getIntY());
        for (GameText gameObject : words) {
            gameObject.setPosition(new Position(gameObject.getPosition().getIntX() + posX, gameObject.getPosition().getIntY()));
        }
    }

    public void updatedPosY(int posY){
        position = new Position(position.getIntX(), posY);
        for (GameText gameObject : words) {
            gameObject.setPosition(new Position(gameObject.getPosition().getIntX(), gameObject.getPosition().getIntY() + posY));
        }
    }

    @Override
    public void update(State state) {
        super.update(state);
        if (!hasFocus && state.getInput().isMouseClicked()) {
            clicked = false;
        }
        if (!stop) {
            if (count) {
                if (ttl <=0) {
                    count = false;             
                    fadeOut(0.006f);
                }
                ttl--;
            }
            if (fadeIn) {
                opacity += speed;
                if (opacity > 1) {
                    opacity = 1.0f;
                    fadeIn = false;
                    count = true;
                }
                setOpacity(state.getSpriteLibrary(), opacity);
            }
            if (fadeOut) {
                opacity -= speed;
                if (opacity <= 0) {
                    opacity = 0.0f;
                    fadeOut = false;
                    canClick = false;
                    answerPool.getToRemove().add(this);
                    answerPool.getInPool()[indexNumber] = false;
                    answerPool.setCanPopulate(true);
                }
                setOpacity(state.getSpriteLibrary(), opacity);
            }
        }
    }

    public void setAnswerPool(AnswerPool answerPool) {
        this.answerPool = answerPool;
    }

    public boolean isStopped() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public PlayerLine getLine() {
        return line;
    }

    public void setLine(PlayerLine line) {
        this.line = line;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean toggleClick) {
        this.clicked = toggleClick;
    }

    @Override
    protected void onClick(State state) {
        clicked = true;
        super.onClick(state);
    }
    
}

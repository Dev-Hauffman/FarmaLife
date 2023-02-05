package state.counter.ui;

import java.util.ArrayList;
import java.util.List;

import core.Position;
import core.Size;
import entity.GameObject;

import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import gfx.ImageUtils;
import gfx.SpriteLibrary;
import state.State;
import state.counter.WorkCounterState;
import text.GameText;
import ui.object.UIObject;

public class SpeechDisplay extends UIObject{
    protected List<GameText> phrase;
    private boolean canLoad;
    private int loadCooldown;
    private int clearCooldown;
    private int index;

    public SpeechDisplay(Position position, State state, int renderOrder){
        super(null, position,state, renderOrder);
        phrase = new ArrayList<>();
        size = new Size(300, 300);
        loadCooldown = 0;
        clearCooldown = 200;
        loadGraphics(state.getSpriteLibrary());
    }

    protected void breakNCentralize(int startIndex, int startHeight) {
        int finalWidth = 0;
        int startWidthPos = size.getWidth()/2 - finalWidth /2;
        int wordCount = 0; //what happens if the first word is already bigger than the initial size?
        int currentHeight = startHeight;
        boolean endedLoop = true;
        for (int i = startIndex; i < phrase.size(); i++) {
            if (i + 1 >= phrase.size()) {
                finalWidth += phrase.get(i).getStringSpriteWidth();
            }else{
                finalWidth += phrase.get(i).getStringSpriteWidth() + phrase.get(i).getFontSize();
            }
            if (finalWidth > size.getWidth()) {
                finalWidth -= phrase.get(i).getStringSpriteWidth() + phrase.get(i).getFontSize(); // remove the last word which overflowed the size
                startWidthPos = (size.getWidth()/2) - (finalWidth /2);
                for (int j = startIndex; j < phrase.size(); j++) { //breakingNCentralizing the current words before moving to the next
                    if (j > startIndex) {
                        startWidthPos += phrase.get(j).getFontSize() * 1 + phrase.get(j - 1).getStringSpriteWidth();                        
                    }
                    phrase.get(j).setPosX(startWidthPos + this.getPosition().getIntX());
                    phrase.get(j).setPosY(currentHeight + this.getPosition().getIntY());
                }
                if (i > 0) {
                    breakNCentralize(i, currentHeight + 16);
                }
                endedLoop = false;
                break;
            }
        }
        if (endedLoop) {
            startWidthPos = (size.getWidth()/2) - (finalWidth /2);
            for (int j = startIndex; j < phrase.size(); j++) {
                if (j > startIndex) {
                    startWidthPos += phrase.get(j).getFontSize() * 1 + phrase.get(j - 1).getStringSpriteWidth();                        
                }
                phrase.get(j).setPosX(startWidthPos + this.getPosition().getIntX());
                phrase.get(j).setPosY(currentHeight + this.getPosition().getIntY());
            }
        }
    }

    public void loadWords(List<String> text) {
        for (int i = 0; i < text.size(); i++) {
            GameText word = new GameText(text.get(i), state, "testFont", new Position(0, 0), 12, renderOrder);
            word.parent(this);
            phrase.add(word);
        }
        checkWordSize();
        breakNCentralize(0, 0);
        canLoad = true;
    }

    protected void checkWordSize() {
        for (GameText gameObject : phrase) {
            if (gameObject.getStringSpriteWidth() + 10 > size.getWidth()) {
                size = new Size(gameObject.getStringSpriteWidth() + 10, size.getHeight());
            }
        }
    }

    @Override
    public void loadGraphics(SpriteLibrary spriteLibrary) {
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(new Size(size.getWidth(), size.getHeight()), ImageUtils.ALPHA_BITMASKED);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.BLUE);
        graphics.drawRect(0, 0, size.getWidth() - 1, size.getHeight() - 1);
        for (GameObject gameText : children) {
            graphics.drawImage(
                gameText.getSprite(),
                gameText.getPosition().getIntX() - gameText.getParent().getPosition().getIntX(),
                gameText.getPosition().getIntY() - gameText.getParent().getPosition().getIntY(),
                null
            );
        }
        graphics.dispose();
        Image result = image;
        sprite = result;
    }

    @Override
    public void update(State state) {
        if (canLoad) {
            if (loadCooldown <= 0) {
                if (index < phrase.size()) {
                    if (!children.contains(phrase.get(index))) {
                        children.add(phrase.get(index));
                        index++;
                        loadGraphics(state.getSpriteLibrary());
                    }
                }else{
                    index = 0;
                    loadCooldown = 0;
                    if (state instanceof WorkCounterState) {
                        WorkCounterState workState = (WorkCounterState)state;
                        workState.getActivePatient().getAction().playAction(workState);
                        ((WorkCounterState)state).getQuickAnswers().generateQuickAnswer(workState);
                        ((WorkCounterState)state).getQuickAnswers().addQuickAnswers(state);
                    }
                    canLoad = false;
                }
                loadCooldown = 20;
            }
            loadCooldown--;
            clearCooldown = 100;
        }else{
            if (!phrase.isEmpty()) {
                if (clearCooldown > 0) {
                    clearCooldown--;
                }else{
                    clearDisplay(state);
                }
            }
        }
    }

    public void clearDisplay(State state){
        phrase = new ArrayList<>();
        children.clear();
        loadGraphics(null);
    }
}

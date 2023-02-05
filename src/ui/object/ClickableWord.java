package ui.object;

import java.util.ArrayList;
import java.util.List;
import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;

import core.Position;
import core.Size;
import gfx.ImageUtils;
import gfx.SpriteLibrary;
import state.State;
import state.counter.WorkCounterState;
import state.mind.ui.PoolItem;
import text.GameText;
import ui.clickable.IClickAction;

public class ClickableWord extends ClickableObject{
    protected List<GameText> words;
    protected IClickAction clickAction;
    protected boolean disappear;
    protected boolean flash;
    protected boolean fadeIn;
    protected boolean fadeOut;
    protected double speed;
    protected float opacity;
    protected boolean canClick;

    public ClickableWord(Position position, int answerNumber, State state, List<String> phrase, float initialOpacity, int renderOrder, IClickAction clickEvent){
        super(null, position, state, renderOrder);
        words = new ArrayList<>();
        size = new Size(100, 100);
        opacity = initialOpacity;
        canClick = true;
        loadWords(phrase, answerNumber);
        checkWordSize();
        breakNCentralize(0, 0);
        defineSize();
        setOpacity(state.getSpriteLibrary(), initialOpacity);
        clickAction = clickEvent;
    }

    private void checkWordSize() {
        for (GameText gameObject : words) {
            if (gameObject.getStringSpriteWidth() + 10 > size.getWidth()) {
                size = new Size(gameObject.getStringSpriteWidth() + 10, size.getHeight());
            }
        }
    }

    private void defineSize() {
        int finalHeight = (words.get(words.size() - 1).getPosition().getIntY() - getPosition().getIntY()) + words.get(words.size() - 1).getStringSpriteHeight();
        int minimalWidth = words.get(0).getPosition().getIntX();
        int maximalWidth = words.get(0).getPosition().getIntX() + words.get(0).getStringSpriteWidth();
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getPosition().getIntX() + words.get(i).getStringSpriteWidth() > maximalWidth ) {
                maximalWidth = words.get(i).getPosition().getIntX() + words.get(i).getStringSpriteWidth();
            }
            if (words.get(i).getPosition().getIntX() < minimalWidth ) {
                minimalWidth = words.get(i).getPosition().getIntX();
            }
        }
        size = new Size((maximalWidth) - position.getIntX(), finalHeight);
    }

    private void breakNCentralize(int startIndex, int startHeight) {
        int finalWidth = 0;
        int startWidthPos = size.getWidth()/2 - finalWidth /2;
        int wordCount = 0; //what happens if the first word is already bigger than the initial size?
        int space = 0;
        int currentHeight = startHeight;
        boolean endedLoop = true;
        for (int i = startIndex; i < words.size(); i++) {
            if (i + 1 >= words.size()) {
                finalWidth += words.get(i).getStringSpriteWidth();
            }else{
                finalWidth += words.get(i).getStringSpriteWidth() + words.get(i).getFontSize();
            }
            if (finalWidth > size.getWidth()) {
                finalWidth -= words.get(i).getStringSpriteWidth() + words.get(i).getFontSize(); // remove the last word which overflowed the size
                startWidthPos = (size.getWidth()/2) - (finalWidth /2);
                for (int j = startIndex; j < words.size(); j++) { //breakingNCentralizing the current words before moving to the next
                    if (j > startIndex) {
                        startWidthPos += words.get(j).getFontSize() * space + words.get(j - 1).getStringSpriteWidth();                        
                    }
                    words.get(j).setPosX(startWidthPos + this.getPosition().getIntX());
                    words.get(j).setPosY(currentHeight + this.getPosition().getIntY());
                    words.get(j).loadGraphics(state.getSpriteLibrary());
                    space++;
                }
                breakNCentralize(i, currentHeight + 12);
                endedLoop = false;
                break;
            }
        }
        if (endedLoop) {
            space = 0;
            startWidthPos = (size.getWidth()/2) - (finalWidth /2);
            for (int j = startIndex; j < words.size(); j++) {
                if (j > startIndex) {
                    startWidthPos += words.get(j).getFontSize() * space + words.get(j - 1).getStringSpriteWidth();                        
                }
                words.get(j).setPosX(startWidthPos + this.getPosition().getIntX());
                words.get(j).setPosY(currentHeight + this.getPosition().getIntY());
                words.get(j).loadGraphics(state.getSpriteLibrary());
                space++;
            }
        }
    }

    private void loadWords(List<String> phrase, int answerNumber) {
        if (answerNumber >= 0) {
            GameText numberPosition = new GameText((answerNumber + 1) + ".", state, "testFont", new Position(0, 0), 10, renderOrder);
            numberPosition.parent(this);
            words.add(numberPosition);
        }
        for (int i = 0; i < phrase.size(); i++) {
            GameText word = new GameText(phrase.get(i), state, "testFont", new Position(0, 0), 10, renderOrder);
            word.parent(this);
            words.add(word);
        }
    }

    @Override
    public void loadGraphics(SpriteLibrary spriteLibrary) {
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(new Size(size.getWidth(), size.getHeight()), ImageUtils.ALPHA_BITMASKED);
        Graphics2D graphics = image.createGraphics();
        // graphics.setColor(Color.BLUE);
        // graphics.drawRect(0, 0, size.getWidth(), size.getHeight());
        for (GameText gameText : words) {
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

    public void setOpacity(SpriteLibrary spriteLibrary, float opacity) {
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(new Size(size.getWidth(), size.getHeight()), ImageUtils.ALPHA_BLEND);
        Graphics2D graphics = image.createGraphics();
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        for (GameText gameText : words) {
            // if (this instanceof PoolItem) {
            //     System.out.println(gameText.getPosition().getIntX() - gameText.getParent().getPosition().getIntX());
            //     System.out.println(gameText.getPosition().getIntY() - gameText.getParent().getPosition().getIntY());
            // }
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
        super.update(state);
        
    }

    @Override
    protected void onFocus(State state) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void onDrag(State state) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void onClick(State state) {
        if (canClick) {
            clickAction.execute(state);
        }
    }

    public List<GameText> getWords() {
        return words;
    }

    public void disappear(float fadeSpeed){
        speed = fadeSpeed;
        disappear = true;
    }

    public void fadeIn(float fadeSpeed){
        opacity = 0.0f;
        speed = fadeSpeed;
        fadeIn = true;
    }

    public void fadeOut(float fadeSpeed){
        opacity = 1.0f;
        speed = fadeSpeed;
        fadeOut = true;
    }
}

package state.mind.ui;

import java.util.ArrayList;
import java.util.List;
import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import core.Position;
import core.Size;
import entity.GameObject;
import gfx.ImageUtils;
import gfx.SpriteLibrary;
import state.State;
import state.counter.ui.SpeechDisplay;
import text.GameText;
import ui.object.ClickableWord;
import ui.object.UIObject;

public class AnswerDisplay extends SpeechDisplay{
    private List<List<GameText>> history;

    public AnswerDisplay(Position position, Size size, State state, int renderOrder){
        super(position, state, renderOrder);
        this.size = size;
        history = new ArrayList<>();
        phrase = new ArrayList<>();
        loadGraphics(state.getSpriteLibrary());        
    }

    @Override
    public void loadWords(List<String> text) {
        phrase = new ArrayList<>();
        if (!history.isEmpty()) {
            phrase.addAll(history.get(history.size() - 1));
        }
        for (int i = 0; i < text.size(); i++) {
            GameText word = new GameText(text.get(i), state, "testFont", new Position(0, 0), 12, renderOrder);
            word.parent(this);
            phrase.add(word);
        }
        breakNCentralize(0, 0);
        history.add(List.copyOf(phrase));
        for (GameText gameText : List.copyOf(phrase)) {
            children.add(gameText);            
        }
        loadGraphics(state.getSpriteLibrary());
    }

    @Override
    public void update(State state) {
        
    }

    public void removeLastAdded(State state) {
        children = new ArrayList<>();
        if (!history.isEmpty()) {
            history.remove(history.size() - 1);
            if (!history.isEmpty()) {
                children.addAll(history.get(history.size() - 1));
                phrase = history.get(history.size() - 1);
            }else{
                phrase = new ArrayList<>();
            }
        }
        loadGraphics(state.getSpriteLibrary());
    }
}

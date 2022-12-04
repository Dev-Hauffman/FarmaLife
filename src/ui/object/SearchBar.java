package ui.object;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import controller.KeyboardController;
import core.Position;
import entity.StaticObject;
import state.State;
import text.GameText;

public class SearchBar extends ClickableObject{
    private StaticObject cursor;
    private KeyboardController controller;
    private List<String> storedInput;
    private boolean writable;
    private GameText text;

    public SearchBar(String name, Position position, State state, int renderOrder) {
        super(name, position, state, renderOrder);
        writable = false;
        text = new GameText(null, state, "testFont", new Position(0, 0), 16, renderOrder);
        // text.setPosY(this.getPosition().getIntY()/2);
        addChildren(text);
    }

    @Override
    public void update(State state) {
        super.update(state);
        if (!hasFocus && state.getInput().isMouseClicked()) {
            controller = null;
            children.remove(cursor);
            loadGraphics(state.getSpriteLibrary());
            state.getInput().setCanRead(false);
            writable = false;
        }
        if (writable) {
            if (getInputText(List.copyOf(state.getInput().getBufferedKeys())).length() > 0) {
                if (!children.contains(text)) {
                    children.add(text);
                }
                text.setText(getInputText(List.copyOf(state.getInput().getBufferedKeys())));
                cursor.setPosX(getCursorPosition().getIntX());
            }else{
                children.remove(text);
                cursor.setPosX(getCursorPosition().getIntX());
                loadGraphics(state.getSpriteLibrary());
            }
        }
        if (controller != null && controller.isRequestingEnter()) {
            System.out.println("should search for word");
        }
    }

    private String getInputText(List<Integer> list) {
        String text = "";
        for (Integer integer : list) {
            if (integer == KeyEvent.VK_BACK_SPACE) {
                if (!text.isEmpty()) {
                    text = text.substring(0, text.length()-1);
                }
            }else if(integer == KeyEvent.VK_SPACE) {
                text = text + " ";
            }else{
                String character = KeyEvent.getKeyText(integer);
                if (character.length() == 1) {
                    text += character;
                }                    
            }            
        }
        return text;
    }

    @Override
    protected void onFocus(State state) {
        
        
    }

    @Override
    protected void onDrag(State state) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void onClick(State state) {
        controller = new KeyboardController(state.getInput());
        System.out.println("clicked");
        cursor = new StaticObject("cursor", getCursorPosition(), state.getSpriteLibrary(), renderOrder+1);
        state.getInput().setCanRead(true);
        writable = true;
        addChildren(cursor);
    }

    private Position getCursorPosition() {
        if (children.contains(text)) {
            if (text.getCharacterSprites().size() > 0) {
                return new Position(0 + text.getCharacters().size() * text.getSpacing(), 0);
            }
        }
        return new Position(0, 0);
    }
    
}

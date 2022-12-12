package ui.object;
import java.awt.event.KeyEvent;
import java.util.List;

import core.Position;
import entity.StaticObject;
import state.State;
import state.counter.WorkCounterState;
import state.counter.pc.states.SearchBarPCState;
import text.GameText;

public class SearchBar extends ClickableObject{
    private StaticObject cursor;
    private boolean writable;
    private GameText searchBarContent;

    public SearchBar(String name, Position position, State state, int renderOrder) {
        super(name, position, state, renderOrder);
        writable = false;
        searchBarContent = new GameText(null, state, "testFont", new Position(0, 5), 16, renderOrder);        
        addChildren(searchBarContent);
    }

    @Override
    public void update(State state) {
        super.update(state);
        if (!hasFocus && state.getInput().isMouseClicked()) {
            children.remove(cursor);
            loadGraphics(state.getSpriteLibrary());
            state.getInput().setCanRead(false);
            writable = false;
        }
        if (writable) {
            if (getInputText(List.copyOf(state.getInput().getBufferedKeys())).length() > 0) {
                if (!children.contains(searchBarContent)) {
                    children.add(searchBarContent);
                }
                searchBarContent.setText(getInputText(List.copyOf(state.getInput().getBufferedKeys())));
                cursor.setPosX(getCursorPosition().getIntX());
            }else{
                children.remove(searchBarContent);
                cursor.setPosX(getCursorPosition().getIntX());
                searchBarContent.setText("");
                loadGraphics(state.getSpriteLibrary());
            }
        }
    }

    private String getInputText(List<Integer> list) {
        String text = "";
        for (Integer integer : list) {
            if (integer == KeyEvent.VK_BACK_SPACE) {
                if (!text.isEmpty()) {
                    if (text.length() == 1) {
                        text = "";
                    }else{
                        text = text.substring(0, text.length()-1);
                    }
                }
            }else if(integer == KeyEvent.VK_SPACE) {
                if (text != "") {
                    text = text + " ";
                }
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
        if (state instanceof WorkCounterState) {
            if (!(((WorkCounterState)state).getComputer() instanceof SearchBarPCState)) {
                ((WorkCounterState)state).changePCState(new SearchBarPCState(((WorkCounterState)state)));
            }
        }
        cursor = new StaticObject("cursor", getCursorPosition(), state.getSpriteLibrary(), renderOrder+1);
        state.getInput().setCanRead(true);
        writable = true;
        addChildren(cursor);
    }

    private Position getCursorPosition() {
        if (children.contains(searchBarContent)) {
            if (searchBarContent.getCharacterSprites().size() > 0) {
                return new Position(0 + searchBarContent.getCharacters().size() * searchBarContent.getSpacing(), 0);
            }
        }
        return new Position(0, 0);
    }

    public GameText getSearchBarContent() {
        return searchBarContent;
    }

    public boolean isWritable() {
        return writable;
    }

    public StaticObject getCursor() {
        return cursor;
    }
    
}

package state.counter.pc.states;

import core.Position;
import state.State;
import state.counter.WorkCounterState;
import state.counter.pc.PCState;
import text.GameText;
import ui.object.SearchBar;
import ui.object.UIObject;

public class SearchBarPCState extends PCState{

    public SearchBarPCState(State state) {
        super(state);
    }

    @Override
    protected void createComputer(State state) {
        UIObject screen = new UIObject("startcomputerscreen", new Position(1014, 534), state, 5);
        // GameText patientInfo = new GameText("Cliente " + "#" + String.format("%03d", WorkCounterState.patientsCounter), state, "testFont", new Position(40, 34), 16, 7);
        // screen.addChildren(patientInfo);
        UIObject searchBarBackground = new UIObject("searchbarbackground", new Position(36, 60), state, 6);
        screen.addChildren(searchBarBackground);
        SearchBar searchArea = new SearchBar("typingareawhite", new Position(0, 0), state, 6);
        searchArea.setPosX((searchBarBackground.getSprite().getWidth(null)/2)-(searchArea.getSprite().getWidth(null)/2));
        searchArea.setPosY((searchBarBackground.getSprite().getHeight(null)/2)-(searchArea.getSprite().getHeight(null)/2));
        searchBarBackground.addChildren(searchArea);
        objects.add(screen);
    }
    
}

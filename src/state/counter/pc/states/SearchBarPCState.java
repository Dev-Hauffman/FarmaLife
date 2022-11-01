package state.counter.pc.states;

import core.Position;
import state.State;
import state.counter.pc.PCState;
import text.GameText;
import ui.object.UIObject;

public class SearchBarPCState extends PCState{

    public SearchBarPCState(State state) {
        super(state);
    }

    @Override
    protected void createComputer(State state) {
        UIObject screen = new UIObject("startcomputerscreenempty", new Position(1014, 534), state, 5);
        UIObject searchBarBackground = new UIObject("searchbarbackground", new Position(36, 60), state, 6);
        GameText search = new GameText("testing", state, "testFont", new Position(0, 0), 8, 7);
        screen.addChildren(searchBarBackground);
        // screen.addChildren(search);
        objects.add(screen);
    }
    
}

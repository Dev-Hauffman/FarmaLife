package state.mind;

import core.Size;
import game.Game;
import game.settings.GameSettings;
import input.Input;
import state.State;
import state.counter.WorkCounterState;

public class MindState extends State{
    public MindState(Size windowSize, Input input, GameSettings gameSettings, WorkCounterState state){
        super(windowSize, input, gameSettings);
        initializeUI();
    }

    private void initializeUI() {

    }

    @Override
    public void update(Game game) {
        // TODO Auto-generated method stub
        super.update(game);
    }
}

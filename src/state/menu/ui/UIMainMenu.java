package state.menu.ui;

import core.Size;
import state.game.GameState;
import state.game.WorkCounterState;
import state.menu.MenuState;
import ui.Alignment;
import ui.UIText;
import ui.VerticalContainer;
import ui.clickable.UIButton;

public class UIMainMenu extends VerticalContainer{

    public UIMainMenu(Size windowSize) {
        super(windowSize);
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        addUIComponent(new UIText("FarmaLife"));
        addUIComponent(new UIButton("PLAY", (state) -> state.setNextState(new WorkCounterState(windowSize, state.getInput(), state.getGameSettings()))));
        addUIComponent(new UIButton("OPTIONS", (state) -> ((MenuState)state).enterMenu(new UIOptionMenu(windowSize, state.getGameSettings()))));
        addUIComponent(new UIButton("EXIT", (state) -> System.exit(0)));
    }
    
}

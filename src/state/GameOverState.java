package state;

import core.Position;
import core.Size;
import game.settings.GameSettings;
import input.Input;
import state.menu.MenuState;
import text.GameText;
import ui.object.ButtonObject;

public class GameOverState extends State{

    public GameOverState(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize, input, gameSettings);
        GameText message = new GameText("END OF DEMO", this, "testFont", new Position(400, 50), 42, 2);
        ButtonObject sayIt = new ButtonObject("MAIN MENU", new Size(250, 50), this, new Position(550, 650), 2, false, (localState) -> {
            setNextState(new MenuState(windowSize, input, gameSettings));
        });
        gameObjects.add(message);
        gameObjects.add(sayIt);
    }
    
}

package state.game;

import java.awt.Color;

import controller.NPCController;
import controller.PlayerController;
import core.Size;
import entity.NPC;
import entity.Player;
import entity.humanoid.effect.Sick;
import game.settings.GameSettings;
import gamespace.map.GameMap;
import input.Input;
import state.State;
import state.game.ui.UIGameTime;
import ui.HorizontalContainer;
import ui.Spacing;
import ui.UIContainer;
import ui.UIText;
import ui.VerticalContainer;

public class GameState extends State {

    private boolean playing;    

    public GameState(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize, input, gameSettings);
        gameSpace = new GameMap(new Size(15, 10), spriteLibrary);
        playing = true;
        initializeCharacters();
        initializeUI(windowSize);
    }

    private void initializeUI(Size windowSize) {
        // UIContainer container = new VerticalContainer(windowSize);
        // container.setPadding(new Spacing(5));
        // container.setBackgroundColor(new Color(0, 0, 0, 0));
        
        // container.addUIComponent(new UIText("Hello UI World!"));
        // uiContainers.add(container);
        uiContainers.add(new UIGameTime(windowSize));
    }

    private void initializeCharacters() {
        Player player = new Player(new PlayerController(input), spriteLibrary);        
        gameObjects.add(player);
        camera.focusOn(player);

        initializeNPC(50);
    }

    private void initializeNPC(int numberOfNPCs) {
        for (int i = 0; i < numberOfNPCs; i++) {
            NPC npc = new NPC(new NPCController(), spriteLibrary);
            npc.setPosition(gameSpace.getRandomPosition());
            npc.addEffect(new Sick());
            gameObjects.add(npc);
        }
    }    
}

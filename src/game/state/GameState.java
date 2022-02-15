package game.state;

import java.awt.Color;

import controller.NPCController;
import controller.PlayerController;
import core.Size;
import entity.NPC;
import entity.Player;
import entity.effect.Sick;
import input.Input;
import map.GameMap;
import ui.HorizontalContainer;
import ui.Spacing;
import ui.UIContainer;
import ui.UIText;
import ui.VerticalContainer;

public class GameState extends State {

    public GameState(Size windowSize, Input input) {
        super(windowSize, input);        
        gameMap = new GameMap(new Size(20, 20), spriteLibrary);        
        initializeCharacters();
        initializeUI(windowSize);
    }

    private void initializeUI(Size windowSize) {
        UIContainer container = new VerticalContainer(windowSize);
        container.setPadding(new Spacing(5));
        container.setBackgroundColor(new Color(0, 0, 0, 0));
        
        container.addUIComponent(new UIText("Hello UI World!"));
        uiContainers.add(container);
    }

    private void initializeCharacters() {
        Player player = new Player(new PlayerController(input), spriteLibrary);        
        gameObjects.add(player);
        camera.focusOn(player);

        initializeNPC(100);
    }

    private void initializeNPC(int numberOfNPCs) {
        for (int i = 0; i < numberOfNPCs; i++) {
            NPC npc = new NPC(new NPCController(), spriteLibrary);
            npc.setPosition(gameMap.getRandomPosition());
            npc.addEffect(new Sick());
            gameObjects.add(npc);
        }
    }    
}

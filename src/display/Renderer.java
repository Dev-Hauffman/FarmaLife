package display;
import java.awt.*;

import core.Position;
import game.Game;
import gamespace.map.GameMap;
import state.State;
import state.game.GameState;

public class Renderer {
    public void render(State state, Graphics graphics) {
        if (state instanceof GameState) {
            renderMap(state, graphics);            
        }
        renderGameObjects(state, graphics);
        renderUI(state, graphics);
    }

    private void renderUI(State state, Graphics graphics) {
        state.getUiContainers().forEach(uiContainer -> graphics.drawImage(
                uiContainer.getSprite(),
                uiContainer.getRelativePosition().getIntX(),
                uiContainer.getRelativePosition().getIntY(),
                null
            )
        );
    }

    private void renderGameObjects(State state, Graphics graphics) {
        Camera camera = state.getCamera();
        state.getGameObject().stream()
            .filter(gameObject -> camera.isInView(gameObject))
            .forEach(gameObject -> graphics.drawImage(
                gameObject.getSprite(),
                gameObject.getRenderPosition(camera).getIntX(),
                gameObject.getRenderPosition(camera).getIntY(), 
                null
            )
        );        
    }

    private void renderMap(State state, Graphics graphics) {
        GameState gameState = (GameState) state;
        GameMap map = (GameMap) gameState.getGameSpace();
        Camera camera = gameState.getCamera();
        Position start = map.getViewableStartingGridPosition(camera);
        Position end = map.getViewableEndingGridPosition(camera);
        if (end.getIntX() > map.getTiles().length) {
            end.setX(map.getTiles().length);
        }
        if (end.getIntY() > map.getTiles()[0].length) {
            end.setY(map.getTiles()[0].length);
        }
        for (int x = start.getIntX(); x < end.getIntX(); x++) {
            for (int y = start.getIntY(); y < end.getIntY(); y++) {
                graphics.drawImage(
                    map.getTiles()[x][y].getSprite(),
                    x * Game.SPRITE_SIZE - camera.getPosition().getIntX(),
                    y * Game.SPRITE_SIZE - camera.getPosition().getIntY(),
                    null
                );
            }
        }
    }
}

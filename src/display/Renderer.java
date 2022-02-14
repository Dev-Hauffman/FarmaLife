package display;
import java.awt.*;

import core.Position;
import game.Game;
import game.state.State;
import map.GameMap;

public class Renderer {
    public void render(State state, Graphics graphics) {
        renderMap(state, graphics);
        Camera camera = state.getCamera();
        state.getGameObject().stream()
            .filter(gameObject -> camera.isInView(gameObject))
            .forEach(gameObject -> graphics.drawImage(
            gameObject.getSprite(), 
            gameObject.getPosition().intX() - camera.getPosition().intX() - gameObject.getSize().getWidth() / 2,
            gameObject.getPosition().intY() - camera.getPosition().intY() - gameObject.getSize().getHeight() / 2, 
            null
    ));
    }

    private void renderMap(State state, Graphics graphics) {
        GameMap map = state.getGameMap();
        Camera camera = state.getCamera();
        Position start = map.getViewableStartingGridPosition(camera);
        Position end = map.getViewableEndingGridPosition(camera);
        if (end.intX() > state.getGameMap().getTiles().length) {
            end.setX(state.getGameMap().getTiles().length);
        }
        if (end.intY() > state.getGameMap().getTiles()[0].length) {
            end.setY(state.getGameMap().getTiles()[0].length);
        }
        for (int x = start.intX(); x < end.intX(); x++) {
            for (int y = start.intY(); y < end.intY(); y++) {
                graphics.drawImage(
                    map.getTiles()[x][y].getSprite(),
                    x * Game.SPRITE_SIZE - camera.getPosition().intX(),
                    y * Game.SPRITE_SIZE - camera.getPosition().intY(),
                    null
                );
            }
        }
    }
}

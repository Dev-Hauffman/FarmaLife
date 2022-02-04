package display;
import java.awt.*;

import game.Game;
import game.state.State;
import map.Tile;

public class Renderer {
    public void render(State state, Graphics graphics) {
        renderMap(state, graphics);
        state.getGameObject().forEach(gameObject -> graphics.drawImage(
            gameObject.getSprite(), 
            gameObject.getPosition().intX(),
            gameObject.getPosition().intY(), 
            null
    ));
    }

    private void renderMap(State state, Graphics graphics) {
        Tile[][] tiles = state.getGameMap().getTiles();
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                graphics.drawImage(
                    tiles[x][y].getSprite(),
                    x * Game.SPRITE_SIZE,
                    y * Game.SPRITE_SIZE,
                    null
                );
            }
        }
    }
}

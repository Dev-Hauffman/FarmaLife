package display;
import java.awt.*;
import game.Game;

public class Renderer {
    public void render(Game game, Graphics graphics) {
        game.getGameObject().forEach(gameObject -> graphics.drawImage(
            gameObject.getSprite(), 
            gameObject.getPosition().intX(),
            gameObject.getPosition().intY(), 
            null
    ));
    }
}

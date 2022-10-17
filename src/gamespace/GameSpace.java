package gamespace;

import core.Position;
import core.Size;

public class GameSpace implements IGameSpace{

    private Size size;

    public GameSpace(Size screenSize) {
        this.size = screenSize;
    }

    @Override
    public int getWidth() {
        return size.getWidth();
    }

    @Override
    public int getHeight() {
        return size.getHeight();
    }

    @Override
    public Position getRandomPosition() {
        double x = Math.random() * size.getWidth();
        double y = Math.random() * size.getHeight();
        
        return new Position(x, y);
    }

}

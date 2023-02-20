import game.Game;
import game.GameLoop;

public class Launcher {
    
    public static void main(String[] args) {
        // new Thread(new GameLoop(new Game(1600, 900))).start();
        new Thread(new GameLoop(new Game(1280, 720))).start();
    }
}

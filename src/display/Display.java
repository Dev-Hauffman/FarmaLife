/*
* Customize the jframe display options
* JFrame is the one who actually listen to key inputs, so when you press a key it has to be passed to it
*/

package display;

import javax.swing.JFrame;
import game.Game;
import input.Input;
import java.awt.*;
import java.awt.image.BufferStrategy;
import input.Input;

public class Display extends JFrame{
    private Canvas canvas;
    private Renderer renderer;

    public Display(int width, int height, Input input){
        setTitle("PharmaLife");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        this.renderer = new Renderer();

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width,height));
        canvas.setFocusable(false);
        add(canvas);
        addKeyListener(input);
        pack();

        canvas.createBufferStrategy(3);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void update(){

    }

    public void render(Game game) {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, canvas.getWidth(),canvas.getHeight());

        renderer.render(game, graphics);

        graphics.dispose();
        bufferStrategy.show();
    }
}
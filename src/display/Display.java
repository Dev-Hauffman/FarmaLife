/*
* Customize the jframe display options
* JFrame is the one who actually listen to key inputs, so when you press a key it has to be passed to it
*/

package display;

import javax.swing.JFrame;

import input.Input;
import state.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Display extends JFrame{
    private Canvas canvas;
    private Renderer renderer;
    private DebugRenderer debugRenderer;

    public Display(int width, int height, Input input){
        setTitle("PharmaLife");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        this.renderer = new Renderer();
        this.debugRenderer = new DebugRenderer();

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width,height));
        canvas.setFocusable(false);
        canvas.addMouseListener(input);
        canvas.addMouseMotionListener(input);
        add(canvas);
        addKeyListener(input);
        pack();

        canvas.createBufferStrategy(2);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void update(){}

    public void render(State state, boolean debugMode) {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, canvas.getWidth(),canvas.getHeight());

        renderer.render(state, graphics);
        if (debugMode) {
            debugRenderer.render(state, graphics);
        }

        graphics.dispose();
        bufferStrategy.show();
    }
}
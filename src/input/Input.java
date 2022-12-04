package input;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import core.Position;

public class Input implements KeyListener, MouseListener, MouseMotionListener {

    private Position mousePosition;
    private boolean mouseClicked; // register if the mouse button was clicked once
    private boolean mousePressed; // register if the mouse button is currently hold down

    private boolean[] pressed;
    private boolean[] toggled;
    private boolean canRead;
    private List<Integer> bufferedKeys;

    public Input() {
        toggled = new boolean[525];
        pressed = new boolean[525];
        bufferedKeys = new ArrayList<>();
        canRead = false;
        mousePosition = new Position(-1, -1);
    }
    /*
    * This methods makes the game check only once for input, if you press the key once to call a method on a single update, it'll call
    * the method, next update, if the key is still pressed in a split second, it won't call the method again
    * (or end up calling twice a method, causing a boolean which toggles a value to switch on and immediatly off the next second).
    */
    public boolean isToggled(int keyCode) {
        if (!toggled[keyCode] && pressed[keyCode]) {
            toggled[keyCode] = true;
            return true;
        }
        return false;
    }

    /*
    * This methods will call another method continuously until you let go of the key
    */
    public boolean isPressed(int keyCode) {
        return pressed[keyCode];
    }

    public void clearMouseClick() {
        mouseClicked = false;
    }

    public Position getMousePosition() {
        return mousePosition;
    }

    public boolean isMouseClicked() {
        return mouseClicked;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        pressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed[e.getKeyCode()] = false;
        toggled[e.getKeyCode()] = false;
        captureKey(e);
    }

    private void captureKey(KeyEvent e) {
        if (canRead) {
            bufferedKeys.add(e.getKeyCode());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseClicked = true;
        mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition = new Position(e.getPoint().getX(), e.getPoint().getY());
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition = new Position(e.getPoint().getX(), e.getPoint().getY());
    }

    public void clearBufferedKeys(){
        bufferedKeys.clear();
    }

    public void setCanRead(boolean value){
        canRead = value;
    }
    public List<Integer> getBufferedKeys() {
        return bufferedKeys;
    }    
}

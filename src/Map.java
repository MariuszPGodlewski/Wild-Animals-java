import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Map extends JPanel implements KeyListener {
    private int keyCode = 0;

    public Map() {
        //setPreferredSize(new Dimension(400, 300));
        //setBackground(Color.WHITE);
        setFocusable(true); // Ensure panel can receive key events
        addKeyListener(this); // Add the key listener to the panel
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyCode = e.getKeyCode();
    }
    public int GetKey(){
        return keyCode;
    }
    public void ResetKey(){
        keyCode = 0;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used in this example
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used in this example
    }

}

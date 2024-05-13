import javax.swing.*;
import java.awt.event.*;

// Class implementing KeyListener
class MyFrame extends JFrame implements KeyListener {
    private JLabel label;
    private String lastKeyPressed;

    MyFrame() {
        /*label = new JLabel("Press a letter key...");
        add(label);
        addKeyListener(this);
        setSize(300, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
    }

    // KeyListener methods
    public void keyPressed(KeyEvent e) {
        if (Character.isLetter(e.getKeyChar())) {
            lastKeyPressed = KeyEvent.getKeyText(e.getKeyCode());
            //label.setText("Letter Key Pressed: " + lastKeyPressed);
        }
    }

    public void keyReleased(KeyEvent e) {
        // Not needed for this example
    }

    public void keyTyped(KeyEvent e) {
        // Not needed for this example
    }

    // Method to get the last pressed key
    public String getLastKeyPressed() {
        return lastKeyPressed;
    }
}
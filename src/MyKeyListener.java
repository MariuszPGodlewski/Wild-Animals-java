import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.awt.*;

class MyKeyListener implements KeyListener {
    private HexagonalGrid hexagonalGrid;

    public MyKeyListener(HexagonalGrid hexagonalGrid) {
        this.hexagonalGrid = hexagonalGrid;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // You can handle key events here
        // For example:
        // if (keyCode == KeyEvent.VK_SPACE) {
        //     // Do something
        // }
        hexagonalGrid.setKeyCode(keyCode);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }
}

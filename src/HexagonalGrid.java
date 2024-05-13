import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class HexagonalGrid {
    private final int board_size = 20;
    private Cell[][] cells;
    //private KeyListenerExample content;
    private JPanel content;
    private World game;
    private int cellSize = 12;
    private int offsetX = 10;
    private int offsetY = 150;
    private boolean addAnimalMode = false; // Flag to indicate whether adding animal mode is active
    private CellClickListener cellClickListener; // Reference to the mouse listener
    private String organims_to_add;
    private MainWindow mainWindow;
    private String messages;
    int keyCode = 0;

    public HexagonalGrid(World game, MainWindow main) {
        this.game = game;
        this.mainWindow = main;
        cells = new Cell[board_size][board_size];
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                cells[i][j] = new Cell();
            }
        }
        // Set up content pane
        content = new JPanel() {
        //content = new KeyListenerExample(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
  
                for (int i = 0; i < board_size; i++) {
                    for (int j = 0; j < board_size; j++) {
                        int x = offsetX + (int) (1.5 * cellSize * j) + (int) (1.5 * cellSize * (board_size - i));
                        int y = offsetY + (int) (Math.sqrt(3) * cellSize * (i + j / 2.0)) - (int)((Math.sqrt(3) * cellSize / 2.0) * i);
                        cells[i][j].draw(g2d, x, y, cellSize);
                    }
                }
                //print organisms into the board
                for(int i = 0; i < game.GetSize(); i++){
                    int organism_x = game.GetOrganism(i).Get_X();
                    int organism_y = game.GetOrganism(i).Get_Y();
                    int x = offsetX + (int) (1.5 * cellSize * organism_x) + (int) (1.5 * cellSize * (board_size - organism_y));
                    int y = offsetY + (int) (Math.sqrt(3) * cellSize * (organism_y + organism_x / 2.0)) - (int)((Math.sqrt(3) * cellSize / 2.0) * organism_y);
                      
                    cells[organism_y][organism_x].drawAnimal(game.GetOrganism(i).GetSymbol(), g2d, x, y, cellSize);
                }
            }
        };
        //content.addKeyListener(this);
        content.setPreferredSize(new Dimension(1300, 800));
        cellClickListener = new CellClickListener(); // Initialize the mouse listener
        content.addMouseListener(cellClickListener); // Add the mouse listener to the content panel
        //i would need to add massage box on the whole east pannel 

        // Add "Next Turn" button
        JButton nextTurnButton = new JButton("Next Turn");
       
        nextTurnButton.addActionListener(e -> {
            //messages = "       b    ";
            //JOptionPane.showMessageDialog(content, "Next turn button");
            addAnimalMode = false; // Turn off add animal mode
            toggleMouseListener(); // Toggle the mouse listener
           
            game.MakeTurn(keyCode);
            messages = game.GetMessages();
            updateMessageBox(messages);
            content.repaint();
        });
        content.add(nextTurnButton, BorderLayout.NORTH);

        // Add "Add Animal" button with pop-up options
        JButton addAnimalButton = new JButton("Add Organism");
        addAnimalButton.addActionListener(e -> {
            // Move the list of animals to the main
            String selected_option = (String) JOptionPane.showInputDialog(content, "Choose an animal:", "Add Animal",
            JOptionPane.QUESTION_MESSAGE, null, game.GetListOfOrganisms(), game.GetListOfOrganisms()[0]);
            if (selected_option != null) {
                //JOptionPane.showMessageDialog(content, "Selected option: " + selected_option);
                organims_to_add = selected_option;
                //Later add swich to know wich animal to be placed
                addAnimalMode = true; // Turn on add animal mode
                toggleMouseListener(); // Toggle the mouse listener
            }
        });
        content.add(addAnimalButton, BorderLayout.NORTH);

        JButton saveGame = new JButton("Save Game");
        saveGame.addActionListener(e -> {
            JOptionPane.showMessageDialog(content, "Game has been saved");
            addAnimalMode = false; // Turn off add animal mode
            toggleMouseListener(); // Toggle the mouse listener
            game.SaveGame();
            content.repaint();
        });
        content.add(saveGame, BorderLayout.NORTH);
    }

    // Inner class representing each hexagonal cell
    private class Cell {

        public void reveal() {
            content.repaint();
        }

        public void draw(Graphics2D g2d, int x, int y, int size) {
            int[] xPoints = {x, x + size, x + 3 * size / 2, x + size, x, x - size / 2};
            int[] yPoints = {y, y, y + (int) (Math.sqrt(3) * size / 2), y + (int) (Math.sqrt(3) * size), y + (int) (Math.sqrt(3) * size), y + (int) (Math.sqrt(3) * size / 2)};
            Polygon hexagon = new Polygon(xPoints, yPoints, 6);

            g2d.setColor(Color.WHITE);
            g2d.fill(hexagon);

            g2d.setColor(Color.BLACK);
            g2d.draw(hexagon);
        }

        public void drawAnimal(String symbol, Graphics2D g2d, int x, int y, int size) {
            int[] xPoints = {x, x + size, x + 3 * size / 2, x + size, x, x - size / 2};
            int[] yPoints = {y, y, y + (int) (Math.sqrt(3) * size / 2), y + (int) (Math.sqrt(3) * size), y + (int) (Math.sqrt(3) * size), y + (int) (Math.sqrt(3) * size / 2)};
            Polygon hexagon = new Polygon(xPoints, yPoints, 6);

            // Set the font to bold
            FontMetrics fm = g2d.getFontMetrics();
            Font originalFont = g2d.getFont();
            Font boldFont = new Font(originalFont.getName(), Font.BOLD, originalFont.getSize());
            g2d.setFont(boldFont);

            // Later change it to color by animal
            g2d.setColor(Color.YELLOW);
            g2d.fill(hexagon);
            g2d.setColor(Color.BLACK);
            int textWidth = fm.stringWidth(symbol);
            g2d.drawString(symbol, x + (size - textWidth) / 2, y + size / 2 + fm.getAscent());

            // Reset the font
            g2d.setFont(originalFont);
        }
    }

    public void updateMessageBox(String newMessage) {
        // Update the message box in the MainWindow
        mainWindow.updateMessageBox(newMessage);
    }

    public JPanel getContentPanel() {
        return content;
    }

    // Inner class to handle cell click events
    private class CellClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (addAnimalMode) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                for (int i = 0; i < board_size; i++) {
                    for (int j = 0; j < board_size; j++) {
                        int x = offsetX + (int) (1.5 * cellSize * j) + (int) (1.5 * cellSize * (board_size - i));
                        int y = offsetY + (int) (Math.sqrt(3) * cellSize * (i + j / 2.0)) - (int) ((Math.sqrt(3) * cellSize / 2.0) * i);
                        if (isInsideHexagon(mouseX, mouseY, x, y, cellSize)) {
                            //Later out sorece the if to diffrent function calld Add animal switch
                            mainWindow.AddOrganismToWorld(game, organims_to_add, j, i);
                            cells[i][j].reveal();
                            return;
                        }
                    }
                }
            }
        }

        private boolean isInsideHexagon(int x, int y, int hexagonX, int hexagonY, int size) {
            int[] xPoints = {hexagonX, hexagonX + size, hexagonX + 3 * size / 2, hexagonX + size, hexagonX, hexagonX - size / 2};
            int[] yPoints = {hexagonY, hexagonY, hexagonY + (int) (Math.sqrt(3) * size / 2), hexagonY + (int) (Math.sqrt(3) * size), hexagonY + (int) (Math.sqrt(3) * size), hexagonY + (int) (Math.sqrt(3) * size / 2)};
            Polygon hexagon = new Polygon(xPoints, yPoints, 6);

            return hexagon.contains(x, y);
        }
    }

    // Method to toggle the mouse listener on and off
    private void toggleMouseListener() {
        if (addAnimalMode) {
            content.addMouseListener(cellClickListener);
        } else {
            content.removeMouseListener(cellClickListener);
        }
    }
}

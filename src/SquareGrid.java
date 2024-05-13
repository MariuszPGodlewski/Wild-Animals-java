import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class SquareGrid {
    private final int boardSize = 20;
    private Cell[][] cells;
    private JPanel content;
    private World game;
    private int cellSize = 30;
    private int offsetX = 50;
    private int offsetY = 50;
    private boolean addAnimalMode = false; // Flag to indicate whether adding animal mode is active
    private CellClickListener cellClickListener; // Reference to the mouse listener
    private String organismToAdd;
    private MainWindow mainWindow;
    private String messages;

    public SquareGrid(World game, MainWindow main) {
        this.game = game;
        this.mainWindow = main;
        // Create a square grid of cells
        cells = new Cell[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                cells[i][j] = new Cell();
            }
        }

        // Set up content pane
        content = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                for (int i = 0; i < boardSize; i++) {
                    for (int j = 0; j < boardSize; j++) {
                        int x = offsetX + j * cellSize;
                        int y = offsetY + i * cellSize;
                        cells[i][j].draw(g2d, x, y, cellSize);
                    }
                }
                // Print organisms onto the board
                for (int i = 0; i < game.GetSize(); i++) {
                    int organismX = game.GetOrganism(i).Get_X();
                    int organismY = game.GetOrganism(i).Get_Y();
                    int x = offsetX + organismX * cellSize;
                    int y = offsetY + organismY * cellSize;

                    cells[organismY][organismX].drawAnimal(game.GetOrganism(i).GetSymbol(), g2d, x, y, cellSize);
                }
            }
        };

        content.setPreferredSize(new Dimension(1300, 800));
        cellClickListener = new CellClickListener(); // Initialize the mouse listener
        content.addMouseListener(cellClickListener); // Add the mouse listener to the content panel

        // Add "Next Turn" button
        JButton nextTurnButton = new JButton("Next Turn");
        nextTurnButton.addActionListener(e -> {
            //JOptionPane.showMessageDialog(content, "Next turn button");
            addAnimalMode = false; // Turn off add animal mode
            toggleMouseListener(); // Toggle the mouse listener
            game.MakeTurn(1);
            messages = game.GetMessages();
            updateMessageBox(messages);
            content.repaint();
        });
        content.add(nextTurnButton, BorderLayout.NORTH);

        // Add "Add Animal" button with pop-up options
        JButton addAnimalButton = new JButton("Add Organism");
        addAnimalButton.addActionListener(e -> {
            
            String selectedOption = (String) JOptionPane.showInputDialog(content, "Choose an animal:", "Add Animal",
            JOptionPane.QUESTION_MESSAGE, null, game.GetListOfOrganisms(), game.GetListOfOrganisms()[0]);
            if (selectedOption != null) {
                //JOptionPane.showMessageDialog(content, "Selected option: " + selectedOption);
                organismToAdd = selectedOption;
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

    // Inner class representing each square cell
    private class Cell {

        public void reveal() {
            content.repaint();
        }

        public void draw(Graphics2D g2d, int x, int y, int size) {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(x, y, size, size);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, size, size);
        }

        public void drawAnimal(String symbol, Graphics2D g2d, int x, int y, int size) {
            // Set the font to bold
            FontMetrics fm = g2d.getFontMetrics();
            Font originalFont = g2d.getFont();
            Font boldFont = new Font(originalFont.getName(), Font.BOLD, originalFont.getSize());
            g2d.setFont(boldFont);

            g2d.setColor(Color.YELLOW);
            g2d.fillRect(x, y, size, size);
            g2d.setColor(Color.BLACK);
            int textWidth = fm.stringWidth(symbol);
            g2d.drawString(symbol, x + (size - textWidth) / 2, y + size / 2 + fm.getAscent() - fm.getHeight()/2);

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
                int cellX = (mouseX - offsetX) / cellSize;
                int cellY = (mouseY - offsetY) / cellSize;
                if (cellX >= 0 && cellX < boardSize && cellY >= 0 && cellY < boardSize) {
                    mainWindow.AddOrganismToWorld(game, organismToAdd, cellX, cellY);
                    cells[cellY][cellX].reveal();
                }
            }
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

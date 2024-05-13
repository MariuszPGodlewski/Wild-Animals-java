import javax.swing.*;
import java.awt.*;

public class MainWindow {

    private JFrame window;
    //private MyFrame window;
    JTextArea messageText = new JTextArea("");
    public MainWindow(World game){
        initialize(game);
    }

    private void initialize(World game){
        window = new JFrame();
        //window = new MyFrame();
        
        game.SetFrame(window);
        window.setTitle("Mariusz Godlewski");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(1000,    800);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(10);
        borderLayout.setVgap(10);
        window.setLayout(borderLayout);
        
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> {
            
            Human stefan = new Human(game, 17, 6, 6);
            game.AddOrganism(stefan);
    
            Wolf wolf1 = new Wolf(game, 3 ,1, 1);
            Wolf wolf2 = new Wolf(game, 3 ,19, 1);
            game.AddOrganism(wolf1);
            //game.AddOrganism(wolf2);
    
            Sheep sheep1 = new Sheep(game, 4, 0, 0);
            Sheep sheep2 = new Sheep(game, 7, 4, 3);
            game.AddOrganism(sheep1);
            //game.AddOrganism(sheep2);
    
            Fox fox1 = new Fox(game, 0, 14, 5);
            game.AddOrganism(fox1);
    
            Turtle turtle1 = new Turtle(game, 16, 4, 16);
            game.AddOrganism(turtle1);
    
            Antelope antelope1 = new Antelope(game, 0, 9, 4);
            game.AddOrganism(antelope1);
    
            Cyber_sheep cyber_sheep = new Cyber_sheep(game, 0, 6, 8);
            game.AddOrganism(cyber_sheep);

            Grass grass1 = new Grass(game, 0, 11, 11);
            game.AddOrganism(grass1);
    
            Thistle thistle1 = new Thistle(game, 0, 17, 6);
            game.AddOrganism(thistle1);
    
            Guarana guarana1 = new Guarana(game, 6, 6, 19);
            game.AddOrganism(guarana1);
    
            Belladonna belladonna1 = new Belladonna(game, 5, 12, 13);
            game.AddOrganism(belladonna1);
    
            Sosnowsky_hogweed sosnowsky_hogweed1 = new Sosnowsky_hogweed(game, 11, 12, 11);
            game.AddOrganism(sosnowsky_hogweed1);
    


            //JOptionPane.showMessageDialog(window, "Starting a new game...");
            replaceButtons(centerPanel, game);
        });
        
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> {
            // Add functionality for load button here
            JOptionPane.showMessageDialog(window, "Loading game...");
            LoadGame(centerPanel, game);
        });
        
        // Add message box panel to the east
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout()); // Border layout for the message panel
        
        messagePanel.setPreferredSize(new Dimension(200, 0));
        // Text area for multiline text
        
        messageText.setEditable(false); // Make it read-only
        JScrollPane scrollPane = new JScrollPane(messageText); // Add scroll bars if needed
        messagePanel.add(scrollPane, BorderLayout.CENTER);
        
        window.add(messagePanel, BorderLayout.EAST);
        centerPanel.add(newGameButton);
        centerPanel.add(loadButton);
        
        window.add(new JButton("Mariusz Godlewski 197816 "), BorderLayout.NORTH);
       // window.add(new JLabel("Mariusz Godlewski 197816 "), BorderLayout.EAST);
        window.add(centerPanel, BorderLayout.CENTER);
    }

    private void replaceButtons(JPanel centerPanel, World game) {
        centerPanel.removeAll(); 
        
        // Add square button
        JButton squareButton = new JButton("Square");
        squareButton.addActionListener(e -> {
            game.SetIsGameHex(false);
            PrintSquareBoard(centerPanel, game);
        });
        
        // Add hexagonal button
        JButton hexagonalButton = new JButton("Hexagonal");
        hexagonalButton.addActionListener(e -> {
            game.SetIsGameHex(true);
            PrintHexBoard(centerPanel, game);
        });
        
        centerPanel.add(squareButton);
        centerPanel.add(hexagonalButton);

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void PrintHexBoard(JPanel centerPanel, World game){
        centerPanel.removeAll();
        HexagonalGrid hex_board = new HexagonalGrid(game, this);
        centerPanel.add(hex_board.getContentPanel());
        centerPanel.revalidate();
        centerPanel.repaint();
    }
    private void PrintSquareBoard(JPanel centerPanel, World game){
        centerPanel.removeAll();
        SquareGrid square_board = new SquareGrid(game, this);
        centerPanel.add(square_board.getContentPanel());
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public void LoadGame(JPanel centerPanel, World game){
        game.LoadSave();
        if(!game.IsGameHex()){
            PrintSquareBoard(centerPanel, game);
        }
        else{
            PrintHexBoard(centerPanel, game);
        }
    }

    public void updateMessageBox(String newMessage) {
        // Access the JTextArea and update its text
        messageText.setText(newMessage);
    }

    public void show(){
        window.setVisible(true);
    }

    public void AddOrganismToWorld(World game, String organims_to_add,int x ,int y){
        //System.out.println("Last pressed key: " + myFrame.getLastKeyPressed());
        if(!game.Collide(0, false, x, y)){
            if(organims_to_add == "Wolf"){
                Wolf wolf = new Wolf(game, 1, x, y);
                game.AddOrganism(wolf);
            }
            else if(organims_to_add == "Sheep"){
                Sheep sheep = new Sheep(game, 1, x, y);
                game.AddOrganism(sheep);
            }
            else if(organims_to_add == "Fox"){
                Fox fox = new Fox(game, 1, x, y);
                game.AddOrganism(fox);
            }
            else if(organims_to_add == "Turtle"){
                Turtle turtle = new Turtle(game, 1, x, y);
                game.AddOrganism(turtle);
            }
            else if(organims_to_add == "Antelope"){
                Antelope antelope = new Antelope(game, 1, x, y);
                game.AddOrganism(antelope);
            }
            else if(organims_to_add == "Cyber_sheep"){
                Cyber_sheep cyber_sheep = new Cyber_sheep(game, 1, x, y);
                game.AddOrganism(cyber_sheep);
            }
            else if(organims_to_add == "Grass"){
                Grass grass = new Grass(game, 1, x, y);
                game.AddOrganism(grass);
            }
            else if(organims_to_add == "Thistle"){
                Thistle thistle = new Thistle(game, 1, x, y);
                game.AddOrganism(thistle);
            }
            else if(organims_to_add == "Guarana"){
                Guarana guarana = new Guarana(game, 1, x, y);
                game.AddOrganism(guarana);
            }
            else if(organims_to_add == "Belladonna"){
                Belladonna belladonna = new Belladonna(game, 1, x, y);
                game.AddOrganism(belladonna);
            }
            else if(organims_to_add == "Sosnowsky_hogweed"){
                Sosnowsky_hogweed sosnowsky_hogweed = new Sosnowsky_hogweed(game, 1, x, y);
                game.AddOrganism(sosnowsky_hogweed);
            }
        }
    }
    

}
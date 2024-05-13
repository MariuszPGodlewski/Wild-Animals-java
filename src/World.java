import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class World {
    private final int BOARD_SIZE = 20;
    private final int UP = -1;
    private final int DOWN = 1;
    private final int LEFT = -1;
    private final int RIGHT = 1;
    private final int MAX_NR_OF_ORGANISMS = 400;
    private int turn_nr = 0;
    private Organism[] organisms = new Organism[MAX_NR_OF_ORGANISMS];
    private int size = 0;
    private int key = 0;
    private boolean hex_board = false;
    private int size_at_beginning = 0;
    private String messages = "                    ";
    private JFrame frame;
    private String[] listOfOrganisms = {"Wolf", "Sheep", "Fox", "Turtle" , "Antelope", "Cyber_sheep", "Grass", "Thistle", "Guarana", "Belladonna", "Sosnowsky_hogweed"};
    public World() {
        System.out.println("World created");
    }
    public String[] GetListOfOrganisms(){
        return listOfOrganisms;
    }


    public void SetFrame(JFrame frame){
        this.frame = frame;
    }
    public JFrame GetFrame(){
        return frame;
    }
    public void AddMessage(String message){
        messages += message + "\n";
    }

    public String GetMessages(){
        return messages; 
    }

    public boolean LegalField(int x, int y){
        if(x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE)
            return true;
        return false;
    }

    public void SearchForFreeSpace(int[] xy,int x, int y, boolean sawing){
        xy[0] = -1;
        xy[1] = -1;
        if(LegalField(x + LEFT, y) && !Collide(0,false, x + LEFT, y)){
            xy[0] = x + LEFT;
            xy[1] = y;
        }
        else if(LegalField(x + RIGHT, y) && !Collide(0,false, x + RIGHT, y)){
            xy[0] = x + RIGHT;
            xy[1] = y;
        }
        else if(LegalField(x , y + UP) && !Collide(0,false, x, y + UP)){
            xy[0] = x;
            xy[1] = y + UP;
        }
        else if(LegalField(x , y + DOWN) && !Collide(0,false, x, y + DOWN)){
            xy[0] = x;
            xy[1] = y + DOWN;
        }
        else if((!sawing || hex_board ) && LegalField(x + LEFT, y + UP) && !Collide(0,false, x + LEFT, y + UP)){
            xy[0] = x + LEFT;
            xy[1] = y + UP;
        }
        else if((!sawing || hex_board ) && LegalField(x + RIGHT , y + DOWN) && !Collide(0,false, x + RIGHT, y + DOWN)){
            xy[0] = x + RIGHT;
            xy[1] = y + DOWN;
        }
        else if(!sawing && !hex_board && LegalField(x + RIGHT , y + UP) && !Collide(0,false, x + RIGHT, y + UP)){
            xy[0] = x + RIGHT;
            xy[1] = y + UP;
        }
        else if(!sawing && !hex_board && LegalField(x + LEFT, y + DOWN) && !Collide(0, false, x + LEFT, y + DOWN)){
            xy[0] = x + LEFT;
            xy[1] = y + DOWN;
        }
    }

    public void AreThereTwoSpieciesINTheSameField(){
        boolean flag = false;
        for(int i = 0; i < size; i++){
            for(int j = 0 ; j < size; j++){
                if(i != j && organisms[i].Get_X() == organisms[j].Get_X() && organisms[i].Get_Y() == organisms[j].Get_Y()){
                    flag = true;
                }
            }
            if(organisms[i].Get_X() < 0 || organisms[i].Get_X() > BOARD_SIZE - 1 ||organisms[i].Get_Y() < 0 || organisms[i].Get_Y() > BOARD_SIZE - 1  ){
                flag = true;
            }
        }
        if (flag) {
            System.out.println("There are two species in the same field");
        }
    }
    
    public boolean Collide(int currnet_speice, boolean act_if_collide, int x, int y){
        for(int i = 0 ; i < size; i++){
            if(organisms[i].Get_X() == x && organisms[i].Get_Y() == y){
                if (act_if_collide) {
                    return organisms[currnet_speice].Collison(currnet_speice, i);
                }
                else
                return true;
            }
        }
        return false;
    }
    public void SortOrganism(){
        // Using Bubble  for simplicity, you can replace it with more efficient algorithms like QuickSort or MergeSort
        for (int i = 0; i < size - 1; ++i) {
            for (int j = 0; j < size - i - 1; ++j) {
                // Compare by initiative first
                if (organisms[j].GetInitiative() != organisms[j + 1].GetInitiative()) {
                    // Sort by initiative in descending order
                    if (organisms[j].GetInitiative() < organisms[j + 1].GetInitiative()) {
                        Organism temp = organisms[j];
                        organisms[j] = organisms[j + 1];
                        organisms[j + 1] = temp;
                    }
                }
                else { // If initiative is equal, compare by age
                    // Sort by age in descending order
                    if (organisms[j].GetAge() < organisms[j + 1].GetAge()) {
                        Organism temp = organisms[j];
                        organisms[j] = organisms[j + 1];
                        organisms[j + 1] = temp;
                    }
                }
            }
        }
    }

    public void MakeTurn(int key){
        this.key = key;      
        turn_nr++;
        messages = " ";
        AddMessage("Turn nr: " + turn_nr);
        System.out.println("Turn nr: " + turn_nr);
        System.out.println("Size: " + size);
        AreThereTwoSpieciesINTheSameField();
        size_at_beginning = size;
        SortOrganism();
        for (int i = 0; i < size_at_beginning; i++){
            organisms[i].AgeIncresse();
            organisms[i].SetMoved(false);
        }

        for(int i = 0;i < size_at_beginning; i++){
            while(!organisms[i].GetMoved()){
                organisms[i].Action(i);
            }
        }
        SortOrganism();
    }
    public void RemoveOrganism(int id){
        organisms[id] = null;
        for (int i = id; i < size - 1; i++) {
            organisms[i] = organisms[i + 1];
        }
        size--;
        if(id < size_at_beginning){
            size_at_beginning--;
        }
    }


    public void AddOrganism(Organism newOrganism) {
        if (size < MAX_NR_OF_ORGANISMS) {
            organisms[size] = newOrganism;
            System.out.println("Organism added :" + organisms[size].GetSymbol() + " ( " + organisms[size].Get_Y() + " " + organisms[size].Get_X() + " )");
            size++;
        } else {
            System.out.println("The world is full. Cannot add more organisms.");
        }
    }

    public int GetSize(){
        return size;
    }

    public boolean IsGameHex(){
        return hex_board;
    }

    public void SetIsGameHex(boolean is_hex){
        hex_board = is_hex;
    }

    public Organism GetOrganism(int index){
        return organisms[index];
    }

    public void SaveGame(){
        String filename = "Wield_animals_save.txt";
        
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile(); // Create the file if it doesn't exist
            }
            
            FileWriter writer = new FileWriter(file);
            writer.write(hex_board + "\n");
            writer.write(size + "\n");
            for(int i = 0; i < size; i++){
                Organism current = organisms[i];
                 writer.write(
                 current.GetSymbol() + " " +
                 current.GetInitiative() + " " +
                 current.GetAge() + " " +
                 current.GetStrength() + " " +
                 current.Get_X() + " " +
                 current.Get_Y()
                 );
                 if(current.GetSymbol() != "H"){
                    writer.write("\n");
                 }
                 else {
                    int[] abilities = new int[2];
                    current.GetAbilities(abilities);
                    writer.write(" " + abilities[0] + " " + abilities[1] + "\n");
                 }
            }
            writer.close();
            System.out.println("Data has been written to " + filename);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void LoadSave(){
        String filename = "Wield_animals_save.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            String symbol;
            int intiative;
            int age;
            int strength;
            int X;
            int Y;
            int[] abilities = new int[2];

            String line = reader.readLine();
            String[] parts = line.split(" ");
            hex_board = parts[0].equalsIgnoreCase("true");
            System.out.println(line);

            line = reader.readLine();
            parts = line.split(" ");
            int save_size = Integer.parseInt(parts[0]);
            System.out.println(line); // Print each line to the console

            for(int i = 0; i < save_size; i++){
                line = reader.readLine();
                System.out.println(line); 
                parts = line.split(" ");
                symbol = parts[0];
                intiative = Integer.parseInt(parts[1]);
                age = Integer.parseInt(parts[2]);
                strength = Integer.parseInt(parts[3]);
                X = Integer.parseInt(parts[4]);
                Y = Integer.parseInt(parts[5]);
                if(symbol.equals("H")){
                    abilities[0] = Integer.parseInt(parts[6]);
                    abilities[1] = Integer.parseInt(parts[7]);
                }

                if(symbol.equals("H")){
                    Human adam = new Human(this, intiative, age, strength, X, Y, abilities[0], abilities[1]);
                    AddOrganism(adam);
                }
                else if(symbol.equals("W")){
                    Wolf adam = new Wolf(this, intiative, age, strength, X, Y);
                    AddOrganism(adam);
                }
                else if(symbol.equals("S")){
                    Sheep adam = new Sheep(this, intiative, age, strength, X, Y);
                    AddOrganism(adam);
                }
                else if(symbol.equals("F")){
                    Fox adam = new Fox(this, intiative, age, strength, X, Y);
                    AddOrganism(adam);
                }
                else if(symbol.equals("T")){
                    Turtle adam = new Turtle(this, intiative, age, strength, X, Y);
                    AddOrganism(adam);
                }
                else if(symbol.equals("A")){
                    Antelope adam = new Antelope(this, intiative, age, strength, X, Y);
                    AddOrganism(adam);
                }
                else if(symbol.equals("C")){
                    Cyber_sheep adam = new Cyber_sheep(this, intiative, age, strength, X, Y);
                    AddOrganism(adam);
                }
                else if(symbol.equals("''")){
                    Grass adam = new Grass(this, intiative, age, X, Y);
                    AddOrganism(adam);
                }
                else if(symbol.equals("t")){
                    Thistle adam = new Thistle(this, intiative, age, X, Y);
                    AddOrganism(adam);
                }
                else if(symbol.equals("g")){
                    Guarana adam = new Guarana(this, intiative, age, X, Y);
                    AddOrganism(adam);
                }
                else if(symbol.equals("b")){
                    Belladonna adam = new Belladonna(this, intiative, age, X, Y);
                    AddOrganism(adam);
                }
                else if(symbol.equals("s")){
                    Sosnowsky_hogweed adam = new Sosnowsky_hogweed(this, intiative, age, X, Y);
                    AddOrganism(adam);
                }
                

            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

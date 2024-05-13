import java.util.Random;

abstract class Organism {
    final int BOARD_SIZE = 20;
    final int UP = -1;
    final int DOWN = 1;
    final int LEFT = -1;
    final int RIGHT = 1;
    protected int strength;
    protected int initiative;
    protected int age;
    protected int X;
    protected int Y;
    protected String symbol;
    protected boolean moved = false;
    protected boolean animal;
    protected World game;

    public Organism(World game, int strength, int initiative, int age, int position_X, int position_Y, String symbol, boolean animal) {
        this.game = game;
        this.strength = strength;
        this.initiative = initiative;
        this.age = age;
        this.X = position_X;
        this.Y = position_Y;
        this.symbol = symbol;
        this.animal = animal;
    }
    
    public abstract void Action(int my_id);

    public abstract boolean Collison(int my_id, int defender_id);

    public boolean BasicFight(int my_id, int attacker_id) {
        game.AddMessage("Fight: " + game.GetOrganism(attacker_id).GetSymbol() + " vs " + symbol);
        if(game.GetOrganism(attacker_id).GetStrength() >= strength){
            game.RemoveOrganism(my_id);
            game.AddMessage("Won: " + game.GetOrganism(attacker_id).GetSymbol());
            return false;
        }
        game.AddMessage("Won: " + symbol);
        game.RemoveOrganism(attacker_id);
        return true;
    }

    public boolean Fight(int my_id, int attacker_id){
       return BasicFight(my_id, attacker_id);
    }
    
    public int GetStrength() {
        return strength;
    }

    public void StrengthAdjustment(int bonus){
        strength += bonus;
    }

    public int GetInitiative() {
        return initiative;
    }

    public void SetInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int GetAge() {
        return age;
    }

    public void AgeIncresse() {
        this.age++;
    }

    public int Get_X() {
        return X;
    }

    public void GetAbilities(int[] abilities){

    }

    public void Set_X(int position_X) {
        this.X = position_X;
    }

    public int Get_Y() {
        return Y;
    }

    public void Set_Y(int position_Y) {
        this.Y = position_Y;
    }

    public String GetSymbol() {
        return symbol;
    }

    public boolean GetMoved() {
        return moved;
    }

    public void Move(int move_x, int move_y, int speed){
        if(move_x == LEFT * speed && move_y == 0 && X >= 1 * speed){
            X += LEFT  * speed;
            moved = true;
        }
        else if(move_x == RIGHT * speed && move_y == 0 && X < BOARD_SIZE - 1 * speed){
            X += RIGHT * speed;
            moved = true;
        }
        else if(move_x == 0 && move_y == UP * speed && Y >= 1 * speed){
            Y += UP * speed;
            moved = true;
        }
        else if(move_x == 0 && move_y == DOWN * speed && Y < BOARD_SIZE - 1 * speed){
            Y += DOWN * speed;
            moved = true;
        }
        else if(move_x == LEFT * speed && move_y == UP * speed && X >= 1 * speed && Y >= 1 * speed){
            X += LEFT * speed;
            Y += UP * speed;
            moved = true;
        }
        else if(move_x == RIGHT * speed && move_y == DOWN * speed && X < BOARD_SIZE - 1 * speed && Y  < BOARD_SIZE - 1 * speed ){
            X += RIGHT * speed;
            Y += DOWN * speed;
            moved = true;
        }
    }

    public void SetMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean IsAnimal() {
        return animal;
    }


}

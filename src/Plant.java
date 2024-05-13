import java.util.Random;

abstract public class Plant extends Organism {
    private final int SAWING_CHANCE = 10;

    public Plant(World game, int strength, int age, int position_X, int position_Y, String symbol) {
        super(game, strength, 0, age, position_X, position_Y, symbol, false);
    }

    public abstract void Baby(int x, int y);

    public void Sawing(int my_id){
        Random random = new Random();
        int saw_roll = random.nextInt(100);
        
        int[] xy = new int[2];
        game.SearchForFreeSpace(xy, X, Y, true);
        if(xy[0] == -1)
            moved = true;

        if(!moved && saw_roll < SAWING_CHANCE){
            int roll = random.nextInt(6) + 1;
            if (roll == 1) {
                if(game.LegalField(X + LEFT, Y) && !game.Collide(my_id, false, X + LEFT, Y))
                    Baby(X + LEFT, Y);
            }
            else if (roll == 2) {
                if(game.LegalField(X, Y + UP) && !game.Collide(my_id, false, X, Y + UP))
                    Baby(X, Y + UP);
            }
            else if (roll == 3) {
                if(game.LegalField(X + RIGHT, Y) && !game.Collide(my_id, false,X + RIGHT, Y ))
                    Baby(X + RIGHT, Y);
            }
            else if (roll == 4) {
                if(game.LegalField(X , Y + DOWN) && !game.Collide(my_id, false, X, Y + DOWN))
                    Baby(X , Y + DOWN);
            }
            else if (roll == 5 && game.IsGameHex()) {
                if(game.LegalField(X + LEFT, Y + UP) && !game.Collide(my_id, false, X + LEFT, Y + UP))
                    Baby(X + LEFT, Y + UP);
            }
            else if (roll == 6 && game.IsGameHex()) {
                if(game.LegalField(X + RIGHT, Y + DOWN) && !game.Collide(my_id, false, X + RIGHT, Y + DOWN))
                    Baby(X + RIGHT, Y + DOWN);;
            }
            if(moved){
                game.AddMessage("Sawd: " + symbol);
            }
        }
        else{
            moved = true;
        }
    }
    
    @Override
    public void Action(int my_id){
        Sawing(my_id);
    }

    public boolean Collison(int my_id, int defender_id){
        return false;
    }
}

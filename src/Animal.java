import java.util.Random;

abstract class Animal extends Organism {
    public Animal(World game, int strength, int initiative, int age, int position_X, int position_Y, String symbol) {
        super(game, strength, initiative, age, position_X, position_Y, symbol, true);
    }

    public abstract void Baby(int[] xy);


    @Override
    public boolean Collison(int my_id, int defender_id){
        Organism defender = game.GetOrganism(defender_id);
        if(symbol == defender.GetSymbol()){
            moved = true;
            if(!game.GetOrganism(defender_id).GetMoved()){
                game.GetOrganism(defender_id).SetMoved(true);
                int[] xy = new int[2];
                game.SearchForFreeSpace(xy, X, Y, false);
                if(xy[0] == -1){
                    game.SearchForFreeSpace(xy, game.GetOrganism(defender_id).Get_X(), game.GetOrganism(defender_id).Get_Y(), false);
                } 
                //check if found a free space
                if(xy[0]==-1){
                    return true;
                }
                else {
                    game.AddMessage("New Organism : " + symbol);
                    Baby(xy);
                    return true;
                }
            }
            return true;
        }
        else{
            moved = true;
            return game.GetOrganism(defender_id).Fight(defender_id, my_id);
        }
    }
    public boolean CanMoveSafely(int nr_in_table, int x, int y) {
        for(int i = 0; i < game.GetSize();i++){
             if(x == game.GetOrganism(i).Get_X() && y == game.GetOrganism(i).Get_Y()){
                 if(strength >= game.GetOrganism(i).GetStrength()){
                     return true;
                 } 
                 else{
                     return false;
                 }
             }
        }
        return true;
     }

    public boolean IsthereOptionToMoveSafely(int my_id){
        if(
        CanMoveSafely(my_id, X + LEFT, Y)||
        CanMoveSafely(my_id, X, Y + UP)||
        CanMoveSafely(my_id, X + RIGHT, Y)||
        CanMoveSafely(my_id, X, Y + DOWN)||
        (game.IsGameHex() && CanMoveSafely(my_id, X + LEFT, Y + UP))||
        (game.IsGameHex() && CanMoveSafely(my_id, X + RIGHT, Y + DOWN))
        ){
            return true;    
        }
        return false;
    }

    public void MakeAMove(int my_id, int speed){
        Random random = new Random();
        int roll = random.nextInt(6) + 1;
        if (roll == 1) {
            if(!game.Collide(my_id, true, X + LEFT * speed, Y)){
                this.Move(LEFT  * speed, 0, speed);
            }
        }
        else if (roll == 2) {
            if(!game.Collide(my_id, true, X, Y + UP * speed))
                this.Move(0, UP * speed, speed);
        }
        else if (roll == 3) {
            if(!game.Collide(my_id, true,X + RIGHT * speed, Y ))
                this.Move(RIGHT  * speed, 0, speed);
        }
        else if (roll == 4) {
            if(!game.Collide(my_id, true, X, Y + DOWN * speed))
                this.Move(0, DOWN * speed, speed);
        }
        else if (roll == 5 && game.IsGameHex()) {
            if(!game.Collide(my_id, true, X + LEFT * speed, Y + UP * speed))
                this.Move(LEFT * speed, UP * speed, speed);
        }
        else if (roll == 6 && game.IsGameHex()) {
            if(!game.Collide(my_id, true, X + RIGHT * speed, Y + DOWN * speed))
                this.Move(RIGHT * speed, DOWN * speed, speed);
        }
    }

    @Override
    public void Action(int my_id) {
        MakeAMove(my_id, 1);
    }
}

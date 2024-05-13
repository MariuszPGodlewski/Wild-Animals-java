import java.util.Random;

class Fox extends Animal {
    
    public Fox(World game, int age, int position_X, int position_Y) {
        super(game, 3, 7, age, position_X, position_Y, "F");
    }

    public Fox(World game,  int[] xy) {
        super(game, 3, 7, 1, xy[0], xy[1], "F");
    }

    public Fox(World game,int initiative, int age, int strength, int position_X, int position_Y) {
        super(game, strength, initiative, age, position_X, position_Y, "F");
    }

    public void Action(int my_id) {
        if(!IsthereOptionToMoveSafely(my_id)){
            moved = true;
        }
        else{
            Random random = new Random();
            int roll = random.nextInt(6) + 1;
            if (roll == 1) {
                if(CanMoveSafely(my_id, X + LEFT, Y) && !game.Collide(my_id, true, X + LEFT, Y)){
                    this.Move(LEFT, 0, 1);
                    this.moved = true;
                }
            }
            else if (roll == 2) {
                if(CanMoveSafely(my_id, X, Y + UP) && !game.Collide(my_id, true, X, Y + UP))
                    this.Move(0, UP, 1);
            }
            else if (roll == 3) {
                if(CanMoveSafely(my_id, X + RIGHT, Y) && !game.Collide(my_id, true,X + RIGHT, Y ))
                    this.Move(RIGHT, 0, 1);
            }
            else if (roll == 4) {
                if(CanMoveSafely(my_id, X, Y + DOWN) && !game.Collide(my_id, true, X, Y + DOWN))
                    this.Move(0, DOWN, 1);
            }
            else if (roll == 5 && game.IsGameHex()) {
                if(CanMoveSafely(my_id, X + LEFT, Y + UP) && !game.Collide(my_id, true, X + LEFT, Y + UP))
                    this.Move(LEFT, UP, 1);
            }
            else if (roll == 6 && game.IsGameHex()) {
                if(CanMoveSafely(my_id, X + RIGHT, Y + DOWN) && !game.Collide(my_id, true, X + RIGHT, Y + DOWN))
                    this.Move(RIGHT, DOWN, 1);
            }
        }
       
    }

    @Override
    public void Baby(int[] xy){
        Fox baby = new Fox(game, xy);
        game.AddOrganism(baby);
        moved = true;
    }
}
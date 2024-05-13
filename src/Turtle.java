import java.util.Random;

class Turtle extends Animal {
    private final int TURTLE_REFLECTION = 5;
    public Turtle(World game, int age, int position_X, int position_Y) {
        super(game, 2, 1, age, position_X, position_Y, "T");
    }

    public Turtle(World game,  int[] xy) {
        super(game, 2, 1, 1, xy[0], xy[1], "T");
    }
    
    public Turtle(World game,int initiative, int age, int strength, int position_X, int position_Y) {
        super(game, strength, initiative, age, position_X, position_Y, "T");
    }

    @Override
    public boolean Fight(int my_id, int attacker_id){
        if(game.GetOrganism(attacker_id).GetStrength() <= TURTLE_REFLECTION){
            game.GetOrganism(attacker_id).SetMoved(true);
            return true;
        }
        else{
            return BasicFight(my_id, attacker_id);
        }
     }

    @Override
    public void Action(int my_id) {
        Random random = new Random();
        int roll = random.nextInt(4) + 1;
        if (roll == 1) {
            MakeAMove(my_id, 1);
        }
        else{
            moved = true;
        }
    }

    @Override
    public void Baby(int[] xy){
        Turtle baby = new Turtle(game, xy);
        game.AddOrganism(baby);
        moved = true;
    }
}
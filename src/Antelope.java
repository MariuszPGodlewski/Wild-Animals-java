import java.util.Random;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

class Antelope extends Animal {
    private final int ANTELOPE_SPEED = 2;
    public Antelope(World game, int age, int position_X, int position_Y) {
        super(game, 4, 4, age, position_X, position_Y, "A");
    }

    public Antelope(World game,  int[] xy) {
        super(game, 4, 4, 1, xy[0], xy[1], "A");
    }

    public Antelope(World game,int initiative, int age, int strength, int position_X, int position_Y) {
        super(game, strength, initiative, age, position_X, position_Y, "A");
    }

    @Override
    public void Baby(int[] xy){
        Antelope baby = new Antelope(game, xy);
        game.AddOrganism(baby);
        moved = true;
    }
    @Override
    public boolean Fight(int my_id, int attacker_id){
        Random random = new Random();
        int roll = random.nextInt(2) + 1;
        if(roll == 1){
            moved = false;
            MakeAMove(my_id, 2);
            return false;
        }
        else{
            return BasicFight(my_id, attacker_id);
        }
     }

    @Override
    public void Action(int my_id) {
        MakeAMove(my_id, ANTELOPE_SPEED);
    }
}
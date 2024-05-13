
import java.util.Random;
import java.util.Scanner;

class Human extends Animal  {
    private int bonus_duration = 0;
    //private boolean Antelope_speed = false;
    private int speed = 1;
    public Human(World game, int age, int position_X, int position_Y) {
        super(game, 5, 4, age, position_X, position_Y, "H");
    }

    public Human(World game, int initiative, int age, int strength, int position_X, int position_Y, int speed_s, int bonus_d) {
        super(game, strength, initiative, age, position_X, position_Y, "H");
        this.speed = speed_s;
        this.bonus_duration = bonus_d;
    }
    
    @Override
    public void Action(int my_id) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a character: ");
        char direction = scanner.next().charAt(0); // This will catch the first character entered
        // Do something with the input character
        //System.out.println("You entered: " + direction);
        
        if (direction == 'f') {
            if(!game.Collide(my_id, true, X + LEFT * speed, Y)){
                this.Move(LEFT * speed, 0, speed);
            }
        }
        else if (direction == 'y') {
            if(!game.Collide(my_id, true, X, Y + UP * speed))
                this.Move(0, UP * speed, speed);
        }
        else if (direction == 'h') {
            if(!game.Collide(my_id, true,X + RIGHT * speed, Y ))
                this.Move(RIGHT * speed, 0, speed);
        }
        else if (direction == 'b') {
            if(!game.Collide(my_id, true, X, Y + DOWN * speed))
                this.Move(0, DOWN * speed, speed);
        }
        else if (direction == 't' && game.IsGameHex()) {
            if(!game.Collide(my_id, true, X + LEFT * speed, Y + UP * speed))
                this.Move(LEFT * speed, UP * speed, speed);
        }
        else if (direction == 'v' && game.IsGameHex()) {
            if(!game.Collide(my_id, true, X + RIGHT * speed, Y + DOWN * speed))
                this.Move(RIGHT * speed, DOWN*  speed, speed);
        }
        else if(direction == 's' && bonus_duration == 0){
            bonus_duration = 5;
            //Antelope_speed = true;
            speed = 2;
        }

        if(moved){
            if (bonus_duration == 0) {
                speed = 1;
                //Antelope_speed = false;
            }
            if(bonus_duration <= 3 && bonus_duration >0){

                Random random = new Random();
                int roll = random.nextInt(2) + 1;
                if(roll == 1){
                    speed = 2;
                }
                else{
                    speed = 1;
                }
            }

            if (bonus_duration > 0) {
                bonus_duration--;
            }
        }
    }
    public Human(World game,  int[] xy) {
        super(game, 5, 4, 1, xy[0], xy[1], "H");
    }

    @Override
    public void Baby(int[] xy){
        Human baby = new Human(game, xy);
        //game.AddOrganism(baby);
        moved = true;
    }

    @Override
    public void GetAbilities(int[] abilities){
        abilities[0] = speed;
        abilities[1] = bonus_duration;
    }
}
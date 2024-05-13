class Cyber_sheep extends Animal {
    
    public Cyber_sheep(World game, int age, int position_X, int position_Y) {
        super(game, 11, 4, age, position_X, position_Y, "C");
    }

    public Cyber_sheep(World game,  int[] xy) {
        super(game, 11, 4, 1, xy[0], xy[1], "C");
    }

    public Cyber_sheep(World game,int initiative, int age, int strength, int position_X, int position_Y) {
        super(game, strength, initiative, age, position_X, position_Y, "C");
    }

    @Override
    public void Baby(int[] xy){
        Cyber_sheep baby = new Cyber_sheep(game, xy);
        game.AddOrganism(baby);
        moved = true;
    }
    public void BasicWhayToWeed(int my_id, int hogweed_x, int hogweed_y){
        if (hogweed_x > Get_X()) {
            if(!game.Collide(my_id, true, X + RIGHT, Y)){
                this.Move(RIGHT, 0, 1);
            }
        }
        else if (hogweed_x < Get_X()) {
            if(!game.Collide(my_id, true, X + LEFT, Y)){
                this.Move(LEFT, 0,1);
            }
        }
        else if (hogweed_y < Get_Y()) {
            if(!game.Collide(my_id, true, X, Y + UP)){
                this.Move(0 , UP, 1);
            }
        }
        else if (hogweed_y > Get_Y()) {
            if(!game.Collide(my_id, true, X, Y + DOWN)){
                this.Move(0, DOWN, 1);
            }
        }
    }
    @Override
    public void Action(int my_id) {
        Organism closest_hogweed = null;
        int distance = BOARD_SIZE + BOARD_SIZE + 1;
        for(int i = 0; i < game.GetSize(); i++ ){
            Organism current = game.GetOrganism(i);
            if(current.GetSymbol() == "s"){
                if(!game.IsGameHex()){
                    int x = Math.abs(X - current.Get_X());
                    int y = Math.abs(Y - current.Get_Y());
                    int tmp = x + y;
                    if (tmp < distance) {
                        distance = tmp;
                        closest_hogweed = current;
                    }
                }
                else{
                    int x = X - current.Get_X();
                    int y = Y - current.Get_Y();
                    int tmp = 0 ;// Math.abs(x) + Math.abs(y);
                    if(x < 0 && y < 0){
                        while (x < 0 && y < 0) {
                            tmp++;
                            x++;
                            y++;
                        }  
                        tmp += Math.abs(x) + Math.abs(y);
                    }
                    else if(x > 0 && y > 0){
                        while (x < 0 && y < 0) {
                            tmp++;
                            x++;
                            y++;
                        }  
                        tmp += Math.abs(x) + Math.abs(y);
                    }
                    else{
                        tmp = Math.abs(x) + Math.abs(y);
                    }

                    if (tmp < distance) {
                        distance = tmp;
                        closest_hogweed = current;
                    }
                }
            }
        }
        if(closest_hogweed == null){
            MakeAMove(my_id, 1);
        }
        else{
            if(!game.IsGameHex()){
                int hogweed_x = closest_hogweed.Get_X();
                int hogweed_y = closest_hogweed.Get_Y();
                // ma chodzic w dobra strone 
                BasicWhayToWeed(my_id, hogweed_x, hogweed_y);
            }
            else {
                int x = closest_hogweed.Get_X() - X;
                int y = closest_hogweed.Get_Y() - Y;
                if (x > 0 && y > 0) {
                    if(!game.Collide(my_id, true, X + RIGHT, Y + DOWN)){
                        this.Move(RIGHT, DOWN, 1);
                    }
                }
                else if (x < 0 && y < 0) {
                    if(!game.Collide(my_id, true, X + LEFT, Y + UP)){
                        this.Move(LEFT, UP, 1);
                    }
                }
                else{
                    BasicWhayToWeed(my_id, closest_hogweed.Get_X(), closest_hogweed.Get_Y());
                }
            }
        }
    }
}
class Sosnowsky_hogweed extends Plant {
    
    public Sosnowsky_hogweed(World game, int age, int position_X, int position_Y) {
        super(game, 10, age, position_X, position_Y, "s");
    }

    public Sosnowsky_hogweed(World game,  int x, int y) {
        super(game, 10, 1, x, y, "s");
    }
    public Sosnowsky_hogweed(World game,int initiative, int age, int position_X, int position_Y) {
        super(game, initiative, age, position_X, position_Y, "s");
    }

    @Override
    public void Baby(int x, int y){
        Sosnowsky_hogweed baby = new Sosnowsky_hogweed(game, x, y);
        game.AddOrganism(baby);
        moved = true;
    }

    
    @Override
    public void Action(int my_id){
        // kill the neighoburs
        for(int i = 0 ; i< game.GetSize(); i++){
            if(game.GetOrganism(i).IsAnimal() && game.GetOrganism(i).GetSymbol() != "C" ){
                if (X + LEFT == game.GetOrganism(i).Get_X() && Y == game.GetOrganism(i).Get_Y()) {
                    game.RemoveOrganism(i);
                }
                else if (X + RIGHT == game.GetOrganism(i).Get_X() && Y == game.GetOrganism(i).Get_Y()) {
                    game.RemoveOrganism(i);
                }
                else if (X == game.GetOrganism(i).Get_X() && Y + DOWN == game.GetOrganism(i).Get_Y()) {
                    game.RemoveOrganism(i);
                }
                else if (X == game.GetOrganism(i).Get_X() && Y + UP == game.GetOrganism(i).Get_Y()) {
                    game.RemoveOrganism(i);
                }
                else if (game.IsGameHex() && X + LEFT == game.GetOrganism(i).Get_X() && Y + UP == game.GetOrganism(i).Get_Y()) {
                    game.RemoveOrganism(i);
                }
                else if (game.IsGameHex() && X + RIGHT == game.GetOrganism(i).Get_X() && Y + DOWN == game.GetOrganism(i).Get_Y()) {
                    game.RemoveOrganism(i);
                }
            }
        }
        Sawing(my_id);
    }

    @Override
    public boolean Fight(int my_id, int attacker_id){
         game.AddMessage("Fight: " + game.GetOrganism(attacker_id).GetSymbol() + " vs " + symbol);
        if(game.GetOrganism(attacker_id).IsAnimal() && game.GetOrganism(attacker_id).GetSymbol() != "C"){
            game.AddMessage("Both died");
            game.RemoveOrganism(my_id);
            game.RemoveOrganism(attacker_id);
            return false;
        }
        else if(game.GetOrganism(attacker_id).GetSymbol() != "C"){
            game.AddMessage("Cyber sheep ate hogweed");
            game.RemoveOrganism(my_id);
            return false; 
        }
        return BasicFight(my_id, attacker_id);
     }
}

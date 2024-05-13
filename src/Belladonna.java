class Belladonna extends Plant {

    public Belladonna(World game, int age, int position_X, int position_Y) {
        super(game, 99, age, position_X, position_Y, "b");
    }

    public Belladonna(World game,  int x, int y) {
        super(game, 99, 1, x, y, "b");
    }

    public Belladonna(World game,int initiative, int age, int position_X, int position_Y) {
        super(game, initiative, age, position_X, position_Y, "b");
    }

    @Override
    public void Baby(int x, int y){
        Belladonna baby = new Belladonna(game, x, y);
        game.AddOrganism(baby);
        moved = true;
    }

    @Override
    public boolean Fight(int my_id, int attacker_id){
         game.AddMessage("Fight: " + game.GetOrganism(attacker_id).GetSymbol() + " vs " + symbol);
        if(game.GetOrganism(attacker_id).IsAnimal()){
            game.AddMessage("Both died");
            game.RemoveOrganism(my_id);
            game.RemoveOrganism(attacker_id);
            return false;
        }
        return BasicFight(my_id, attacker_id);
     }
}
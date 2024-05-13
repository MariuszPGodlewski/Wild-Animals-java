class Grass extends Plant {
    
    public Grass(World game, int age, int position_X, int position_Y) {
        super(game, 0, age, position_X, position_Y, "''");
    }

    public Grass(World game,  int x, int y) {
        super(game, 0, 1, x, y, "''");
    }

    public Grass(World game,int initiative, int age, int position_X, int position_Y) {
        super(game, initiative, age, position_X, position_Y, "''");
    }

    @Override
    public void Baby(int x, int y){
        Grass baby = new Grass(game, x, y);
        game.AddOrganism(baby);
        moved = true;
    }
}
class Thistle extends Plant {
    private final int NR_OF_SAWING = 3;
    public Thistle(World game, int age, int position_X, int position_Y) {
        super(game, 0, age, position_X, position_Y, "t");
    }

    public Thistle(World game,  int x, int y) {
        super(game, 0, 1, x, y, "t");
    }

    public Thistle(World game,int initiative, int age, int position_X, int position_Y) {
        super(game, initiative, age, position_X, position_Y, "t");
    }

    @Override
    public void Action(int my_id) {
        for(int i =0 ; i < NR_OF_SAWING; i++){
            Sawing(my_id);
        }
    }
    
    @Override
    public void Baby(int x, int y){
        Thistle baby = new Thistle(game, x, y);
        game.AddOrganism(baby);
        moved = true;
    }
}
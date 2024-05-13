class Wolf extends Animal {
    
    public Wolf(World game, int age, int position_X, int position_Y) {
        super(game, 9, 5, age, position_X, position_Y, "W");
    }

    public Wolf(World game,int initiative, int age, int strength, int position_X, int position_Y) {
        super(game, strength, initiative, age, position_X, position_Y, "W");
    }

    public Wolf(World game,  int[] xy) {
        super(game, 9, 5, 1, xy[0], xy[1], "W");
    }

    @Override
    public void Baby(int[] xy){
        Wolf baby = new Wolf(game, xy);
        game.AddOrganism(baby);
        moved = true;
    }
}
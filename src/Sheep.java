class Sheep extends Animal {
    
    public Sheep(World game, int age, int position_X, int position_Y) {
        super(game, 4, 4, age, position_X, position_Y, "S");
    }

    public Sheep(World game,  int[] xy) {
        super(game, 4, 4, 1, xy[0], xy[1], "S");
    }

    public Sheep(World game,int initiative, int age, int strength, int position_X, int position_Y) {
        super(game, strength, initiative, age, position_X, position_Y, "S");
    }

    @Override
    public void Baby(int[] xy){
        Sheep baby = new Sheep(game, xy);
        game.AddOrganism(baby);
        moved = true;
    }
}
class Guarana extends Plant {
    private final int BONUS_STRENGTH = 3;
    public Guarana(World game, int age, int position_X, int position_Y) {
        super(game, 0, age, position_X, position_Y, "g");
    }

    public Guarana(World game,  int x, int y) {
        super(game, 0, 1, x, y, "g");
    }

    public Guarana(World game,int initiative, int age, int position_X, int position_Y) {
        super(game, initiative, age, position_X, position_Y, "g");
    }
    
    @Override
    public void Baby(int x, int y){
        Guarana baby = new Guarana(game, x, y);
        game.AddOrganism(baby);
        moved = true;
    }

    @Override
    public boolean Fight(int my_id, int attacker_id){
        game.GetOrganism(attacker_id).StrengthAdjustment(BONUS_STRENGTH);
        return BasicFight(my_id, attacker_id);
     }
}
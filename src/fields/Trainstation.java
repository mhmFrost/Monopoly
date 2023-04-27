package fields;

import player.Player;

public class Trainstation extends Field{
    private int price;
    private Player owner;
    int[] upgradeFactors;

    public Trainstation(String name) {
        super(name, "none");
    }

    public void sell(Player newOwner) {

    }

    @Override
    public String toString() {
        return "🚂" + super.name();
    }
}

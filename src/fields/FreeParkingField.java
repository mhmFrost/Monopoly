package fields;

import player.Player;

public class FreeParkingField extends SpecialField{
    public FreeParkingField() {
        super("🚗Free Parking");
    }

    @Override
    public void action(Player player) {
        System.out.println("Nothing to see here 👀");
    }

    @Override
    public String toString() {
        return super.name();
    }
}

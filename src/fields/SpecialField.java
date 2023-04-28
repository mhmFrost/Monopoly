package fields;

import player.Actionable;
import player.Player;

public abstract class SpecialField extends Field {
    public SpecialField(String name) {
        super(name, "none");
    }

    public void action(Player player) {};
    @Override
    public String toString() {
        return "SpecialField";
    }
}

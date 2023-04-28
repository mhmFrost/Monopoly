package fields;

import player.Actionable;
import player.Player;

public class GoToJailField extends SpecialField{
    public GoToJailField() {
        super("🦆Go to Jail");
    }

    @Override
    public void action(Player player) {
        player.goToJail();
        //TODO: implement setPosition in class Player
        //player.setPosition(10);
    }

    @Override
    public String toString() {
        return super.name();
    }
}

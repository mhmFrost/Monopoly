package fields;

import player.Player;

public class GoField extends SpecialField{
    public GoField() {
        super("🚀GO");
    }

    @Override
    public void action(Player player) {
        player.setMoney(player.getMoney() + 200);
        System.out.println(player.getName() + " passed 🚀GO, here's an income of $200");
        System.out.println(player);
    }

    @Override
    public String toString() {
        return super.name();
    }
}

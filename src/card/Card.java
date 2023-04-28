package card;

import player.Actionable;
import player.Player;
import java.util.function.Consumer;

public class Card {
    private String title;
    private String message;
    private int money = 0;
    private Actionable action;

    /**
     * Creates a card, where a player can win or loose money, if drawn.
     * @param title "Congratulations"
     * @param message "Banking error: collect $200"
     * @param money 200 | value can be positive or negative
     * @see fields.CommunityChest
     * @see fields.ChanceField
     */
    public Card(String title, String message, int money) {
        this.title = title;
        this.message = message;
        this.money = money;
    }

    /**
     * Creates a card, where a player can be sent to jail, moved or earn a ticket out of jail, if drawn.
     * @param title "Oops"
     * @param message "You're going to jail..."
     * @param actionable player::goToJail
     * @see fields.CommunityChest
     * @see fields.ChanceField
     */
    public Card(String title, String message, Actionable actionable) {
        this.title = title;
        this.message = message;
        this.action = actionable;
    }

    public void activate(Player player) {
        if (action != null) {
            System.out.println( "Card 🃏\n"
                    + title + "\n"
                    + message);
            action.action(player);
        } else {
        int currentBalance = player.getMoney();
        player.setMoney(currentBalance + money);

        System.out.println(this);
        System.out.println(player);
        }
    }

    @Override
    public String toString() {
        return  "Card 🃏\n"
                + title + "\n"
                + message + "\n"
                + (money != 0 ? "$" + money + "\n" : "");
    }
}

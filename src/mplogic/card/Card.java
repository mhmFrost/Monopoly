package mplogic.card;

import mplogic.player.Actionable;
import mplogic.player.Player;

/**
 * Card that contain title, message, money, debt or action.
 * Check out {@link #Card(String, String, int) constructor #1} or
 * {@link #Card(String, String, Actionable) constructor #2} for more information.
 */
public class Card {
    private final String title;
    private final String message;
    private int money = 0;
    private Actionable action;

    /**
     * Creates a card, where a player can win or loose money, if drawn.
     * @param title "Congratulations"
     * @param message "Banking error: collect $200"
     * @param money 200 | value can be positive or negative
     * @see mplogic.fields.CommunityChest
     * @see mplogic.fields.ChanceField
     */
    public Card(String title, String message, int money) {
        this.title = title;
        this.message = message;
        this.money = money;
    }

    /**
     * Creates a mplogic.card, where a mplogic.player can be sent to jail, moved or earn a ticket out of jail, if drawn.
     * @param title "Oops"
     * @param message "You're going to jail..."
     * @param actionable mplogic.player::goToJail
     * @see mplogic.fields.CommunityChest
     * @see mplogic.fields.ChanceField
     */
    public Card(String title, String message, Actionable actionable) {
        this.title = title;
        this.message = message;
        this.action = actionable;
    }

    /**
     * Activates card on current player and prints itself to console.
     * @param player Player
     * @see Player
     * @see Actionable
     * @see mplogic.fields.CommunityChest
     * @see mplogic.fields.ChanceField
     */
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

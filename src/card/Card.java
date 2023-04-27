package card;

import player.Player;
import java.util.function.Consumer;

public class Card {
    private String title;
    private String message;
    private int money = 0;
    private Object action;

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
     * @param function player::goToJail
     * @see fields.CommunityChest
     * @see fields.ChanceField
     */
    public Card(String title, String message, Consumer<Integer> function, int n) {
        this.title = title;
        this.message = message;
        function.accept(n);
    }

    public void activate(Player player) {
        int currentBalance = player.getMoney();
        player.setMoney(currentBalance + money);
    }

    @Override
    public String toString() {
        return  "Card 🃏\n"
                + title + "\n"
                + message + "\n"
                + (money != 0 ? "$" + money + "\n" : "")
                + (action != null ? action : "");
    }
}

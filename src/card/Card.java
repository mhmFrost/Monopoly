package card;

import player.Player;

import java.util.function.Function;

public class Card {
    private String title;
    private String message;
    private int money;
    private Function<Void, Void> action;

    /**
     * Creates a card, where a player can get or loose money, if drawn.
     * @param title "Congratulations"
     * @param message "Banking error: collect $200"
     * @param money 200 | value can be positive or negative
     */
    public Card(String title, String message, int money) {
        this.title = title;
        this.message = message;
        this.money = money;
    }

    /**
     * Creates a card, where a player can be sent to jail, moved or earn a free ticket out of jail, if drawn.
     * @param title "Oops"
     * @param message "You're going to jail..."
     * @param action player::goToJail
     */
    public Card(String title, String message, Function<Void, Void> action) {
        this.title = title;
        this.message = message;
        this.action = action;

        this.action.apply(null);
    }

    public void activate(Player player) {
//        int currentBalance = player.getMoney();
//        player.setMoney(currentBalance + money);
    }
}

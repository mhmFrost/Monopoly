package fields;

import card.Card;
import player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChanceField extends Field {
    private ArrayList<Card> cards;

    /**
     * Initializes Chance Field with a given list of pre-defined Cards.
     * @see Card
     */
    public ChanceField() {
        super("Chance", "none");
        cards = new ArrayList<>(List.of(
                //new Card("💰 Wohoo!", "Advance to Go (Collect $200)", 200),
                //new Card("💰 Wohoo!", "Advance to Illinois Ave—If you pass Go, collect $200", 200),
                //new Card("💰 Wohoo!", "Advance to St. Charles Place – If you pass Go, collect $200", 200),
                //new Card("♟ Oh no!", "Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times the amount thrown.", 0),
                //new Card("♟ Oh no!", "Advance token to the nearest Railroad and pay owner twice the rental to which he/she {he} is otherwise entitled. If Railroad is unowned, you may buy it from the Bank.", 0),
//                new Card("💰 Wohoo!", "Bank pays you dividend of $50", 50),
//                new Card("💰 Wohoo!", "Get Out of Jail Free", 50),
//                new Card("💵 Oh no!", "Pay poor tax of $15", -15),
                new Card("♟ Oh no!", "Go Back 3 Spaces", (Player player) -> System.out.println("moved back")),
                //new Card("💵 Oh no!", "Go to Jail–Go directly to Jail–Do not pass Go, do not collect $200", 0),
                //new Card("💵 Oh no!", "Make general repairs on all your property–For each house pay $25–For each hotel $100", 0),
                new Card("💵 Oh no!", "Pay poor tax of $15", -15),
                //new Card("💰 Wohoo!", "Take a trip to Reading Railroad–If you pass Go, collect $200", 0),
                //new Card("💰 Wohoo!", "Take a walk on the Boardwalk–Advance token to Boardwalk", 0),
                //new Card("💵 Oh no!", "You have been elected Chairman of the Board–Pay each player $50", 0),
                new Card("💰 Wohoo!", "Your building and loan matures — Collect $150", 150),
                new Card("💰 Wohoo!", "You have won a crossword competition — Collect $100", 100)
                //TODO: modify Cards that are commented out
        ));
    }
    /**
     * Gets a random Card from this Chance Field.
     * @return Card
     * @see Card
     */
    public Card getCard() {
        Random random = new Random();
        int x = random.nextInt(0, cards.size());
        return cards.get(x);
    }

    /**
     * Prints all Chance Field cards to the console<br><br>
     * <i>Example Output:</i><br>
     * Card 🃏<br>
     * 💰 Wohoo!<br>
     * Bank pays you dividend of $50<br>
     * $50<br>
     *<br>
     * Card 🃏<br>
     * 💵 Oh no!<br>
     * Pay poor tax of $15<br>
     * $-15<br>
     * ...
     */
    public void printAllCards() {
        cards.stream().forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "❓" + super.name();
    }
}

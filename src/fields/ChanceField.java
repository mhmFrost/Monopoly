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
                new Card("💰 Wohoo!", "Bank pays you dividend of $50", 50),
                new Card("💵 Oh no!", "Pay poor tax of $15", -15),
                new Card("♟ Oh no!","Go Back 3 Spaces", new Player("Tim", "Red")::move, -3)
                //TODO: Add more cards later
                //https://monopolyguide.com/traditional/monopoly-list-of-chance-cards-main-version/
        ));
    }
    /**
     * Gets a random Card from this Chance Field.
     * @return Card
     * @see Card
     */
    public Card getCard() {
        Random random = new Random();
        int x = random.nextInt(1, cards.size());
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

package fields;

import card.Card;
import player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChanceField extends Field {
    private ArrayList<Card> cards;

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

    public Card getCard() {
        Random random = new Random();
        int x = random.nextInt(1, cards.size());
        return cards.get(x);
        //TODO: Maybe remove Card from deck when drawn?
    }

    public void printAllCards() {
        cards.stream().forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "❓" + super.name();
    }
}

package fields;

import board.Board;
import card.Card;
import player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommunityChest extends Field {
    private ArrayList<Card> cards;

    public CommunityChest() {
        super("Community Chest", "none");
        cards = new ArrayList<>(List.of(
                new Card("💰 Wohoo!", "Bank error in your favor — Collect $200", 200),
                new Card("💵 Oh no!", "Doctor's fee — Pay $50", -50)
                //TODO: Add more cards later
                //https://monopolyguide.com/traditional/monopoly-list-of-community-chest-cards-main-version/
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
        return "♦️" + super.name();
    }
}

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
                new Card("💵 Oh no!", "Doctor's fee — Pay $50", -50),
                //new Card("Advance to Go and Collect $200",)
                new Card("💰 Wohoo!", "From Sale of stock you get $50", 50),
                //new Card("Get Out of Jail Free",)
                //new Card("Go to Jail–Go directly to jail–Do not pass Go–Do not collect $200",)
                //new Card("Grand Opera Night—Collect $50 from every player for opening night seats",)
                new Card("💵 Oh no!", "Doctor's fee — Pay $50", -50),
                new Card("💰 Wohoo!", "Holiday Fund matures—Receive $100", 100),
                new Card("💰 Wohoo!", "Income tax refund–Collect $20", 20),
                new Card("💰 Wohoo!", "It is your birthday—Collect $10", 10),
                new Card("💰 Wohoo!", "Life insurance matures–Collect $100", 100),
                new Card("💵 Oh no!", "Pay hospital fees of $100", -100),
                new Card("💵 Oh no!", "Pay school fees of $150", -150),
                new Card("💰 Wohoo!", "Receive $25 consultancy fee", 25),
                //new Card("You are assessed for street repairs–$40 per house–$115 per hotel",)
                new Card("💰 Wohoo!", "You have won second prize in a beauty contest–Collect $10", 10),
                new Card("💰 Wohoo!", "You inherit $100", 100)
                //TODO: complete the Cards
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

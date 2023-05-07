package mplogic.fields;

import mplogic.building.Building;
import mplogic.building.Hotel;
import mplogic.building.House;
import mplogic.card.Card;
import mplogic.player.Actionable;
import mplogic.player.Player;

import java.util.*;

/**
 * Community chest with a list of pre-defined cards.
 *
 * @see Actionable
 * @see Card
 * @see ChanceField
 */
public class CommunityChest extends Field {
    private final ArrayList<Card> cards;

    /**
     * Initializes Community Chest with a given list of pre-defined Cards.
     *
     * @see Card
     */
    public CommunityChest() {
        super("Community Chest", "none");
        cards = new ArrayList<>(List.of(
                new Card("💰 Wohoo!", "Bank error in your favor — Collect $200", 200),
                new Card("💵 Oh no!", "Doctor's fee — Pay $50", -50),
                new Card("💰 Wohoo!", "Advance to Go and Collect $200", jumpToGo()),
                new Card("💰 Wohoo!", "From Sale of stock you get $50", 50),
                new Card("💵 Oh no!", "Go to Jail–Go directly to jail–Do not pass Go–Do not collect $200", jumpToJail()),
                new Card("💰 Wohoo!", "Grand Opera Night—Collect $50 from every player for opening night seats", operaNight()),
                new Card("💵 Oh no!", "Doctor's fee — Pay $50", -50),
                new Card("💰 Wohoo!", "Holiday Fund matures — Receive $100", 100),
                new Card("💰 Wohoo!", "Income tax refund–Collect $20", 20),
                new Card("💰 Wohoo!", "It is your birthday — Collect $10", 10),
                new Card("💰 Wohoo!", "Life insurance matures – Collect $100", 100),
                new Card("💵 Oh no!", "Pay hospital fees of $100", -100),
                new Card("💵 Oh no!", "Pay school fees of $150", -150),
                new Card("💰 Wohoo!", "Receive $25 consultancy fee", 25),
                new Card("💵 Oh no!", "You are assessed for street repairs – $40 per house – $115 per hotel", makeStreetRepairs()),
                new Card("💰 Wohoo!", "You have won second prize in a beauty contest – Collect $10", 10),
                new Card("💰 Wohoo!", "You inherit $100", 100)

        ));
    }

    private static Actionable operaNight() {
        return (Player player) -> {
            player.setMoney(player.getMoney() + 50 * 4);
        };
    }

    private static Actionable jumpToJail() {
        return (Player player) -> player.setPosition(10);
    }

    private static Actionable jumpToGo() {
        return (Player player) -> player.move(39 - player.getPosition());
    }

    private static Actionable makeStreetRepairs() {
        return (Player player) -> {

            List<List<Building>> buildings = player.getProperties().stream()
                    .filter(p -> p instanceof Street).map(s -> ((Street) s).getBuildings()).toList();

            var numHouses = buildings.stream()
                    .flatMap(Collection::stream).filter(b -> b instanceof House).count();
            var numHotels = buildings.stream()
                    .flatMap(Collection::stream).filter(b -> b instanceof Hotel).count();

            int houseRepairCost = (int) numHouses * 40;
            int hotelRepairCost = (int) numHotels * 115;

            player.setMoney(player.getMoney() - (houseRepairCost + hotelRepairCost));


            String message = player.getName() + " paid $ " + houseRepairCost + " for "
                    + numHouses + new House() + "s and $" + hotelRepairCost + " for "
                    + numHotels + " " + new Hotel() + "s";

            System.out.println(message);
            System.out.println(player);
        };
    }

    /**
     * Gets a random Card from this Community Chest.
     *
     * @return Card
     * @see Card
     */
    public Card getCard() {
        Random random = new Random();
        int x = random.nextInt(0, cards.size());
        return cards.get(x);
    }

    /**
     * Prints all Community Chest cards to the console<br><br>
     * <i>Example Output:</i><br>
     * Card 🃏<br>
     * 💰 Wohoo!<br>
     * Bank error in your favor — Collect $200<br>
     * $200<br>
     * <br>
     * Card 🃏<br>
     * 💵 Oh no!<br>
     * Doctor's fee — Pay $50<br>
     * $-50<br>
     * ...
     */
    public void printAllCards() {
        cards.stream().forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "♦️" + super.name();
    }
}

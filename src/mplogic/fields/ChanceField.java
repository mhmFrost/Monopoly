package mplogic.fields;

import mplogic.building.Building;
import mplogic.building.Hotel;
import mplogic.building.House;
import mplogic.card.Card;
import mplogic.player.Actionable;
import mplogic.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Chance field with a list of pre-defined cards.
 *
 * @see Card
 * @see CommunityChest
 * @see Actionable
 */
public class ChanceField extends Field {
    private final ArrayList<Card> cards;

    /**
     * Initializes chance field with a list of pre-defined cards.
     *
     * @see Card
     */
    public ChanceField() {
        super("Chance", "none");
        cards = new ArrayList<>(List.of(
                //TODO implement logic of last 3 cards
                new Card("💰 Wohoo!", "Advance to Go (Collect $200)", jumpTo(39)),
                new Card("💰 Wohoo!", "Advance to Illinois Ave — If you pass Go, collect $200", jumpTo(24)),
                new Card("💰 Wohoo!", "Advance to St. Charles Place – If you pass Go, collect $200", jumpTo(11)),
                //new Card("♟ Oh no!", "Advance token to nearest utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times the amount thrown.", 0),
                //new Card("♟ Oh no!", "Advance token to the nearest Railroad and pay owner twice the rental to which he/she is otherwise entitled. If Railroad is unowned, you may buy it from the Bank.", 0),
                new Card("💰 Wohoo!", "Bank pays you dividend of $50", 50),
                new Card("💰 Wohoo!", "Get Out of Jail Free", 50),
                new Card("💵 Oh no!", "Pay poor tax of $15", -15),
                new Card("♟ Oh no!", "Go Back 3 spaces", (Player player) -> player.setPosition(player.getPosition() - 3)),
                new Card("⛓ Oh no!", "Go directly to Jail – Do not pass Go, do not collect $200", goToJail()),
                new Card("💵 Oh no!", "Make general repairs on all your property – For each house pay $25 – For each hotel $100", makeGeneralRepairs()),
                new Card("💵 Oh no!", "Pay poor tax of $15", -15),
                new Card("💰 Wohoo!", "Take a trip to Reading Railroad – If you pass Go, collect $200", jumpTo(5)),
                new Card("💰 Wohoo!", "Take a walk on the Boardwalk – Advance token to Boardwalk", jumpTo(39)),
                //new Card("💵 Oh no!", "You have been elected Chairman of the Board – Pay each player $50", 0),
                new Card("💰 Wohoo!", "Your building and loan matures — Collect $150", 150),
                new Card("💰 Wohoo!", "You have won a crossword competition — Collect $100", 100)
        ));
    }

    private static Actionable goToJail() {
        return (Player player) -> {
            player.setPosition(10);
            player.setInJail(true);
        };
    }

    private Actionable jumpTo(int fieldID) {
        return (Player player) -> {
            if (fieldID == 39) {
                player.setPosition(39);
                return;
            }
            player.move(fieldID - player.getPosition());
        };
    }

    /**
     * Gets a random Card from this ChanceField.
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
     * Prints all ChanceField cards to the console<br><br>
     * <i>Example Output:</i><br>
     * Card 🃏<br>
     * 💰 Wohoo!<br>
     * Bank pays you dividend of $50<br>
     * $50<br>
     * <br>
     * Card 🃏<br>
     * 💵 Oh no!<br>
     * Pay poor tax of $15<br>
     * $-15<br><br>
     * ...
     */
    public void printAllCards() {
        cards.forEach(System.out::println);
    }

    private static Actionable makeGeneralRepairs() {
        return (Player player) -> {

            List<List<Building>> buildings = player.getProperties().stream()
                    .filter(p -> p instanceof Street).map(s -> ((Street) s).getBuildings()).toList();

            var numHouses = buildings.stream()
                    .flatMap(Collection::stream).filter(b -> b instanceof House).count();
            var numHotels = buildings.stream()
                    .flatMap(Collection::stream).filter(b -> b instanceof Hotel).count();

            int houseRepairCost = (int) numHouses * 50;
            int hotelRepairCost = (int) numHotels * 100;

            player.setMoney(player.getMoney() - (houseRepairCost + hotelRepairCost));

            String message = player.getName() + " paid $ " + houseRepairCost + " for "
                    + numHouses + new House() + "s and $" + hotelRepairCost + " for "
                    + numHotels + " " + new Hotel() + "s";

            System.out.println(message);
            System.out.println(player);
        };
    }

    @Override
    public String toString() {
        return "❓" + super.name();
    }
}

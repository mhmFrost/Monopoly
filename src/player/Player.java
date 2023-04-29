package player;

import card.Card;
import fields.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player implements Actionable {
    private String name = "";
    private String color = "";
    private int score = 0;
    private boolean isInJail = false;
    private String figurine = "";
    private List<Field> properties = new ArrayList<>();
    private int doubles = 0;
    private int money = 1500;
    private boolean hasDouble = false;
    private int diceSum = 0;
    private List<Card> cards = new ArrayList<>();
    private int position = 0;

    /**
     * Initializes a new player with a given name, color and starting amount of $1,500 in the bank.
     *
     * @param name  e.g. <i>Tim</i>
     * @param color e.g. <i>red</i>
     */
    public Player(String name, String color, String figurine) {
        this.name = name;
        this.color = color;
        this.figurine = figurine;
    }

    public void addProperty(Field field) {
        properties.add(field);
    }

    public void removeProperty(String streetname) {
        Field field = null;
        for (Field property : properties) {
            if (property.name().toLowerCase().equals(streetname.toLowerCase())) {
                field = property;
                break;
            }
        }
        properties.remove(field);
    }

    public boolean buy(int price) {
        if (checkMoney(price)) {
            money -= price;
            return true;
        }
        return false;
    }

    /**
     * Rolls dice and moves player by any number between <b>2 - 12</b> across the board.
     * <ul>
     *     <li>If the player rolls a <b>double</b>, he/she can will automatically <b>roll again</b>.</li>
     *     <li>If <b>3 doubles</b> occur in a row, the player is being <b>sent to jail</b>.</li>
     * </ul>
     */
    public int rollDice() {
        Random rand = new Random();
        int diceOne = rand.nextInt(1, 7);
        int diceTwo = rand.nextInt(1, 7);

        if (diceOne == diceTwo) {
            doubles++;
            hasDouble = true;
            diceSum = diceOne + diceTwo;
            if (doubles == 3) {
                setInJail(true);
                doubles = 0;
                hasDouble = false;
            }
        } else {
            move(diceOne + diceTwo);
            hasDouble = false;
            doubles = 0;
        }
        return diceSum;
    }

    /**
     * checkMoney
     * <ul>
     *     <li>returns True if Player has enough Money</li>
     *     <li>returns False if Player has not enough Money</li>
     * </ul>
     */
    public boolean checkMoney(int n) {
        return money - n > 0;
    }

    public boolean checkMortgages() {
        List<Field> mortgagedProperties = properties.stream().filter(f -> !f.hasMortgage()).toList();
        if (mortgagedProperties.size() > 0) {
            return true;
        }
        return false;
    }

    public void drawCard(Field field) {
        Card drawnCard = determineCardTypeFrom(field);
        drawnCard.activate(this);
    }

    private Card determineCardTypeFrom(Field field) {
        Card drawnCard = null;
        if (field instanceof ChanceField) {
            drawnCard = ((ChanceField) field).getCard();

        }
        if (field instanceof CommunityChest) {
            drawnCard = ((CommunityChest) field).getCard();
        }
        return drawnCard;
    }

    public int move(int n) {
        int endPosition = position + n % 39;

        if (position == endPosition) {
            return 0;
        }

        position++;
        if (position > 38) {
            position = 0;
            new GoField().action(this);
        }

        return move(endPosition - position);
    }

    public void goToJail() {
        System.out.println("😢Oh no, " + name + ", you're going to jail.");
        this.isInJail = true;
    }

    public void payRent(Field field) {
        if (!field.hasMortgage()) {
            int rent = getRentOf(field);
            Player owner = getOwnerOf(field);

            setMoney(money - rent);
            owner.setMoney(owner.getMoney() + rent);

            System.out.println(this.name + " paid $" + rent + " in rent to " + owner.name);

            //TODO: askForTakingOutMortgages or SellingHouses or Trade Streets
            //TODO: getProperties() -> print with mortgage next to Straße 1 $30, Straße 2, $100
            //TODO: if getProperties() buildings.size()>0 -> print houses with sell price (50% of buildprice) in each street
            if (money < 0) {

                System.out.println("Oops, " + name + " has no money left: $" + money);
                System.out.println("You should take out a mortgage, sell houses or streets.");
            }
        }
    }

    private int getRentOf(Field field) {

        int rent = 0;

        if (field instanceof Street) {
            rent = ((Street) field).getRent();
        }
        if (field instanceof Trainstation) {
            rent = ((Trainstation) field).getRent();
        }
        if (field instanceof ServiceField) {
            rent = diceSum * ((ServiceField) field).getRent();
        }

        if (field.hasMortgage()) {
            rent = 0;
        }

        return rent;
    }

    private Player getOwnerOf(Field field) {
        Player owner = null;
        if (field instanceof Street) {
            owner = ((Street) field).getOwner();
        }
        if (field instanceof Trainstation) {
            owner = ((Trainstation) field).getOwner();
        }
        if (field instanceof ServiceField) {
            owner = ((ServiceField) field).getOwner();
        }
        return owner;
    }

    public void payTax(Field field) {

        int tax = ((TaxField) field).getTax();
        setMoney(money - tax);
        System.out.println(this.name + " paid $" + tax + " in taxes to the bank 🏦");

        //TODO: askForTakingOutMortgages or SellingHouses or Trade Streets
        if (money < 0) {
            System.out.println("Oops, " + name + " has no money left: $" + money);
            System.out.println("You should take out a mortgage, sell houses or streets.");
        }
    }

    // Getter and Setter


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        if (name == null) {
            return "";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isInJail() {
        return isInJail;
    }

    public void setInJail(boolean inJail) {
        isInJail = inJail;
    }

    public String getFigurine() {
        return figurine;
    }

    public void setFigurine(String figurine) {
        this.figurine = figurine;
    }

    public int getDoubles() {
        return doubles;
    }

    public void setDoubles(int doubles) {
        this.doubles = doubles;
    }

    public List<Field> getProperties() {
        return properties;
    }

    public void setProperties(List<Field> properties) {
        this.properties = properties;
    }

    public boolean isHasDouble() {
        return hasDouble;
    }

    public void setHasDouble(boolean hasDouble) {
        this.hasDouble = hasDouble;
    }

    /**
     * Returns player name with current balance.
     *
     * @return e.g. <i>Tim $1500</i>
     */
    @Override
    public String toString() {
        return name + " $" + money;
    }

    @Override
    public void action(Player player) {

    }
}

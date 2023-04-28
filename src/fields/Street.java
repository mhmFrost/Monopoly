package fields;

import board.Board;
import building.Building;
import player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Street extends Field {

    private int price;
    private int buildPrice;
    private List<Building> buildings = new ArrayList<>();
    private int[] rents;
    private Player owner;

    public Street(String name, String color, int price, int[] rents) {
        super(name, color);
        this.price = price;
        determineBuildPrice();
        this.rents = rents;
    }

    @Override
    public String toString() {
        String colorEmoji = getColorEmoji();

        return (hasMortgage() ? "🚧" : "🏘")
                + super.name()
                + " " + colorEmoji
                + " $" + getPrice()
                + (owner != null ? " 🔑" + owner.getName() + " " : "")
                + (buildings.size() > 0 ? buildings : "")
                + (hasMortgage() ? " 💸$" + ((int) ((price * 0.5) * 1.1)) : "");
    }

    private String getColorEmoji() {
        String colorEmoji = "";
        switch (super.color().toLowerCase()) {
            case "brown" -> colorEmoji = "🟤";
            case "blue" -> colorEmoji = "🔵";
            case "green" -> colorEmoji = "🟢";
            case "orange" -> colorEmoji = "🟠";
            case "pink" -> colorEmoji = "🌸";
            case "red" -> colorEmoji = "🔴";
            case "yellow" -> colorEmoji = "🟡";
            case "lightblue" -> colorEmoji = "🌐";
        }
        return colorEmoji;
    }

    public void takeOutMortgage() {
        int mortgage = owner.getMoney() + (int) (price * 0.5);
        if (!hasMortgage()) {
            owner.setMoney(mortgage);
            super.setHasMortgage(true);
            System.out.println(owner + " has got $" + mortgage + " from mortgage for " + this);
        }
    }

    /**
     * demolish building on Street
     * <ul>
     *     <li>removes the last building in buildings</li>
     * </ul>
     */
    public void demolish() {
        buildings.remove(buildings.size() - 1);
    }

    public void determineBuildPrice() {
        switch (color().toLowerCase()) {
            case "orange", "pink" -> buildPrice = 100;
            case "red", "yellow" -> buildPrice = 150;
            case "green", "blue" -> buildPrice = 200;
            default -> buildPrice = 50;
        }
    }

    public boolean ownEntireNeighborhood(Board board) {
        boolean checkOwner = true;
        Street[] currentBoard = board.getAllStreetsOfOneColor(color());
        for (Street street : currentBoard) {
            if (street.owner == null || street.owner != owner) {
                checkOwner = false;
            }
        }
        return checkOwner;
    }

    /**
     * has Empty slot
     * <ul>
     *     <li>returns True if there are less then 4 Buildings on the street</li>
     * </ul>
     */
    public boolean hasEmptySlot() {
        return buildings.size() < 4;
    }

    /**
     * evenly Built
     * checks if the min & max Buildingnumber of each Street is equal or new build + 1 is not larger than max
     *
     * @param board current board
     * @return boolean
     */
    public boolean evenBuilt(Board board) {
        boolean evenBuilt = false;
        Street[] neighborhood = board.getAllStreetsOfOneColor(color());
        int max = Arrays.stream(neighborhood).mapToInt(s -> s.getBuildings().size()).max().orElse(0);
        int min = Arrays.stream(neighborhood).mapToInt(s -> s.getBuildings().size()).min().orElse(0);
        if (min == max) {
            evenBuilt = true;
        } else {
            evenBuilt = this.getBuildings().size() + 1 <= max;
        }
        return evenBuilt;
    }

    /**
     * has Owner
     * <ul>
     *     <li>If Street has no Owner return true</li>
     *     <li>If Street has an Owner return false</li>
     * </ul>
     */
    public boolean hasOwner() {
        return getOwner() != null;
    }

    /**
     * build building on Street
     * <ul>
     *     <li>build Building on Street possibilities House or Hotel </li>
     * </ul>
     */
    public void build(Building building, Board board) {
        String colorEmoji = getColorEmoji();

        if (!hasEmptySlot()) {
            System.out.println(this.name() + " has no space left to build! ❌");
        }

        if (!ownEntireNeighborhood(board)) {
            System.out.println(owner.getName() + " doesn't own all required streets in this neighborhood " + colorEmoji + " ❌");
        }

        if (!owner.checkMoney(buildPrice)) {
            System.out.println(owner.getName() + " doesnt have enough money! ❌");
        }

        if (!evenBuilt(board)) {
            System.out.println(this.name() + " has to be evenly built across all streets in neighborhood " + colorEmoji + " ❌");
        }

        if (hasEmptySlot()
                && ownEntireNeighborhood(board)
                && owner.checkMoney(buildPrice)
                && evenBuilt(board)) {
            buildings.add(building);
            owner.buy(buildPrice);
            System.out.println(this);
        }
    }

    /**
     * Sell Street to Player
     * <ul>
     *     <li>If Player want to buy this Street, this method checks if there is enough money to buy
     *     and Street don't already has an Owner.</li>
     * </ul>
     */
    public void sell(Player newOwner) {
        String colorEmoji = getColorEmoji();

        if (!hasOwner() && newOwner.checkMoney(price)) {
            if (newOwner.buy(price)) {

                this.setOwner(newOwner);
                newOwner.addProperty(this);

                System.out.println(
                        newOwner.getName()
                                + " has bought "
                                + this.name()
                                + " " + colorEmoji
                                + " for $"
                                + this.price
                );
            }
        }

        if (!newOwner.checkMoney(price)) {
            System.out.println(newOwner.getName() + " doesn't have enough money to buy " + this.name());
        }

        if (!hasOwner()) {
            System.out.println(this.name() + " " + colorEmoji + " is already owned by " + owner.getName());
        }
    }

    // Getter & Setter
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    public int getRent() {
        // TODO: Add hotel rent later
        return rents[buildings.size()];
    }
    public int[] getRents() {
        return rents;
    }

    public void setRents(int[] rents) {
        this.rents = rents;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}

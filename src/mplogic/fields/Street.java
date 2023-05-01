package mplogic.fields;

import mplogic.board.Board;
import mplogic.building.Building;
import mplogic.building.Hotel;
import mplogic.building.House;
import mplogic.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        String displayMortgage = hasMortgage() ? "🖍$" + ((int) ((price * 0.5) * 1.1)) : "";
        String displayBuildings = buildings.stream().map(Object::toString).collect(Collectors.joining());
        String displayOwner = owner != null ? " 🔑" + owner.getName() + " " : "";
        String displayPriceRent = owner != null ? " 🧾$" + getRent() : " $" + getPrice();
        String displayState = hasMortgage() ? "🚧" : "🏘";
        return displayState
                + super.name()
                + " " + super.colorEmoji()
                + displayPriceRent
                + displayOwner
                + displayBuildings
                + displayMortgage;
    }

    public void takeOutMortgage() {
        int mortgage = getMortgageValue();
        if (!hasMortgage() && buildings.size() == 0) {
            owner.setMoney(owner.getMoney() + mortgage);
            super.setHasMortgage(true);
            System.out.println(owner + " has got $" + mortgage + " from mortgage for " + this);
        } else {
            System.out.println(this + " is not empty, remove buildings first ❌");
        }
    }

    public void paybackMortgage() {
        int paybackMortgage = (int) (price * 0.55); // half plus 10 %
        if (hasMortgage()) {
            owner.setMoney(owner.getMoney() - paybackMortgage);
            super.setHasMortgage(false);
            System.out.println(owner + " has paid back $" + paybackMortgage + " for " + this);
        } else {
            System.out.println("There is no mortgage on " + this);
        }
    }

    public void sellBuilding() {
        if (buildings.size() > 0) { // Todo check the possibility of evenbuilt? we should implement an other method here
            Building temp = buildings.get(buildings.size() - 1);
            demolish();
            int sellValue = (int) (buildPrice * 0.5);
            owner.setMoney(owner.getMoney() + sellValue);
            System.out.println("Sold " + temp + " for $" + sellValue + " on " + this);
            System.out.println(this);
        } else {
            System.out.println("There is no mplogic.building to sell ❌");
        }
    }

    /**
     * demolish mplogic.building on Street
     * <ul>
     *     <li>removes the last mplogic.building in buildings</li>
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
        if (buildings.size() > 0 && buildings.get(0).toString().equals("🏩")) {
            return false;
        }
        return buildings.size() < 4;
    }
    /**
     * evenly Built
     * checks if the min & max Buildingnumber of each Street is equal or new build + 1 is not larger than max
     *
     * @param board current mplogic.board
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
     * build mplogic.building on Street
     * <ul>
     *     <li>build Building on Street possibilities House or Hotel </li>
     * </ul>
     */
    public void build(Building building, Board board) {
        Street[] neighborhood = board.getAllStreetsOfOneColor(color());
        boolean streetsAllHave4Houses = true;
        for (Street street : neighborhood) {
            if (street.hasEmptySlot()) {
                streetsAllHave4Houses = false;
            }
        }

        if (building instanceof Hotel && streetsAllHave4Houses && owner.checkMoney(buildPrice)) {
            while (buildings.size() > 0) {
                demolish();
            }
            finalBuild(building);
        }
        if (building instanceof Hotel && !streetsAllHave4Houses && !hasMortgage()) {
            System.out.println("neighborhood " + super.colorEmoji() + " doesn't have enough houses built! ❌");
        }

        if (building instanceof House) {
            if (!hasEmptySlot()) {
                System.out.println(this.name() + " has no space left to build! ❌");
            }

            if (!evenBuilt(board)) {
                System.out.println(this.name() + " has to be evenly built across all streets in neighborhood " + super.colorEmoji() + " ❌");
            }

            if (hasEmptySlot()
                    && ownEntireNeighborhood(board)
                    && owner.checkMoney(buildPrice)
                    && evenBuilt(board)
                    && !hasMortgage()) {
                finalBuild(building);
            }
        }
        if (!owner.checkMoney(buildPrice)) {
            System.out.println(owner.getName() + " doesnt have enough money! ❌");
        }
        if (!ownEntireNeighborhood(board)) {
            System.out.println(owner.getName() + " doesn't own all required streets in this neighborhood " + super.colorEmoji() + " ❌");
        }
        if (hasMortgage()) {
            System.out.println("Can't build because of mortgage on property " + this + " ❌");
        }
    }

    private void finalBuild(Building building) {
        buildings.add(building);
        owner.buy(buildPrice);
        System.out.println(this);
    }

    /**
     * Sell Street to Player
     * <ul>
     *     <li>If Player want to buy this Street, this method checks if there is enough money to buy
     *     and Street don't already has an Owner.</li>
     * </ul>
     */
    public void sell(Player newOwner) {
        if (!hasOwner() && newOwner.checkMoney(price)) {
            if (newOwner.buy(price)) {

                this.setOwner(newOwner);
                newOwner.addProperty(this);

                System.out.println(
                        newOwner.getName()
                                + " has bought "
                                + this.name()
                                + " " + super.colorEmoji()
                                + " for $"
                                + this.price
                );
            }
        }

        if (!newOwner.checkMoney(price)) {
            System.out.println(newOwner.getName() + " doesn't have enough money to buy " + this.name());
        }

        if (!hasOwner()) {
            System.out.println(this.name() + " " + super.colorEmoji() + " is already owned by " + owner.getName());
        }
    }

    // Getter & Setter
    public int getBuildPrice() {
        return buildPrice;
    }

    public int getMortgageValue() {
        return (int) (price * 0.5);
    }

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
        if (buildings.stream().filter(b -> b.equals("🏩")).count() == 1) {
            return rents[rents.length - 1];
        }
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

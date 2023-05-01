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
    /**
     * takes out mortage on the property and giving it to owners account
     */
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

    /**
     * pays back mortage on the property by deducting the required amount from the owner
     */
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
     * demolish building on Street
     * removes the last building in buildings<
     */
    public void demolish() {
        buildings.remove(buildings.size() - 1);
    }

    /**
     * Sets the Price for buildings
     */
    public void determineBuildPrice() {
        switch (color().toLowerCase()) {
            case "orange", "pink" -> buildPrice = 100;
            case "red", "yellow" -> buildPrice = 150;
            case "green", "blue" -> buildPrice = 200;
            default -> buildPrice = 50;
        }
    }

    /**
     * Own entire Neighborhood
     * checks if Player owns each street of the specific color
     *
     * @param board
     * @return true if Player owns all streets of the same color
     */
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
     * Checks if property has Empty slot
     *
     * @return True if there are less then 4 Buildings on the street otherwise false
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
     * Checks whether the property has an owner or not.
     *
     * @return true if property has owner, otherwise false
     */
    public boolean hasOwner() {
        return getOwner() != null;
    }


    /**
     * Builds a structure (either a house or a hotel) on a street in a neighborhood of the board, if the necessary conditions are met.
     *
     * @param building the type of structure to build
     * @param board    the game board where the neighborhood and streets are located
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


    public List<Building> getBuildings() {
        return buildings;
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


    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}

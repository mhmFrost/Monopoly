package fields;

import building.Building;
import player.Player;

import java.util.ArrayList;
import java.util.List;

public class Street extends Field {

    private int price;
    private List<Building> buildings = new ArrayList<>();
    private int[] upgradeValues;
    private Player owner;

    public Street(String name, String color, int price) {
        super(name, color);
        this.price = price;
    }

    @Override
    public String toString() {
        return super.name() + ","
                + super.color() + ","
                + getPrice()
                + (owner != null ? "," + owner.getName() + "," : "")
                + (buildings.size() > 0 ? buildings : "");
    }
    /**
     * build building on Street
     * <ul>
     *     <li>build Building on Street possibilities House or Hotel </li>
     * </ul>
     */
    public void build(Building building) {
        buildings.add(building);
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

    /**
     * Sell Street to Player
     * <ul>
     *     <li>If Player want to buy this Street, this method checks if there is enough money to buy
     *     and Street don't already has an Owner.</li>
     * </ul>
     */
    public void sell(Player newOwner) {
        if (getOwner() == null) {
            if (newOwner.getMoney() < this.getPrice()) {
                System.out.println(newOwner.getName() + " don't have enough money to buy this street.");
            } else {
                this.setOwner(newOwner);
                newOwner.setMoney(newOwner.getMoney() - this.getPrice());
                System.out.println(newOwner.getName() + " has bought this street");
            }
        } else {
            System.out.println("This Street already has a Owner.");
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

    public int[] getUpgradeValues() {
        return upgradeValues;
    }

    public void setUpgradeValues(int[] upgradeValues) {
        this.upgradeValues = upgradeValues;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}

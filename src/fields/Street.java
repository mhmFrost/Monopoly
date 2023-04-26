package fields;

import building.Building;
import player.Player;

import java.util.ArrayList;
import java.util.Arrays;
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

    public void build(Building building) {
        buildings.add(building);
    }

    public void demolish() {
        buildings.remove(buildings.size() - 1);
    }

    public void sell(Player newOwner) {
        System.out.println(newOwner.getMoney());
        this.setOwner(newOwner);
        newOwner.setMoney(newOwner.getMoney() - this.getPrice());
        System.out.println(newOwner.getMoney());
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

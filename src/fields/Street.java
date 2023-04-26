package fields;

import building.Building;
import player.Player;

public class Street {
    private int price;
    private Building[] buildings;
    private int[] upgradeValues;
    private Player owner;

    public void build(Building building){
    }
    public void demolish(Building building){
    }
    public void sell(Player newOwner){
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Building[] getBuildings() {
        return buildings;
    }

    public void setBuildings(Building[] buildings) {
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

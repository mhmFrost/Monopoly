package fields;

import building.Building;
import player.Player;

import java.util.Arrays;

public class Street extends Field{

    private int price;
    private Building[] buildings = new Building[4];
    private int[] upgradeValues;
    private Player owner;

    public Street(String name, String color, int price) {
        super(name, color);
        this.price = price;
    }

    @Override
    public String toString() {
        return super.name() +"," + super.color()+"," + getPrice() + "," + getOwner() + "," +  getBuildings();
    }

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

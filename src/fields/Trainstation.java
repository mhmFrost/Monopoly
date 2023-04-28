package fields;

import player.Player;

public class Trainstation extends Field {
    private int price;
    private Player owner;
    int[] rents = {25, 50, 100, 200};

    public Trainstation(String name, int price) {
        super(name, "none");
        this.price = price;
    }

    public void sell(Player newOwner) {
        if (!hasOwner() && newOwner.checkMoney(price)) {
            if (newOwner.buy(price)) {

                this.setOwner(newOwner);
                newOwner.addProperty(this);

                System.out.println(
                        newOwner.getName()
                                + " has bought "
                                + this
                                + " for $"
                                + this.price
                );
            }
        }

        if (!newOwner.checkMoney(price)) {
            System.out.println(newOwner.getName() + " doesn't have enough money to buy " + this);
        }

        if (!hasOwner()) {
            System.out.println(this + " is already owned by " + owner.getName());
        }
    }

    public Player getOwner() {
        return owner;
    }

    private void setOwner(Player newOwner) {
        this.owner = newOwner;
    }

    private boolean hasOwner() {
        return owner != null;
    }

    public int getRent() {
        var rentFactor = owner.getProperties().stream().filter(f -> f instanceof Trainstation).count();
        return rents[(int) rentFactor];
    }

    @Override
    public String toString() {
        return "🚂" + super.name()
                + (owner != null ? " 🔑" + owner.getName() : "");
    }
}

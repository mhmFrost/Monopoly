package fields;

import player.Player;

public class ServiceField extends Field {
    private int price;
    private Player owner;

    public ServiceField(String name, int price) {
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

    public int getRent() {
        var size = owner.getProperties().stream().filter(s -> s instanceof ServiceField).count();
        return size == 1 ? 4 : 10;
    }

    private void setOwner(Player newOwner) {
        this.owner = newOwner;
    }

    private boolean hasOwner() {
        return owner != null;
    }
    @Override
    public String toString() {
        return super.name()
                + (owner != null ? " 🔑" + owner.getName() : "");

    }
}

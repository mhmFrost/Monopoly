package fields;

import player.Player;

public abstract class Field {
    private String name;
    private String color;
    private boolean hasMortgage;

    public Field(String name, String color) {
        this.name = name;
        this.color = color;
        this.hasMortgage = false;
    }

    public void takeOutMortgage() {
        this.hasMortgage = !hasMortgage;
    };
    public void sell(Player newOwner) {

    };

    public String name() {
        return name;
    }

    public String color() {
        return color;
    }


    public boolean hasMortgage() {
        return hasMortgage;
    }
}

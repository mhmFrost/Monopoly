package fields;

import player.Player;

public abstract class Field {
    private String name;
    private String color;
    private boolean hasMortgage;

    /**
     * Abstract parent class of all fields in the game.
     * @param name e.g. <i>Mediterranean Avenue</i>
     * @param color e.g. <i>red</i>
     */
    public Field(String name, String color) {
        this.name = name;
        this.color = color.toUpperCase();
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

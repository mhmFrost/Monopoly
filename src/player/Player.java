package player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class Player {
    private String name;
    private String color;
    private int score;
    private boolean isInJail;
    private String figurine;
    private Field[] properties;
    private int doubles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isInJail() {
        return isInJail;
    }

    public void setInJail(boolean inJail) {
        isInJail = inJail;
    }

    public String getFigurine() {
        return figurine;
    }

    public void setFigurine(String figurine) {
        this.figurine = figurine;
    }

    public int getDoubles() {
        return doubles;
    }

    public void setDoubles(int doubles) {
        this.doubles = doubles;
    }

    public Field[] getProperties() {
        return properties;
    }

    public void setProperties(Field[] properties) {
        this.properties = properties;
    }

    public void rollDice() {
        Random rand = new Random();
        int diceOne = rand.nextInt(6) + 1;
        int diceTwo = rand.nextInt(6) + 1;
        int diceResult = diceOne + diceTwo;

        if (diceOne == diceTwo) {
            doubles++;
            move(diceResult);
            if (doubles == 3) {
                setInJail(true);
                doubles = 0;
            }
        } else {
            move(diceResult);
            doubles = 0;
        }
    }

    public void checkMortgages() {
    }

    public void drawChanceCard() {
    }

    public void drawCommunityChestCard() {
    }

    public void move(int n) {
    }

    public void payRent(Field Field) {
    }

    public void payTax() {
    }

}

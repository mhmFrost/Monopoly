package player;

import fields.Field;
import fields.TaxField;


import java.util.Random;

public class Player {
    private String name;
    private String color;
    private int score;
    private boolean isInJail;
    private String figurine;
    private Field[] properties;
    private int doubles;
    private int money;


    public void rollDice() {
        Random rand = new Random();
        int diceOne = rand.nextInt(6) + 1;      // roll Dice 1
        int diceTwo = rand.nextInt(6) + 1;      // roll Dice 2
        int diceResult = diceOne + diceTwo;     // result of Dices

        if (diceOne == diceTwo) {               // check if doubles
            doubles++;                          // count doubles
            move(diceResult);
            if (doubles == 3) {                 // if roll 3 doubles go to Jail
                setInJail(true);
                doubles = 0;                    // reset doubles
            }
        } else {                                // no double
            move(diceResult);                   // move
            doubles = 0;                        // reset doubles
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

    public void payRent(Field field) {
    }

    public void payTax(TaxField field) {
        //setMoney(money - field.getTax());
    }

    // Getter and Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
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

}

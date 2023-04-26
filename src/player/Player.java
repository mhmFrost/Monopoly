package player;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Player {
    private String name;
    private String color;
    private int score;
    private boolean isInJail;
    private String figurine;
    private ArrayList[] properties;

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

    public ArrayList[] getProperties() {
        return properties;
    }

    public void setProperties(ArrayList[] properties) {
        this.properties = properties;
    }
    public void checkMortgages(){
    }
    public void drawChanceCard(){
    }
    public void drawCommunityChestCard(){
    }
    public void move(int n){
    }
    public void payRent(Field Field){
    }
    public void payTax(){
    }

}

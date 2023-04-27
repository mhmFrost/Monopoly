package board;

import fields.*;
import player.Player;

import java.util.Arrays;

public class Board {
    private Field[] fields = new Field[40];
    private Player[] players = new Player[8];


    public Board() {
        setupBoard();
    }

    public void setupBoard() {
        String[] streets = {
                "Mediterranean Avenue", "Brown",
                "Baltic Avenue", "Brown",
                "Oriental Avenue", "Lightblue",
                "Vermont Avenue", "Lightblue",
                "Connecticut Avenue", "Lightblue",
                "St. Charles Place", "Pink",
                "States Avenue", "Pink",
                "Virginia Avenue", "Pink",
                "St. James Place", "Orange",
                "Tennessee Avenue", "Orange",
                "New York Avenue", "Orange",
                "Kentucky Avenue", "Red",
                "Indiana Avenue", "Red",
                "Illinois Avenue", "Red",
                "Atlantic Avenue", "Yellow",
                "Ventnor Avenue", "Yellow",
                "Marvin Gardens", "Yellow",
                "Pacific Avenue", "Green",
                "North Carolina Avenue", "Green",
                "Pennsylvania Avenue", "Green",
                "Park Place", "Blue",
                "Boardwalk", "Blue"
        };

        String[] railroads = {
                "Reading Railroad",
                "Pennsylvania Railroad",
                "B&O Railroad",
                "Short Line",
        };

        int[] streetPrices = {
                60,
                60,
                100,
                100,
                120,
                140,
                140,
                160,
                180,
                180,
                200,
                220,
                220,
                240,
                260,
                260,
                280,
                300,
                300,
                320,
                350,
                400
        };


        int streetCounter = 0;
        int priceCounter = 0;
        int railRoadCounter = 0;
        for (int fieldID = 0; fieldID < fields.length; fieldID++) {
            switch (fieldID) {
                case 0 -> fields[fieldID] = new GoField();
                case 2, 17, 33 -> fields[fieldID] = new CommunityChest();
                case 4, 38 -> fields[fieldID] = new TaxField(fieldID % 4 == 0 ? "Income Tax" : "Luxury Tax");
                case 5, 15, 25, 35 -> fields[fieldID] = new Trainstation(railroads[railRoadCounter++]);
                case 7, 22, 36 -> fields[fieldID] = new ChanceField();
                case 10 -> fields[fieldID] = new JailField();
                case 12, 28 ->
                        fields[fieldID] = new ServiceField(fieldID % 12 == 0 ? "⚡️Electric Company " : "💧Water Works");
                case 20 -> fields[fieldID] = new FreeParkingField();
                case 30 -> fields[fieldID] = new GoToJailField();

                default -> {
                    fields[fieldID] = new Street(streets[streetCounter], streets[streetCounter + 1], streetPrices[priceCounter++]);
                    streetCounter += 2;
                }
            }
        }
    }

    public Field[] getFields() {
        return fields;
    }

    public Street getStreetByName(String streetName) {
        return (Street) Arrays.stream(fields).filter(f -> f.name().equals(streetName)).toList().get(0);
    }

    public Street[] getAllStreets() {
        return (Street[]) Arrays.stream(fields).filter(f -> f instanceof Street).toArray();
    }

    public Street[] getAllStreetsOfOneColor(String color) {
        Object[] temp = Arrays.stream(fields).filter(f -> f instanceof Street && f.color().toLowerCase().equals(color.toLowerCase())).toArray();
        Street[] streets = new Street[temp.length];
        for (int i = 0; i < temp.length; i++) {
            streets[i] = (Street) temp[i];
        }
        return streets;
    }

    public void printBoard() {
        Arrays.stream(fields).forEach(System.out::println);
    }
}

package board;

import fields.*;
import player.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Board {
    private Field[] fields = new Field[40];
    private Player[] players = new Player[8];


    /**
     * Initializes game board with 40 spaces in its US version of <i>Monopoly</i>.
     */
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
                case 4, 38 ->
                        fields[fieldID] = new TaxField(fieldID % 4 == 0 ? "Income Tax" : "Luxury Tax", fieldID % 4 == 0 ? 200 : 100);
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

    /**
     * Gets all fields on the board and returns them as Field[].
     *
     * @return Field[]
     * @see Field
     * @see Street
     * @see Trainstation
     * @see ServiceField
     * @see TaxField
     * @see CommunityChest
     * @see ChanceField
     */
    public Field[] getFields() {
        return fields;
    }

    /**
     * Get a Street-object by a given name.
     *
     * @param streetName e.g. <i>Mediterranean Avenue</i>
     * @return Street-object
     * @see Street
     */
    public Street getStreetByName(String streetName) {
        return (Street) Arrays.stream(fields).filter(f -> f.name().equals(streetName)).toList().get(0);
    }

    /**
     * Returns all streets on the board as Street[].
     *
     * @return Street[]
     * @see Street
     */
    public Street[] getAllStreets() {
        return (Street[]) Arrays.stream(fields).filter(f -> f instanceof Street).toArray();
    }

    /**
     * Returns an array of all streets that have the same color.
     *
     * @param color e.g. <i>red</i>
     * @return Street[]
     */
    public Street[] getAllStreetsOfOneColor(String color) {
        Object[] temp = Arrays.stream(fields).filter(f -> f instanceof Street && f.color().toLowerCase().equals(color.toLowerCase())).toArray();

        Street[] streets = new Street[temp.length];
        for (int i = 0; i < temp.length; i++) {
            streets[i] = (Street) temp[i];
        }

        return streets;
    }

    /**
     * Prints entire board to console, with each space being represented on a line.<br><br>
     * <i>Example Output:</i><br>
     * 🚀GO<br>
     * Mediterranean Avenue,Brown,60<br>
     * ♦️Community Chest<br>
     * Baltic Avenue,Brown,60<br>
     * 🏦Income Tax<br>
     * 🚂Reading Railroad<br>
     * ...
     */
    public void printBoard() {
        Arrays.stream(fields).forEach(System.out::println);
    }

    /**
     * Lists all streets of a given color.<br><br>
     * <i>Example Output:</i><br>
     * BROWN 🏘<br>
     * -	Mediterranean Avenue,Brown,60,Tim,[🏠]<br>
     * -	Baltic Avenue,Brown,60,Tim,<br>
     *
     * @param color "blue"
     */
    public void printStreetsOfOneColor(String color) {
        Street[] neighborhood = getAllStreetsOfOneColor(color);

        String output = "\n" + color.toUpperCase() + " 🏘\n-\t";
        output += Arrays
                .stream(neighborhood)
                .map(Street::toString)
                .collect(Collectors.joining("\n-\t"));

        System.out.println(output);
    }
}

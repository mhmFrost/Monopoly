package mplogic.board;

import mplogic.fields.*;
import mplogic.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Board {
    private Field[] fields = new Field[40];
    private Player[] players = new Player[8];


    /**
     * Initializes game mplogic.board with 40 spaces in its US version of <i>Monopoly</i>.
     */
    public Board() {
        setupBoard();
    }

    /**
     * Initializes game board with 40 spaces in its US version of <i>Monopoly</i>.
     * Max: 8 Players
     *
     * @param playersAtStart Player[]
     * @see Player
     */
    public Board(Player[] playersAtStart) {
        this();
        for (Player player : playersAtStart) {
            add(player);
        }
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
                60,     // Mediterranean Avenue, Brown
                60,     // Baltic Avenue, Brown
                100,    // Oriental Avenue, Lightblue
                100,    // Vermont Avenue, Lightblue
                120,    // Connecticut Avenue, Lightblue
                140,    // St. Charles Place, Pink
                140,    // States Avenue, Pink
                160,    // Virginia Avenue, Pink
                180,    // St. James Place, Orange
                180,    // Tennessee Avenue, Orange
                200,    // New York Avenue, Orange
                220,    // Kentucky Avenue, Red
                220,    // Indiana Avenue, Red
                240,    // Illinois Avenue, Red
                260,    // Atlantic Avenue, Yellow
                260,    // Ventnor Avenue, Yellow
                280,    // Marvin Gardens, Yellow
                300,    // Pacific Avenue, Green
                300,    // North Carolina Avenue, Green
                320,    // Pennsylvania Avenue, Green
                350,    // Park Place, Blue
                400     // Boardwalk, Blue
        };

        int[][] rents = {
                {2, 10, 30, 90, 160, 250},          // Mediterranean Avenue, Brown
                {4, 20, 60, 180, 320, 450},         // Baltic Avenue, Brown
                {6, 30, 90, 270, 400, 550},         // Oriental Avenue, Lightblue
                {6, 30, 90, 270, 400, 550},         // Vermont Avenue, Lightblue
                {8, 40, 100, 300, 450, 600},        // Connecticut Avenue, Lightblue
                {10, 50, 150, 450, 625, 750},       // St. Charles Place, Pink
                {10, 50, 150, 450, 625, 750},       // States Avenue, Pink
                {12, 60, 180, 500, 700, 900},       // Virginia Avenue, Pink
                {14, 70, 200, 550, 750, 950},       // St. James Place, Orange
                {14, 70, 200, 550, 750, 950},       // Tennessee Avenue, Orange
                {16, 80, 220, 600, 800, 1000},      // New York Avenue, Orange
                {18, 90, 250, 700, 875, 1050},      // Kentucky Avenue, Red
                {18, 90, 250, 700, 875, 1050},      // Indiana Avenue, Red
                {20, 100, 300, 750, 925, 1100},     // Illinois Avenue, Red
                {22, 110, 330, 800, 975, 1150},     // Atlantic Avenue, Yellow
                {22, 110, 330, 800, 975, 1150},     // Ventnor Avenue, Yellow
                {22, 120, 360, 850, 1025, 1200},    // Marvin Gardens, Yellow
                {26, 130, 390, 900, 1100, 1275},    // Pacific Avenue, Green
                {26, 130, 390, 900, 1100, 1275},    // North Carolina Avenue, Green
                {28, 150, 450, 1000, 1200, 1400},   // Pennsylvania Avenue, Green
                {35, 175, 500, 1100, 1300, 1500},   // Park Place, Blue
                {50, 200, 600, 1400, 1700, 2000},   // Boardwalk, Blue
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
                case 5, 15, 25, 35 -> fields[fieldID] = new Trainstation(railroads[railRoadCounter++], 200);
                case 7, 22, 36 -> fields[fieldID] = new ChanceField();
                case 10 -> fields[fieldID] = new JailField();
                case 12, 28 ->
                        fields[fieldID] = new ServiceField(fieldID % 12 == 0 ? "⚡️Electric Company" : "💧Water Works", 150);
                case 20 -> fields[fieldID] = new FreeParkingField();
                case 30 -> fields[fieldID] = new GoToJailField();

                default -> {
                    fields[fieldID] = new Street(streets[streetCounter], streets[streetCounter + 1], streetPrices[priceCounter], rents[priceCounter++]);
                    streetCounter += 2;
                }
            }
        }
    }

    public void add(Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                players[i] = player;
                break;
            }
        }
    }

    /**
     * Gets all players on the board.
     * @return Player[]
     * @see Player
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Gets all fields on the board and returns them as Field[].
     *
     * @return Field[], e.g. [🚀GO, 🏘Mediterranean Avenue 🟤 $60, 🏘Baltic Avenue 🟤 $60, ♦️Community Chest, ...]
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
     * @param name String, e.g. <i>Mediterranean Avenue</i>
     * @return Street-object, e.g. 🏘Mediterranean Avenue 🟤 $60
     * @see Street
     */
    public Street getStreetByName(String name) {
        return (Street) Arrays.stream(fields).filter(f -> f.name().equals(name)).toList().get(0);
    }

    /**
     * Get a ServiceField-object by a given name.
     *
     * @param name String, e.g. <i>Electric Company</i>
     * @return ServiceField-object, e.g. ⚡️Electric Company
     * @see ServiceField
     */
    public ServiceField getServiceFieldByName(String name) {
        return (ServiceField) Arrays.stream(fields).filter(f -> f.name().equals(name)).toList().get(0);
    }

    /**
     * Get a Trainstation-object by a given name.
     *
     * @param name String, e.g. <i>Reading Railroad</i>
     * @return Trainstation-object, e.g. 🚂Reading Railroad
     * @see Trainstation
     */
    public Trainstation getTrainstationByName(String name) {
        return (Trainstation) Arrays.stream(fields).filter(f -> f.name().equals(name)).toList().get(0);
    }

    /**
     * Returns all streets on the board as Street[].
     *
     * @return Street[], e.g. [🏘Mediterranean Avenue 🟤 $60, 🏘Baltic Avenue 🟤 $60, 🏘Oriental Avenue 🌐 $100, ...]
     * @see Street
     */
    public Street[] getAllStreets() {
        Object[] temp = Arrays.stream(fields).filter(f -> f instanceof Street).toArray();
        Street[] streets = new Street[temp.length];
        for (int s = 0; s < streets.length; s++) {
            streets[s] = (Street) temp[s];
        }
        return streets;
    }

    /**
     * Returns an array of all streets that have the same color.
     *
     * @param color String, e.g. <i>brown</i>
     * @return Street[], e.g. [🏘Mediterranean Avenue 🟤 $60, 🏘Baltic Avenue 🟤 $60]
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
     * Prints entire mplogic.board to console, with each space being represented on a line.<br><br>
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
        for (int i = 0; i < fields.length; i++) {
            List<Player> currentPlayersAtPositionI = new ArrayList<>();
            for (Player player : players) {
                if (player != null && player.getPosition() == i) {
                    currentPlayersAtPositionI.add(player);
                }
            }
            System.out.println(fields[i] + " " + currentPlayersAtPositionI.stream().map(Player::getFigurine).collect(Collectors.joining(" ")));
        }
    }

    /**
     * Lists all streets of a given color.<br><br>
     * <i>Example Output:</i><br>
     * BROWN 🏘<br>
     * -	Mediterranean Avenue,Brown,60,Tim,[🏠]<br>
     * -	Baltic Avenue,Brown,60,Tim,<br>
     *
     * @param color String, e.g. 'blue'
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

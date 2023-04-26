package board;

import fields.Field;
import player.Player;

public class Board {
    private Field[] fields = new Field[40];
    private Player[] players = new Player[8];


    private void setupBoard() {
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

/*        for (int i = 0; i < streetPrices.length; i++) {
            fields[i + 1] =
        }*/
    }
}

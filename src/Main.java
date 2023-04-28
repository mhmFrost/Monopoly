import board.Board;
import building.House;
import fields.*;
import player.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Math.abs;

public class Main {
    private static int currentPlayerID = 0;
    private static List<Player> players;
    private static Scanner scanner = new Scanner(System.in);
    private static Player activePlayer;
    private static Board board;

    public static void main(String[] args) {
        String input = "";

        Player tim = new Player("Tim", "Orange", "💩");
        Player max = new Player("Max", "red", "☀️");

        board = new Board();
        board.add(tim);
        board.add(max);

        //Todo: Remove following two lines for fresh start
        board.getStreetByName("Mediterranean Avenue").sell(tim);
        board.getStreetByName("Baltic Avenue").sell(tim);
        players = Arrays.stream(board.getPlayers()).filter(Objects::nonNull).toList();

        while (true) {
            board.printBoard();
            System.out.println(players.get(currentPlayerID).getName() + ", press any key to roll dice 🎲🎲");

            activePlayer = players.get(currentPlayerID);
            Field startField = getField();
            int diceSum = activePlayer.rollDice();

            input = scanner.nextLine();
            if (onExit(input)) break; // type 'exit' to exit game
            openPropertiesDialog(input); // type 'prop' to see dialog
            openMortgageDialog(input); // type 'mort' to see dialog

            activePlayer.move(diceSum);
            Field endField = getField();

            openSellDialog(endField);
            openChanceCommunityFieldDialog(endField);

            printMove(startField, endField);
            passTurnToPlayer();
        }
    }

    private static void openMortgageDialog(String input) {
        if (input.toLowerCase().equals("mort")) {
            System.out.println("1 Would you like to take out a mortgage?");
            if (activePlayer.getProperties().stream().filter(Field::hasMortgage).toList().size() > 0) {
                System.out.println("2 Would you like to pay back a mortgage?");
            }
            int option = scanner.nextInt();
            switch (option) {
                case 2 -> {
                    activePlayer.getProperties().stream().filter(Field::hasMortgage).forEach(p -> System.out.println((activePlayer.getProperties().indexOf(p) + 1) + " " + p));
                    System.out.print("Enter number of property you would like to pay back the mortgage for?");
                    option = scanner.nextInt() - 1;
                    ((Street) activePlayer.getProperties().get(option)).paybackMortgage();
                }
                default -> {
                    activePlayer.getProperties().forEach(p -> System.out.println((activePlayer.getProperties().indexOf(p) + 1) + " " + p));
                    System.out.print("Enter number of property you would like to out the mortgage on?");
                    option = scanner.nextInt() - 1;
                    ((Street) activePlayer.getProperties().get(option)).takeOutMortgage();
                }
            }
        }
    }

    private static void openPropertiesDialog(String input) {
        if (input.toLowerCase().equals("prop")) {
            List<Field> temp = activePlayer.getProperties();
            if (temp.size() > 0) {
                System.out.println("Your properties:");
                activePlayer.getProperties().forEach(p -> System.out.println(p.toString().substring(0, p.toString().indexOf("🔑"))));
                var canBuildStreets = activePlayer.getProperties().stream().filter(s -> s instanceof Street).filter(s -> ((Street) s).ownEntireNeighborhood(board)).toList();
                if (canBuildStreets.size() > 0) {
                    System.out.println("You can build, would you like to?");
                    System.out.print("Enter 'true' for yes and 'false' for no:");
                    boolean yes = scanner.nextBoolean();
                    buildDialog(canBuildStreets, yes);
                }
            } else {
                System.out.println("No properties to see here 👀");
            }
            scanner.nextLine();
        }
    }

    private static boolean buildDialog(List<Field> canBuildStreets, boolean yes) {
        if (!yes) {
            return false;
        }
        if (yes) {
            canBuildStreets.stream().forEach(s -> System.out.println((canBuildStreets.indexOf(s) + 1) + " " + s));
            System.out.print("Enter number which you would like to build on:");
            int buildOn = scanner.nextInt() - 1;
            if (buildOn > -1) {
                ((Street) canBuildStreets.get(buildOn)).build(new House(), board);
            }
            System.out.println("Would you like to build more?");
            yes = scanner.nextBoolean();
        }
        return buildDialog(canBuildStreets, yes);
    }

    private static void openChanceCommunityFieldDialog(Field endField) {
        if (endField instanceof CommunityChest || endField instanceof ChanceField) {
            activePlayer.drawCard(endField);
        }
    }

    private static void openSellDialog(Field endField) {
        if (endField instanceof Street && !((Street) endField).hasOwner()) {
            printSellDialog(endField);
        }
        if (endField instanceof Trainstation && !((Trainstation) endField).hasOwner()) {
            printSellDialog(endField);
        }
        if (endField instanceof ServiceField && !((ServiceField) endField).hasOwner()) {
            printSellDialog(endField);
        }

        //pay rent where necessary
        if (endField instanceof Street && ((Street) endField).hasOwner() && activePlayer != ((Street) endField).getOwner()) {
            activePlayer.payRent(endField);
        }
        if (endField instanceof Trainstation && ((Trainstation) endField).hasOwner() && activePlayer != ((Trainstation) endField).getOwner()) {
            activePlayer.payRent(endField);
        }
        if (endField instanceof ServiceField && ((ServiceField) endField).hasOwner() && activePlayer != ((ServiceField) endField).getOwner()) {
            activePlayer.payRent(endField);
        }

        //pay tax where necessary
        if (endField instanceof TaxField) {
            activePlayer.payTax(endField);
        }

        //go to jail if you land on GoToJailField
        if (endField instanceof GoToJailField) {
            endField.action(activePlayer);
        }

        //go to FreeParkingField
        if (endField instanceof FreeParkingField) {
            endField.action(activePlayer);
        }
    }

    private static void printSellDialog(Field endField) {
        System.out.println(activePlayer + ", would you like to buy " + endField);
        System.out.print("Enter 'true' for yes and 'false' for no:");
        boolean yes = scanner.nextBoolean();
        if (yes) {
            endField.sell(activePlayer);
        }
        scanner.nextLine();
    }

    private static void printMove(Field startField, Field endField) {
        System.out.println(activePlayer.getName() + " moves from " + startField.name() + " --- " + calculateDistance(startField, endField) + " ---> " + endField.name());
        scanner.nextLine();
    }

    private static Field getField() {
        Field startField = board.getFields()[activePlayer.getPosition()];
        return startField;
    }

    private static int calculateDistance(Field startField, Field endField) {
        int distance = Arrays.asList(board.getFields()).indexOf(endField) - Arrays.asList(board.getFields()).indexOf(startField);
        if (distance < 0) {
            distance = 40 % abs(distance);
        }
        return distance;
    }

    private static boolean onExit(String input) {
        if (input.toLowerCase().equals("exit")) {
            System.out.println("Exiting game...");
            return true;
        }
        return false;
    }

    private static void passTurnToPlayer() {
        currentPlayerID = currentPlayerID + 1 % players.size();
        if (currentPlayerID > players.size() - 1) {
            currentPlayerID = 0;
        }
    }
}

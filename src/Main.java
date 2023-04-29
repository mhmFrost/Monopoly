import board.Board;
import building.Hotel;
import building.House;
import fields.*;
import player.Player;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

public class Main {
    private static int currentPlayerID = 0;
    private static List<Player> players;
    private static Scanner scanner = new Scanner(System.in);
    private static Player activePlayer;
    private static Board board;

    public static void main(String[] args) {
        String input = "";

        Player tim = new Player("Tim", "orange", "💩");
        Player max = new Player("Max", "red", "☀️");
        Player lars = new Player("Lars", "green", "🤖");

        board = new Board(new Player[]{tim, max, lars});

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
            openPropertiesDialog(input); // type 'pro' to see dialog
            openMortgageDialog(input); // type 'mor' to see dialog

            activePlayer.move(diceSum);
            Field endField = getField();

            openSellDialog(endField);
            openChanceCommunityFieldDialog(endField);

            printMove(startField, endField);
            passTurnToPlayer();
        }
    }

    private static void openMortgageDialog(String input) {
        boolean enteredMortgageKeyword = enteredKeyword(input, "mortgage", "mort", "mor");

        if (enteredMortgageKeyword && activePlayer.checkMortgages()) {
            printBankWelcomeMessage();
            boolean yes = true;
            printMortgageOptions(yes);
        }
        if (enteredMortgageKeyword && !activePlayer.checkMortgages()) {
            System.out.println("You have no properties, therefore you can't take out any mortgages.");
        }
    }

    /**
     * <i>🏦 Welcome to Siliconopoly Bank, how may we help you?</i>
     */
    private static void printBankWelcomeMessage() {
        System.out.println("🏦 Welcome to Siliconopoly Bank, how may we help you?");
    }

    /**
     * <i>1 💸 Would you like to take out a mortgage?<br>
     * 2 🖍 Would you like to pay back a mortgage?</i>
     */
    private static void printBankActions() {
        System.out.println("1 💸 Would you like to take out a mortgage?");
        if (activePlayer.getProperties().stream().filter(Field::hasMortgage).toList().size() > 0) {
            System.out.println("2 🖍 Would you like to pay back a mortgage?");
        }
    }

    private static boolean
    printMortgageOptions(boolean yes) {
        if (!yes) {
            return false;
        }
        if (yes) {
            System.out.println("Account:" + activePlayer);
            printBankActions();
            int option = scanner.nextInt();
            final List<Field> properties = activePlayer.getProperties();
            switch (option) {
                case 2 -> {
                    properties.stream().filter(Field::hasMortgage).forEach(p -> System.out.println((properties.indexOf(p) + 1) + " " + p));
                    System.out.print("Enter number of property you would like to pay back the mortgage for?");
                    option = scanner.nextInt() - 1;
                    ((Street) properties.get(option)).paybackMortgage();
                }
                default -> {
                    properties.forEach(p -> {
                        Street street = (Street) p;
                        System.out.println((properties.indexOf(p) + 1) + " " + street + "💸$" + street.getMortgageValue() +  " 🖍$" + (int)(street.getMortgageValue() * 1.1));
                    });
                    System.out.print("Enter number of property you would like to out the mortgage on?");
                    option = scanner.nextInt() - 1;
                    ((Street) properties.get(option)).takeOutMortgage();
                }
            }
        }
        System.out.println("Can we help you with anything else?");
        yes = enteredKeyword(scanner.nextLine(), "yes", "ye", "y");
        scanner.nextLine();
        return printMortgageOptions(yes);
    }

    /**
     * Checks if user entered keyword somewhat correctly.
     * Case-insensitive.
     * @param input <i>shortkey</i>
     * @param keyword1 <i>longkeyword</i>
     * @param keyw2 <i>shortkey</i>
     * @param key3 <i>key</i>
     * @return boolean
     */
    private static boolean enteredKeyword(String input, String keyword1, String keyw2, String key3) {
        boolean enteredKeyword = input.equalsIgnoreCase(keyword1);
        boolean enteredKeyw = input.equalsIgnoreCase(keyw2);
        boolean enteredKey = input.equalsIgnoreCase(key3);
        return enteredKeyword || enteredKeyw || enteredKey;
    }

    /**
     * Prints list of properties.<br>
     *<i>Your properties:<br>
     * 🏘Mediterranean Avenue 🟤 🧾$2<br>
     * 🏘Baltic Avenue 🟤 🧾$4<br>
     * 👷You can build, would you like to?</i>
     */
    private static void openPropertiesDialog(String input) {
        if (enteredKeyword(input, "properties", "prop", "pro")) {
            List<Field> temp = activePlayer.getProperties();
            if (temp.size() > 0) {
                printPropertiesOfPlayer();
                List<Field> canBuildStreets = printUpgradableStreets();
                askIfWantsToBuildMore(canBuildStreets);
            } else {
                System.out.println("No properties to see here 👀");
            }
            scanner.nextLine();
        }
    }

    private static void askIfWantsToBuildMore(List<Field> canBuildStreets) {
        if (canBuildStreets.size() > 0) {
            System.out.println("👷You can build, would you like to?");
            System.out.print("Enter yes or anything else for no:");
            boolean yes = enteredKeyword(scanner.next(), "yes", "ye", "y");
            buildDialog(canBuildStreets, yes);
        }
    }

    /**
     *Prints upgradable streets.<br>
     * <i>1 🏘Mediterranean Avenue 🟤 🧾$2 BUILD 🧱$50<br>
     *   $2 🏠$10 🏠🏠$30 🏠🏠🏠$90 🏠🏠🏠🏠$160 🏩$250<br>
     * 2 🏘Baltic Avenue 🟤 🧾$4 BUILD 🧱$50<br>
     *   $4 🏠$20 🏠🏠$60 🏠🏠🏠$180 🏠🏠🏠🏠$320 🏩$450</i>
     */
    private static List<Field> printUpgradableStreets() {
        var canBuildStreets = activePlayer.getProperties().stream().filter(s -> s instanceof Street).filter(s -> ((Street) s).ownEntireNeighborhood(board)).toList();
        return canBuildStreets;
    }

    private static void printPropertiesOfPlayer() {
        System.out.println("Your properties:");
        activePlayer.getProperties().forEach(p -> System.out.println(p.toString().substring(0, p.toString().indexOf("🔑"))));
    }

    private static boolean buildDialog(List<Field> canBuildStreets, boolean yes) {
        if (!yes) {
            return false;
        }
        if (yes) {
            printStreetsWithPossibleUpgrades(canBuildStreets);

            System.out.print("Enter number which you would like to build on.\n Enter invalid number to escape:");
            int buildOn = scanner.nextInt() - 1;
            boolean enteredValidNum = buildOn > -1 && buildOn < canBuildStreets.size();

            if (enteredValidNum) {
                determineUpgradeForStreet(canBuildStreets, buildOn);
            } else {
                System.out.println("Alrighty, no construction here, yet 😉.");
                return false;
            }
            System.out.println("Would you like to build more?");
            scanner.nextLine();
            yes = enteredKeyword(scanner.next(), "yes", "ye", "y");
        }
        return buildDialog(canBuildStreets, yes);
    }

    private static void determineUpgradeForStreet(List<Field> canBuildStreets, int buildOn) {
        Street streetToBuildOn = (Street) canBuildStreets.get(buildOn);
        if (streetToBuildOn.getBuildings().size() > 3) {
            streetToBuildOn.build(new Hotel(), board);
        } else {
            streetToBuildOn.build(new House(), board);
        }
    }

    private static void printStreetsWithPossibleUpgrades(List<Field> canBuildStreets) {
        canBuildStreets.stream().forEach(field -> {
            String streetNum = (canBuildStreets.indexOf(field) + 1) + " ";
            Street street = (Street) field;
            String streetName = field.toString();
            streetName = streetName.substring(0, streetName.indexOf("🔑"));
            String printBuildPrice = "BUILD 🧱$" + street.getBuildPrice() + " ";
            
            System.out.println(streetNum
                    + streetName
                    + printBuildPrice + "\n  "
                    + printHouseUpgrades(street));
        });
    }

    private static String printHouseUpgrades(Street street) {
        return Arrays.stream(street.getRents())
                .mapToObj(r ->
                        {
                            int numHouses = Arrays.binarySearch(street.getRents(), r);
                            return "🏠".repeat(numHouses)
                                    .replace("🏠🏠🏠🏠🏠", "🏩")
                                    + "$" + r;
                        }
                ).collect(Collectors.joining(" "));
    }

    private static void openChanceCommunityFieldDialog(Field endField) {
        if (endField instanceof CommunityChest || endField instanceof ChanceField) {
            activePlayer.drawCard(endField);
        }
    }

    private static void openSellDialog(Field endField) {
        //try selling property to player
        ifLandsOnPropertyForSale(endField);

        //pay rent where necessary
        ifLandsOnPropertyThatIsOwned(endField);

        //pay tax where necessary
        ifLandsOnTaxField(endField);

        //go to jail if you land on GoToJailField
        ifLandsOnGoToJailField(endField);

        //go to FreeParkingField
        ifLandsOnFreeParkingField(endField);
    }

    private static void ifLandsOnFreeParkingField(Field endField) {
        if (endField instanceof FreeParkingField) {
            endField.action(activePlayer);
        }
    }

    private static void ifLandsOnGoToJailField(Field endField) {
        if (endField instanceof GoToJailField) {
            endField.action(activePlayer);
        }
    }

    private static void ifLandsOnTaxField(Field endField) {
        if (endField instanceof TaxField) {
            activePlayer.payTax(endField);
        }
    }

    private static void ifLandsOnPropertyThatIsOwned(Field endField) {
        if (endField instanceof Street && ((Street) endField).hasOwner() && activePlayer != ((Street) endField).getOwner()) {
            activePlayer.payRent(endField);
        }
        if (endField instanceof Trainstation && ((Trainstation) endField).hasOwner() && activePlayer != ((Trainstation) endField).getOwner()) {
            activePlayer.payRent(endField);
        }
        if (endField instanceof ServiceField && ((ServiceField) endField).hasOwner() && activePlayer != ((ServiceField) endField).getOwner()) {
            activePlayer.payRent(endField);
        }
    }

    private static void ifLandsOnPropertyForSale(Field endField) {
        if (endField instanceof Street && !((Street) endField).hasOwner()) {
            printSellDialog(endField);
        }
        if (endField instanceof Trainstation && !((Trainstation) endField).hasOwner()) {
            printSellDialog(endField);
        }
        if (endField instanceof ServiceField && !((ServiceField) endField).hasOwner()) {
            printSellDialog(endField);
        }
    }

    private static void printSellDialog(Field endField) {
        System.out.println(activePlayer + ", would you like to buy " + endField);
        System.out.print("Enter yes or anything else for no:");
        boolean yes = enteredKeyword(scanner.nextLine(), "yes", "ye", "y");
        if (yes) {
            endField.sell(activePlayer);
        }
        scanner.nextLine();
    }

    private static void printMove(Field startField, Field endField) {
        System.out.println(activePlayer.getName() + " moved from " + startField.name() + " --- " + calculateDistance(startField, endField) + " ---> " + endField.name());
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

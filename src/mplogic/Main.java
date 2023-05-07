package mplogic;

import mplogic.board.Board;
import mplogic.building.Hotel;
import mplogic.building.House;
import mplogic.fields.*;
import mplogic.player.Player;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

public class Main {
    private static int currentPlayerID = 0;
    private static List<Player> players;
    private static final Scanner scanner = new Scanner(System.in);
    private static Player activePlayer;
    private static Board board;
    private static int diceSum;

    public static void main(String[] args) {
        String input = "";

        //TODO: Remove hard coded players and insert start menu instead
        Player tim = new Player("Tim", "orange", "💩");
        Player max = new Player("Max", "red", "☀️");
        Player lars = new Player("Lars", "green", "🤖");

        board = new Board(new Player[]{tim, max, lars});

        //Todo: Remove following two lines for fresh start

        players = Arrays.stream(board.getPlayers()).filter(Objects::nonNull).toList();

        while (true) {
            board.printBoard();
            System.out.println(players.get(currentPlayerID).getName() + ", press any key to roll dice 🎲🎲");

            activePlayer = players.get(currentPlayerID);
            Field startField = getField();
            diceSum = activePlayer.rollDice();

            input = scanner.nextLine();
            if (onExit(input)) break; // type 'exit' to exit game
            openPropertiesDialog(input); // type 'pro' to see dialog
            openMortgageDialog(input); // type 'mor' to see dialog

            openJailDialog();
            activePlayer.move(diceSum);
            Field endField = getField();

            openSellDialog(endField);
            openChanceCommunityFieldDialog(endField);

            printMove(startField, endField);
            passTurnToPlayer();
        }
    }

    private static void openJailDialog() {
        if (activePlayer.isInJail()) {
            System.out.println("Good morning, cupcake 🧁");
            System.out.println("Wanna try your luck? 🎲🎲");
            System.out.print("Enter yes or anything else for no:");
            boolean wantsOutOfJail = enteredKeyword(scanner.nextLine(), "yes", "ye", "y");
            if (wantsOutOfJail) {
                activePlayer.rollDice();
                if (activePlayer.isHasDouble()) {
                    System.out.println("🎉 Congratulations, you made it of jail. We're gonna miss ya.");
                    System.out.println(activePlayer.getName() + ", press any key to roll dice 🎲🎲");
                    scanner.nextLine();
                    diceSum = activePlayer.rollDice();
                } else {
                    System.out.println("Not so lucky, hmmm? 😈");
                }
            }
            if (activePlayer.getMoney() > 50) {
                System.out.println("Wanna bail yourself out? 💰");
                System.out.print("Enter yes or anything else for no:");
                boolean wantsToPay = enteredKeyword(scanner.nextLine(), "yes", "ye", "y");
                if (wantsToPay) {
                    activePlayer.setMoney(activePlayer.getMoney() - 50);
                    activePlayer.setInJail(false);
                    System.out.println("You're a free person again...for now 😈 We're gonna miss ya.");
                    System.out.println(activePlayer.getName() + ", press any key to roll dice 🎲🎲");
                    scanner.nextLine();
                    diceSum = activePlayer.rollDice();
                }
            }
        }
    }

    private static void openMortgageDialog(String input) {
        boolean enteredMortgageKeyword = enteredKeyword(input, "mortgage", "mort", "mor");

        if (enteredMortgageKeyword && activePlayer.checkMortgages()) {
            printBankWelcomeMessage();
            printMortgageOptions(true);
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

    /**
     * <i>1 🏘Mediterranean Avenue 🟤 🧾$2 🔑Tim 💸$30 🖍$33<br>
     * 2 🏘Baltic Avenue 🟤 🧾$4 🔑Tim 💸$30 🖍$33<br>
     * Enter number of property you would like to out the mortgage on?</i>
     * @param yes boolean
     * @return boolean, recursive method
     */
    private static boolean printMortgageOptions(boolean yes) {
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
                        System.out.println((properties.indexOf(p) + 1) + " " + street + "💸$" + street.getMortgageValue() + " 🖍$" + (int) (street.getMortgageValue() * 1.1));
                    });
                    System.out.print("Enter number of property you would like to out the mortgage on?");
                    option = scanner.nextInt() - 1;
                    ((Street) properties.get(option)).takeOutMortgage();
                }
            }
            System.out.println("Can we help you with anything else?");
        }
        scanner.nextLine();
        scanner.nextLine();
        yes = enteredKeyword(scanner.nextLine(), "yes", "ye", "y");
        printMortgageOptions(yes);
        return printMortgageOptions(yes);
    }

    /**
     * Checks if user entered keyword somewhat correctly.
     * Case-insensitive.
     *
     * @param input    String, <i>shortkey</i>
     * @param keyword1 String, <i>longkeyword</i>
     * @param keyw2    String, <i>shortkey</i>
     * @param key3     String, <i>key</i>
     * @return boolean 'true', if matches, otherwise 'false'
     */
    private static boolean enteredKeyword(String input, String keyword1, String keyw2, String key3) {
        boolean enteredKeyword = input.equalsIgnoreCase(keyword1.trim());
        boolean enteredKeyw = input.equalsIgnoreCase(keyw2.trim());
        boolean enteredKey = input.equalsIgnoreCase(key3.trim());
        return enteredKeyword || enteredKeyw || enteredKey;
    }

    /**
     * Prints list of properties.<br><br>
     * <i>Your properties:<br>
     * 🏘Mediterranean Avenue 🟤 🧾$2<br>
     * 🏘Baltic Avenue 🟤 🧾$4<br>
     * 👷You can build, would you like to?</i>
     */
    private static void openPropertiesDialog(String input) {
        if (enteredKeyword(input, "properties", "prop", "pro")) {
            List<Field> temp = activePlayer.getProperties();
            if (temp.size() > 0) {
                printPropertiesOfPlayer();
                List<Street> canBuildStreets = printUpgradableStreets();
                askIfWantsToBuildMore(canBuildStreets);
            } else {
                System.out.println("No properties to see here 👀");
            }
            scanner.nextLine();
        }
    }

    /**
     * Ask user if they want to build more.<br><br>
     *
     *
     * @param canBuildStreets List of Streets
     * @see Street
     */
    private static void askIfWantsToBuildMore(List<Street> canBuildStreets) {
        if (canBuildStreets.size() > 0) {
            System.out.println("👷You can build, would you like to?");
            System.out.print("Enter yes or anything else for no:");
            boolean yes = enteredKeyword(scanner.next(), "yes", "ye", "y");
            buildDialog(canBuildStreets, yes);
        }
    }

    /**
     * Prints upgradable streets.<br><br>
     * <i>1 🏘Mediterranean Avenue 🟤 🧾$2 BUILD 🧱$50
     * $2 🏠$10 🏠🏠$30 🏠🏠🏠$90 🏠🏠🏠🏠$160 🏩$250<br><br>
     * 2 🏘Baltic Avenue 🟤 🧾$4 BUILD 🧱$50
     * $4 🏠$20 🏠🏠$60 🏠🏠🏠$180 🏠🏠🏠🏠$320 🏩$450</i>
     */
    private static List<Street> printUpgradableStreets() {
        return activePlayer.getProperties().stream()
                .filter(s -> s instanceof Street)
                .filter(s -> ((Street) s).ownEntireNeighborhood(board))
                .map(s -> (Street) s)
                .toList();
    }

    /**
     * Prints list of properties to console.<br><br>
     * <i>Your properties:<br>
     * 🏘Mediterranean Avenue 🟤 🧾$2<br>
     * 🏘Baltic Avenue 🟤 🧾$4</i>
     */
    private static void printPropertiesOfPlayer() {
        System.out.println("Your properties:");
        activePlayer.getProperties().forEach(p -> {
            String fieldNameKeyRemoved = p.toString().substring(0, p.toString().indexOf("🔑"));
            System.out.println(fieldNameKeyRemoved);
        });
    }

    private static boolean buildDialog(List<Street> canBuildStreets, boolean yes) {
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

    /**
     * Decides automatically whether to place a house or hotel on a street.
     * @param canBuildStreets List of Street
     * @param buildOn number of street in player's property list
     * @see Street
     * @see House
     * @see Hotel
     */
    private static void determineUpgradeForStreet(List<Street> canBuildStreets, int buildOn) {
        Street streetToBuildOn = canBuildStreets.get(buildOn);
        if (streetToBuildOn.getBuildings().size() > 3) {
            streetToBuildOn.build(new Hotel(), board);
        } else {
            streetToBuildOn.build(new House(), board);
        }
    }

    private static void printStreetsWithPossibleUpgrades(List<Street> canBuildStreets) {
        canBuildStreets.stream().forEach(field -> {
            String streetNum = (canBuildStreets.indexOf(field) + 1) + " ";
            Street street = field;
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
        //try selling property to mplogic.player
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

    /**
     * Gets a field based on the active player's position.
     * @return Field
     * @see Player
     * @see Field
     * @see Street
     * @see Trainstation
     * @see ServiceField
     * @see TaxField
     * @see CommunityChest
     * @see ChanceField
     */
    private static Field getField() {
        Field startField = board.getFields()[activePlayer.getPosition()];
        return startField;
    }

    /**
     * Calculates distances between two fields.
     * @param startField Field, e.g. 'Go'
     * @param endField Field, e.g. 'Connecticut Avenue'
     * @return int, e.g. 9
     */
    private static int calculateDistance(Field startField, Field endField) {
        int distance = Arrays.asList(
                board.getFields()).indexOf(endField)
                - Arrays.asList(board.getFields()).indexOf(startField);

        if (distance < 0) {
            distance = 40 % abs(distance);
        }
        return distance;
    }

    /**
     * Ends game without saving.
     * @param input String, 'exit'
     * @return boolean 'true', if matches, otherwise 'false'
     */
    private static boolean onExit(String input) {
        if (input.toLowerCase().equals("exit")) {
            System.out.println("Exiting game...");
            return true;
        }
        return false;
    }

    /**
     * Passes turn to next player.
     */
    private static void passTurnToPlayer() {
        currentPlayerID = currentPlayerID + 1 % players.size();
        if (currentPlayerID > players.size() - 1) {
            currentPlayerID = 0;
        }
    }
}

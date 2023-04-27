import board.Board;
import building.Hotel;
import building.House;
import fields.ChanceField;
import fields.CommunityChest;
import fields.Field;
import fields.Street;
import player.Player;

public class Main {
    public static void main(String[] args) {
        Player tim = new Player("Tim", "Orange");
        Field street = new Street("Badstr.", "Braun", 25);

        ((Street) street).build(new House());
        ((Street) street).build(new House());
        ((Street) street).build(new Hotel());
        ((Street) street).build(new House());
        ((Street) street).build(new House());
        ((Street) street).build(new House());
        ((Street) street).demolish();
        System.out.println(street);
        street.sell(tim);

        Board board = new Board();
        board.printBoard();
        ((CommunityChest)board.getFields()[2]).printAllCards();
    }
}

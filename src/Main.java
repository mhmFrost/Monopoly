import board.Board;
import building.Hotel;
import building.House;
import fields.*;
import player.Player;

public class Main {
    public static void main(String[] args) {
        Player tim = new Player("Tim", "Orange");
        Player max = new Player("Max", "red");

        Board board = new Board();
        board.printBoard();

        board.getStreetByName("Mediterranean Avenue").sell(tim);
        board.getStreetByName("Mediterranean Avenue").build(new House(),board);
        board.getStreetByName("Baltic Avenue").sell(tim);
        board.getStreetByName("Mediterranean Avenue").build(new House(),board);
        board.getStreetByName("Mediterranean Avenue").build(new House(),board);
        board.getStreetByName("Baltic Avenue").build(new House(),board);
        board.getStreetByName("Mediterranean Avenue").build(new House(),board);

        System.out.println(tim);
        tim.payTax(board.getFields()[4]);
        System.out.println(tim);
    }
}

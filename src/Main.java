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

        tim.payTax(board.getFields()[4]);

        board.getTrainstationByName("Reading Railroad").sell(max);


        board.getServiceFieldByName("⚡️Electric Company").sell(max);
        System.out.println(max.getProperties());
        System.out.println(tim.getProperties());

        System.out.println(max);
        System.out.println(tim);
        max.payRent(board.getStreetByName("Baltic Avenue"));
        System.out.println(max);
        System.out.println(tim);
    }
}

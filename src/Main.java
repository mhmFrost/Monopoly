import board.Board;
import building.Hotel;
import building.House;
import fields.*;
import player.Player;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Player tim = new Player("Tim", "Orange");
        Player max = new Player("Max", "red");

        Board board = new Board();
        board.printBoard();

        board.getStreetByName("Mediterranean Avenue").sell(tim);
        board.getStreetByName("Baltic Avenue").sell(tim);
        board.getStreetByName("Mediterranean Avenue").build(new House(),board);
        board.getStreetByName("Baltic Avenue").build(new House(),board);
        board.getStreetByName("Mediterranean Avenue").build(new House(),board);
        board.getStreetByName("Baltic Avenue").build(new House(),board);
        board.getStreetByName("Mediterranean Avenue").build(new House(),board);
        board.getStreetByName("Baltic Avenue").build(new House(),board);
        board.getStreetByName("Mediterranean Avenue").build(new House(),board);
        board.getStreetByName("Baltic Avenue").build(new House(),board);
        board.getStreetByName("Mediterranean Avenue").build(new Hotel(),board);
        board.getStreetByName("Baltic Avenue").build(new Hotel(),board);


    }
}

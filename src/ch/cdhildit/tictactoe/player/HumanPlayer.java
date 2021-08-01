package ch.cdhildit.tictactoe.player;

import ch.cdhildit.tictactoe.board.Board;
import ch.cdhildit.tictactoe.game.GameExitedException;

import java.util.Scanner;
import java.util.Vector;

public class HumanPlayer extends Player {
    private final Scanner scanner;

    HumanPlayer(char symbol, Scanner scanner) {
        super(symbol);
        this.scanner = scanner;
    }

    @Override
    public void makeMove(Board board) {
        Vector<Integer> coordinates = getCoordinates();

        try {
            board.addCell(coordinates.get(0), coordinates.get(1), this.symbol);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Vector<Integer> getCoordinates() {
        Vector<Integer> vector = new Vector<>(2, 2);

        while (true) {
            System.out.printf("%s: enter coordinates or type 'exit' to quit ", this.symbol);
            String input = scanner.nextLine().trim();

            if (input.equals("exit")) {
                throw new GameExitedException();
            }

            String[] coordinates = input.split("\\s");

            // Parse the coordinates into integers
            try {
                vector.add(0, Integer.parseInt(coordinates[0]));
                vector.add(1, Integer.parseInt(coordinates[1]));
                return vector;
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("That was not supposed to happen!");
            }
        }
    }
}

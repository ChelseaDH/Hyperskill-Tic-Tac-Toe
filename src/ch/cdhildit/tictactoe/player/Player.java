package ch.cdhildit.tictactoe.player;

import ch.cdhildit.tictactoe.game.Difficulty;
import ch.cdhildit.tictactoe.board.Board;

import java.util.Scanner;

public abstract class Player {
    protected char symbol;

    Player(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public abstract void makeMove(Board board);

    public static Player NewForType(String type, char symbol, Scanner s) {
        Player player;

        switch (type) {
            case "human":
                player = new HumanPlayer(symbol, s);
                break;
            case "easy":
                player = new EasyAIPlayer(symbol);
                break;
            case "medium":
                player = new MediumAIPlayer(symbol);
                break;
            case "hard":
                player = new HardAIPlayer(symbol);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return player;
    }

    public static boolean isValidPlayer(String player) {
        if (player.equals("human")) {
            return true;
        }

        for (Difficulty d: Difficulty.values()) {
            if (player.equals(d.name().toLowerCase())) {
                return true;
            }
        }

        return false;
    }
}

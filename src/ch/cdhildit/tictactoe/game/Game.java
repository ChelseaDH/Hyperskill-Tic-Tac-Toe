package ch.cdhildit.tictactoe.game;

import ch.cdhildit.tictactoe.board.Board;
import ch.cdhildit.tictactoe.board.State;
import ch.cdhildit.tictactoe.player.AIPlayer;
import ch.cdhildit.tictactoe.player.Player;

import java.util.Scanner;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public Scanner scanner;

    public Game(Scanner scanner) {
        this.scanner = scanner;
    }

    public void play() {
        this.printTicTacToe();

        while (true) {
            System.out.println("To start a new game type 'start'");
            System.out.println("To quit type 'exit'\n");

            switch (this.scanner.nextLine()) {
                case "exit":
                    this.exit();

                    return;

                case "start":
                    setUpNewGame();

                    try {
                        playGame();
                    } catch (GameExitedException $e) {
                        this.exit();
                        return;
                    }

                    break;

                default:
                    System.out.println("Invalid input, please try again\n");
            }
        }
    }

    private void exit() {
        System.out.println("\nBye!");
        System.exit(0);
    }

    private void setUpNewGame() {
        this.board = new Board();

        System.out.println("Select players\n");
        System.out.println("For human: type 'human'\nFor AI: choose one of 'easy', 'medium' or 'hard' to select their difficulty\n");

        this.player1 = getPlayerFromUser("1", 'X', scanner);
        this.player2 = getPlayerFromUser("2", '0', scanner);
        this.currentPlayer = player1;

        // Set opponents
        if (player1 instanceof AIPlayer) {
            ((AIPlayer) player1).setOpponent(player2);
        }

        if (player2 instanceof AIPlayer) {
            ((AIPlayer) player2).setOpponent(player1);
        }

        System.out.println("\nLets begin!");
        this.board.printBoard();
    }

    private Player getPlayerFromUser(String playerName, char playerSymbol, Scanner s) {
        String playerType;

        while (true) {
            System.out.printf("Player %s: ", playerName);
            playerType = s.nextLine().trim();

            if (Player.isValidPlayer(playerType)) {
                return Player.NewForType(playerType, playerSymbol, s);
            } else {
                System.out.println("Invalid player selection, please try again!");
            }
        }
    }

    private void playGame() {
        do {
            currentPlayer.makeMove(board);
            board.printBoard();

            // Check for winners or draw
            switch (board.check()) {
                case WINNER_FOUND:
                    System.out.println(currentPlayer.getSymbol() + " wins\n");
                    continue;
                case DRAW:
                    System.out.println("Draw\n");
                    continue;
            }

            // Change player
            currentPlayer = currentPlayer == player1 ? player2 : player1;

        } while (board.getState() == State.IN_PROGRESS);
    }

    private void printTicTacToe() {
        System.out.println(" _______ _        _______           _______         ");
        System.out.println("|__   __(_)      |__   __|         |__   __|");
        System.out.println("   | |   _  ___     | | __ _  ___     | | ___   ___");
        System.out.println("   | |  | |/ __|    | |/ _` |/ __|    | |/ _ \\ / _ \\");
        System.out.println("   | |  | | (__     | | (_| | (__     | | (_) |  __/");
        System.out.println("   |_|  |_|\\___|    |_|\\__,_|\\___|    |_|\\___/ \\___|");
        System.out.println();
    }
}
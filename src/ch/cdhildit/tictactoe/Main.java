package ch.cdhildit.tictactoe;

import ch.cdhildit.tictactoe.game.Game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Game game = new Game(scanner);
        game.play();
    }
}



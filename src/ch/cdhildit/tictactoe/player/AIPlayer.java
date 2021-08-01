package ch.cdhildit.tictactoe.player;

import ch.cdhildit.tictactoe.board.Point;
import ch.cdhildit.tictactoe.board.Board;
import ch.cdhildit.tictactoe.game.Difficulty;

import java.util.Random;

public abstract class AIPlayer extends Player {
    protected Difficulty difficulty;
    protected Player opponent;

    AIPlayer(char symbol, Difficulty difficulty) {
        super(symbol);
        this.difficulty = difficulty;
    }

    @Override
    public void makeMove(Board board) {
        System.out.printf("%s: making move at level \"%s\"\n", this.symbol, this.difficulty.name().toLowerCase());

        this.makeAIMove(board);
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public abstract void makeAIMove(Board board);

    protected void makeRandomMove(Board board) {
        Random random = new Random(System.currentTimeMillis());
        Point p = new Point();
        do {
            p.x = random.nextInt(Board.BOARD_SIZE);
            p.y = random.nextInt(Board.BOARD_SIZE);
        } while (board.isOccupied(p));

        board.setCell(p, this.symbol);
    }
}

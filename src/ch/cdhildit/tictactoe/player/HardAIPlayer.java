package ch.cdhildit.tictactoe.player;

import java.util.*;

import ch.cdhildit.tictactoe.board.Point;
import ch.cdhildit.tictactoe.board.Board;
import ch.cdhildit.tictactoe.game.Difficulty;

public class HardAIPlayer extends AIPlayer {

    public HardAIPlayer(char symbol) {
        super(symbol, Difficulty.HARD);
    }

    @Override
    public void makeAIMove(Board board) {
        MinimaxMove bestMove = minimax(board, this, 1);

        board.setCell(bestMove.getPoint(), this.symbol);
    }

    private MinimaxMove minimax(Board b, Player player, int depth) {
        Board board = b.makeCopy();
        MinimaxMove bestMove = player == this ? new MinimaxMove(null, Float.NEGATIVE_INFINITY) : new MinimaxMove(null, Float.POSITIVE_INFINITY);

        switch (board.check()) {
            case DRAW:
                return new MinimaxMove(null, 0);
            case WINNER_FOUND:
                return (player == this) ? new MinimaxMove(null, 100 - depth) : new MinimaxMove(null,-100 + depth);
        }

        List<Point> possibleMoves = board.getEmptyCells();

        for (Point p : possibleMoves) {
            board.setCell(p, player.symbol);

            if (player == this) {
                MinimaxMove move = minimax(board, this.opponent, depth - 1);
                if (move.getScore() > bestMove.getScore()) {
                    bestMove = new MinimaxMove(p, move.getScore());
                }
            } else {
                MinimaxMove move = minimax(board, this, depth - 1);
                if (move.getScore() < bestMove.getScore()) {
                    bestMove = new MinimaxMove(p, move.getScore());;
                }
            }
        }

        return bestMove;
    }
}

class MinimaxMove {
    private final Point point;
    private final float score;

    MinimaxMove(Point point, float score) {
        this.point = point;
        this.score = score;
    }

    public Point getPoint() {
        return point;
    }

    public float getScore() {
        return score;
    }
}


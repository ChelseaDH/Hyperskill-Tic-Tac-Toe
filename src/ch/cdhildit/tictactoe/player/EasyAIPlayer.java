package ch.cdhildit.tictactoe.player;

import ch.cdhildit.tictactoe.game.Difficulty;
import ch.cdhildit.tictactoe.board.Board;

public class EasyAIPlayer extends AIPlayer {

    public EasyAIPlayer(char symbol) {
        super(symbol, Difficulty.EASY);
    }

    @Override
    public void makeAIMove(Board board) {
        makeRandomMove(board);
    }
}

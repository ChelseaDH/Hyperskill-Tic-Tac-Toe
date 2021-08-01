package ch.cdhildit.tictactoe.board;

public class WinningState {
    private final Point point;
    private final char symbol;

    WinningState(Point p, char symbol) {
        this.point = p;
        this.symbol = symbol;
    }

    public Point getPoint() {
        return point;
    }

    public char getSymbol() {
        return symbol;
    }
}

package ch.cdhildit.tictactoe.board;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int BOARD_SIZE = 3;

    private char[][] cells = new char[BOARD_SIZE][BOARD_SIZE];
    private State state;

    public Board() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.cells[i][j] = ' ';
            }
        }
        this.state = State.IN_PROGRESS;
    }

    public State getState() {
        return state;
    }

    public Board makeCopy() {
        Board copy = new Board();

        copy.cells = this.cells.clone();
        for (int i = 0; i < this.cells.length; i++) {
            copy.cells[i] = this.cells[i].clone();
        }
        copy.state = this.state;

        return copy;
    }

    public void printBoard() {
        System.out.println();
        System.out.println("---------");
        System.out.printf("| %c %c %c | \n", this.cells[0][2], this.cells[1][2], this.cells[2][2]);
        System.out.printf("| %c %c %c | \n", this.cells[0][1], this.cells[1][1], this.cells[2][1]);
        System.out.printf("| %c %c %c | \n", this.cells[0][0], this.cells[1][0], this.cells[2][0]);
        System.out.println("---------");
        System.out.println();
    }

    // Adds a new cell into the board
    public void addCell(int coord1, int coord2, char symbol) {

        // Check that coordinates are in range
        if (coord1 < 1 || coord1 > 3 || coord2 < 1 || coord2 > 3) {
            throw new IllegalArgumentException("Coordinates should be from 1 to 3!");
        }

        Point p = new Point(coord1 - 1, coord2 - 1);

        // Check if the board is already occupied at those indices
        if (this.isOccupied(p)) {
            throw new IllegalArgumentException("This cell is occupied! Choose another one!");
        }

        // Set the cell
        setCell(p, symbol);
    }

    public void setCell(Point p, char symbol) {
        this.cells[p.x][p.y] = symbol;
    }

    // Checks if a cell in the board is already occupied
    public boolean isOccupied(Point p) {
        return this.cells[p.x][p.y] != ' ';
    }

    // Check for wins in rows, columns, and diagonals
    public State check() {
        // Check for wins in rows and columns
        for (int i = 0; i < 3; i++) {
            if ((this.cells[0][i] == this.cells[1][i] && this.cells[0][i] == this.cells[2][i] && this.cells[0][i] != ' ')
                    || (this.cells[i][0] == this.cells[i][1] && this.cells[i][0] == this.cells[i][2] && this.cells[i][0] != ' ')) {
                this.state = State.WINNER_FOUND;
                break;
            }
        }

        // Check for wins on the diagonals
        if ((this.cells[0][0] == this.cells[1][1] && this.cells[0][0] == this.cells[2][2] && this.cells[1][1] != ' ')
                || (this.cells[2][0] == this.cells[1][1] && this.cells[2][0] == this.cells[0][2] && this.cells[1][1] != ' ')) {
            this.state = State.WINNER_FOUND;
        }

        // Checks for a draw
        if (this.state != State.WINNER_FOUND && getEmptyCells().size() == 0) {
            this.state = State.DRAW;
        }

        return this.state;
    }

    // Returns the cell coordinates that will cause a win in the next turn
    // Or null if win not possible
    public List<WinningState> cellsToWinNextTurn() {
        List<WinningState> winners = new ArrayList<>();
        Point p;

        for (int i = 0; i < 3; i++) {
            // Check for possible wins in rows
            if (this.cells[i][0] == this.cells[i][1] && !isOccupied(p = new Point(i, 2))) {
                winners.add(new WinningState(p, this.cells[i][0]));
            }
            if (this.cells[i][0] == this.cells[i][2] && !isOccupied(p = new Point(i, 1))) {
                winners.add(new WinningState(p, this.cells[i][0]));
            }
            if (this.cells[i][1] == this.cells[i][2] && !isOccupied(p = new Point(i, 0))) {
                winners.add(new WinningState(p, this.cells[i][1]));
            }

            // Check for possible wins in columns
            if (this.cells[0][i] == this.cells[1][i] && !isOccupied(p = new Point(2, i))) {
                winners.add(new WinningState(p, this.cells[0][i]));
            }
            if (this.cells[0][i] == this.cells[2][i] && !isOccupied(p = new Point(1, i))) {
                winners.add(new WinningState(p, this.cells[0][i]));
            }
            if (this.cells[1][i] == this.cells[2][i] && !isOccupied(p = new Point(0, i))) {
                winners.add(new WinningState(p, this.cells[1][i]));
            }
        }

        // Check for possible wins in main diagonal
        if (this.cells[0][0] == this.cells[1][1] && !isOccupied(p = new Point(2, 2))) {
            winners.add(new WinningState(p, this.cells[0][0]));
        }
        if (this.cells[0][0] == this.cells[2][2] && !isOccupied(p = new Point(1, 1))) {
            winners.add(new WinningState(p, this.cells[0][0]));
        }
        if (this.cells[1][1] == this.cells[2][2] && !isOccupied(p = new Point(0, 0))) {
            winners.add(new WinningState(p, this.cells[1][1]));
        }

        // Check for possible wins in side diagonal
        if (this.cells[0][2] == this.cells[1][2] && !isOccupied(p = new Point(2, 0))) {
            winners.add(new WinningState(p, this.cells[0][2]));
        }
        if (this.cells[0][2] == this.cells[2][0] && !isOccupied(p = new Point(1, 2))) {
            winners.add(new WinningState(p, this.cells[0][2]));
        }
        if (this.cells[1][2] == this.cells[2][0] && !isOccupied(p = new Point(0, 2))) {
            winners.add(new WinningState(p, this.cells[1][2]));
        }

        return winners;
    }

    public List<Point> getEmptyCells() {
        List<Point> empty = new ArrayList<>();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Point e = new Point(i, j);
                if (!isOccupied(e)) {
                    empty.add(e);
                }
            }
        }
        return empty;
    }
}

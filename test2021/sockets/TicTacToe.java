package test2021.sockets;

/**
 * Represents a game of tic tac toe. Players take turns putting marks onto the grid until one wins or there's a draw.
 */
public class TicTacToe {
    /**
     * The second player, or their mark on the grid
     */
    public static final char X = 'x';
    /**
     * The first player, or their mark on the grid
     */
    public static final char O = 'o';
    /**
     * The absence of a player, or of a mark on the grid
     */
    public static final char EMPTY = ' ';
    /**
     * Represents a draw when returned by {@link #getWinner}
     */
    public static final char DRAW = '!';

    /**
     * Given the single character {@link #O} or {@link #X}, returns the other one.
     *
     * @param player one of the player constants {@link #O} or {@link #X}
     * @return the other of the two constants
     */
    public static char getOtherPlayer(char player) {
        switch (player) {
            case X:
                return O;
            case O:
                return X;
            default:
                throw new IllegalArgumentException("invalid player");
        }
    }

    /**
     * The 3x3 grid containing the players' marks
     */
    private final char[][] grid;
    /**
     * The player that goes next
     */
    private char nextPlayer;

    /**
     * Creates a new game. {@link #O} goes first.
     */
    public TicTacToe() {
        grid = new char[3][3];
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                grid[y][x] = EMPTY;
            }
        }
        nextPlayer = O;
    }

    /**
     * Returns the player that goes next.
     *
     * @return the player to go next
     */
    public char getNextPlayer() {
        return nextPlayer;
    }

    /**
     * Makes a move in the game. The given player must be the one who goes next,
     * and x and y must be numbers specifying an empty spot on the grid.
     *
     * On success, the mark is placed and the other player goes next.
     *
     * @param player the player; must be the one that would be returned by {@link #getNextPlayer}
     * @param x the x position of the mark to place; {@code 0 <= x < 3}
     * @param x the y position of the mark to place; {@code 0 <= y < 3}
     */
    public void makeMove(char player, int x, int y) {
        if (player != nextPlayer) throw new IllegalArgumentException("wrong player taking their turn");
        if (x < 0 || x >= 3 || y < 0 || y >= 3) throw new IllegalArgumentException("illegal position");
        if (grid[y][x] != EMPTY) throw new IllegalArgumentException("position already occupied");

        grid[y][x] = player;
        nextPlayer = nextPlayer == O ? X : O;
    }

    /**
     * Returns the winner. {@link #EMPTY} means the game is still going, and {@link #DRAW} means there is a draw.
     *
     * @return one of {@link #X}, {@link #O}, {@link #DRAW}, or {@link #EMPTY} depending on the outcome of the game (or lack thereof)
     */
    public char getWinner() {
        char potentialWinner;

        // check all rows for a winner
        for (int y = 0; y < 3; y++) {
            if ((potentialWinner = checkForWinner(0, y, 1, y, 2, y)) != EMPTY)
                return potentialWinner;
        }

        // check all columns for a winner
        for (int x = 0; x < 3; x++) {
            if ((potentialWinner = checkForWinner(x, 0, x, 1, x, 2)) != EMPTY)
                return potentialWinner;
        }

        // check the main diagonal for a winner
        if ((potentialWinner = checkForWinner(0, 0, 1, 1, 2, 2)) != EMPTY)
            return potentialWinner;

        // check the off diagonal for a winner
        if ((potentialWinner = checkForWinner(0, 2, 1, 1, 2, 0)) != EMPTY)
            return potentialWinner;

        // check if there are more empty spaces
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (grid[y][x] == EMPTY) return EMPTY;
            }
        }

        // if there's no free spaces, it's a draw
        return DRAW;
    }

    /**
     * Checks a single line, given by three pairs of (x, y) coordinates, for whether it is three-in-a-row.
     *
     * @return one of {@link #X}, {@link #O} if there's three in a row, or {@link #EMPTY} if there's not
     */
    private char checkForWinner(int x1, int y1, int x2, int y2, int x3, int y3) {
        char potentialWinner = grid[y1][x1];
        // EMPTY can't win
        if (potentialWinner == EMPTY) return EMPTY;
        // if there's a different/no mark in the line, it's no win
        if (potentialWinner != grid[y2][x2]) return EMPTY;
        if (potentialWinner != grid[y3][x3]) return EMPTY;
        // it's a win
        return potentialWinner;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("+---+\n");
        for (int y = 0; y < 3; y++) {
            sb.append("|");
            for (int x = 0; x < 3; x++) {
                sb.append(grid[y][x]);
            }
            sb.append("|\n");
        }
        sb.append("+---+");

        return sb.toString();
    }
}

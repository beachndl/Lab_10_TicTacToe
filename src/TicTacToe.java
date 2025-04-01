import java.util.Scanner;

public class TicTacToe {
    // Step 6: declare the board array and the constants that define it before main()
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static final String[][] board = new String[ROWS][COLS];

    public static void main(String[] args) {
        // Scanner
        Scanner in = new Scanner(System.in);

        // Variable declaration
        int moveCount, row, col;
        boolean playAgain = true;
        boolean gameOver;
        String player;

        // Main game loop
        while (playAgain) {
            // Clear the board and set the player to X (since X always moves first)
            clearBoard();
            player = "X";

            // Display initial board, set count to 0, set gameOver to false.
            display();
            moveCount = 0;
            gameOver = false;

            // Game loop
            while (!gameOver) {
                System.out.println("\nPlayer " + player + "'s turn");

                // Get the coordinates for the move which should be 1 – 3 for the row and col
                // Loop until the converted player coordinates are a valid move which is an empty space on the board
                do {
                    row = SafeInput.getRangedInt(in, "Enter row (1-3)", 1, 3) - 1;
                    col = SafeInput.getRangedInt(in, "Enter column (1-3)", 1, 3) - 1;

                    if (!isValidMove(row, col)) {
                        System.out.println("That space is already taken. Try again.");
                    }
                } while (!isValidMove(row, col));

                // Record the valid move on the board
                board[row][col] = player;

                // Increment the move counter
                moveCount++;

                // Display updated board
                display();

                // Check for win or tie (possible after 5 moves)
                if (moveCount >= 5) {
                    if (isWin(player)) {
                        System.out.println("\nPlayer " + player + " wins.");
                        gameOver = true;
                    } else if (moveCount >= 9 || isTie()) {
                        System.out.println("\nGame ended in a tie.");
                        gameOver = true;
                    }
                }

                // Toggle the player (i.e. X becomes O, O becomes X)
                if (!gameOver) {
                    if (player.equals("X")) {
                        player = "O";
                    } else {
                        player = "X";
                    }
                }
            }

            // Ask to play again
            playAgain = SafeInput.getYNConfirm(in, "Do you want to play again?");
        }
    }


    // Sets all the board elements to a space
    private static void clearBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = " ";
            }
        }
    }

    // Shows the Tic Tac Toe game used as part of the prompt for the users move choice…
    private static void display() {
        System.out.println("\nCurrent Board:");
        System.out.println("  1 2 3");
        for (int row = 0; row < ROWS; row++) {
            System.out.print((row + 1) + " ");
            for (int col = 0; col < COLS; col++) {
                System.out.print(board[row][col]);
                if (col < COLS - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (row < ROWS - 1) {
                System.out.println("  -----");
            }
        }
    }

    // Returns true if there is a space at the given proposed move coordinates which means it is a legal move.
    private static boolean isValidMove(int row, int col) {
        // Check row
        if (row < 0 || row >= ROWS) {
            return false;
        }

        // Check column
        if (col < 0 || col >= COLS) {
            return false;
        }

        // Check if the space is empty
        if (!board[row][col].equals(" ")) {
            return false;
        }

        // If all checks pass, the move is valid
        return true;
    }

    // Checks to see if there is a win state on the current board for the specified player (X or O)
    // This method in turn calls three additional methods that break down the 3 kinds of wins that are possible.
    private static boolean isWin(String player) {
        // Check for row, column, or diagonal win
        if (isRowWin(player)) {
            return true;
        }

        if (isColWin(player)) {
            return true;
        }

        if (isDiagonalWin(player)) {
            return true;
        }

        // If no win is found, return false
        return false;
    }

    // Checks for a col win for specified player
    private static boolean isColWin(String player) {
        for (int col = 0; col < COLS; col++) {
            if (board[0][col].equals(player) && board[1][col].equals(player) && board[2][col].equals(player)) {
                return true;
            }
        }
        return false;
    }

    // Checks for a row win for specified player
    private static boolean isRowWin(String player) {
        for (int row = 0; row < ROWS; row++) {
            if (board[row][0].equals(player) && board[row][1].equals(player) && board[row][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    // Checks for a diagonal win for the specified player
    private static boolean isDiagonalWin(String player) {
        // Check main diagonal (top-left to bottom-right)
        if (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) {
            return true;
        }

        // Check other diagonal (top-right to bottom-left)
        if (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player)) {
            return true;
        }

        return false;
    }

    // Checks for a tie condition: all spaces on the board are filled OR there is an X and an O in every win vector
    // (i.e. all possible 8 wins are blocked by having both and X and an O in them.)
    private static boolean isTie() {
        // Check if all spaces are filled
        boolean boardFull = true;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col].equals(" ")) {
                    boardFull = false;
                    break;
                }
            }
            if (!boardFull) {
                break;
            }
        }
        if (boardFull) {
            return true;
        }

        // Check if all win vectors are blocked (have both X and O)

        // Check rows
        for (int row = 0; row < ROWS; row++) {
            boolean hasX = false;
            boolean hasO = false;

            for (int col = 0; col < COLS; col++) {
                if (board[row][col].equals("X")) {
                    hasX = true;
                }
                if (board[row][col].equals("O")) {
                    hasO = true;
                }
            }

            // If any row doesn't have both X and O, it's not a tie yet
            if (!(hasX && hasO)) {
                return false;
            }
        }

        // Check columns
        for (int col = 0; col < COLS; col++) {
            boolean hasX = false;
            boolean hasO = false;

            for (int row = 0; row < ROWS; row++) {
                if (board[row][col].equals("X")) {
                    hasX = true;
                }
                if (board[row][col].equals("O")) {
                    hasO = true;
                }
            }

            // If any column doesn't have both X and O, it's not a tie yet
            if (!(hasX && hasO)) {
                return false;
            }
        }

        // Variables to check diagonals
        boolean mainDiagHasX = false;
        boolean mainDiagHasO = false;
        boolean otherDiagHasX = false;
        boolean otherDiagHasO = false;

        // Main diagonal
        for (int i = 0; i < ROWS; i++) {
            if (board[i][i].equals("X")) {
                mainDiagHasX = true;
            }
            if (board[i][i].equals("O")) {
                mainDiagHasO = true;
            }
        }

        // If main diagonal doesn't have both X and O, it's not a tie yet
        if (!(mainDiagHasX && mainDiagHasO)) {
            return false;
        }

        // Other diagonal
        for (int i = 0; i < ROWS; i++) {
            if (board[i][COLS - 1 - i].equals("X")) {
                otherDiagHasX = true;
            }
            if (board[i][COLS - 1 - i].equals("O")) {
                otherDiagHasO = true;
            }
        }
        // If other diagonal doesn't have both X and O, it's not a tie yet
        if (!(otherDiagHasX && otherDiagHasO)) {
            return false;
        }

        // If all win vectors have both X and O, it's a tie
        return true;
    }
}
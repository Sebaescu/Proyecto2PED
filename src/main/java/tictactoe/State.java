package tictactoe;

public class State {
    private final int size = 3;
    private String[] board;

    public State(int depth, String[] board) {
        this.board = board.clone();
    }

    public String[] getBoard() {
        return board;
    }

    public boolean isGameOver() {
        return checkWin("X") || checkWin("O") || isBoardFull();
    }

    public boolean isBoardFull() {
        for (String cell : board) {
            if (cell.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean checkWin(String player) {
        // Check rows
        for (int i = 0; i < size; i++) {
            if (board[i * size].equals(player) && board[i * size + 1].equals(player) && board[i * size + 2].equals(player)) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < size; i++) {
            if (board[i].equals(player) && board[i + size].equals(player) && board[i + 2 * size].equals(player)) {
                return true;
            }
        }

        // Check diagonals
        if (board[0].equals(player) && board[4].equals(player) && board[8].equals(player)) {
            return true;
        }
        if (board[2].equals(player) && board[4].equals(player) && board[6].equals(player)) {
            return true;
        }

        return false;
    }
}

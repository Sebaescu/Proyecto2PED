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


/*
//Clase de Respaldo con la version Anterior del algoritmo

public class State {
    private int position;
    private String[] state;

    public State(int position, String[] state) {
        this.position = position;
        this.state = state;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String[] getState() {
        return state;
    }

    public String getStateIndex(int i) {
        return state[i];
    }

    public void setState(String[] state) {
        this.state = state;
    }

    public void changeState(int i, String player) {
        state[i] = player;
    }

    public boolean isEmptyBoard() {
        for (String s : state) {
            if (s != null && !s.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "State{" +
                "position=" + position +
                ", state=" + Arrays.toString(state) +
                '}';
    }
}

*/

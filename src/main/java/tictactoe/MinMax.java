package tictactoe;

import java.util.ArrayList;
import java.util.List;

public class MinMax {
    private static final int WIN_SCORE = 10;
    private static final int DRAW_SCORE = 0;
    private static final int LOSS_SCORE = -10;

    public int minMaxDecision(State state) {
        int winningMove = findWinningMove(state);
        if (winningMove != -1) {
            return winningMove;
        }

        int blockingMove = findBlockingMove(state);
        if (blockingMove != -1) {
            return blockingMove;
        }

        return findBestMove(state);
    }

    private int findWinningMove(State state) {
        List<Integer> availableMoves = getAvailableMoves(state);
        for (int move : availableMoves) {
            State nextState = applyMove(state, Integer.toString(move), "X");
            if (nextState.checkWin("X")) {
                return move;
            }
        }
        return -1;
    }

    private int findBlockingMove(State state) {
        List<Integer> availableMoves = getAvailableMoves(state);
        for (int move : availableMoves) {
            State nextState = applyMove(state, Integer.toString(move), "O");
            if (nextState.checkWin("O")) {
                return move;
            }
        }
        return -1;
    }

    private int findBestMove(State state) {
        int bestMove = -1;
        int bestScore = Integer.MIN_VALUE;

        List<Integer> availableMoves = getAvailableMoves(state);

        for (int move : availableMoves) {
            State nextState = applyMove(state, Integer.toString(move), "X");
            int score = minMove(nextState);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private int maxMove(State state) {
        if (isTerminal(state)) {
            return evaluate(state);
        }

        int bestScore = Integer.MIN_VALUE;

        List<Integer> availableMoves = getAvailableMoves(state);

        for (int move : availableMoves) {
            State nextState = applyMove(state, Integer.toString(move), "X");
            int score = minMove(nextState);

            bestScore = Math.max(bestScore, score);
        }

        return bestScore;
    }

    private int minMove(State state) {
        if (isTerminal(state)) {
            return evaluate(state);
        }

        int bestScore = Integer.MAX_VALUE;

        List<Integer> availableMoves = getAvailableMoves(state);

        for (int move : availableMoves) {
            State nextState = applyMove(state, Integer.toString(move), "O");
            int score = maxMove(nextState);

            bestScore = Math.min(bestScore, score);
        }

        return bestScore;
    }

    public boolean isTerminal(State state) {
        return state.isGameOver() || state.isBoardFull();
    }

    private int evaluate(State state) {
        if (state.checkWin("X")) {
            return LOSS_SCORE;
        } else if (state.checkWin("O")) {
            return WIN_SCORE;
        } else {
            return DRAW_SCORE;
        }
    }

    private List<Integer> getAvailableMoves(State state) {
        List<Integer> availableMoves = new ArrayList<>();

        for (int i = 0; i < state.getBoard().length; i++) {
            if (state.getBoard()[i].isEmpty()) {
                availableMoves.add(i);
            }
        }

        return availableMoves;
    }

    private State applyMove(State state, String move, String player) {
        State newState = new State(0, state.getBoard().clone());

        int index = Integer.parseInt(move);

        if (index >= 0 && index < newState.getBoard().length && newState.getBoard()[index].isEmpty()) {
            newState.getBoard()[index] = player;
        }

        return newState;
    }
}
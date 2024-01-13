package TDAs;

import java.util.ArrayList;
import java.util.List;
import tictactoe.State;

public class NaryTree {

    private TreeNode root;

    public NaryTree(State initialState) {
        this.root = new TreeNode(initialState);
    }

    public TreeNode getRoot() {
        return root;
    }

    public void buildTree(TreeNode node, int depth, boolean maximizingPlayer) {
        if (depth == 0 || node.getState().isGameOver()) {
            node.setValue(evaluate(node.getState()));
            return;
        }

        List<String> availableMoves = getAvailableMoves(node.getState());
        for (String move : availableMoves) {
            State nextState = applyMove(node.getState(), move, maximizingPlayer ? "X" : "O");
            TreeNode childNode = new TreeNode(nextState);
            node.addChild(childNode);

            buildTree(childNode, depth - 1, !maximizingPlayer);

            if (maximizingPlayer && childNode.getValue() > node.getValue()) {
                node.setValue(childNode.getValue());
            } else if (!maximizingPlayer && childNode.getValue() < node.getValue()) {
                node.setValue(childNode.getValue());
            }
        }
    }

    private int evaluate(State state) {
        if (state.checkWin("X")) {
            return 10;
        } else if (state.checkWin("O")) {
            return -10;
        } else {
            return 0;
        }
    }
    private List<String> getAvailableMoves(State state) {
        List<String> availableMoves = new ArrayList<>();

        for (int i = 0; i < state.getBoard().length; i++) {
            if (state.getBoard()[i].isEmpty()) {
                availableMoves.add(Integer.toString(i));
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

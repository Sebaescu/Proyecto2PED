package tictactoe;
import TDAs.NaryTree;
import TDAs.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;

public class MinMax {

    public int minMaxDecision(State state) {
        NaryTree gameTree = buildGameTree(state);
        TreeNode rootNode = gameTree.getRoot();

        ArrayList<TreeNode> possibleMoves = rootNode.getChildren();
        ArrayList<Integer> movesList = new ArrayList<>();

        String player = getCurrentPlayer(state);

        for (TreeNode node : possibleMoves) {
            State nextState = node.getState();
            int utility = (player.equals("X")) ? maxValue(node) : minValue(node);
            movesList.add(utility);
        }

        int bestIndex = movesList.indexOf(movesList.stream().max(Integer::compareTo).orElseThrow());

        return rootNode.getChildren().get(bestIndex).getState().getPosition();
    }

    private String getCurrentPlayer(State state) {
        long xCount = Arrays.stream(state.getState()).filter("X"::equals).count();
        long oCount = Arrays.stream(state.getState()).filter("O"::equals).count();

        return (xCount <= oCount) ? "X" : "O";
    }

    public int maxValue(TreeNode node) {
        State state = node.getState();

        if (isTerminal(state)) {
            return utilityOf(state);
        }

        return node.getChildren().stream().mapToInt(this::minValue).max().orElseThrow();
    }

    public int minValue(TreeNode node) {
        State state = node.getState();

        if (isTerminal(state)) {
            return utilityOf(state);
        }

        return node.getChildren().stream().mapToInt(this::maxValue).min().orElseThrow();
    }

    public NaryTree buildGameTree(State state) {
        NaryTree gameTree = new NaryTree(state);
        buildTreeRecursively(gameTree.getRoot());
        return gameTree;
    }

    public void buildTreeRecursively(TreeNode node) {
        State state = node.getState();

        if (!isTerminal(state)) {
            ArrayList<State> successors = successorsOf(state);

            for (State successor : successors) {
                TreeNode childNode = new TreeNode(successor);
                node.addChild(childNode);
                buildTreeRecursively(childNode);
            }
        }
    }

    public boolean isTerminal(State state) {
        long takenSpots = Arrays.stream(state.getState()).filter(s -> "X".equals(s) || "O".equals(s)).count();

        if (takenSpots == 9) {
            return true;
        }

        for (int a = 0; a < 8; a++) {
            String line = checkState(state, a);

            if ("XXX".equals(line) || "OOO".equals(line)) {
                return true;
            }
        }

        return false;
    }

    private int utilityOf(State state) {
        for (int a = 0; a < 8; a++) {
            String line = checkState(state, a);

            if ("XXX".equals(line)) {
                return 1;
            } else if ("OOO".equals(line)) {
                return -1;
            }
        }
        return 0;
    }

    private String checkState(State state, int a) {
        if (state == null) {
            return "";
        }
        return switch (a) {
            case 0 -> state.getStateIndex(0) + state.getStateIndex(1) + state.getStateIndex(2);
            case 1 -> state.getStateIndex(3) + state.getStateIndex(4) + state.getStateIndex(5);
            case 2 -> state.getStateIndex(6) + state.getStateIndex(7) + state.getStateIndex(8);
            case 3 -> state.getStateIndex(0) + state.getStateIndex(3) + state.getStateIndex(6);
            case 4 -> state.getStateIndex(1) + state.getStateIndex(4) + state.getStateIndex(7);
            case 5 -> state.getStateIndex(2) + state.getStateIndex(5) + state.getStateIndex(8);
            case 6 -> state.getStateIndex(0) + state.getStateIndex(4) + state.getStateIndex(8);
            case 7 -> state.getStateIndex(2) + state.getStateIndex(4) + state.getStateIndex(6);
            default -> "";
        };
    }

    private ArrayList<State> successorsOf(State state) {
        ArrayList<State> possibleMoves = new ArrayList<>();
        long xMoves = Arrays.stream(state.getState()).filter("X"::equals).count();
        long oMoves = Arrays.stream(state.getState()).filter("O"::equals).count();
        String player = (xMoves <= oMoves) ? "X" : "O";

        for (int i = 0; i < 9; i++) {
            if (!"X".equals(state.getStateIndex(i)) && !"O".equals(state.getStateIndex(i))) {
                String[] newState = Arrays.copyOf(state.getState(), state.getState().length);
                newState[i] = player;
                possibleMoves.add(new State(i, newState));
            }
        }
        return possibleMoves;
    }
}

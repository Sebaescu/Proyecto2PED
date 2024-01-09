package tictactoe;
import TDAs.NaryTree;
import TDAs.TreeNode;
import java.util.ArrayList;

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

        int max = movesList.get(0);
        int bestIndex = 0;

        for (int i = 1; i < movesList.size(); i++) {
            if (movesList.get(i) > max) {
                max = movesList.get(i);
                bestIndex = i;
            }
        }

        return rootNode.getChildren().get(bestIndex).getState().getPosition();
    }

    private String getCurrentPlayer(State state) {
        int xCount = 0;
        int oCount = 0;

        for (String s : state.getState()) {
            if ("X".equals(s)) {
                xCount++;
            } else if ("O".equals(s)) {
                oCount++;
            }
        }

        return (xCount <= oCount) ? "X" : "O";
    }


    //Picks best option for the X-player
    public int maxValue(TreeNode node) {
        State state = node.getState();

        if (isTerminal(state)) {
            return utilityOf(state);
        }

        int v = (int) -Double.POSITIVE_INFINITY;

        for (TreeNode child : node.getChildren()) {
            v = Math.max(v, minValue(child));
        }

        return v;
    }

    //Picks best option for the O-player
    public int minValue(TreeNode node) {
        State state = node.getState();

        if (isTerminal(state)) {
            return utilityOf(state);
        }

        int v = (int) Double.POSITIVE_INFINITY;

        for (TreeNode child : node.getChildren()) {
            v = Math.min(v, maxValue(child));
        }

        return v;
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

    //Returns true if the game is over
    public boolean isTerminal(State state) {
        int takenSpots = 0;
        for (int a = 0; a < 9; a++) {
            if(state.getStateIndex(a).equals("X") || state.getStateIndex(a).equals("O") ){ 
                takenSpots++;
            }

            String line = checkState(state, a);

            //Check for Winners
            if (line.equals("XXX")) {
                return true;
            } else if (line.equals("OOO")) {
                return true;
            }

            if(takenSpots == 9){
                return true;
            }
        }
        return false;
    }

    //Returns +1 if X is winner
    //Return -1 if O is winner
    //Returns 0 if no one won
    private int utilityOf(State state){
        for (int a = 0; a < 8; a++) {
            String line = checkState(state, a);
            //Check for Winners
            if (line.equals("XXX")) {
                return 1;
            } else if (line.equals("OOO")) {
                return -1;
            }
        }
        return 0;
    }

   //Find any win state if it exists
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

    //Returns all possible states form a given state
    private ArrayList<State> successorsOf(State state){
        ArrayList<State> possibleMoves = new ArrayList<>();
        int xMoves = 0;
        int yMoves = 0;
        String player;

        //Calculate player turn
        for (String s: state.getState()) {
            if(s!=null){
                if (s.equals("X")) {
                    xMoves++;
                }else if(s.equals("O")){
                    yMoves++;
                }
            }
        }

        if(xMoves <= yMoves){
            player = "X";
        } else {
            player = "O";
        }

        //Create all possible states
        for (int i = 0; i < 9; i++) {
            String[] newState = state.getState().clone();

            if(newState[i] != "X" && newState[i] != "O"){
                newState[i] = player;
                possibleMoves.add(new State(i, newState));
            }
        }
        return possibleMoves;
    }
    
}
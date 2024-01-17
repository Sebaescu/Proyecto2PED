package tictactoe;

import TDAs.NaryTree;
import TDAs.TreeNode;
import java.util.ArrayList;
import java.util.List;

public class MinMax {
    
    public static int calculateUtility(String[] board, String currentPlayer) {

        int utilityX = calculatePlayerUtility(board, "X");
        int utilityO = calculatePlayerUtility(board, "O");
        
        // Ajusta la utilidad según el jugador actual
        if (currentPlayer.equals("O")) {
            return utilityO - utilityX;
        } else {
            return utilityX - utilityO;
        }
        
    }

    private static int calculatePlayerUtility(String[] board, String player) {
        int utility = 0;

        // Filas
        for (int i = 0; i < 3; i++) {
            utility += countMarksInLine(board[i * 3], board[i * 3 + 1], board[i * 3 + 2], player);
            if (player.equals("X") && countMarksInLine(board[i * 3], board[i * 3 + 1], board[i * 3 + 2], "O") == 2 || 
                    player.equals("O") && countMarksInLine(board[i * 3], board[i * 3 + 1], board[i * 3 + 2], "X") == 2) {
                utility -= 2;
            }
        }

        // Columnas
        for (int i = 0; i < 3; i++) {
            utility += countMarksInLine(board[i], board[i + 3], board[i + 6], player);
            if (player.equals("X") && countMarksInLine(board[i], board[i + 3], board[i + 6], "O") == 2 || 
                    player.equals("O") && countMarksInLine(board[i], board[i + 3], board[i + 6], "X") == 2) {
                utility -= 2;
            }
        }

        // Diagonales
        utility += countMarksInLine(board[0], board[4], board[8], player);
        utility += countMarksInLine(board[2], board[4], board[6], player);
        if (player.equals("X") && countMarksInLine(board[0], board[4], board[8], "O") == 2 || 
                player.equals("O") && countMarksInLine(board[0], board[4], board[8], "X") == 2) {
            utility -= 2;
        }
        if (player.equals("X") && countMarksInLine(board[2], board[4], board[6], "O") == 2 ||
                player.equals("O") && countMarksInLine(board[2], board[4], board[6], "X") == 2) {
            utility -= 2;
        }
        
        return utility;
    }

    private static int countMarksInLine(String mark1, String mark2, String mark3, String player) {
        int count = 0;
        if (mark1.equals(player)) {
            count++;
        }
        if (mark2.equals(player)) {
            count++;
        }
        if (mark3.equals(player)) {
            count++;
        }
        return count;
    }
    
    public int minMaxDecision(State state, String player) {
        NaryTree gameTree = buildGameTree(state);
        TreeNode rootNode = gameTree.getRoot();
        ArrayList<TreeNode> possibleMoves = rootNode.getChildren();
        ArrayList<Integer> movesList = new ArrayList<>();

        for (TreeNode node : possibleMoves) {
            movesList.add(calculateMinUtility(node, player));
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
    
    private int calculateMinUtility(TreeNode node, String jugadorActual) {
        int minUtility = Integer.MAX_VALUE;

        for (TreeNode child : node.getChildren()) {
            int childUtility = calculateUtility(child.getState().getBoard(), jugadorActual);
            minUtility = Math.min(minUtility, childUtility);
        }
            
            
        return minUtility;
    }

    //Picks best option for the X-player
    public int maxValue(TreeNode node) {
        State state = node.getState();

        if (isTerminal(state)) {
            return utilityOf(state);
        }

        int v =  Integer.MIN_VALUE;

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
        int min = Integer.MAX_VALUE;
        for (TreeNode child : node.getChildren()) {
            v = Math.min(v, maxValue(child));
            int childValue = maxValue(child);
            min = Math.min(min, childValue);
        }
        
        node.setValue(min);

        return min;
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
            if(state.getBoardIndex(a).equals("X") || state.getBoardIndex(a).equals("O") ){ 
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

    
    public int minMaxDecisionTwo(State state) {
        NaryTree gameTree = buildGameTree(state);
        TreeNode rootNode = gameTree.getRoot();

        ArrayList<TreeNode> possibleMoves = rootNode.getChildren();
        ArrayList<Integer> movesList = new ArrayList<>();

        for (TreeNode node : possibleMoves) {
            movesList.add(minValue(node));
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
        //return rootNode.children.get(bestIndex).state.getPosition();
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
            case 0 -> state.getBoardIndex(0) + state.getBoardIndex(1) + state.getBoardIndex(2);
            case 1 -> state.getBoardIndex(3) + state.getBoardIndex(4) + state.getBoardIndex(5);
            case 2 -> state.getBoardIndex(6) + state.getBoardIndex(7) + state.getBoardIndex(8);
            case 3 -> state.getBoardIndex(0) + state.getBoardIndex(3) + state.getBoardIndex(6);
            case 4 -> state.getBoardIndex(1) + state.getBoardIndex(4) + state.getBoardIndex(7);
            case 5 -> state.getBoardIndex(2) + state.getBoardIndex(5) + state.getBoardIndex(8);
            case 6 -> state.getBoardIndex(0) + state.getBoardIndex(4) + state.getBoardIndex(8);
            case 7 -> state.getBoardIndex(2) + state.getBoardIndex(4) + state.getBoardIndex(6);
            default -> "";
        };
    }

    
    private ArrayList<State> successorsOf(State state){
        ArrayList<State> possibleMoves = new ArrayList<>();
        int xMoves = 0;
        int yMoves = 0;
        String player;

        //Calculate player turn
        for (String s: state.getBoard()) {
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
            String[] newState = state.getBoard().clone();

            if(newState[i] != "X" && newState[i] != "O"){
                newState[i] = player;
                possibleMoves.add(new State(i, newState));
            }
        }
        return possibleMoves;
    }
    
}


//public class MinMax {
//    private static final int WIN_SCORE = 10;
//    private static final int DRAW_SCORE = 0;
//    private static final int LOSS_SCORE = -10;
//
//    public int minMaxDecision(State state) {
//        int winningMove = findWinningMove(state);
//        if (winningMove != -1) {
//            return winningMove;
//        }
//
//        int blockingMove = findBlockingMove(state);
//        if (blockingMove != -1) {
//            return blockingMove;
//        }
//
//        return findBestMove(state);
//    }
//
//    private int findWinningMove(State state) {
//        List<Integer> availableMoves = getAvailableMoves(state);
//        for (int move : availableMoves) {
//            State nextState = applyMove(state, Integer.toString(move), "X");
//            if (nextState.checkWin("X")) {
//                return move;
//            }
//        }
//        return -1;
//    }
//
//    private int findBlockingMove(State state) {
//        List<Integer> availableMoves = getAvailableMoves(state);
//        for (int move : availableMoves) {
//            State nextState = applyMove(state, Integer.toString(move), "O");
//            if (nextState.checkWin("O")) {
//                return move;
//            }
//        }
//        return -1;
//    }
//
//    private int findBestMove(State state) {
//        int bestMove = -1;
//        int bestScore = Integer.MIN_VALUE;
//
//        List<Integer> availableMoves = getAvailableMoves(state);
//
//        for (int move : availableMoves) {
//            State nextState = applyMove(state, Integer.toString(move), "X");
//            int score = minMove(nextState);
//
//            if (score > bestScore) {
//                bestScore = score;
//                bestMove = move;
//            }
//        }
//
//        return bestMove;
//    }
//
//    private int maxMove(State state) {
//        if (isTerminal(state)) {
//            return evaluate(state);
//        }
//
//        int bestScore = Integer.MIN_VALUE;
//
//        List<Integer> availableMoves = getAvailableMoves(state);
//
//        for (int move : availableMoves) {
//            State nextState = applyMove(state, Integer.toString(move), "X");
//            int score = minMove(nextState);
//
//            bestScore = Math.max(bestScore, score);
//        }
//
//        return bestScore;
//    }
//
//    private int minMove(State state) {
//        if (isTerminal(state)) {
//            return evaluate(state);
//        }
//
//        int bestScore = Integer.MAX_VALUE;
//
//        List<Integer> availableMoves = getAvailableMoves(state);
//
//        for (int move : availableMoves) {
//            State nextState = applyMove(state, Integer.toString(move), "O");
//            int score = maxMove(nextState);
//
//            bestScore = Math.min(bestScore, score);
//        }
//
//        return bestScore;
//    }
//
//    public boolean isTerminal(State state) {
//        return state.isGameOver() || state.isBoardFull();
//    }
//
//    private int evaluate(State state) {
//        if (state.checkWin("X")) {
//            return LOSS_SCORE;
//        } else if (state.checkWin("O")) {
//            return WIN_SCORE;
//        } else {
//            return DRAW_SCORE;
//        }
//    }
//
//    private List<Integer> getAvailableMoves(State state) {
//        List<Integer> availableMoves = new ArrayList<>();
//
//        for (int i = 0; i < state.getBoard().length; i++) {
//            if (state.getBoard()[i].isEmpty()) {
//                availableMoves.add(i);
//            }
//        }
//
//        return availableMoves;
//    }
//
//    private State applyMove(State state, String move, String player) {
//        State newState = new State(0, state.getBoard().clone());
//
//        int index = Integer.parseInt(move);
//
//        if (index >= 0 && index < newState.getBoard().length && newState.getBoard()[index].isEmpty()) {
//            newState.getBoard()[index] = player;
//        }
//
//        return newState;
//    }
//}


/*
//Clase de Respaldo con la version Anterior del algoritmo

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
            if(state.getBoardIndex(a).equals("X") || state.getBoardIndex(a).equals("O") ){ 
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
            case 0 -> state.getBoardIndex(0) + state.getBoardIndex(1) + state.getBoardIndex(2);
            case 1 -> state.getBoardIndex(3) + state.getBoardIndex(4) + state.getBoardIndex(5);
            case 2 -> state.getBoardIndex(6) + state.getBoardIndex(7) + state.getBoardIndex(8);
            case 3 -> state.getBoardIndex(0) + state.getBoardIndex(3) + state.getBoardIndex(6);
            case 4 -> state.getBoardIndex(1) + state.getBoardIndex(4) + state.getBoardIndex(7);
            case 5 -> state.getBoardIndex(2) + state.getBoardIndex(5) + state.getBoardIndex(8);
            case 6 -> state.getBoardIndex(0) + state.getBoardIndex(4) + state.getBoardIndex(8);
            case 7 -> state.getBoardIndex(2) + state.getBoardIndex(4) + state.getBoardIndex(6);
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

*/
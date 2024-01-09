package TDAs;

import tictactoe.MinMax;
import tictactoe.State;

public class NaryTree {

    private TreeNode root;

    public NaryTree(State rootState) {
        this.root = new TreeNode(rootState);
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public void inOrderTraversal() {
        inOrderTraversal(root);
    }

    public void inOrderTraversal(TreeNode node) {
        if (node != null) {
            inOrderTraversal(node.getChildren().get(0)); // Recorre el hijo izquierdo
            System.out.println(node.getState()); // Procesa el nodo actual
            for (int i = 1; i < node.getChildren().size(); i++) {
                inOrderTraversal(node.getChildren().get(i)); // Recorre los demás hijos
            }
        }
    }
}

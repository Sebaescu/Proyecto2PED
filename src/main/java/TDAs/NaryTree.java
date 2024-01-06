package com.mycompany.arboles;

import tictactoe.AdversarialSearchTicTacToe;


/**
 *
 * @author Abeni
 */
public class NaryTree<E> {
    private TreeNode<E> root;
    
    public NaryTree() {
        this.root = new TreeNode();
    }

    public TreeNode<E> getRoot() {
        return root;
    }

    public boolean isEmpty () {
        return this.root == null;
    }
    
    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(TreeNode<E> node) {
        if (node == null) {
            return 0;
        } else {
            int maxHeight = 0;
            for (TreeNode<E> child : node.getChildren()) {
                int height = getHeight(child);
                if (height > maxHeight) {
                    maxHeight = height;
                }
            }
            return 1 + maxHeight;
        }
    }


   public int getMinDepth() {
        return getMinDepth(root);
    }

    private int getMinDepth(TreeNode<E> node) {
        if (node == null) {
            return 0;
        } else if (node.getChildren().isEmpty()) {
            return 1;
        } else {
            int minDepth = Integer.MAX_VALUE;
            for (TreeNode<E> child : node.getChildren()) {
                int depth = getMinDepth(child);
                if (depth < minDepth) {
                    minDepth = depth;
                }
            }
            return 1 + minDepth;
        }
    }

    public int getMaxDepth() {
        return getMaxDepth(root);
    }

    private int getMaxDepth(TreeNode<E> node) {
        if (node == null) {
            return 0;
        } else if (node.getChildren().isEmpty()) {
            return 1;
        } else {
            int maxDepth = 0;
            for (TreeNode<E> child : node.getChildren()) {
                int depth = getMaxDepth(child);
                if (depth > maxDepth) {
                    maxDepth = depth;
                }
            }
            return 1 + maxDepth;
        }
    }

   public TreeNode<E> getParent(TreeNode<E> child) {
        return getParent(root, child);
    }

    private TreeNode<E> getParent(TreeNode<E> current, TreeNode<E> target) {
        if (current == null || target == null) {
            return null;
        } else {
            for (TreeNode<E> child : current.getChildren()) {
                if (child == target) {
                    return current;
                } else {
                    TreeNode<E> parent = getParent(child, target);
                    if (parent != null) {
                        return parent;
                    }
                }
            }
            return null;
        }
    }


    public TreeNode<E> findNode(E data) {
        return findNode(root, data);
    }

    private TreeNode<E> findNode(TreeNode<E> current, E data) {
        if (current == null) {
            return null;
        } else if (current.getContent().equals(data)) {
            return current;
        } else {
            for (TreeNode<E> child : current.getChildren()) {
                TreeNode<E> node = findNode(child, data);
                if (node != null) {
                    return node;
                }
            }
            return null;
        }
    }

    public void setNodeData(TreeNode<E> node, E newData) {
        if (node != null) {
            node.setContent(newData);
        }
    }
    
     public void buildTree(TreeNode node, boolean isComputerTurn) {
        if (node == null || node.isTerminal()) {
            return;
        }

        // Calcular el jugador actual
        int currentPlayer = isComputerTurn ? 1 : -1;

        // Crear todos los posibles estados
        for (int i = 0; i < 9; i++) {
            if (node.getBoard()[i] == 0) {  // Verificar si la casilla está vacía
                TreeNode childNode = new TreeNode();
                childNode.setMejorMovimiento(i);
                childNode.setMiTurno(!node.isMiTurno());  // Cambiar el turno
                childNode.getBoard()[i] = currentPlayer;
                node.addChild(childNode);

                // Recursión para construir el árbol
                buildTree(childNode, !isComputerTurn);
            }
        }
    }


}

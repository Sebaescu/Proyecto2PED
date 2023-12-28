/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.arboles;

/**
 *
 * @author Abeni
 */
public class NaryTree<E> {
    private TreeNode<E> root;

    public NaryTree(E rootData) {
        this.root = new TreeNode<>(rootData);
    }

    public TreeNode<E> getRoot() {
        return root;
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

}

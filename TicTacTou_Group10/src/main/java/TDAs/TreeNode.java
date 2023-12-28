/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.arboles;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abeni
 */
class TreeNode<E> {
    private E content;
    private List<TreeNode<E>> children;

    public TreeNode(E data) {
        this.content = data;
        this.children = new ArrayList<>();
    }

    public E getContent() {
        return content;
    }

    public List<TreeNode<E>> getChildren() {
        return children;
    }

    public void addChild(TreeNode<E> child) {
        children.add(child);
    }
    
    public void setContent(E content) {
        this.content = content;
    }
}

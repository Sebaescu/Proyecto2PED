/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TDAs;

import java.util.ArrayList;
import java.util.List;
import tictactoe.State;

public class TreeNode {

    private State state;
    private List<TreeNode> children;
    private int value;

    public TreeNode(State state) {
        this.state = state;
        this.children = new ArrayList<>();
        this.value = 0;
    }

    public State getState() {
        return state;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

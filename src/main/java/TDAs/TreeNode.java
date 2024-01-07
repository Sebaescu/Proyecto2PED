/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TDAs;

import java.util.ArrayList;
import java.util.List;
import tictactoe.MinMax;
import tictactoe.State;

/**
 *
 * @author Abeni
 */
public class TreeNode {

    private State state;
    
    private ArrayList<TreeNode> children;

    public TreeNode(State state) {
        this.state = state;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<TreeNode> children) {
        this.children = children;
    }
    
}

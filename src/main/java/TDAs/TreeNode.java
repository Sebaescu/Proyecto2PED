/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.arboles;

import java.util.ArrayList;
import java.util.List;
import tictactoe.AdversarialSearchTicTacToe;
import tictactoe.State;

/**
 *
 * @author Abeni
 */
public class TreeNode<E> {
    private E content;
    private int[] board;
    private List<TreeNode<E>> children;
    private int mejorMovimiento;
    private boolean miTurno;
    AdversarialSearchTicTacToe search = new AdversarialSearchTicTacToe();

    public int getMejorMovimiento() {
        return mejorMovimiento;
    }

    public void setMejorMovimiento(int mejorMovimiento) {
        this.mejorMovimiento = mejorMovimiento;
    }

    public TreeNode() {
        this.board = new int[9];
        this.children = new ArrayList<>();
    }
    
    public boolean isMiTurno() {
            return miTurno;
    }

    public void setMiTurno(boolean miTurno) {
        this.miTurno = miTurno;
    }

    // Método para verificar si es el turno del jugador actual
    public boolean isPlayerTurn() {
        return (miTurno && mejorMovimiento % 2 == 0) || (!miTurno && mejorMovimiento % 2 == 1);
    }

    public int[] getBoard() {
        return board;
    }
    
    
    public boolean isTerminal() {
            // Delegar la lógica al método en AdversarialSearchTicTacToe
            return search.isTerminal(new State(0, convertBoardToStringArray()));
        }

    // Método para convertir el tablero en un arreglo de strings
    private String[] convertBoardToStringArray() {
        String[] stringBoard = new String[9];
        for (int i = 0; i < 9; i++) {
            if (board[i] == 1) {
                stringBoard[i] = "X";
            } else if (board[i] == -1) {
                stringBoard[i] = "O";
            }
            // Si el valor es 0, el espacio está vacío
        }
        return stringBoard;
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

package tictactoe;

import java.util.Scanner;

public class Main_AI {
    public static void main(String[] args) {

        MinMax adsTicTacToe = new MinMax();

        String[] board = {"", "", "", "", "", "", "", "", ""};  // Initialize with empty strings

        State state = new State(0, board);

        Scanner scanner = new Scanner(System.in);

        while (!adsTicTacToe.isTerminal(state)) {
            int aiMove = adsTicTacToe.minMaxDecision(state);
            board[aiMove] = "X";
            
            if (!adsTicTacToe.isTerminal(state)) {
                drawBoard(board);
                System.out.print("Enter your move (0-8): ");
                int userInput = Integer.parseInt(scanner.nextLine());
                
                while (userInput < 0 || userInput > 8 || !board[userInput].isEmpty()) {
                    System.out.println("Invalid move. Try again.");
                    userInput = Integer.parseInt(scanner.nextLine());
                }
                
                board[userInput] = "O";
                state = new State(0, board);
            }
        }
        drawBoard(board);
        System.out.println("Game is over");
    }

    public static void drawBoard(String[] board) {
        for (int i = 0; i < 7; i += 3) {
            System.out.println(board[i] + " " + board[i + 1] + " " + board[i + 2]);
        }
    }
}

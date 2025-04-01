package p1;

import java.awt.Color;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Display display = new Display();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter difficulty level (number of moves away from solved state):");
        
        //Get difficulty input via Scanner
        int difficulty = -1;
        while (difficulty < 0) {
            try {
                difficulty = scanner.nextInt(); // Read user input as an integer
                scanner.nextLine(); // Consume newline
                if (difficulty < 0) {
                    System.out.println("Invalid difficulty. Enter a non-negative number.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
        
        //Create a board with desired difficulty
        Board board = new Board(difficulty);
        
        //Display the original board
        display.drawBoard(board.getGrid());
        
        // Short pause to ensure UI renders
        try { Thread.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
        
        //Main game loop to run the Board class methods
        while (true) {
            // Display the board graphically
            display.drawBoard(board.getGrid());
            
            // Check if solved
            if (board.isSolved()) {
                System.out.println("You won!");
                break;
            }
            
            // Get user input for a move
            System.out.println("Enter a move (1-6 for swaps, a-d for shifts):");
            char move = display.getKeyStroke();
            
            // Validate the user's move
            if (!isValidMove(move)) {
                System.out.println("Invalid move. Try again.");
                continue;
            }
            
            // Make the move
            	// Check if board state was repeated, the loss condition is handled in Board class
            board.move(move);
            
            // Redraw the board to reflect changes
            display.drawBoard(board.getGrid());
            
            // Check for loss condition after move
            if (board.getPreviousStates().contains(board.getBoardState())) {
                System.out.println("You lost! Board state repeated.");
                break;
            
        }
        }
        scanner.close(); // Close scanner when game ends
    }

    
    //Extra method we need to make sure the move is valid
    	//letters a-d
    	//integers 1-6
    private static boolean isValidMove(char move) {
        return (move >= '1' && move <= '6') || (move >= 'a' && move <= 'd');
    }
}
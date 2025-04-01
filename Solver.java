package p2;

import java.awt.Color;
import java.util.*;  // Import utilities for collections (Queue, HashSet, Map, etc.)

public class Solver {

    /**
     * Inner class to hold predecessor information for each board encountered during BFS.
     * For each board (except the starting board), we store:
     * - The board from which it was generated (its parent).
     * - The move (a char) that was applied to the parent to get this board.
     * 		- Could be a character representation of the integer as well
     */
    private static class ParentInfo {
        Board parent;
        char move; // The move applied to the parent board to produce this board

        ParentInfo(Board parent, char move) {
            this.parent = parent;
            this.move = move;
        }
    }
    
    /**
     * Instance Variables
     */ 
    // Mapping from a board configuration (reachable from the solved board) to its ParentInfo.
    	//predecessors[] = edgeTo[]
    private Map<Board, ParentInfo> predecessors;
    
    // The solved board configuration (our goal state).
    private Board solvedBoard;
    
    // Valid moves: numeric moves swap columns; letter moves shift rows.
    private static final char[] moves = {'1', '2', '3', '4', '5', '6', 'a', 'b', 'c', 'd'};

    /**
     * No-argument constructor.
     * It pre-computes the BFS tree starting from the solved board.
     */
    public Solver() {
    	//HashMap Version
    	predecessors = new HashMap<>();
    	
    	//TreeMap version
    	//predecessors = new TreeMap<>();
        
    	// Create the solved board using the default constructor of Board.
    		//Default constructor creates a solved version so this works ideally
        solvedBoard = new Board();
        
        // Precompute all reachable board configurations from the solved board.
        precomputeBFS();
    }
    
    /**
     * Precomputes the BFS tree starting from the solved board.
     * For every new board configuration encountered, it stores in the predecessors map the board
     * from which it was generated and the move used.
     */
    private void precomputeBFS() {
        Queue<Board> queue = new LinkedList<>();
        Set<Board> visited = new HashSet<>();

        // Begin with the solved board.
        queue.add(solvedBoard);
        visited.add(solvedBoard);

        //Run the BFS
        while (!queue.isEmpty()) {
            Board current = queue.poll();

            // Try every valid move from the current board.
            for (char m : moves) {
                // Use the immutable move() method (which returns a new board configuration).
                Board next = current.move(m);
                // If the move returns null, it was invalid; skip it.
                if (next == null)
                    continue;
                // If this configuration has already been processed, skip it.
                if (visited.contains(next))
                    continue;
                // Record the predecessor: from 'current' applying move 'm' gives 'next'.
                predecessors.put(next, new ParentInfo(current, m));
                visited.add(next);
                queue.add(next);
            }
        }
    }
    
    /**
     * Inverts a move.
     * Numeric moves (swaps) are self-inverse.
     * For slide moves, the inverse of 'a' is 'b' and vice versa; similarly, the inverse of 'c' is 'd' and vice versa.
     *
     * @param m the move to invert.
     * @return the inverse move.
     */
    private char invertMove(char m) {
        if (m >= '1' && m <= '6') {
            return m; // Swapping columns is self-inverse.
        }
        
        //The letter slides are what need to be inverted
        switch (m) {
            case 'a': return 'b';
            case 'b': return 'a';
            case 'c': return 'd';
            case 'd': return 'c';
            default:
                throw new IllegalArgumentException("Invalid move: " + m);
        }
    }
    
    /**
     * Solves the puzzle given a starting configuration (a 2-by-6 Color array).
     * It uses the precomputed BFS tree (from the solved board) to quickly backtrack the minimal sequence of moves.
     *
     * The method works as follows:
     * 1. Convert the input Color[][] to a Board object.
     * 2. If the board is already solved, return an empty move sequence.
     * 3. Otherwise, locate the board in the predecessors map.
     * 4. Backtrack from the starting board to the solved board:
     *    - For each step, invert the move stored (so that it represents the move needed from the start to solve the board).
     * 5. Reverse the collected move sequence (since backtracking gives moves from start back to solved).
     *
     * @param start a 2-by-6 Color array representing the randomized board configuration.
     * @return a char array of moves (the minimal solution) to transform the board into the solved configuration.
     */
    public char[] solve(Color[][] start) {
        // Create a Board from the given Color array.
        Board startBoard = new Board(start);
        
        //If it's already solved, no moves necessary
        if (startBoard.isSolved()) {
            return new char[0];
        }

        // Ensure the starting configuration is in our precomputed BFS mapping.
        if (!predecessors.containsKey(startBoard)) {
            throw new IllegalArgumentException("The given board configuration is unsolvable or not reached.");
        }

        // Backtrack from startBoard up to the solved board.
        // As the BFS was built from the solved board outward, the predecessor chain provides the moves from solved to start.
        // Invert each move to obtain the move from start to solved.
        List<Character> reversedMoves = new ArrayList<>();
        Board current = startBoard;
        
        //Start from the random start board and iterate over its parents until we get to the solved board
        while (!current.equals(solvedBoard)) {
        	
        	// Get the move it took to get there and the board it came from
            ParentInfo info = predecessors.get(current);
            if (info == null) {
                throw new IllegalStateException("Error in predecessor chain.");
            }
            // Invert the move stored: 
            //if info.move was used to go from parent to current,
            	// its inverse is needed to go from current to parent.
            
            //Add the inverted move to our list of reversed moves to begin building the path
            reversedMoves.add(invertMove(info.move));
            
            //set the new current to info.parent, effectively moving one step closer to the solved board along the predecessor chain.
            current = info.parent;
        }

        // Since we worked from the startBoard back to solvedBoard
        	//The moves are now in reverse order, so no need to reverse the list


        // Convert the list of moves to a char array.
        char[] solution = new char[reversedMoves.size()];
        for (int i = 0; i < reversedMoves.size(); i++) {
            solution[i] = reversedMoves.get(i);
        }
        return solution;
    }

    /**
     * Main method for testing the solver.
     * Creates a sample board configuration and prints the solution sequence.
     */
    public static void main(String[] args) {
        // Example starting configuration (a 2-by-6 array of Colors).
        Color[][] easy3AD = {
            {Display.VIOLET, Display.RED,    Display.GREEN,  Display.YELLOW, Display.GREEN,  Display.BLUE},
            {Display.ORANGE, Display.YELLOW, Display.ORANGE, Display.BLUE,   Display.VIOLET, Display.RED}
        };

        // Create an instance of Solver.
        Solver solver = new Solver();
        // Call solve() with the sample board.
        char[] sol = solver.solve(easy3AD);
        // Print the solution moves.
        for (char c : sol) {
            System.out.print(c);
        }
    }
    //Mark to check that is file to submit
    
}


package p2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Board implements Comparable<Board>{
	
	// Instance variables
    private char[][] grid; // A 2x6 grid representing the board
    private static final char[] COLORS = {'R', 'O', 'Y', 'G', 'B', 'V'}; // Rainbow colors in order
    
//    // To track the sequence of moves leading to this configuration.
//    private List<String> moveHistory;
    
    
//    private Set<Board> previousStates; // Stores previous board states to avoid repeats
//    	//No more string representation of board objects
//    //Perhaps remove and then debug
//    
    

    /**
     * Default constructor: Creates a solved board (Rainbow Order
     * 
     */
    public Board() {
        this.grid = new char[2][6];				// 2 x 6 board configuration
 //       this.moveHistory = new ArrayList<>(); //Stores the list of moves that will be made
 //       previousStates = new HashSet<>();	//Will store unique strings that represent board configurations
        
        // Initialize the board to the solved state
        for (int col = 0; col < 6; col++) {
            grid[0][col] = COLORS[col];
            grid[1][col] = COLORS[col];
        }
    }
    
    /**
     * Creates a board that is a given number of moves away from the solved board.
     * Random valid moves are applied. If a random move produces a repeated configuration,
     * another move is attempted.
     * 
     * @param count the number of random moves to apply.
     */
    public Board(int count) {
        this();
        Random rand = new Random();
        Board current = this;
        for (int i = 0; i < count; i++) {
            char randMove = getRandomMove(rand);
            Board next = current.move(randMove);
            
            // If the move produces a repeated configuration, try another random move.
            	//Shouldn't ever happen but just in case
            while (next == null) {
                randMove = getRandomMove(rand);
                next = current.move(randMove);
            }
            //Shuffles to the specified random board
            current = next;
        }
        // Update this board's configuration to be the result after count moves.
        this.grid = current.grid;
//        this.moveHistory = current.moveHistory;
//        this.previousStates = current.previousStates;
    }
	
    
    /**
     * Creates a board from a Color[][] grid.
     * Converts each Color to its corresponding character representation.
     * 
     * @param gridColors a 2x6 array of Color objects.
     */
  //Color objects are allowed through the java.awt.Color import
    public Board(Color[][] gridColors) {
        this();	//Have the 2x6 array ready to be iterated over
        
        //Fill the character grid to mirror the Color grid 
    		//Uses the helper function down below
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 6; col++) {
                this.grid[row][col] = getColorChar(gridColors[row][col]);
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
    /**
     * Why do I need to have a constructor for this, can't I just use the same board constructor every time I create a new object?
     */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Copy constructor for deep copying a Board.
     * This copies the grid, move history, and the set of previous states.
     * 
     * @param other the Board to copy.
     */
    public Board(Board other) {
        // Create a new grid and copy each cell exactly.
        this.grid = new char[2][6];
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 6; col++) {
                this.grid[row][col] = other.grid[row][col];
            }
        }
        // Create a new move history list copying the parent's move history.
//        this.moveHistory = new ArrayList<>(other.moveHistory);
        // Create a new set for previous states and copy parent's previous states.
//        this.previousStates = new HashSet<>(other.previousStates);
    }
    
    /**
     * Returns the copy of this Board instance that we presumably just constructed.
     *
     * @return a new Board object that is a deep copy of this board.
     */
    public Board copy() {
        return new Board(this);
    }
    
    /**
     * Helper method to get a random valid move, 
     * 	This helps with the constructor that takes the count
     */
    private char getRandomMove(Random rand) {
        char[] moves = {'1', '2', '3', '4', '5', '6', 'a', 'b', 'c', 'd'}; //Array of valid moves
        return moves[rand.nextInt(moves.length)];	//Generates a random index to select a move
    }
    
    /**
     * Color object --> Char object
     * Helper method to convert Color to char.
     * This helps with the Color[][] constructor
     * Ensures that char[][] and Color [][] match each other
     */
    private char getColorChar(Color color) {
        if (color.equals(Display.RED)) return 'R';
        if (color.equals(Display.ORANGE)) return 'O';
        if (color.equals(Display.YELLOW)) return 'Y';
        if (color.equals(Display.GREEN)) return 'G';
        if (color.equals(Display.BLUE)) return 'B';
        if (color.equals(Display.VIOLET)) return 'V';
        throw new IllegalArgumentException("Invalid color: " + color);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Do we still need it
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Char object--> Color object
     * Helper method Maps each character ('R', 'O', 'Y', 'G', 'B', 'V') to its corresponding Java Color object.
     * Throws an exception if there is an invalid color
     */
    //Prevents repetitive if-else checks by using a switch statement
    private Color getColorFromChar(char colorChar) {
        switch (colorChar) {
            case 'R': return Display.RED;
            case 'O': return Display.ORANGE;
            case 'Y': return Display.YELLOW;
            case 'G': return Display.GREEN;
            case 'B': return Display.BLUE;
            case 'V': return Display.VIOLET;
            default: throw new IllegalArgumentException("Invalid color character: " + colorChar);
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   

//    /**
//     * Returns the result of previousStates thus far
//     */
//    public Set<Board> getPreviousStates() {
//        return previousStates;
//    }
//    
    /**
     * Returns the move history that led to this board configuration.
     *
     * @return a list of moves as strings.
     */
 //   public List<String> getMoveHistory() {
 //       return moveHistory;
  //  }
    
    /**
	 * Returns the arrangement of the colors on the board as a 2 by 6
	 * array of Color type.
	 * 
	 * @return the arrangement of the colors on the board
	 */
    public Color[][] getGrid() {
        Color[][] colorGrid = new Color[2][6];	//Creates a new Color array 

        for (int row = 0; row < 2; row++) {		//Iterates through every cell
            for (int col = 0; col < 6; col++) {
                colorGrid[row][col] = getColorFromChar(grid[row][col]);
                
                //Gets the corresponding color via a helper method we defined below
                	//Below method correctly maps the characters to colors
            }
        }

        return colorGrid;
    }
    
    
    /**
     * Returns a new Board object resulting from applying the move 'x' to this board.
     * 1. A copy of the current board is created.
     * 2. The move is applied to the copy using applyMove().
     * 3. The move is recorded in the move history array.
     * 4. The copy's previousStates is updated by inheriting all of the parent's previous states
     *    and adding the parent itself.
     * 5. If the new board configuration is already present in previousStates (i.e., a repeated state),
     *    the method returns null to reject the move.
     *
     * @param c the move to apply (valid moves: '1'-'6', 'a', 'b', 'c', 'd').
     * @return the new Board after applying the move, or null if the move results in a repeated state.
     */
    public Board move(char x) {
        // Create a copy to preserve the immutability of the current board.
        Board newBoard = this.copy();
        
        // Apply the desired move to the copy.
        newBoard.applyMove(x);
        
        // Record the move in the move history.
        	//move history will be consistent across all boards as it will grow every time a move is made
        	//!!! This makes it so that you don't have to add a representation of the move manually!!!
 //       newBoard.moveHistory.add(String.valueOf(x));
        
        // Inherit all previous states from the parent, then add the parent's configuration.
        	//addAll will copy everything that was there 
        	//Then manually add the current state
//        newBoard.previousStates.addAll(this.previousStates);
//        newBoard.previousStates.add(this);
//        
//        // Check if the new board configuration has already been encountered.
//        if (newBoard.previousStates.contains(newBoard)) {
//            
//        	// If the move results in a repeated configuration. Reject this move.
//            return null;
//            //return;
//        }
        //Return the now modified copy
        return newBoard;
    }
    
    
    /**
	 * Updates the board to reflect a move being performed.
	 * 
	 * @param c the move to be performed
	 */
    public void applyMove(char c) {

    //Handles numeric column swap moves
        if (c >= '1' && c <= '6') {
            int col = c - '1';			// Subtract one so that we can have the input match our indices
            char temp = grid[0][col];	//Hold onto what was in the top, prior to swapping
            
            grid[0][col] = grid[1][col];	//Swap the top with the bottom
            grid[1][col] = temp;			//Swap to bottom with what was in the top
            
    
    //Handles the alphabetical LEFT row slides 
    //LETTERS A (top) & C (bottom)
          //Will have to always move to the end what was in slot 0 to slot 5
          //Slide everything else back 1 slot
        } else if (c == 'a') {
            char temp = grid[0][0];			//Hold onto 0th index (what will be wrapped around)
            for (int i = 0; i < 5; i++) {
                grid[0][i] = grid[0][i + 1];	//Take what is to the right of you and move it left to where you are
            }
            grid[0][5] = temp;				//Manually replace the 5th slot with the 0th slot
            
        } else if (c == 'c') {				
            char temp = grid[1][0];
            for (int i = 0; i < 5; i++) {
                grid[1][i] = grid[1][i + 1];
            }
            grid[1][5] = temp;
        
     
     //Handles the alphabetical RIGHT row slides
     //LETTERS B (top) & D (bottom)
          //Will have to always move back around what was in slot 5 to slot 0
          //Slide everything else forward 1 slot
        } else if (c == 'b') {	
            char temp = grid[0][5];			//Hold onto 5th index (what will be wrapped back around)
            for (int i = 5; i > 0; i--) {
                grid[0][i] = grid[0][i - 1];	//Take what is to the left of you and move it right to where you are
            }
            grid[0][0] = temp;    			//Manually replace the 0th slot with the 5th slot
            		
        } else if (c == 'd') {
            char temp = grid[1][5];
            for (int i = 5; i > 0; i--) {
                grid[1][i] = grid[1][i - 1];
            }
            grid[1][0] = temp;
        }else {
            throw new IllegalArgumentException("Invalid move: " + c);
        }
        
    }
    
    /**
     * Performs an in-place move on this Board.
     * Before applying the move, a deep copy of the current state is saved in previousStates.
     * Then the move is applied on the same board object.
     * After the move, it checks whether the new board configuration has already been encountered.
     *
     * @param c the move to apply (valid moves: '1'-'6', 'a', 'b', 'c', 'd').
     * @return true if the move was successful (i.e. no repeated configuration);
     *         false if the move results in a repeated board state.
     */
//    public boolean moveInPlace(char c) {
//        // Save the current state (as a deep copy) into the history.
//        previousStates.add(this.copy());
//        
//        // Apply the move in-place.
//        applyMove(c);
//        
//        // Record the move in the move history.
// //       moveHistory.add(String.valueOf(c));
//        
//        // Check if the new configuration has been seen before.
//        // This uses equals() which compares the grid of colors.
//        if (previousStates.contains(this)) {
//            return false;
//        }
//        return true;
//    }
    

	/**
	 * Determines if the board is in the solved configuration.  The board is solved
	 * if the two cells in the same column contain the same color and the colors
	 * appear in "rainbow order" from left to right (red, orange, yellow, green, blue, violet)
	 * 
	 * @return <code>true</code> if the board is solved.  
	 */
    public boolean isSolved() {
    	//Loop through the "columns" of each row and compare the items at the corresponding columns
    		
    	//FIRST CONDITION: If any of them do not equal each other, the board is not solved
    		// ||
    	//SECOND CONDITION: This ensures that the top row follows the correct rainbow sequence
    			//Since they must be the same we only need to check top row against COLORS
        for (int col = 0; col < 6; col++) {
            if (grid[0][col] != grid[1][col] || grid[0][col] != COLORS[col]) {
                return false;
            }
        }
        return true;
    }
	
    
    
    /**
     * Overrides equals() to compare boards based solely on their grid configuration.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  // If same object reference, return true
        if (!(obj instanceof Board)) return false;  // Ensure obj is a Board object
        Board other = (Board) obj;
        
        // Compare each cell of the board to ensure identical states
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 6; col++) {
                if (this.grid[row][col] != other.grid[row][col]) {
                    return false;  // If any mismatch, boards are not equal
                }
            }
        }
        return true;  // Board states are identical
    }
    
    /**
     * Implements my own hashcode function for storing Board objects in Hashsets
     * 		Can be optimized later
     */
    @Override
    public int hashCode() {
        int hash = 7;  // Arbitrary prime number as a starting point
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 6; col++) {
                hash = 31 * hash + grid[row][col];  // Multiply hash by prime 31 and add grid value
            }
        }
        return hash;
    }
    
    /**
     * Necessary for my TreeMap version to work
     * @param other
     * @return
     */
    public int compareTo(Board other) {
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 6; col++) {
                int diff = this.grid[row][col] - other.grid[row][col];
                if (diff != 0) {
                    return diff;
                }
            }
        }
        return 0;
    }
    //Mark to check that is file to submit

}

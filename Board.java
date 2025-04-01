package p1;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public class Board {
	
	// Instance variables
    private char[][] grid; // A 2x6 grid representing the board
    private static final char[] COLORS = {'R', 'O', 'Y', 'G', 'B', 'V'}; // Rainbow colors in order
    
    
    private Set<String> previousStates; // Stores previous board states to avoid repeats
    	//0-5 slots will be top row
    	//6-10 slots will be bottom row

	/**
	 * Creates a new Board that is <code> count </code> moves away from a solved board.
	 * Furthermore, the sequence of moves does not repeat any board configurations.
	 * For example, the sequence of moves a11c would not be a valid sequence of 4 moves
	 * for this constructor because the board configuration after "a" is identical to the
	 * board configuration after "a11".
	 * 
	 * @param count the number of moves to make starting from the solved board
	 */
    public Board(int count) {
        this.grid = new char[2][6];
        previousStates = new HashSet<>();	//Will store unique strings that represent board configurations
        
        // Initialize the board to the solved state
        for (int col = 0; col < 6; col++) {
            grid[0][col] = COLORS[col];
            grid[1][col] = COLORS[col];
        }
        
        // Apply 'count' random valid moves to create a shuffled starting position
        	//Will use the move() method we define below to make 'count' number of random moves away
        	//from the solved board
        Random rand = new Random();	//Created a random object for generating random numbers
        for (int i = 0; i < count; i++) {
            char randMove = getRandomMove(rand);	//Selects what the random move will be made
            move(randMove);		//Applies the selected random move via our move function
        }
    }
	
	
	/**
	 * Creates a Board whose colors are arranged as they appear in a given grid.
	 * 
	 * @param grid the arrangement of colors for the board to be created.
	 */
    	//Color objects are allowed through the java.awt.Color import
    public Board(Color[][] grid) {
        this.grid = new char[2][6];		
        previousStates = new HashSet<>();	//Same as above constructor
           
        //Fill the character grid to mirror the Color grid 
        	//Uses the helper function down below
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 6; col++) {
            	this.grid[row][col] = getColorChar(grid[row][col]);

            }
        }
    }
	
    /**
     * Color --> Char
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
    
    /**
     * Does the opposite of above Char --> Color
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
    
    /**
     * Helper method to get a random valid move, 
     * This helps with the count constructor 
     */
    private char getRandomMove(Random rand) {
        char[] moves = {'1', '2', '3', '4', '5', '6', 'a', 'b', 'c', 'd'}; //Array of valid moves
        return moves[rand.nextInt(moves.length)];	//Generates a random index to select a move
    }
	

    /**
     * Helper method to convert the board state to a string representation.
     */
    
    //0-5 are top row
    //6-10 are bottom row
    public String getBoardState() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 6; col++) {
                sb.append(grid[row][col]);
            }
        }
        return sb.toString();
    }
    
    //Returns the result of previousStates thus far
    public Set<String> getPreviousStates() {
        return previousStates;
    }
    
    
    /**
	 * Updates the board to reflect a move being performed.
	 * 
	 * @param c the move to be performed
	 */
    public void move(char c) {
    	// Convert the board state to a string via the helper method
    		//By removing the check before executing the move, every move 
    		//is now guaranteed to run before checking for repeat states
        String boardState = getBoardState();

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
        }
        
        //Get the updated board state after the move()
        boardState = getBoardState();

        // Check if this current board state has been seen before
        	//If it has been seen before, break out
        if (previousStates.contains(boardState)) {
            System.out.println("You lost! Board state repeated.");
            return;
        }
        //If it is a new board configuration, add it to the set for when we check again    
         else {
        	previousStates.add(boardState);
        }
    }
    
    
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
	 * Returns the arrangement of the colors on the board as a 2 by 6
	 * array of Color.
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

	

}

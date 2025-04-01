# BFS-Solver-for-Puzzle
Java program that creates a puzzle game to be played and also a class that can solve the puzzle in the shortest way possible

Board Class: Creates the functionality for the game board to run
Display Class: Utilizes graphics to create a visual and playable board
Board 2 Class: Only the necessary functionality to allow for the board to be solved and save runtime
Solver Class: A Breadth First Search Approach to solving the game board in the least amount of moves

The game/puzzle consists of a 2 by 6 grid where each of the colors red, orange, yellow, green, blue, and
violet are used to color 2 of the 12 cells. The object of the game/puzzle is to arrange the cells in columns of matching colors so that they appear in the order of the rainbow. (From left to right, the columns should contain red, orange, yellow, green, blue, and violet in that order.) The cells can be moved around in one of 10 ways:
      - The columns are numbered 1 through 6 from left to right. A move that is a number between 1
and 6 swaps the two colors appearing in the corresponding column.
      - All the colors in the top row can be shifted left one position with an ‘a’ move or right one
position with a ‘b’ move. A color that moves past the last column “wraps around” to the other
side of the grid.
      - All the colors in the bottom row can be shifted left one position with an ‘c’ move or right one
position with a ‘d’ move. A color that moves past the last column “wraps around” to the other
side of the grid.

The player continues to make moves by typing in the name of the move (1-6 or a-d) until either they win
by arranging all the tiles correctly, or they lose by repeating any prior board position.

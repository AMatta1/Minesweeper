/**
  VisibleField class
  This is the data that's being displayed at any one point in the game (i.e., visible field, because it's what the
  user can see about the minefield), Client can call getStatus(row, col) for any square.
  It actually has data about the whole current state of the game, including  
  the underlying mine field (getMineField()).  Other accessors related to game status: numMinesLeft(), isGameOver().
  It also has mutators related to actions the player could do (resetGameDisplay(), cycleGuess(), uncover()),
  and changes the game state accordingly.
  
  It, along with the MineField (accessible in mineField instance variable), forms
  the Model for the game application, whereas GameBoardPanel is the View and Controller, in the MVC design pattern.
  It contains the MineField that it's partially displaying.  That MineField can be accessed (or modified) from 
  outside this class via the getMineField accessor.  
 */


public class VisibleField {
   // ----------------------------------------------------------   
   // The following public constants (plus numbers mentioned in comments below) are the possible states of one
   // location (a "square") in the visible field (all are values that can be returned by public method 
   // getStatus(row, col)).
   
   // Covered states (all negative values):
   public static final int COVERED = -1;   // initial value of all squares
   public static final int MINE_GUESS = -2;
   public static final int QUESTION = -3;

   // Uncovered states (all non-negative values):
   
   // values in the range [0,8] corresponds to number of mines adjacent to this square
   
   public static final int MINE = 9;      // this loc is a mine that hasn't been guessed already (end of losing game)
   public static final int INCORRECT_GUESS = 10;  // is displayed a specific way at the end of losing game
   public static final int EXPLODED_MINE = 11;   // the one you uncovered by mistake (that caused you to lose)
   // ----------------------------------------------------------   
  
   // Reference to the minefield that the visible field 'covers' 
   private MineField coveredMineField;
   
   /** 2D array declared to hold the state of all the squares corresponding to the locations of the underlying minefield
       Represents the visible field */
   
   private int [][] stateOfSquare ;    
   
   private int numOfRows;                                                       // No. of rows in field
   private int numOfCols;                                                       // No. of columns in field
   private int numOfMines;                                                      // No. of mines in field
   
   // Holds number of squares uncovered at a paticular instant while playing a game
   private int numUncoveredSquares;
   
   // Holds number of squares marked in yellow (MINE_GUESS) by the user
   private int mineGuess;
    
   
   /**
      Create a visible field that has the given underlying mineField.
      The initial state will have all the mines covered up, no mines guessed, and the game
      not over.
      @param mineField  the minefield to use for for this VisibleField
    */
   
   public VisibleField(MineField mineField) {                                   // Total no. of lines : 10
      
      coveredMineField = mineField;                                             // Points to the underlying minefield
      
      numOfRows = getMineField().numRows();
      numOfCols = getMineField().numCols();
      numOfMines = getMineField().numMines();
      
      // Creating a visible field using a 2D array of the same dimensions as the underlying minefield
      stateOfSquare = new int [numOfRows][numOfCols];
      
      for (int i = 0; i < numOfRows; i++) {
         
         for (int j = 0; j < numOfCols; j++) {
         
            stateOfSquare [i][j]= COVERED;                                      // Covering up the squares for the initial state                               
         }
         
      }
    
      // No squares are uncovered and no mines are guessed , so game is not over
      numUncoveredSquares = 0;                                                   
      mineGuess = 0;                                                             
      
   }
     
 
   
   /**
      Reset the object to its initial state (see constructor comments), using the same underlying
      MineField. 
   */
   
   public void resetGameDisplay() {                                             // Total no. of lines : 5
      
      /** Just resets the display, not the minefield itself, because populateMineField(row,col) clears old mines
          before populating it with new ones for random MineField and for non-random MineField, we use the same minefield in
          subsequent games. */
      
      for (int i = 0; i < numOfRows; i++) {
         
         for (int j = 0; j < numOfCols; j++) {
         
            stateOfSquare [i][j] = COVERED;                                     // All squares reset to covered state
            
         }
         
      }
      
      numUncoveredSquares = 0;                                                  // No squares are uncovered and no mines are guessed
      mineGuess = 0;
   
   }
  
  
   
   /**
      Returns a reference to the mineField that this VisibleField "covers"
      @return the minefield
    */
   
   public MineField getMineField() {                                            // Total no. of lines : 1
      
      return coveredMineField;                                                  // Reference to the underlying minefield
      
   }

   
   
   /**
      Returns the visible status of the square indicated.
      @param row  row of the square
      @param col  col of the square
      @return the status of the square at location (row, col).  See the public constants at the beginning of the class
      for the possible values that may be returned, and their meanings.
      PRE: getMineField().inRange(row, col)
    */
   
   public int getStatus(int row, int col) {                                     // Total no. of lines : 2
      
      assert getMineField().inRange(row,col);                                   // Checks if the location is within range
      
      int status = stateOfSquare [row][col];
      
      return status;                                                            // Returns status of a particular square
      
   }

   
   
   /**
      Returns the the number of mines left to guess.  This has nothing to do with whether the mines guessed are correct
      or not.  Just gives the user an indication of how many more mines the user might want to guess.  This value can
      be negative, if they have guessed more than the number of mines in the minefield.     
      @return the number of mines left to guess.
    */
   
   public int numMinesLeft() {                                                  // Total no. of lines : 2
      
      int minesLeft = getMineField().numMines() - mineGuess ;                   // Holds the number of 'more' mines that the user can guess 
      
      return minesLeft;       

   }
 
   
   
   /**
      Cycles through covered states for a square, updating number of guesses as necessary.  Call on a COVERED square
      changes its status to MINE_GUESS; call on a MINE_GUESS square changes it to QUESTION;  call on a QUESTION square
      changes it to COVERED again; call on an uncovered square has no effect.  
      @param row  row of the square
      @param col  col of the square
      PRE: getMineField().inRange(row, col)
    */
   
   public void cycleGuess(int row, int col) {                                   // Total no. of lines : 8
      
      assert getMineField().inRange(row, col);
      
      // Any square with status apart from MINE_GUESS, QUESTION or COVERED, does not respond to a right click
      
      // If square is covered, change status to MINE_GUESS (yellow) on right click 
      if (stateOfSquare [row][col] == COVERED) {
         
         stateOfSquare [row][col] = MINE_GUESS; 
         mineGuess ++;                                                          // Increments value of mines guessed
         
      }
      
      // If square is marked as a guess, change status to QUESTION on right click
      else if (stateOfSquare [row][col] == MINE_GUESS) {
         
         stateOfSquare [row][col] = QUESTION;
         mineGuess -- ;                                                         // Decrements value of mines guessed
         
      }
      
      // If square is marked as question, change status to COVERED on right click , no change to no. of mine guesses
      else if (stateOfSquare [row][col] == QUESTION) {
         
         stateOfSquare [row][col] = COVERED;
         
      }
    
   }

   
   
   /**
      Uncovers this square and returns false iff you uncover a mine here.
      If the square wasn't a mine or adjacent to a mine it also uncovers all the squares in 
      the neighboring area that are also not next to any mines, possibly uncovering a large region.
      Any mine-adjacent squares you reach will also be uncovered, and form 
      (possibly along with parts of the edge of the whole field) the boundary of this region.
      Does not uncover, or keep searching through, squares that have the status MINE_GUESS. 
      Note: this action may cause the game to end: either in a win (opened all the non-mine squares)
      or a loss (opened a mine).
      @param row  of the square
      @param col  of the square
      @return false   iff you uncover a mine at (row, col)
      PRE: getMineField().inRange(row, col)
    */
   
   public boolean uncover(int row, int col) {                                   // Total no. of mines : 14
      
      assert getMineField().inRange(row, col);
       
      boolean uncover ;
      
      /** If square has a mine, square's status changed to EXPLODED_MINE leading to loss in game. Calls the losing game display
          function to modify the gameboard display accordingly. Returns false */
      
      if (getMineField().hasMine(row,col)) {                                    
         
         losingGameDisplay();
         
         // Assigning the EXPLODED_MINE status to highlight this mine in Red
         stateOfSquare [row][col] = EXPLODED_MINE;
         uncover = false;
         
      }
      
      // Enters this section if square has [0,8] adjacent mines, including one marked as 'QUESTION' and uncovers it. Returns true
      else {
        
         // If square has non-0 adjacent mines, assigns its value to status and increments numUncoveredSquares
         if (getMineField().numAdjacentMines(row,col) != 0) {
        
            stateOfSquare [row][col] = getMineField().numAdjacentMines(row,col);
            numUncoveredSquares ++;
           
         }
        
         /** If square does not have adjacent mines, calls squaresRecursiveFill or recursive function to open up a
             region consisting of other empty squares all the way till the squares with adjacent mines at the boundary of the region. */
             
         else {
           
            squaresRecursiveFill (row,col); 
           
         }
        
         uncover = true;
        
      }
    
      // Calls winningGameDisplay() to modify game display if winning condition is reached 
      winningGameDisplay();
                    
      return uncover;     
      
   }

   
   
   /**
      Returns whether the game is over.
      (Note: This is not a mutator.)
      @return whether game over
    */
   
   public boolean isGameOver() {                                                // Total no. of lines : 8
      
      boolean isGameOver = false;
      
      // Condition 1 : If there is an exploded mine on board, game is over (LOSS)
      for (int i = 0; i < numOfRows; i++ ) {                           
                                                                 
         for (int j = 0; j < numOfCols; j++ ) {
         
            if (stateOfSquare [i][j] == EXPLODED_MINE) {
               
               isGameOver = true;
               
            }
            
         }
         
      }
      
      // Condition 2 : If all non - mine squares are uncovered, game is over (WIN)
      if (numUncoveredSquares == numOfRows * numOfCols - numOfMines) { 
         
         isGameOver = true;
         
      }
              
      return isGameOver;
      
   }
 
   
   
    /**
      Returns whether this square has been uncovered.  (i.e., is in any one of the uncovered states, 
      vs. any one of the covered states).
      @param row of the square
      @param col of the square
      @return whether the square is uncovered
      PRE: getMineField().inRange(row, col)
    */
   
   public boolean isUncovered(int row, int col) {                               // Total no. of lines : 4
      
      assert getMineField().inRange(row,col);
      
      if (stateOfSquare [row][col] >= 0) {                                     // Negative status: Covered and Non-Negative status: Uncovered
    
         return true;
         
      }
      
      else {
         
         return false;  
         
      }
      
   }
   
 
   
   /**Recursive method that is called when user left clicks on an empty square, that is, one without any adjacent mines.
      Uses flood-fill algorithm (a variation of DFS) to recursively check all the 8 neighbours of the concerned
      square. Uncovers all squares that are not a mine or not adjacent to any mine, all the way to the first occurrence
      of squares with adjacent mines at the boundary of the uncovered region.
      @param row row of the empty square
      @param col col of the empty square  
    */
   
   private void squaresRecursiveFill(int row, int col) {                        // Total no. of lines : 20
      
      if (!getMineField().inRange(row,col)) {                                   // Returns if location of square is not in range
         
         return;
         
      }
      
      if (isUncovered(row,col)) {                                               // Returns if square is already uncovered 
         
         return;
      }
      
      /** Returns or stops searching in that particular direction if MINE_GUESS is encountered. Such a square is not 
          uncovered even if it falls within the uncovered region during recursion */
      
      if (stateOfSquare [row][col] == MINE_GUESS) {                         
            
         return;
         
      }
            
      int adjacentMines = getMineField().numAdjacentMines(row,col);             
     
      // If no. of adjacent mines >= 0, then assigns that value as the status and increments numUncoveredSquares
      if (adjacentMines >= 0 ) {
     
         stateOfSquare [row][col] = adjacentMines;
         numUncoveredSquares ++ ;
         
         // Returns on encountering a non-empty square at the region's boundary but still assigns status to it in outer 'if' loop
         if (adjacentMines != 0) {
            
            return;
            
         }
         
      }
           
      //Recursive call to function for 8- way connectivity - 1 for each neighbour
      
      squaresRecursiveFill (row-1, col-1);                                      // North - West neighbour
      
      squaresRecursiveFill (row-1, col);                                        // North neighbour
      
      squaresRecursiveFill (row-1, col+1);                                      // North - East neighbour
      
      squaresRecursiveFill (row, col-1);                                        // West neighbour
      
      squaresRecursiveFill (row, col+1);                                        // East neighbour
      
      squaresRecursiveFill (row+1, col-1);                                      // South - West neighbour
      
      squaresRecursiveFill (row+1, col);                                        // South neighbour
      
      squaresRecursiveFill (row+1, col+1);                                      // South - East neighbour
      
   }
   
   
   
   /**The display function that is called by 'uncover' method at the end of a losing game, that is, when a mine has been 
      uncovered / exploded. It does two things: 
      
      1) Marks a MINE_GUESS as incorrect (X) if it does not contain a mine 
      2) Indicates the location of mines that were not exploded or guessed correctly during the game with a black square
    */
   
   private void losingGameDisplay() {                                           // Total no. of lines : 6
      
      for (int i = 0; i < numOfRows; i++) {
         
         for (int j = 0; j < numOfCols; j++) {
             
            // Checks for an incorrect guess, i.e, user marked it as a MINE_GUESS but it did not contain a mine
            if (stateOfSquare [i][j] == MINE_GUESS && !getMineField().hasMine(i,j)) {
                
               stateOfSquare [i][j] = INCORRECT_GUESS;                          // Status changed to INCORRECT_GUESS (X)
                
            }
             
            // Checks for mines that existed but were not guessed during the game
            if (stateOfSquare [i][j] != MINE_GUESS && getMineField().hasMine(i,j)) {
                
               stateOfSquare [i][j] = MINE;                                     // Status changed to MINE (black)
                
            }
            
         }
         
      }
      
   }
   
   
   
   /**The display function that is called by 'uncover' method after a square is left clicked to check if the winning
      condition of the game is achieved. It basically checks if all the non - mine squares have been uncovered successfully. 
      If so, then all mines are highlighted in yellow at the end of the game  - indicating a win
    */
   
   private void winningGameDisplay() {                                           // Total no. of lines : 5
      
      if (numUncoveredSquares == numOfRows * numOfCols - numOfMines) {
              
         for (int i = 0; i < numOfRows; i++) {
         
            for (int j = 0; j < numOfCols; j++) {
         
               if (getMineField().hasMine(i,j)) {
                  
                  stateOfSquare [i][j]= MINE_GUESS;                              // If square has mine, marks it in yellow
                  
               }
               
            }
            
         }
         
      }
      
   }
   
}
                  
           
     
     
     
    
     
          
         
 
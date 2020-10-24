/** 
   MineField
      class with locations of mines for a game.
      This class is mutable, because we sometimes need to change it once it's created.
      mutators: populateMineField, resetEmpty
      includes convenience method to tell the number of mines adjacent to a location.
 */

import java.util.Random;
   
public class MineField {
   
   private boolean [][] underlyingMineField;                          // Represents the actual minefield
   private int numOfRows;
   private int numOfCols;
   private int numOfMines;
   private Random generator = new Random();                           //Single Random object used to populate till an object is in scope (like PA1)
   
   
   /**
      Create a minefield with same dimensions as the given array, and populate it with the mines in the array
      such that if mineData[row][col] is true, then hasMine(row,col) will be true and vice versa.  numMines() for
      this minefield will corresponds to the number of 'true' values in mineData.
      @param mineData  the data for the mines; must have at least one row and one col.
    */
   
   public MineField(boolean[][] mineData) {                              // Total no. of lines : 12
      
      /* For array declared as : private static boolean[][] empty = {{}}, running program with -ea flag disabled 
         creates a gameboard with no sqaures on it - UNDEFINED STATE. For enabled -ea flag, we get assertion error in this case 
         Works fine for all cases with numOfRows > 0 and numOfCols > 0 */
      
      assert mineData.length > 0 && mineData[0].length > 0;
      
      numOfRows = mineData.length;
      numOfCols = mineData[0].length;
      int mineTotal = 0;
      
      // Instance var points to mineData to enable data passing to other methods of class, eg , hasMine(row,col) method
      underlyingMineField = mineData;              
      
      // Creating a new local array and populating it with mines from mineData to enable defensive copying
      boolean [][] newMineField = new boolean [numOfRows][numOfCols];  
         
      for (int i = 0; i < numOfRows; i++) { 
            
         for (int j = 0; j < numOfCols; j++) {
            
            if (hasMine(i,j)) {
               
               newMineField [i][j] = true ;
               mineTotal ++;
               
            }
            
         }
         
      }
      
      // Instance var now points to the newly created array
      underlyingMineField = newMineField;
      
      // Keeping track of value to be passed to numMines()
      numOfMines = mineTotal;
      
   }
                                                                                  
             
      
   /**
      Create an empty minefield (i.e. no mines anywhere), that may later have numMines mines (once 
      populateMineField is called on this object).  Until populateMineField is called on such a MineField, 
      numMines() will not correspond to the number of mines currently in the MineField.
      @param numRows  number of rows this minefield will have, must be positive
      @param numCols  number of columns this minefield will have, must be positive
      @param numMines   number of mines this minefield will have,  once we populate it.
      PRE: numRows > 0 and numCols > 0 and 0 <= numMines < (1/3 of total number of field locations). 
    */
   
   public MineField(int numRows, int numCols, int numMines) {            // Total no. of lines : 8
      
      numOfRows = numRows;
      numOfCols = numCols;
      
      /** No. of mines variable to be passed to numMines() to enable minefield population later
          Until populateMineField is invoked, numMines() will be different than no. of mines currently in minefield */
      
      numOfMines = numMines;                            
      
      assert numOfRows > 0 && numOfCols > 0;
      
      int limit = numOfRows * numOfCols;                                 // Holds size of gameboard
      
      assert numOfMines >= 0 && numOfMines < limit / 3.0;
     
      // Creating an empty minefield with no mines ( i.e, all squares have 'false' value)
      underlyingMineField = new boolean [numOfRows][numOfCols];
      
      for (int i = 0; i < numOfRows; i++) {
      
         for (int j = 0; j < numOfCols; j++) {
          
            underlyingMineField [i][j] = false;
            
         }
         
      }
      
   }
      
   
   
   /**
      Removes any current mines on the minefield, and puts numMines() mines in random locations on the minefield,
      ensuring that no mine is placed at (row, col).
      @param row the row of the location to avoid placing a mine
      @param col the column of the location to avoid placing a mine
      PRE: inRange(row, col)
    */
   
   public void populateMineField(int row, int col) {                     // Total no. of lines : 11
    
      assert inRange(row,col);                                           // Checks row and col are in range as per inRange(row,col) method
      
      for (int i = 0; i < numOfRows; i++) {                              // Removes any current mines on the minefield
         
         for (int j = 0; j < numOfCols; j++) {
      
            if (hasMine(i,j)) {
         
               underlyingMineField [i][j] = false;
               
            }
            
         }
         
      }
      
      /** Generates numMines() mines randomly to populate minefield.
          Range of nos. is 0 (inclusive) and numOfRows or numOfCols (exclusive). So , nos. are generated within array range.*/
      
      for (int i = 0; i < numMines(); i++) {                             
      
         int randomRow = generator.nextInt(numOfRows);
         int randomCol = generator.nextInt(numOfCols);
      
         // Keeps regenerating new mine location if location is (row,col) or already has a mine 
         while(randomRow == row && randomCol == col || underlyingMineField [randomRow][randomCol]) {
         
            randomRow = generator.nextInt(numOfRows);
            randomCol = generator.nextInt(numOfCols);
            
         }
       
         underlyingMineField [randomRow][randomCol] = true;              // Assigns 'true' value or mine at the generated location
         
      }
      
   }
      
      
  
   /**
      Reset the minefield to all empty squares.  This does not affect numMines(), numRows() or numCols()
      Thus, after this call, the actual number of mines in the minefield does not match numMines().  
      Note: This is the state the minefield is in at the beginning of a game.
    */
   
   public void resetEmpty() {                                            // Total no. of lines : 3
      
      /** Resets all squares to empty state, i.e, removes all mines from minefield
          numMines(), numRows() or numCols() are not affected */
      
      for (int i = 0; i < numOfRows; i++) {                     
         
         for (int j = 0; j < numOfCols; j++) {
      
            underlyingMineField [i][j] = false;
            
         }
         
      }
      
   }
            
        
      
   /**
     Returns the number of mines adjacent to the specified mine location (not counting a possible 
     mine at (row, col) itself).
     Diagonals are also considered adjacent, so the return value will be in the range [0,8]
     @param row  row of the location to check
     @param col  column of the location to check
     @return  the number of mines adjacent to the square at (row, col)
     PRE: inRange(row, col)
   */
   
   public int numAdjacentMines(int row, int col) {                       // Total no. of lines : 26
      
      assert inRange(row,col);
      
      int adjacentMines = 0;                                             // Holds number of adjacent mines for a square
      
      /** All the below 'if' blocks have following structure:
          Outer 'if' : Checks if the location of the specified neighbour is within range 
          Inner 'if' : Checks if there is a mine at the neighbour's location. If so, increments adjacentMines 
          Hence, checks all the 8 neighbours surrounding a square. Does not count possible mine at (row,col) itself */
      
      if (inRange(row-1, col-1)) {                                       // Checks neighbour to 'North - West'
         
         if (hasMine(row-1, col-1)) {
            adjacentMines ++;
         }
         
      }
             
       if (inRange(row-1, col)) {                                        // Checks neighbour to 'North' 
          
          if (hasMine(row-1, col)) {
             adjacentMines ++;
         }
          
      }
             
      if (inRange(row-1, col+1)) {                                       // Checks neighbour to 'North - East' 
         
         if (hasMine(row-1, col+1)) {
            adjacentMines ++;
         }
         
      }
             
      if (inRange(row, col-1)) {                                         // Checks neighbour to 'West' 
         
         if (hasMine(row, col-1)) {
            adjacentMines ++;
         }
         
      }
             
      if (inRange(row, col+1)) {                                         // Checks neighbour to 'East' 
         
         if (hasMine(row, col+1)) {
            adjacentMines ++;
         }
         
      }
             
      if (inRange(row+1, col-1)) {                                       // Checks neighbour to 'South - West' 
         
         if (hasMine(row+1, col-1)) {
            adjacentMines ++;
         }
         
      }
             
             
      if (inRange(row+1, col)) {                                         // Checks neighbour to 'South' 
         
         if (hasMine(row+1, col)) {
            adjacentMines ++;
         }
         
      }
             
      if (inRange(row+1, col+1)) {                                       // Checks neighbour to 'South - East' 
         
         if (hasMine(row+1, col+1)) {
            adjacentMines ++;
         }
         
      }
             
      return adjacentMines;
      
   }
   
   
   
   /**
      Returns true iff (row,col) is a valid field location.  Row numbers and column numbers
      start from 0.
      @param row  row of the location to consider
      @param col  column of the location to consider
      @return whether (row, col) is a valid field location
   */
   
   public boolean inRange(int row, int col) {                            // Total no. of lines : 4
      
      boolean isInRange = false;
      
      //Row and col indices start at 0 and must be less than value of numOfRows or numOfCols
      if (row >= 0 && row < numOfRows && col >= 0 && col < numOfCols) {
         
         isInRange = true;
         
      }
      
      return isInRange;       
      
   }
   
   
   
   /**
      Returns the number of rows in the field.
      @return number of rows in the field
   */
   
   public int numRows() {                                                // Total no. of lines : 1
      
      return numOfRows; 
      
   }
   
   
   
   /**
      Returns the number of columns in the field.
      @return number of columns in the field
   */
   
   public int numCols() {                                                // Total no. of lines : 1
      
      return numOfCols;    
      
   }
   
   
   
   /**
      Returns whether there is a mine in this square
      @param row  row of the location to check
      @param col  column of the location to check
      @return whether there is a mine in this square
      PRE: inRange(row, col)   
   */ 
   
   public boolean hasMine(int row, int col) {                            // Total no. of lines : 4
       
      assert inRange(row,col);
      
      boolean hasMine = false;
         
      if (underlyingMineField [row][col]) {                              // Checks if location (row,col) has a 'true' value ,i.e, a mine 
               
         hasMine = true;
         
      }
            
      return hasMine;  
      
   }
   
   
   
   /**
      Returns the number of mines you can have in this minefield.  For mines created with the 3-arg constructor,
      some of the time this value does not match the actual number of mines currently on the field.  See doc for that
      constructor, resetEmpty, and populateMineField for more details.
      @return number of mines in minefield
    */
   
   public int numMines() {                                               // Total no. of lines : 1
      
      return numOfMines;  
      
   }
         
}



/**
   MineFieldTester 
   
   It is a test program to test the MineField class. Creates several MineField objects 
   whose data come from hard-coded or random arrays, and calls the accessors on them to figure out
   whether they were created correctly. 
   
   To run it from the command line: java MineFieldTester
 */

public class MineFieldTester {
   
   public static boolean[][] smallMineField = 
      {{false, false, false, false,true}, 
       {true, false, false, true,false}, 
       {false, true, true, false,true},
       {true, true, false, true,true}};
    
   public static boolean[][] test = 
      {{false, false, false, false,true}, 
       {true, false, true, false,true}, 
       {false, true, false, true,true},
       {true, true, true, true,true}};
   
   
   public static void main(String[] args) {

      System.out.println("Fixed array testing");

      MineField mine= new MineField(smallMineField);
      
      System.out.println("hasMine(0,0)" +mine.hasMine(0,0));
      System.out.println("hasMine(0,4)" +mine.hasMine(0,4));
      System.out.println("hasMine(2,2)" +mine.hasMine(2,2));
      System.out.println("hasMine(3,2)" +mine.hasMine(3,2));
      
      System.out.println("numRows()" + mine.numRows());
      System.out.println("numCols()" + mine.numCols());
      System.out.println("numMines()" + mine.numMines());
      
      System.out.println(" Testing adjacent method");
      
      System.out.println("mine.numAdjacentMines(2,2) is :"+ mine.numAdjacentMines(2,2)) ;
      
      System.out.println(" Testing reset empty -- resetEmpty mutator");
      
      mine.resetEmpty();
      
      System.out.println(" Testing adjacent method after reset");
      
      System.out.println("mine.numAdjacentMines(2,2) after reset is :"+ mine.numAdjacentMines(2,2)) ;
      
      System.out.println("numRows() accessor -- should be unchanged :" + mine.numRows());
      System.out.println("numCols() accessor -- should be unchanged :" + mine.numCols());
      System.out.println("numMines() accessor -- should be unchanged :" + mine.numMines());
      
      System.out.println("Random array testing");
      
      MineField mine1= new MineField(5,6,9);                 
      
      System.out.println("Testing populate field -- populateMineField mutator");
      
      mine1.populateMineField(3,4);
      
      System.out.println(" Testing adjacent method -- numAdjacentMines accessor");
      System.out.println(" Testing adjacent method after randomly created array for 2,2");
      mine1.numAdjacentMines(2,2);
      
      System.out.println(" Testing adjacent method after randomly created array for 0,0 -- boundary");
      mine1.numAdjacentMines(0,0);
      
      System.out.println(" Testing adjacent method after randomly created array for 4,0 -- boundary");
      mine1.numAdjacentMines(4,0);
      
      System.out.println(" Testing adjacent method after randomly created array for 0,4");
      mine1.numAdjacentMines(0,4);
      
      System.out.println(" Testing adjacent method after randomly created array for 0,5 -- boundary");
      mine1.numAdjacentMines(0,5);
      
      System.out.println(" Testing adjacent method after randomly created array for 4,5 -- boundary");
      mine1.numAdjacentMines(4,5);
       
      mine1.resetEmpty();
      
      System.out.println("numRows() accessor:" + mine1.numRows());
      System.out.println("numCols() accessor:" + mine1.numCols());
      System.out.println("numMines() accessor:" + mine1.numMines());

      MineField mine2= new MineField(test);
      
      System.out.println(" Testing adjacent method for hardcoded array for 2,3");
      mine2.numAdjacentMines(2,3);
      
      System.out.println("mine2.numAdjacentMines(2,3) is :"+ mine2.numAdjacentMines(2,3)) ;
     

   }

}

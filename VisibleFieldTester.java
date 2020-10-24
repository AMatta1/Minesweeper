
/**
   VisibleFieldTester 
   
   It is a test program to test the VisibleField class. Creates VisibleField objects 
   to test the class methods on the underlying mine-fields with known mine locations.
   Calls the accessors on objects to figure out whether they were created correctly. 
   
   To run it from the command line: java VisibleFieldTester
 */

public class VisibleFieldTester {
   
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

      System.out.println(" Testing");

      VisibleField visible = new VisibleField(new MineField(smallMineField));
      
      System.out.println(" Testing resetGameDisplay() ");
      
      visible.resetGameDisplay() ;
      
      System.out.println(" Testing other accessors");
      
      System.out.println("getStatus(3,2):" +visible.getStatus(3,2));
      System.out.println("getStatus(0,0):" +visible.getStatus(0,0));
      System.out.println("getStatus(3,4)-- boundary:" +visible.getStatus(3,4));
      System.out.println("numRows() of underlying minefield:" + visible.getMineField().numRows());
      System.out.println("numCols() of underlying minefield:" + visible.getMineField().numCols());
      System.out.println("numMines() of underlying minefield -- should be unchanged:" + visible.getMineField().numMines());
      
      System.out.println("isUncovered(3,2) is:" +visible.isUncovered(3,2));
       
   }

}

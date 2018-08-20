//------------------------------------------------------------------//
// GameManager.java                                                 //
//                                                                  //
// Game Manager for 2048                                            //
//                                                                  //
// Author:  W16-CSE8B-TA group                                      //
// Date:    1/17/16                                                 //
//------------------------------------------------------------------//
/**
 *  Name: Jiaqi Fan
 *  Student ID: A12584051
 *  CSE ID: cse8bwang
 *  Date: 1/22/2016
 **/

import java.util.*;
import java.io.*;

public class GameManager {
    // Instance variables
    private Board board; // The actual 2048 board
    private String outputFileName; // File to save the board to when exiting
 /* 
  * Name: GameManager Constructor  
  * Purpose: Generate new game
  * Parameters: int boardSize (the size of board).
  *             String outputBoard (save out file)
  *             Random random (a random stuff) 
  */ 
    public GameManager(int boardSize, String outputBoard, Random random) 
    {
        System.out.println("Generating a New Board");
        if (boardSize >= 2)
        {
        this.board = new Board(boardSize, random);
        }
        outputFileName = outputBoard;
    }
 /* 
  * Name: GameManager Constructor  
  * Purpose: load a saved game
  * Parameters: String inputBoard (load in file).
  *             String outputBoard (save out file)
  *             Random random (a random stuff) 
  */  
    public GameManager(String inputBoard, String outputBoard, Random random) throws IOException 
    {
        System.out.println("Loading Board from " + inputBoard);
        this.outputFileName = new String(outputBoard);
        this.board = new Board(inputBoard, random);
    }

    // TODO PSA3
    // Main play loop
    // Takes in input from the user to specify moves to execute
    // valid moves are:
    //      k - Move up
    //      j - Move Down
    //      h - Move Left
    //      l - Move Right
    //      q - Quit and Save Board
    //
    //  If an invalid command is received then print the controls
    //  to remind the user of the valid moves.
    //
    //  Once the player decides to quit or the game is over,
    //  save the game board to a file based on the outputFileName
    //  string that was set in the constructor and then return
    //
    //  If the game is over print "Game Over!" to the terminal
   /* 
   * Name: play 
   * Purpose: play the 2048 game by input command in terminal
   * Parameters: no parameter
   * Return: void 
   */  
    public void play() throws IOException 
    {
      this.printControls();
      System.out.println(board);
      while(!board.isGameOver())
      {
        System.out.println("Please Enter a Command: ");
        Scanner forinput = new Scanner(System.in);
        char w = forinput.next().charAt(0);
   
        while (w != 'k' && w != 'j' && w != 'h' && w != 'l' && w != 'q')
        {
         System.out.println("Please Enter a Command: ");
         w = forinput.next().charAt(0);
        }
        Direction dir;
        if (w == 'q')
        {
          board.saveBoard(outputFileName);
          break;
        }
       
       else if (w == 'k')
       {
          dir = Direction.UP;  
       }
       else if (w == 'j')
       {
         dir = Direction.DOWN;
       }
       else if (w == 'h')
       {
         dir = Direction.LEFT;
       }
       else if(w=='l')
       {
         dir = Direction.RIGHT;
       }
       else{
         continue;
       }
       
       if (board.canMove(dir) == true)
       {
         board.move(dir);
         board.getScore();
         board.addRandomTile();
         System.out.println(board);
       }
       
      }
      if (board.isGameOver() == true)
      {
        System.out.println("Game Over");
      }
    }

    // Print the Controls for the Game
    private void printControls() {
        System.out.println("  Controls:");
        System.out.println("    k - Move Up");
        System.out.println("    j - Move Down");
        System.out.println("    h - Move Left");
        System.out.println("    l - Move Right");
        System.out.println("    q - Quit and Save Board");
        System.out.println();
    }
}

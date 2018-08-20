//------------------------------------------------------------------//
// Board.java                                                       //
//                                                                  //
// Class used to represent a 2048 game board                        //
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


/**
 * Sample Board
 * <p/>
 * 0   1   2   3
 * 0   -   -   -   -
 * 1   -   -   -   -
 * 2   -   -   -   -
 * 3   -   -   -   -
 * <p/>
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column: grid[row][column]
 */

import java.util.*;
import java.io.*;

public class Board {
  public final int NUM_START_TILES = 2;
  public final int TWO_PROBABILITY = 90;
  public final int GRID_SIZE;
  
  
  private final Random random;
  private int[][] grid;
  private int score;
  
  /* 
   * Name: Constructor  
   * Purpose: constructs a fresh board with random titles
   * Parameters: int boardSize (the size of the board).
   *             Random random (a random stuff) 
   */  
  public Board(int boardSize, Random random) {
    this.random = random; 
    GRID_SIZE = boardSize;
    grid = new int[boardSize][boardSize];
    score = 0;
    for(int i = 0; i < boardSize; i++)
    {
      for(int j = 0; j < boardSize; j++)
      {
        grid[i][j] = 0;
      }
    }
    for(int i = 0; i < NUM_START_TILES; i++)
    {
      addRandomTile();
    }
  }  
  /* 
   * Name: Constructor  
   * Purpose: constructs a a board based off of an input file
   * Parameters: String inputBoard (the file on inputboard).
   *             Random random (a random stuff) 
   */ 
  public Board(String inputBoard, Random random) throws IOException 
  {
    this.random = random;
    File sourceFile = new File(inputBoard);
    Scanner input = new Scanner(sourceFile);
    int boardSize = input.nextInt();
    grid = new int[boardSize][boardSize];
    GRID_SIZE = boardSize;
    score = input.nextInt();
    ArrayList<Integer> temp = new ArrayList<Integer>();
    int count = 0;
    while(input.hasNextInt())
      temp.add(input.nextInt());
    for (int i = 0; i < boardSize; i++)
    {
      for (int j = 0; j < boardSize; j++)
      {
        grid[i][j] = temp.get(count);
        ++count;
      }
    }
    input.close();
  }
  
  /* 
   * Name: saveBoard 
   * Purpose: This method is called to save the current progress of 
   * the game.
   * Parameters: String outputBoard (the file that will save the 
   * output of the board that we are playing.
   * Return: void 
   */  
  public void saveBoard(String outputBoard) throws IOException 
  {
    File savefile = new File(outputBoard);
    PrintWriter output = new PrintWriter(savefile);
    output.println(GRID_SIZE);
    output.println(score);
    for(int i = 0; i < GRID_SIZE; i++)
    {
      for(int j = 0; j < GRID_SIZE; j++)
      {
        output.print(grid[i][j] + " ");
      }
      output.println(""); 
    }
  output.close();
}

/* 
 * Name: addRandomTile 
 * Purpose: This method will add random number of starter value
 * either 2 or 4 onto the board when movement is made.
 * Parameters: no parameter
 * Return: void 
 */  
public void addRandomTile() 
{
  int count = 0;
  for(int i = 0; i < GRID_SIZE; i++)
  {
    for(int j = 0; j < GRID_SIZE; j++)
    {
      if(grid[i][j] == 0)
      {
        count = count + 1;
      }
      if(count == 0)
      {
        return;
      }
    }
  }
  int location = random.nextInt(count);
  int value = random.nextInt(100);
  if(value < TWO_PROBABILITY)
  {
    value = 2;
  }
  else
  {
    value = 4;
  }
  int decisionplace = 0;
  for(int i = 0; i < GRID_SIZE; i++)
  {
    for(int j = 0; j < GRID_SIZE; j++)
    {
      if(grid[i][j] == 0)
      {
        decisionplace++;
      }
      if(decisionplace == location + 1)
      {
        grid[i][j] = value;
      }
    } 
  }   
}

// TODO PSA3
// Rotates the board by 90 degrees clockwise or 90 degrees counter-clockwise.
// If rotateClockwise == true, rotates the board clockwise , else rotates
// the board counter-clockwise
/* 
 * Name: rotate 
 * Purpose: Rotates the board by 90 degrees clockwise or 90 degrees counter-clockwise. 
 * If rotateClockwise == true, rotates the board clockwise, 
 * else rotates the board counter-clockwise 
 * Parameters: boolean rotateClockwise (decide to rotate the board clockwise or counter clockwise)
 * Return: void 
 */  
public void rotate(boolean rotateClockwise) {
  int temp1, temp2 = 0;
  int g = GRID_SIZE;
  if (rotateClockwise == true)
  {
    for(int i = 0; i < g / 2; i++)
    {
      for(int j = i; j < g - i - 1 ; j++)
      {
        temp1 = grid[i][j];
        grid[i][j] = grid[g - j - 1][i];
        grid[g - j - 1][i] = grid[g - i - 1][g - j - 1];
        grid[g - i - 1][g - j - 1] = grid[j][g - i - 1];
        grid[j][g - i - 1] = temp1;
      }
    }
  }
  else
  {
    for(int i = 0; i < g / 2; i++)
    {
      for(int j = i; j < g - i - 1 ; j++)
      {
        temp2 = grid[i][j];
        grid[i][j] = grid[j][g - i - 1];
        grid[j][g - i - 1] = grid[g - i - 1][g - j - 1];
        grid[g - i - 1][g - j - 1] = grid[g - j - 1][i];
        grid[g - j - 1][i] = temp2;
      }
    }
  }
  
}

//Complete this method ONLY if you want to attempt at getting the extra credit
//Returns true if the file to be read is in the correct format, else return
//false
public static boolean isInputFileCorrectFormat(String inputFile) {
  //The try and catch block are used to handle any exceptions
  //Do not worry about the details, just write all your conditions inside the
  //try block
  try {
    //write your code to check for all conditions and return true if it satisfies
    //all conditions else return false
    return true;
  } catch (Exception e) {
    return false;
  }
}

// No need to change this for PSA3
// Performs a move Operation
/* 
 * Name: move 
 * Purpose: performs a move operation for the board 
 * Parameters: Direction(direction) the direction that was
 * entered by player
 * Return: boolean
 */  
public boolean move(Direction direction) {
  switch (direction)
  {
    case UP:
    {
      if(canMove(direction) == true)
      {
        return MoveUp();
      }
      else
      {
        return false;
      }
    }
    case DOWN:
    {
      if(canMove(direction) == true)
      {
        return MoveDown();
      }
      else
      {
        return false;
      }
    }
    case RIGHT:
    {
      if(canMove(direction) == true)
      {
        return MoveRight();
      }
      else
      {
        return false;
      }
    }
    case LEFT:
    {
      if(canMove(direction) == true)
      {
        return MoveLeft();
      }
      else
      {
        return false;
      }
    }
  }
  return false;
}

/* 
 * Name: isGameOver 
 * Purpose: Check to see if we have a game over 
 * Parameters: no parameters
 * Return: boolean 
 */  
public boolean isGameOver() {
  if(move(Direction.UP) == false && move(Direction.DOWN) == false
    && move(Direction.RIGHT) == false && move(Direction.LEFT) == false)
  {
    System.out.println("Game Over!");
    return true;
  }
  else
  return false;
}

/* 
 * Name: canMove 
 * Purpose: Determine if we can move in a give direction
 * Parameters: Direction(direction) the direction that was 
 * entered by the player
 * Return: boolean 
 */  
public boolean canMove(Direction direction) {
  if(direction.equals(Direction.UP))
  {
    return canMoveUp();
  }
  else if(direction.equals(Direction.DOWN))
  {
    return canMoveDown();
  }
  else if(direction.equals(Direction.LEFT))
  {
    return canMoveLeft();
  }
  else if(direction.equals(Direction.RIGHT))
  {
    return canMoveRight();
  }
  else
    return false;
}

/* 
 * Name: MoveUp 
 * Purpose: helper method for the move method
 * move the board up
 * Parameters: none
 * Return: boolean 
 */  
private boolean MoveUp()
{
  int index;
  int[] temp;
  for(int j = 0; j < GRID_SIZE; j++)
  {
    temp = new int[GRID_SIZE];
    index = 0;
    for(int i = 0; i < GRID_SIZE; i++)
    {
      if(grid[i][j] != 0){
        temp[index] = grid[i][j];
        index++;
      }
      grid[i][j] = 0;
    }
    for(int i = 0; i < GRID_SIZE - 1; i++){
      if(temp[i] == temp[i + 1] && temp[i]>0){
        temp[i] = temp[i]*2;
        temp[i + 1] = 0;
      }
    }
    index = 0;
    for(int i = 0; i < GRID_SIZE; i++){
      if(temp[i] != 0){
        grid[index][j] = temp[i];
        index++;
      }
    }  
  }
  return true;
}
/* 
 * Name: MoveDown 
 * Purpose: helper method for the move method
 * move the board down
 * Parameters: none
 * Return: boolean 
 */  
private boolean MoveDown()
{
  int index;
  int[] temp;
  for(int j = 0; j < GRID_SIZE; j++)
  {
    temp = new int[GRID_SIZE];
    index = 0;
    for(int i = 0; i < GRID_SIZE; i++)
    {
      if(grid[i][j] != 0){
        temp[index] = grid[i][j];
        index++;
      }
      grid[i][j] = 0;
    }
    for(int i = GRID_SIZE-1; i > 0; i--){
      if(temp[i] == temp[i-1] && temp[i]>0){
        temp[i] = temp[i]*2;
        temp[i-1] = 0;
      }
    }
    index = GRID_SIZE -1;
    for(int i = GRID_SIZE-1; i >= 0; i--){
      if(temp[i] != 0){
        grid[index][j] = temp[i];
        index--;
      }
    }  
  }
  return true;
}
/* 
 * Name: MoveRight 
 * Purpose: helper method for the move method
 * move the board right
 * Parameters: none
 * Return: boolean 
 */  
private boolean MoveRight()
{
  int index;
  int[] temp;
  for(int j = 0; j < GRID_SIZE; j++)
  {
    temp = new int[GRID_SIZE];
    index = 0;
    for(int i = 0; i < GRID_SIZE; i++)
    {
      if(grid[j][i] != 0){
        temp[index] = grid[j][i];
        index++;
      }
      grid[j][i] = 0;
    }
    for(int i = GRID_SIZE-1; i > 0; i--){
      if(temp[i] == temp[i-1] && temp[i]>0){
        temp[i] = temp[i]*2;
        temp[i-1] = 0;
      }
    }
    index = GRID_SIZE -1;
    for(int i = GRID_SIZE-1; i >= 0; i--){
      if(temp[i] != 0){
        grid[j][index] = temp[i];
        index--;
      }
    }  
  }
  return true;
}
/* 
 * Name: MoveLeft 
 * Purpose: helper method for the move method
 * move the board left
 * Parameters: none
 * Return: boolean 
 */  
private boolean MoveLeft()
{
  int index;
  int[] temp;
  for(int j = 0; j < GRID_SIZE; j++)
  {
    temp = new int[GRID_SIZE];
    index = 0;
    for(int i = 0; i < GRID_SIZE; i++)
    {
      if(grid[j][i] != 0){
        temp[index] = grid[j][i];
        index++;
      }
      grid[j][i] = 0;
    }
    for(int i = 0; i < GRID_SIZE - 1; i++){
      if(temp[i] == temp[i + 1] && temp[i]>0){
        temp[i] = temp[i]*2;
        temp[i + 1] = 0;
      }
    }
    index = 0;
    for(int i = 0; i < GRID_SIZE; i++){
      if(temp[i] != 0){
        grid[j][index] = temp[i];
        index++;
      }
    }  
  }
  return true;
}
/* 
 * Name: canMoveUp 
 * Purpose: helper method for the canMove method
 * check if the board can move up
 * Parameters: none
 * Return: boolean 
 */  
private boolean canMoveUp()
{
  for(int j = 0; j < GRID_SIZE; j++)
  {
    for(int i = 0; i < GRID_SIZE - 1; i++)
    {
      if(grid[i][j] != 0 && grid[i][j] == grid[i + 1][j])
      {
        return true;
      }
      if (grid[i + 1][j] != 0 && grid[i][j] == 0)
      {
        return true;
      }
    }
  }
  return false;
}
/* 
 * Name: canMoveDown 
 * Purpose: helper method for the canMove method
 * check if the board can move down
 * Parameters: none
 * Return: boolean 
 */  
private boolean canMoveDown()
{
  for(int j = 0; j < GRID_SIZE; j++)
  {
    for(int i = GRID_SIZE - 1; i > 0; i--)
    {
      if(grid[i][j] != 0 && grid[i][j] == grid[i - 1][j])
      {
        return true;
      }
      if (grid[i - 1][j] != 0 && grid[i][j] == 0)
      {
        return true;
      }
    }
  }
  return false;
}
/* 
 * Name: canMoveRight 
 * Purpose: helper method for the canMove method
 * check if the board can move right
 * Parameters: none
 * Return: boolean 
 */  
private boolean canMoveRight()
{
  for(int j = GRID_SIZE - 1; j > 0; j--)
  {
    for(int i = 0; i < GRID_SIZE; i++)
    {
      if(grid[i][j] != 0 && grid[i][j] == grid[i][j - 1])
      {
        return true;
      }
      if(grid[i][j - 1] != 0 && grid[i][j] == 0)
      {
        return true;
      }
    }
  }
  return false;
}
/* 
 * Name: canMoveLeft 
 * Purpose: helper method for the canMove method
 * check if the board can move left
 * Parameters: none
 * Return: boolean 
 */  
private boolean canMoveLeft()
{
  for(int i = 0; i < GRID_SIZE; i++)
  {
    for(int j = 0; j < GRID_SIZE - 1; j++)
    {
      if(grid[i][j] != 0 && grid[i][j] == grid[i][j + 1])
      {
        return true;
      }
      if(grid[i][j + 1] != 0 && grid[i][j] == 0)
      {
        return true;
      }
    }
  }
  return false;
}
// Return the reference to the 2048 Grid
public int[][] getGrid() {
  return grid;
}

// Return the score
public int getScore() {
  for(int i = 0; i < GRID_SIZE; i++)
  {
    for(int j = 0; j < GRID_SIZE; j++)
    {
      if(grid[i][j] > score)
      {
        score = grid[i][j];
      }
    }
  }
  return score;
}

@Override
public String toString() {
  StringBuilder outputString = new StringBuilder();
  outputString.append(String.format("Score: %d\n", score));
  for (int row = 0; row < GRID_SIZE; row++) {
    for (int column = 0; column < GRID_SIZE; column++)
      outputString.append(grid[row][column] == 0 ? "    -" :
                            String.format("%5d", grid[row][column]));
    
    outputString.append("\n");
  }
  return outputString.toString();
}
}

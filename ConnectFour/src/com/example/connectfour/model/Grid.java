package com.example.connectfour.model;

import com.example.connectfour.GameSurfaceView;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Grid {
	
	private static final String TAG = Grid.class.getSimpleName();

protected int[][] currentState = new int[6][7];	//stores the state of our grid
protected int[][] player1Grid = new int [6][7];	// stores the location of player 1's tokens
protected int[][] player2Grid = new int [6][7];	// stores the location of player 2's tokens
protected int[] columnFill = {0, 0, 0, 0, 0, 0, 0};	// stores how full each column is.
	
 private Bitmap emptysquare; //
 private Bitmap redsquare;
 private Bitmap blacksquare;
 private Bitmap dropsquare;
 private int rowHeight;   //
 private int columnWidth;   //
 private int winner = 0;
 private GameSurfaceView gamePanel;

 // Grid Constructor
 public Grid(GameSurfaceView gameSurface, Bitmap empty, Bitmap red, Bitmap black, Bitmap drop, int h, int w) {
	 
	 Log.d(TAG, "** A Grid is Created!!! **");
	 
  emptysquare = empty;
  redsquare = red;
  blacksquare = black;
  dropsquare = drop;
  this.gamePanel = gameSurface;
  
  this.rowHeight = h;
  this.columnWidth = w;
  
  //resize the bitmap for each square
  emptysquare = Bitmap.createScaledBitmap(emptysquare, columnWidth, rowHeight, true);
  redsquare = Bitmap.createScaledBitmap(redsquare, columnWidth, rowHeight, true);
  blacksquare = Bitmap.createScaledBitmap(blacksquare, columnWidth, rowHeight, true);
  dropsquare = Bitmap.createScaledBitmap(dropsquare, columnWidth, rowHeight, true);
 }
 
 
  
 
 // Function to draw the Grid
 public void draw(Canvas canvas) {	 
	 //draw the token drop indicators
	 for(int i = 0; i < 7; i++){
		 canvas.drawBitmap(dropsquare,  (i * columnWidth), (rowHeight), null);
	 }
	 
	 Bitmap temp = emptysquare;	 
	 // draw the grid
	 for(int i = 0; i < 6; i++){		// loop for rows
		 for(int k = 0; k < 7; k++){	//loop for columns
			 // select the right square to draw
			 if (currentState[i][k] == 0) temp = emptysquare;
			 else if (currentState[i][k] == 1) temp = redsquare;
			 else if (currentState[i][k] == 2) temp = blacksquare;
			 //example of draw call: canvas.drawBitmap(bitmap, left, top, null);
			 canvas.drawBitmap(temp,  (k * columnWidth), ((i * rowHeight) + (2 * rowHeight)), null);			 
		 }	 
	 }	 
 }
 
 
 //function that checks if the token is dropped inside a drop zone and updates the grid and state array
 public void tokenDropped(int x, int y){
	 
	 for(int i = 0; i < 7; i++){	//loop through columns
		 
		 // check if x, y coordinates of drop are inside a drop zone
		 if (x >= (i * columnWidth) && x <= (columnWidth * (i + 1)) && y >= rowHeight && y <= (2* rowHeight)){
			 
			 //check if column is full
			 if(columnFill[i] < 6){
				 //if not full add to column
				 currentState[(5 - columnFill[i])][i] = gamePanel.getPlayer();			 	 
			 	 			 	 			 	 
			 	 //if player 1 update its grid and check if winner
			 	 if (gamePanel.getPlayer() == 1) {
			 		 player1Grid[(5 - columnFill[i])][i] = 1;
			 		/** check for winner **/
			 		  if(this.checkforwin(player1Grid)) winner = 1;
			 	 }
			 	 //else player 2 so update its grid, and check if winner
			 	 else{ 		 
			 		player2Grid[(5 - columnFill[i])][i] = 1;
			 		/** check for winner **/
			 		if(this.checkforwin(player2Grid)) winner = 2;
			 	 }
			 	 
			 	 // increment the column fill array
			 	 columnFill[i]++;
			 	 
			 	/** check for winner **/
			 	 if (winner != 0){
			 		 
			 		 /*
			 		 //reset the currentState
			 		for(int j = 0; j < 6; j++){		// loop for rows
						 for(int k = 0; k < 7; k++){	//loop for columns					
							currentState[j][k] = 0;		 
						 }	 
					 }	 	
			 		 */
			 		 
			 		 // display winner
			 		 gamePanel.displayWinner(winner);			 		 
			 		 // end game
			 		 //gamePanel.endGame();			 		 
			 	 }
			 	 
			 	 //move complete			 	 
			 	 //change player
			 	 gamePanel.changePlayer();			 	 
		 	}
			 //else column is full //invalid move, do nothing	 
		 }
	 } 
 }
 
 	
 	
		 
		 
		 /*
		  * Author: CŽline Bensoussan
		  * Line 'Em Up Algorithm
		  * Purpose: Find 4 tokens aligned horizontally, vertically or horizontally in a matrix of size 6x7
		  */
/*
		 public class LineEmUp {
		 	public static void main(String[] args){

		 		int[][] game ={{0, 0, 0, 0, 0, 0, 0},
		 				{0, 1, 0, 0, 0, 1, 0},
		 				{0, 0, 1, 1, 1, 0, 0},
		 				{0, 1, 0, 0, 1, 0, 0},
		 				{0, 0, 0, 1, 0, 1, 0},
		 				{0, 1, 0, 1, 0, 0, 1}};

		 		System.out.println(win(game));

		 	} 

	 
		 	/*
		 	 * Returns the board after someone as player
		 	 * Works with values 0 - Empty, 1 - Player1, 2-Player2
		 	 * or 0 - Empty, 1 - Any Player.
		 	 */
	 /*
		 	public static int[][] play(int[][] board, int column){
		 		if(board[5][column] == 0){
		 			for(int row = 0; row < 6; row++){
		 				if(board[row][column] == 0){
		 					board[row][column] = 1; // Add token to first free spot
		 				}
		 			}
		 		}
		 		return board; // If row is full, player cannot play, return the board as it was
		 	}
		 	
		 	*/

 		//function to check if the player's grid has 4 in a row.
 		private boolean checkforwin(int[][] playerGrid){		 
		 	//Return true if it finds 4 aligned tokens (horizontally, vertically or diagonally)
		 	//public static boolean win(int[][] board){
		 		if(winHorizontal(playerGrid) || winVertical(playerGrid) || winDiagonal(playerGrid)){
		 			return true;
		 		}
		 		else{
		 			return false;
		 		}
		 }	 

		 	//Returns true if it finds 4 tokens aligned horizontally
		 	public static boolean winHorizontal(int[][] board){	
		 		int count = 0;
		 		for(int row = 5; row >= 0; row--){
		 			count = 0; //Re-initialize count to 0 every time we change rows
		 			for(int column = 0; column < 7; column++){
		 				if(board[row][column] == 1){
		 					count++;
		 				}
		 				else if(count != 4){ //Re-initialize count if 0 found in row
		 					count = 0;
		 				}				 
		 			}
		 			if (count == 4){
		 				return true;
		 			}
		 		}
		 		return false;
		 	}

		 	//Returns true if it finds 4 tokens aligned vertically
		 	public static boolean winVertical(int[][] board){	 
		 		int count = 0;
		 		for(int column = 0; column < 7; column++){
		 			count = 0; //Re-initialize count to 0 every time we change columns
		 			for(int row = 0; row < 6; row++){
		 				if(board[row][column] == 1){
		 					count++;
		 				}
		 				else if(count != 4){ //Re-initialize count if 0 found in column
		 					count = 0;
		 				}				 
		 			}
		 			if (count == 4){
		 				return true;
		 			}
		 		}
		 		return false;
		 	}

		 	//Returns true if it finds 4 tokens aligned diagonally
		 	public static boolean winDiagonal(int[][] board){

		 		int count = 0; //Number of tokens aligned  
		 		int k = 0; //Diagonal count

		 		for (int i = 5; i >= 0; i--){ //rows beginning from bottom of board			 
		 			for (int j = 0; j < 7; j++){ //column 
		 				count = 0;
		 				if(board[i][j] == 1){

		 					// Diagonal up right
		 					if(i > 2 && j < 4){
		 						while((board[i-k][j+k]==1)){
		 							count++; 
		 							k++;
		 							if (count == 4){
		 								return true;
		 							}
		 						}
		 					} 

		 					/* Re-initialize count and k 
		 				 		(f.ex: if there were 3 tokens aligned diagonally up and right, 
		 					 	the values of k and count would have changed. */
		 					count=0; 
		 					k=0;

		 					//Diagonal up left 
		 					if(i > 2 && j > 2){ 
		 						while((board[i-k][j-k]==1)){	
		 							count++;
		 							k++;
		 							if(count==4){
		 								return true;
		 							}
		 						}
		 					}
		 				}
		 			}
		 		}
		 		return false;
		 	}

 
/*		 	
 	protected int[][] currentState = new int[6][7];	//stores the state of our grid
 	protected int[][] player1Grid = new int [6][7];	// stores the location of player 1's tokens
 	protected int[][] player2Grid = new int [6][7];	// stores the location of player 2's tokens
 	protected int[] columnFill = {0, 0, 0, 0, 0, 0, 0};	// stores how full each column is.
 	*/
	
 	//
 	 public int[] getCurrentState() {
 			// convert from 2 dimensional array to single dimension and return
 		 	int[] temp = new int[42];
 		 	for(int i = 0; i < 6; i++){		// loop for rows
 				 for(int k = 0; k < 7; k++){	//loop for columns
 					 // 
 					//temp[i+k] = currentState[i][k];
 					temp[i+k+(i*6)] = currentState[i][k];
 				 }	 
 			 }	 	
 			return temp;
 		}
 	
 	//
 	public void setCurrentState(int[] temp) {
 		
			// convert from 1 dimensional array to 2 dimension		 	
		 	for(int i = 0; i < 6; i++){		// loop for rows
				 for(int k = 0; k < 7; k++){	//loop for columns					
					currentState[i][k] = temp[i+k+(i*6)];		 
				 }	 
			 }	 	
			return;			
		}
 	 
 	 

}
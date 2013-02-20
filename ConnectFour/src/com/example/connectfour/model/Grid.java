package com.example.connectfour.model;

import com.example.connectfour.GameSurfaceView;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Grid {

	private int[][] currentState = new int[6][7];	//stores the state of our grid
	private int[][] player1Grid = new int [6][7];	// stores the location of player 1's tokens
	private int[][] player2Grid = new int [6][7];	// stores the location of player 2's tokens
	private int[] columnFill = {0, 0, 0, 0, 0, 0, 0};	// stores how full each column is.
	
 private Bitmap emptysquare; //
 private Bitmap redsquare;
 private Bitmap blacksquare;
 private Bitmap dropsquare;
 private int rowHeight;   //
 private int columnWidth;   //
 private GameSurfaceView gamePanel;

 public Grid(GameSurfaceView gameSurface, Bitmap empty, Bitmap red, Bitmap black, Bitmap drop, int h, int w) {
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
 
 
 public void draw(Canvas canvas) {	 
	 //draw the token drop indicators
	 for(int i = 0; i < 7; i++){
		 canvas.drawBitmap(dropsquare,  (i * columnWidth), (rowHeight), null);
	 }
	 
	 Bitmap temp = emptysquare;
	 
	 //canvas.drawBitmap(bitmap, left, top, null);	 
	 for(int i = 0; i < 6; i++){		// loop for rows
		 for(int k = 0; k < 7; k++){	//loop for columns
			 //
			 if (currentState[i][k] == 0) temp = emptysquare;
			 else if (currentState[i][k] == 1) temp = redsquare;
			 else if (currentState[i][k] == 2) temp = blacksquare;
			 
			 canvas.drawBitmap(temp,  (k * columnWidth), ((i * rowHeight) + (2 * rowHeight)), null);			 
		 }	 
	 }	 
 }
 
 
 //method that checks if token is dropped inside a drop zone and update grid and state array
 public void tokenDropped(int x, int y){
	 
	 for(int i = 0; i < 7; i++){	//loop through columns
		 
		 if (x >= (i * columnWidth) && x <= (columnWidth * (i + 1)) && y >= rowHeight && y <= (2* rowHeight)){
			 
			 //check if column is full
			 if(columnFill[i] < 6){
				 //if not full add to column
				 currentState[(5 - columnFill[i])][i] = gamePanel.getPlayer();
			 	 
			 	 			 	 			 	 
			 	 //if player 1 update its grid
			 	 if (gamePanel.getPlayer() == 1) {
			 		 player1Grid[(5 - columnFill[i])][i] = 1;
			 	 }
			 	 //else player 2 so update its grid
			 	 else{ 		 
			 		player2Grid[(5 - columnFill[i])][i] = 1;
			 	 }
			 	 
			 	 
			 	 columnFill[i]++;
			 	 
			 	 //move complete, check for winner then change player
			 	 //Winner??
			 	 gamePanel.changePlayer();
		 	}
			 //else //invalid move
		 
		 }
	 } 
 }
 
 
 
}
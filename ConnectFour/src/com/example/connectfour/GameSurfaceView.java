package com.example.connectfour;

import com.example.connectfour.model.Grid;
import com.example.connectfour.model.Token;

import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;


public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	
	 private static final String TAG = GameSurfaceView.class.getSimpleName();
	 
	 private Context gridActivity;

	 private GameThread thread;
	 private Token theToken;
	 private Grid theGrid;
	 
	 private Bitmap gridSquareBitmap = (BitmapFactory.decodeResource(getResources(), R.drawable.gridsquare));
	 private Bitmap redsquare = (BitmapFactory.decodeResource(getResources(), R.drawable.redsquare));
	 private Bitmap blacksquare = (BitmapFactory.decodeResource(getResources(), R.drawable.blacksquare));
	 private Bitmap dropsquare = (BitmapFactory.decodeResource(getResources(), R.drawable.dropsquare));
	 private Bitmap exitBitmap = (BitmapFactory.decodeResource(getResources(), R.drawable.exit));
	 private Bitmap redToken = (BitmapFactory.decodeResource(getResources(), R.drawable.redtoken));
	 private Bitmap blackToken = (BitmapFactory.decodeResource(getResources(), R.drawable.blacktoken));
	 
	 private int rowHeight = 0;
	 private int currentplayer;
	 
	 //Constructor
	 public GameSurfaceView(Context context) {
		 super(context);
		 gridActivity = context;
		 
	  // adding the callback (this) to the surface holder to intercept events
	  getHolder().addCallback(this);

	  // create a token and load bitmap	  	  
	  theToken = new Token(redToken, 30, 30);

	  // create the game loop thread
	  thread = new GameThread(getHolder(), this);

	  // make the GamePanel focusable so it can handle events
	  setFocusable(true);
	 }
	 
	 
	 //method to set size of the token and initialize the grid
	 public void setSizes(int h, int w){
		 redToken = Bitmap.createScaledBitmap(redToken, (w - w/5), (h - h/5), true);
		 blackToken = Bitmap.createScaledBitmap(blackToken, (w - w/5), (h - h/5), true);
		 
		 //set the current token
		 currentplayer = 1;
		 theToken.setBitmap(redToken);
		 
		 rowHeight = h;
		 theGrid = new Grid(this, gridSquareBitmap, redsquare, blacksquare, dropsquare, h, w);
	 }
	 
	 
	// Function that ends the game
	 public void endGame(){		 
		 thread.setRunning(false);
		 //((Activity) gridActivity).finish();
	 }
		
	 
	 // 
	 public void displayWinner(int player){
		 //winner =  "Player " + player + " Wins!";
		  ((GridActivity) gridActivity).displayWinnerMenu(("Player " + player + " Wins!"));
	 }
	 
	 
	 
	 @Override
	 public void surfaceChanged(SurfaceHolder holder, int format, int width,
	   int height) {
	 }

	 
	 @Override
	 public void surfaceCreated(SurfaceHolder holder) {
	  // at this point the surface is created and
	  // we can safely start the game loop
	  thread.setRunning(true);
	  thread.start();
	 }
	 

	 @Override
	 public void surfaceDestroyed(SurfaceHolder holder) {
	  Log.d(TAG, "Surface is being destroyed");
	  // tell the thread to shut down and wait for it to finish
	  // this is a clean shutdown
	  boolean retry = true;
	  while (retry) {
	   try {
	    thread.join();
	    retry = false;
	   } catch (InterruptedException e) {
	    // try again shutting down the thread
	   }
	  }
	  Log.d(TAG, "Thread was shut down cleanly");
	 }
	 
	 
	 @Override
	 public boolean onTouchEvent(MotionEvent event) {
	  
		 if (event.getAction() == MotionEvent.ACTION_DOWN) {
	   // delegating event handling to the grid
	   theToken.handleActionDown((int)event.getX(), (int)event.getY());

	   // check if in the lower part of the screen we exit
	   if (event.getY() > getHeight() - rowHeight) {
	    thread.setRunning(false);
	    ((Activity)getContext()).finish();
	   } else {
	    Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
	   }
	  }	  	  
	  
	  //
	  if (event.getAction() == MotionEvent.ACTION_MOVE) {
	   // the gestures
	   if (theToken.isTouched()) {
	    // the grid was picked up and is being dragged
		// continuously update the position of the token
	    theToken.setX((int)event.getX());
	    theToken.setY((int)event.getY());
	   }
	  }
	  	  
	  //
	  if (event.getAction() == MotionEvent.ACTION_UP) {
	   // touch was released
	   if (theToken.isTouched()) {
	    theToken.setTouched(false);
	    
	    //check if inside a drop zone and update grid and state array
	    theGrid.tokenDropped((int)event.getX(), (int)event.getY());	    
	   }
	  }	  
	  return true;
	 }

	 
	 //method that changes the current player
	 public void changePlayer(){
		 if (currentplayer == 1){
			 currentplayer = 2;
			 theToken.setBitmap(blackToken);
		 }
		 else{
			 currentplayer = 1;
			 theToken.setBitmap(redToken);
		 }		 
		 //re-initialize the token to start location
		 theToken.setX(30);
		 theToken.setY(30);
	 }
	 
	 // returns the currentplayer
	 public int getPlayer(){
		 return currentplayer;
	 }
	 
	 // Draws all the elements to the surface
	 protected void draw(Canvas canvas, Rect exitCanvas) {
	  // fills the canvas with white
	  canvas.drawColor(Color.WHITE);	  	  
	  
	  //draw the grid
	  theGrid.draw(canvas);	  
	  	  
	  //add a token to canvas
	  theToken.draw(canvas);
	  
	  //Draw "Exit" at bottom of screen
	  canvas.drawBitmap(exitBitmap, null, exitCanvas, null);
	  
	  //Draw the current player to the screen
	  Paint textPaint = new Paint();
	  textPaint.setTextSize(50);
	  if (currentplayer == 1){
		  textPaint.setColor(Color.RED);
		  canvas.drawText("Player 1", 250, 50, textPaint);
	  }
	  else{
		  textPaint.setColor(Color.BLACK);
		  canvas.drawText("Player 2", 250, 50, textPaint);
	  }	  
	 
	 }
	
	
}

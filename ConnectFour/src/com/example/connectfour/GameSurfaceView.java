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

	 private GameThread thread;
	 private Token theToken;
	 private Grid theGrid;
	 
	 private Bitmap gridBitmap = (BitmapFactory.decodeResource(getResources(), R.drawable.grid));
	 private Bitmap gridSquareBitmap = (BitmapFactory.decodeResource(getResources(), R.drawable.gridsquare));
	 
	 private Bitmap exitBitmap = (BitmapFactory.decodeResource(getResources(), R.drawable.exit));
	 private Bitmap redToken = (BitmapFactory.decodeResource(getResources(), R.drawable.redsquare));
	 
	 private int rowHeight = 0;
	 
	 public GameSurfaceView(Context context) {
	  super(context);
	  // adding the callback (this) to the surface holder to intercept events
	  getHolder().addCallback(this);


	  // create a token and load bitmap	  	  
	  theToken = new Token(redToken, 100, 100);

	  // create the game loop thread
	  thread = new GameThread(getHolder(), this);

	  // make the GamePanel focusable so it can handle events
	  setFocusable(true);
	 }
	 
	 
	 //method to set size of the token and initialize the grid
	 public void setSizes(int h, int w){
		 redToken = Bitmap.createScaledBitmap(redToken, (w - w/5), (h - h/5), true);
		 theToken.setBitmap(redToken);
		 
		 rowHeight = h;
		 theGrid = new Grid(gridSquareBitmap, h, w);
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
	  
	  if (event.getAction() == MotionEvent.ACTION_MOVE) {
	   // the gestures
	   if (theToken.isTouched()) {
	    // the grid was picked up and is being dragged
	    theToken.setX((int)event.getX());
	    theToken.setY((int)event.getY());
	   }
	  }
	  
	  if (event.getAction() == MotionEvent.ACTION_UP) {
	   // touch was released
	   if (theToken.isTouched()) {
	    theToken.setTouched(false);
	   }
	  }
	  return true;
	 }

	 
	 
	 protected void onDraw(Canvas canvas, Rect exitCanvas) {
	  // fills the canvas with black
	  canvas.drawColor(Color.BLACK);	  
	  
	// fill the entire gridcanvas with the stretched grid
	  //canvas.drawBitmap(gridBitmap, null, gridCanvas, null);
	  
	  
	  //test
	  theGrid.draw(canvas);
	  
	  //Bitmap test = Bitmap.createScaledBitmap(gridSquareBitmap, 10, 10, true);
	  //canvas.drawBitmap(test,  0, 0, null);
	  
	  
	  
	  //add a token to canvas
	  theToken.draw(canvas);
	  
	  //Draw "Exit" at bottom of screen
	  canvas.drawBitmap(exitBitmap, null, exitCanvas, null);
	  
	  /*
	  Paint textPaint = new Paint();
	  textPaint.setColor(Color.RED);
	  textPaint.setTextSize(50);
	  canvas.drawText("Exit", 0, getHeight(), textPaint);
	  */
	 
	 }
	
	

	
	
	
}

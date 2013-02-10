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
import android.graphics.Rect;
import android.view.MotionEvent;


public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	

	 private static final String TAG = GameSurfaceView.class.getSimpleName();

	 private GameThread thread;
	 private Token theToken;
	 
	 
	 private Bitmap gridBitmap = (BitmapFactory.decodeResource(getResources(), R.drawable.grid));

	 
	 public GameSurfaceView(Context context) {
	  super(context);
	  // adding the callback (this) to the surface holder to intercept events
	  getHolder().addCallback(this);

	  // create a token and load bitmap
	  theToken = new Token(BitmapFactory.decodeResource(getResources(), R.drawable.redsquare), 100, 100);

	  // create the game loop thread
	  thread = new GameThread(getHolder(), this);

	  // make the GamePanel focusable so it can handle events
	  setFocusable(true);
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
	   if (event.getY() > getHeight() - 50) {
	    thread.setRunning(false);
	    ((Activity)getContext()).finish();
	   } else {
	    Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
	   }
	  } if (event.getAction() == MotionEvent.ACTION_MOVE) {
	   // the gestures
	   if (theToken.isTouched()) {
	    // the grid was picked up and is being dragged
	    theToken.setX((int)event.getX());
	    theToken.setY((int)event.getY());
	   }
	  } if (event.getAction() == MotionEvent.ACTION_UP) {
	   // touch was released
	   if (theToken.isTouched()) {
	    theToken.setTouched(false);
	   }
	  }
	  return true;
	 }

	 
	 
	 protected void onDraw(Canvas canvas, Rect fullCanvas) {
	  // fills the canvas with black
	  //canvas.drawColor(Color.BLACK);	  
	  
		// fill the entire canvas with the stretched grid
	  canvas.drawBitmap(gridBitmap, null, fullCanvas, null);
	  
	  //add a token to canvas
	  theToken.draw(canvas);
	 }
	
	

	
	
	
}

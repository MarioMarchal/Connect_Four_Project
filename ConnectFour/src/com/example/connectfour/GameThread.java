package com.example.connectfour;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;


public class GameThread extends Thread {

	 // flag to hold game state
	 private boolean running;
	 
	 private static final String TAG = GameThread.class.getSimpleName();
	 
	 private SurfaceHolder surfaceHolder;
	 private GameSurfaceView gamePanel;

	 public GameThread(SurfaceHolder surfaceHolder, GameSurfaceView gamePanel) {
	  super();
	  this.surfaceHolder = surfaceHolder;
	  this.gamePanel = gamePanel;
	 }
	 
	 
	 public void setRunning(boolean running) {
	  this.running = running;
	 }

	 /*
	 @Override
	 public void run() {
	  long tickCount = 0L;
	  Log.d(TAG, "Starting game loop");
	  while (running) {
	   tickCount++;
	   // update game state
	   // render state to the screen
	  }
	  Log.d(TAG, "Game loop executed " + tickCount + " times");
	 }
	 */
	 
	 
	 public void run() {
		 long tickCount = 0L;
		  Canvas canvas;
		  Rect gridRec;
		  Rect exitRec;
		  Rect tokenRec;
		  
		  Log.d(TAG, "Starting game loop");
		    
		  /**Check the size of the surface and calculated the size of various parts on the screen
		   * **/
		  canvas = null;
		   // try locking the canvas for exclusive pixel editing on the surface
		   try {
		    canvas = this.surfaceHolder.lockCanvas();
		    synchronized (surfaceHolder) {
		    	
		    //get size of canvas and make a rect for each component
		      int h = canvas.getHeight();
		  	  int w = canvas.getWidth();
		  	  int rowheight = h/9;
		  	  int columnwidth = w/7;
		  	  
		  	  // the rect for the grid
		  	  //gridRec = new Rect(0, (2*rowheight), w, (8*rowheight));
		  	  
		  	  // the rect fo the exit button
		  	  exitRec = new Rect(0, (h-rowheight), w, h);
		  	  
		  	  // set the token size
		  	 this.gamePanel.setSizes((rowheight), (columnwidth));
		  	  
		     // draws the canvas on the panel
		     this.gamePanel.onDraw(canvas, exitRec);
		    }
		   } finally {
		    // in case of an exception the surface is not left in
		    // an inconsistent state
		    if (canvas != null) {
		     surfaceHolder.unlockCanvasAndPost(canvas);
		    }
		   } // end finally
		  
		  
		  while (running) {
			  tickCount++;
			  canvas = null;
		   // try locking the canvas for exclusive pixel editing on the surface
		   try {
		    canvas = this.surfaceHolder.lockCanvas();
		    synchronized (surfaceHolder) {
		    	
		    //get size of canvas and make a rect taking up the whole surface
		    	/*
		      int h = canvas.getHeight();
		  	  int w = canvas.getWidth();		  	  
		  	  Rect aRec = new Rect(0, 0, w, h);
		  	  */
		  	  
		  	  
		    	
		     // update game state
		     // draws the canvas on the panel
		     this.gamePanel.onDraw(canvas, exitRec);
		    }
		   } finally {
		    // in case of an exception the surface is not left in
		    // an inconsistent state
		    if (canvas != null) {
		     surfaceHolder.unlockCanvasAndPost(canvas);
		    }
		   } // end finally
		  }
		  Log.d(TAG, "Game loop executed " + tickCount + " times");
		 }
	 
	 
	}
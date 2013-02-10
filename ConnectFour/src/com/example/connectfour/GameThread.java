package com.example.connectfour;

import android.graphics.Canvas;
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
		  Log.d(TAG, "Starting game loop");
		  while (running) {
			  tickCount++;
			  canvas = null;
		   // try locking the canvas for exclusive pixel editing on the surface
		   try {
		    canvas = this.surfaceHolder.lockCanvas();
		    synchronized (surfaceHolder) {
		     // update game state
		     // draws the canvas on the panel
		     this.gamePanel.onDraw(canvas);
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
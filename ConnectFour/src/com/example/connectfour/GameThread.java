package com.example.connectfour;

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
	 
	 
	 
	}
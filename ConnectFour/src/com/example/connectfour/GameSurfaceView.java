package com.example.connectfour;

import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;


public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	
	private static final String TAG = GameSurfaceView.class.getSimpleName();
	private GameThread thread;

	
	public GameSurfaceView(Context context) {
	  super(context);
	  // adding the callback (this) to the surface holder to intercept events
	  getHolder().addCallback(this);
	 
	  // create the game loop thread	  
	  thread = new GameThread(getHolder(), this);
	  
	  // make the GamePanel focusable so it can handle events
	  setFocusable(true);
	 }
	
	 @Override
	 public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	 }
	
	 @Override
	 public void surfaceCreated(SurfaceHolder holder) {
	 }
	
	 @Override
	 public void surfaceDestroyed(SurfaceHolder holder) {
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
		}
	
	 @Override
	 /*
	  * check if start of a pressed gesture
	  * if in lower part of screen exit app
	  */
	 public boolean onTouchEvent(MotionEvent event) {
		 if (event.getAction() == MotionEvent.ACTION_DOWN) {
		  if (event.getY() > getHeight() - 50) {
		   thread.setRunning(false);
		   ((Activity)getContext()).finish();
		  } else {
		   Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
		  }
		 }
		 return super.onTouchEvent(event);
		}
	 
	 
	 
	 @Override
	 protected void onDraw(Canvas canvas) {
	 }
	
	
}

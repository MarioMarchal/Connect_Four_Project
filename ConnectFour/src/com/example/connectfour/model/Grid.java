package com.example.connectfour.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class Grid {

 private Bitmap bitmap; // the actual bitmap
 private int rowHeight;   //
 private int columnWidth;   // 

 public Grid(Bitmap bitmap, int h, int w) {
  this.bitmap = bitmap;
  this.rowHeight = h;
  this.columnWidth = w;
  
  //resize the bitmap for each square
  this.bitmap = Bitmap.createScaledBitmap(this.bitmap, columnWidth, rowHeight, true);
  
 }
  
 
 public void draw(Canvas canvas) { 
	 
  //canvas.drawBitmap(bitmap, left, top, null);	 
	 for(int i = 0; i < 6; i++){		// loop for rows
		 for(int k = 0; k < 7; k++){	//loop for columns
			 //
			 canvas.drawBitmap(bitmap,  (k * columnWidth), ((i * rowHeight) + (2 * rowHeight)), null);		 
		 }	 
	 }
	 
 }
 
}
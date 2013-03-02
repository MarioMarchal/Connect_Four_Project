package com.example.connectfour;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class GridActivity extends Activity {
	
	private static final String TAG = GridActivity.class.getSimpleName();
	@SuppressLint("NewApi")
	private android.app.FragmentManager fragmentManager = getFragmentManager();

	public final static String EXTRA_MESSAGE = "com.example.connectfour.EXTRA_MESSAGE";
	
	private GameSurfaceView gamePointer; 
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // set our MainGamePanel as the View
        gamePointer = new GameSurfaceView(this);
        setContentView(gamePointer);
        //setContentView(new GameSurfaceView(this));
        
        Log.d(TAG, "View added");
    }

	 @Override
	 protected void onDestroy() {
	  Log.d(TAG, "Destroying...");
	  super.onDestroy();
	 }
	
	 @Override
	 protected void onStop() {
	  Log.d(TAG, "Stopping...");
	  super.onStop();
	 }
	
	 //Reset
	 public void reset(){
		 //end thread
		 gamePointer.endGame();
		 // create new game surface
		 gamePointer = new GameSurfaceView(this);
		 setContentView(gamePointer);
	 }
	 
	
	 // Called when the there is a winner 
	@SuppressLint("NewApi")
	public void displayWinnerMenu(String winner) {		
		
		WinnerDialogFragment winnerDialog = new WinnerDialogFragment();
		winnerDialog.setMessage(winner);
		//winnerDialog.setStyle(2, );
		winnerDialog.show(fragmentManager, "tag");
		
		
		
/*
		// Create the new Dialog.
		Dialog dialog = new Dialog(this);
		// Set the title.
		dialog.setTitle("Game Over");
		// Inflate the layout.
		dialog.setContentView(R.layout.dialog_view);
		// Update the Dialog’s contents.
		TextView text = (TextView)dialog.findViewById(R.id.winner_text);
		text.setText(winner);		
		// Display the Dialog.
		dialog.show();
		
		
		
/*		works	
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		
		String message = winner;
		intent.putExtra(EXTRA_MESSAGE, message);
		
		startActivity(intent);
		*/
	}

	public void exit() {
		//end thread
		 gamePointer.endGame();
		 // finish
		 this.finish();
		
	}
	
}

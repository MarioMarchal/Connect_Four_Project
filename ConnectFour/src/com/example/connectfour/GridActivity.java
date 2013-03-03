package com.example.connectfour;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
	
	protected GameSurfaceView gamePointer; 
	
	
	@Override
	// 
    public void onBackPressed() {
		this.exit();
    }
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {    	
    	Log.d(TAG, "onCreat of GridActivity");
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
        
        /*
        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) { 
        	Log.d(TAG, "Inside onCreate. Savedinstancestate not null ... load state");
        	// Restore state members from saved instance
		    gamePointer.theGrid.setCurrentState(savedInstanceState.getIntArray(GRID_STATE));
        } 
        */
        
        
    }
   
    
    
     
	 
	 private static final String GRID_STATE = "gridstate";
	 private static final String PLAYER1 = "player1";
	 private static final String PLAYER2 = "player2";
	 private static final String COLUMNFILL = "columnfill";
	 // current player, total scores
	 
	 
	     
    
    
    protected void onPause(){
    	Log.d(TAG, "onPause called...");
  	  	super.onPause();
  	  	
  	  	gamePointer.thread.setRunning(false);
  	  	
  	    SharedPreferences storageFile = this.getPreferences(Context.MODE_PRIVATE);
  	  	
  	  	SharedPreferences.Editor editor = storageFile.edit();
  	  	
  	  	int[] temp = gamePointer.theGrid.getCurrentState();
  	  	for (int i = 0; i < temp.length; i++){
  	  		editor.putInt((GRID_STATE + i), temp[i]);  	  		
  	  	}  	
		  	  	
		editor.commit();
  	  	
    }
    
    
    protected void onResume(){
    	Log.d(TAG, "onResume called...");
  	  	super.onResume();
  	  	
  	  	/*
  	  // Restore state members from saved instance
  	  	int[] temp = new int[42];
  	  	
  	  	for (int i = 0; i < temp.length; i++){
	  		temp[i] = storageFile.getInt((GRID_STATE + i), 0);
	  		//temp[i] = 0;
	  	}
  	  	
  	  	//make sure the surface and grid are created
  	  	//while(!(gamePointer.created)){  	  		
  	  	//}
  	  	//wait;
	    gamePointer.theGrid.setCurrentState(temp);
	    */
	    
    }
    
    
	 @Override
	 protected void onDestroy() {
	  Log.d(TAG, "Destroying...");
	  super.onDestroy();
	  this.exit();
	 }
	

	 
	 @Override
	 protected void onStop() {		
		Log.d(TAG, "Stopping... onStop called!!");
		super.onStop();		

		//save the game state data
		
	 }
	 
	 
	 /////////////////////////////////////
	 
	 
	 

	 @Override
	 public void onSaveInstanceState(Bundle savedInstanceState) {
		 Log.d(TAG, "Saving State ");
		 
		 // Save the user's current game state
	     savedInstanceState.putIntArray(GRID_STATE, gamePointer.theGrid.getCurrentState());
	     
	     // Always call the superclass so it can save the view hierarchy state
	     super.onSaveInstanceState(savedInstanceState);
	 }

	 
	 @Override
	 public void onRestoreInstanceState(Bundle savedInstanceState) {
		    
		 Log.d(TAG, "Restore state Called...");
		 
		 // Always call the superclass so it can restore the view hierarchy
		    super.onRestoreInstanceState(savedInstanceState);
		   
		    // Restore state members from saved instance
		    gamePointer.theGrid.setCurrentState(savedInstanceState.getIntArray(GRID_STATE));
		    
		}
	 
	 ///////////////////////////////////////
	 
	 @Override
	 protected void onStart() {
		
		Log.d(TAG, "onStart called...");
		super.onStart();		
		
		//load the saved Game state data
		
	  
	 }
	 
	 
	 @Override
	 protected void onRestart(){
		 super.onRestart();
		 //gamePointer.thread.resume();
	 }
	
	 
	 //Reset
	 @SuppressLint("NewApi")
	public void reset(){
		 
		 // to save state call onPause
		 this.onPause();
		 this.onResume();
		 
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

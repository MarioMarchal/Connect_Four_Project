package com.example.connectfour;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;



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
	
	
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {    	
    	Log.d(TAG, "onCreat of GridActivity");
    	super.onCreate(savedInstanceState);    	
    	   
        // requesting to turn the title OFF
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
    	
    	
    	ActionBar actionBar = getActionBar();
    	//actionBar.setDisplayShowTitleEnabled(false);
    	actionBar.setSubtitle("Drag the token to the top of a column...");
    	actionBar.setTitle("You're playin Line 'Em Up!!");
    	
    	//enable the icon in the action bar to be a home button
    	actionBar.setHomeButtonEnabled(true);
    	
    	// Show the Action Bar
    	actionBar.show();
    	
        // set our MainGamePanel as the View
        gamePointer = new GameSurfaceView(this);
        setContentView(gamePointer);
        
        Log.d(TAG, "View added");        
        
    }
   
    /** action bar related**/
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_grid, menu);
        return true;
    }
    
    
    @SuppressLint("NewApi")
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    case (android.R.id.home) :
		    	this.onBackPressed();
			    return true;
		    
		    case (R.id.help) :		    	
		    	HelpDialogFragment helpDialog = new HelpDialogFragment();				
				helpDialog.show(fragmentManager, "tag");
			    return true; 
		    
		    case (R.id.restart) :
		    	this.reset();
			    return true;
			    
		    default:
		    	return super.onOptionsItemSelected(item);
	    }
    }
    
    
    
    
    ///////////////
	 private static final String GRID_STATE = "gridstate";
	 private static final String PLAYER1 = "player1";
	 private static final String PLAYER2 = "player2";
	 private static final String COLUMNFILL = "columnfill";
	 private static final String CURRENTPLAYER = "currentplayer";
	 // , total scores     
     
	 
    protected void onPause(){
    	Log.d(TAG, "onPause called...");
  	  	super.onPause();
  	  	
  	  	gamePointer.thread.setRunning(false);
  	  	
  	    SharedPreferences storageFile = this.getPreferences(Context.MODE_PRIVATE);  	  	
  	  	SharedPreferences.Editor editor = storageFile.edit();  	  	
  	  	
  	  	//loop until the grid is actually created... useful on exit after win, gives time to reset
 	   while( !(this.gamePointer.created) ){                		   
 	   }	//empty loop
  	  	
  	  	//Current State
  	  	int[] temp = gamePointer.theGrid.getCurrentState();
  	  	for (int i = 0; i < temp.length; i++){
  	  		editor.putInt((GRID_STATE + i), temp[i]);  	  		
  	  	}  	
		
  	  	// player1 State
  	  	int[] temp1 = gamePointer.theGrid.getPlayer1Grid();
  	  	for (int i = 0; i < temp1.length; i++){
  	  		editor.putInt((PLAYER1 + i), temp1[i]);  	  		
  	  	}  	
  	  	
  	  	// player2 State
  	  	int[] temp2 = gamePointer.theGrid.getPlayer2Grid();
  	  	for (int i = 0; i < temp2.length; i++){  	  		
  	  		editor.putInt((PLAYER2 + i), temp2[i]);  	  		
  	  	}
  	  	
  	  	// columnFill State
  	  	int[] temp3 = gamePointer.theGrid.getColumnFill();
  	  	for (int i = 0; i < temp3.length; i++){
  	  		editor.putInt((COLUMNFILL + i), temp3[i]);  	  		
  	  	}  	
  	  	
  	  	editor.putInt((CURRENTPLAYER), gamePointer.getPlayer());  	  	
  	  	
  	  	// save everything
		editor.commit();  	  	
    }
    
  
    //
    protected void loadSavedState(){
    	
    	SharedPreferences storageFile = this.getPreferences(Context.MODE_PRIVATE);
		 
	 	// Restore currentstate
	 	int[] temp = new int[42];	 	
	 	for (int i = 0; i < temp.length; i++){
	 		temp[i] = storageFile.getInt((GRID_STATE + i), 0);	  		
	 	}	 	
	 	//set	  	
	   gamePointer.theGrid.setCurrentState(temp);
	   
	   
	   // Restore player1 state
	 	int[] temp1 = new int[42];	 	
	 	for (int i = 0; i < temp1.length; i++){
	 		temp1[i] = storageFile.getInt((PLAYER1 + i), 0);	  		
	 	}	 	
	 	//set	  	
	   gamePointer.theGrid.setPlayer1Grid(temp1);
   
	   // Restore player2 state
	 	int[] temp2 = new int[42];	 	
	 	for (int i = 0; i < temp2.length; i++){
	 		temp2[i] = storageFile.getInt((PLAYER2 + i), 0);	  		
	 	}	 	
	 	//set	  	
	   gamePointer.theGrid.setPlayer2Grid(temp2);
	   
	   
	   // Restore columnfill state
	 	int[] temp3 = new int[7];	 	
	 	for (int i = 0; i < temp3.length; i++){
	 		temp3[i] = storageFile.getInt((COLUMNFILL + i), 0);	  		
	 	}	 	
	 	//set	  	
	   gamePointer.theGrid.setColumnFill(temp3);
	   
	   gamePointer.setPlayer(storageFile.getInt((CURRENTPLAYER), 0));
	   
	   return;    	
    }
    
     
    /////////////
    protected void onResume(){
    	Log.d(TAG, "onResume called...");
  	  	super.onResume();	    
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
	 }
	 
	 
	 /////////////////////////////////////
	 
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
		 
		 //reset all the states
		int[] temp = new int[42];
  	   	gamePointer.theGrid.setCurrentState(temp);
  	    gamePointer.theGrid.setPlayer1Grid(temp);
  	    gamePointer.theGrid.setPlayer2Grid(temp);
  	   	int[] temp1 = new int[7];
  	   	gamePointer.theGrid.setColumnFill(temp1);
  	   	gamePointer.setPlayer(1);		 
		 
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
		winnerDialog.show(fragmentManager, "tag");
			
	}

	public void exit() {
		 // finish
		 this.finish();		
	}
	
}

package com.example.connectfour;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	//public final static String EXTRA_MESSAGE = "com.example.connectfour.EXTRA_MESSAGE";
	
	/*
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
*/
	@SuppressLint("NewApi")
	private android.app.FragmentManager fragmentManager = getFragmentManager();
	private Button play = null;
	private Button help = null;
	private Button about = null;
	private Button quit = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_main);
		
		
        
		
		play = (Button)findViewById(R.id.play);
		help = (Button)findViewById(R.id.help);
		about = (Button)findViewById(R.id.about);
		quit = (Button)findViewById(R.id.exit);
		
		help.setOnClickListener(helpListener);
		about.setOnClickListener(aboutListener);
		quit.setOnClickListener(quitListener);
		
		play.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	        	sendMessage(v);
	        }
	    });
	}

	private OnClickListener helpListener = new OnClickListener() {
		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			// React to click
			HelpDialogFragment helpDialog = new HelpDialogFragment();				
			helpDialog.show(fragmentManager, "tag");
		}
	};

	private OnClickListener aboutListener = new OnClickListener() {
		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			// React to click
			AboutDialogFragment aboutDialog = new AboutDialogFragment();				
			aboutDialog.show(fragmentManager, "tag");
		}
	};

	private OnClickListener quitListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
            System.exit(0);
		}
	};

	
	
	
	
	
	
	
	
	
	////////////////////
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
*/
	
	/** Called when the user clicks the Send button 
	public void sendMessage(View view) {
	    // Do something in response to button
		
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		
		startActivity(intent);
	}


*/
	
	// Start the grid activity when the button is clicked
	public void sendMessage(View view) {
	    
		Intent intent = new Intent(this, GridActivity.class);
		/*
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		*/
		startActivity(intent);
	}

	



}

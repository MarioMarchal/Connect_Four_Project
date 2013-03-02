package com.example.connectfour;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


@SuppressLint("NewApi")
public class WinnerDialogFragment extends DialogFragment {
    
	private String message;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        
		// Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);
        
        
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View temp = inflater.inflate(R.layout.dialog_view, null);
        builder.setView(temp);
        
        
     // Update the Dialog’s contents.
     		TextView text =  (TextView) temp.findViewById(R.id.winner_text);
     		text.setText(message);
        
     		
        // set the main message
        builder.setMessage("Game Over")
        
        	.setPositiveButton(R.string.new_game, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // reset the game
                	   ((GridActivity) getActivity()).reset();
                	   
                   }
               })
               
               .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // Exit
                	   
                	   ((GridActivity) getActivity()).exit();
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
	
	
	
    
    public void setMessage(String text){
    	message = text;
    }
}
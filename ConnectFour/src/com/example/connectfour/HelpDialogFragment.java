package com.example.connectfour;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


@SuppressLint("NewApi")
public class HelpDialogFragment extends DialogFragment {
    
	//the text contents of the help menu
	private String message = "Players take turns dropping coloured discs " +
			"into a seven-column, six-row grid. The discs must be dragged and dropped at the top of the desired column. The pieces fall straight down, occupying the" +
			" next available space within the column. The object of the game is to connect four of one's own discs of the same " +
			"color next to each other vertically, horizontally, or diagonally before the opponent. Good Luck.";
	
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        
		this.setCancelable(false);
		
		// Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);
        
        
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View temp = inflater.inflate(R.layout.help_dialog_view, null);
        builder.setView(temp);
        
        
     // Update the Dialog’s contents.
     		TextView text =  (TextView) temp.findViewById(R.id.help_menu);
     		text.setText(message);
        
     		
        // set the main message
        builder.setMessage("How to play:")
        
        	.setPositiveButton("Back"/*R.string.new_game*/, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // reset the game  
                	   //((GridActivity) getActivity()).reset();                	   
                   }
               })
               
               .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // Exit
                	   //int[] temp = new int[42];
                	   //((GridActivity) getActivity()).gamePointer.theGrid.setCurrentState(temp);
                	   
                	   //((GridActivity) getActivity()).reset();
                	   
                	   
                	   
                	   (getActivity()).onBackPressed();
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
	
	
	
    
    public void setMessage(String text){
    	message = text;
    }
}
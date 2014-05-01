package pact.ledopiano;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.view.View;

public class Grille extends Activity implements View.OnClickListener {
	
	private Button play;
	private Spinner note;
	private Spinner alteration;
	private Spinner chiffrage;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grille);
		
		play = (Button) findViewById(R.id.button9);
		play.setOnClickListener(this);
		
		
		// Pour afficher la grille d'accords
		note 		= (Spinner) findViewById(R.id.spinner_note);
		alteration 	= (Spinner) findViewById(R.id.spinner_alteration);
		chiffrage 	= (Spinner) findViewById(R.id.spinner_chiffrage);
		String[] notes 			= new String[] {"A","B","C","D","E","F","G"};
		String[] alterations 	= new String[] {"", "b","#"};
		String[] chiffrages 	= new String[] {"","m"};
	    
	        
	    ArrayAdapter<String> adapterNote 		= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, notes);
	    ArrayAdapter<String> adapterAlteration 	= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, alterations);
	    ArrayAdapter<String> adapterChiffrage 	= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chiffrages);
	    
	    adapterNote.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    adapterAlteration.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    adapterChiffrage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
	    note.setAdapter(adapterNote);
	    alteration.setAdapter(adapterAlteration);
	    chiffrage.setAdapter(adapterChiffrage);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, Lecture.class);
		startActivity(intent);
		
	}
}

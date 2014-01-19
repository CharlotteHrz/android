package pact.ledopiano;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends Activity implements View.OnClickListener, OnClickListener {
	private Button choix;
	private Button grille;
	private Button gamme;
	private Button lecture;
	private CheckBox bluetooth;
	private Button quit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		choix = (Button) findViewById(R.id.button1);
		choix.setOnClickListener(this);
		grille = (Button) findViewById(R.id.button2);
		grille.setOnClickListener(this);
		gamme = (Button) findViewById(R.id.button3);
		gamme.setOnClickListener(this);
		lecture = (Button) findViewById(R.id.button4);
		lecture.setOnClickListener(this);
		bluetooth = (CheckBox) findViewById(R.id.checkBox1);
		bluetooth.setOnClickListener(this);
		quit = (Button) findViewById(R.id.button5);
		quit.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button1:
			//appel à la bibliothèque du smartphone
			Intent intent = new Intent(this, Bibliotheque.class);
			startActivity(intent);
			break;
		case R.id.button2:
			//pas fait
			break;
		case R.id.button3:
			//pas fait
			break;
		case R.id.button4:
			//appel à la bibliothèque du smartphone
			Intent intent2 = new Intent(this, Bibliotheque.class);
			startActivity(intent2);
			break;
		case R.id.checkBox1:
			//Ceci ne marche pas encore...
			Builder dialog = new AlertDialog.Builder(getApplicationContext());
			dialog.setMessage("Réactiver le bluetooth ?");
			dialog.setNeutralButton("OK", this);
			
			break;
		case R.id.button5:
			this.onPause();
			this.onStop();
			this.onDestroy();
			//
			this.finish();
		}
		
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// activer bluetooth
		
	}
	
	public void onPause(){
		super.onPause();
		//peut-être éteindre le bluetooth ?
	}
	
	public void onResume(){
		super.onResume();
		//peut-être rallumer le bluetooth ?
	}
	
	public void onStop(){
		super.onStop();
		//RAS
	}
	
	public void onRestart(){
		super.onRestart();
		//RAS
	}
	
	public void onDestroy() {
	    super.onDestroy();
		//il faudra peut-être gérer la fermeture de Threads,
		//à voir avec le module Communication
	}


}

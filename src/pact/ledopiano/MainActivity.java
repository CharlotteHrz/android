package pact.ledopiano;

import communication.Com;

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
	private CheckBox bluetooth;
	private Button quit;
	
	private Com com;

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
		bluetooth = (CheckBox) findViewById(R.id.checkBox1);
		bluetooth.setOnClickListener(this);
		quit = (Button) findViewById(R.id.button5);
		quit.setOnClickListener(this);
		
		com = new Com(this);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button1:
			//ouverture d'une nouvelle activité
			Intent intent = new Intent(this, Choix.class);
			startActivity(intent);
			break;
		case R.id.button2:
			//passage direct à la fenêtre montrant la grille d'accord
			Intent intent2 = new Intent(this, Grille.class);
			startActivity(intent2);
			break;
		case R.id.button3:
			//pour demander au beandeau d'afficher une gamme
			Intent intent3 = new Intent(this, Gamme.class);
			startActivity(intent3);
			break;
		case R.id.checkBox1:
			//pour réactiver le bluetooth
			//pas encore au point...
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
		com.allumerBluetooth();
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


	
	
	
	
	
	public static void etatBluetooth(int i) {
		//Décoche le bouton bluetooth du smartphone
		// i=0 si le bluetooth est éteint
		// i=1 s'il est allumé
				if (i==0) System.out.println("bluetooth éteint");
				else System.out.println("bluetooth allumé");
			}

			
			public static void problemeDeConnexion(){
		//Le but est d'afficher un message à l'utilisateur pour lui dire qu'il y a un problème avec le bluetooth.
			}
	
	
	
}

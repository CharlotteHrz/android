package pact.ledopiano;

import communication.Com;
import communication.ConnectThread;
import communication.ConnectedThread;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
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
	private static CheckBox bluetooth;
	private Button quit;
	
	private static Com com;
	private static ConnectThread cThread;
	private static ConnectedThread thread;
	private final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
	final static int code_bluetooth = 1;

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

		if(adapter.isEnabled()==false){
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, code_bluetooth);
		//empêche l'appel au bluetooth pendant l'allumage
			}
		
		com = new Com();
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
			//pour demander au bandeau d'afficher une gamme
			Intent intent3 = new Intent(this, Gamme.class);
			startActivity(intent3);
			break;
		case R.id.checkBox1:
			while(adapter.isEnabled()==false){}
			com.connexionArduino();
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == code_bluetooth) {
	        if (resultCode == RESULT_OK) {
	        	System.out.println("bluetoothAdapter allumé");
	        }
	        else MainActivity.problemeDeConnexion();
	    }
	}
	
	
	
	//Cocher ou décocher le bouton bluetooth si besoin est.
	public static void etatBluetooth(boolean b) {
		bluetooth.setChecked(b);
	}


	//afficher un message pour signaler un pb de bluetooth
	public static void problemeDeConnexion(){
		MainActivity.etatBluetooth(false);
				//cThread.cancel();	moyen... (car boucle !)
				//thread.cancel();	moyen...
		//puis retenter la connexion ?
		com.connexionArduino();
		//ou afficher une fenêtre à l'utilisateur ?
	}

	public static void signal(ConnectThread ct){
		cThread = ct;
		cThread.run();
	}
	
	public static void signal(ConnectedThread ct){
		thread = ct;
		thread.run();
		
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
		adapter.disable();
		
		if (cThread != null) {
			cThread.cancel();
			cThread.interrupt();
		}
		
		if (thread != null) {
			thread.cancel();
			thread.interrupt();
		}
		
	    super.onDestroy();
		//il faudra peut-être gérer la fermeture de Threads,
		//à voir avec le module Communication
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}


	
	
}

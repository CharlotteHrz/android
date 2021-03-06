package pact.ledopiano;

import java.io.UnsupportedEncodingException;

import communication.Com;
import communication.ConnectThread;
import communication.ConnectedThread;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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
	
	private Com com;
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
		
		com = new Com();
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button1:
			//ouverture d'une nouvelle activit�
			Intent intent = new Intent(this, Choix.class);
			startActivity(intent);
			break;
		case R.id.button2:
			//passage direct � la fen�tre montrant la grille d'accord
			Intent intent2 = new Intent(this, Grille.class);
			startActivity(intent2);
			break;
		case R.id.button3:
			//pour demander au bandeau d'afficher une gamme
			Intent intent3 = new Intent(this, Gamme.class);
			startActivity(intent3);
			break;
		case R.id.checkBox1:
		//devrait �tre dans la classe Com mais n'y fonctionne pas
			//(pb de thread, de classe fille ?
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, code_bluetooth);
		//emp�che l'appel au bluetooth pendant l'allumage
		    while(adapter.isEnabled()==false){}
			
			//com.allumerBluetooth();
			
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
	        	MainActivity.etatBluetooth(true);
	        }
	        else MainActivity.problemeDeConnexion();
	    }
	}
	
	
	
	//Cocher ou d�cocher le bouton bluetooth si besoin est.
	public static void etatBluetooth(boolean b) {
		bluetooth.setChecked(b);
	}

			
	public static void problemeDeConnexion(){
		//Le but est d'afficher un message � l'utilisateur pour lui dire qu'il y a un probl�me avec le bluetooth.
		MainActivity.etatBluetooth(false);
	}

	public static void signal(ConnectThread ct){
		cThread = ct;
		cThread.run();
	}
	
	public static void signal(ConnectedThread ct){
		thread = ct;
		
		try {
			thread.transmettre("0x01".getBytes("ASCII"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public void onPause(){
		super.onPause();
		//peut-�tre �teindre le bluetooth ?
	}
	
	public void onResume(){
		super.onResume();
		//peut-�tre rallumer le bluetooth ?
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
		
	    super.onDestroy();
		//il faudra peut-�tre g�rer la fermeture de Threads,
		//� voir avec le module Communication
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}


	
	
}

package com.example.test;

import java.util.Set;

import communication.Com;
import communication.ConnectThread;
import communication.ConnectedThread;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
	private static ConnectedThread thread;
	private Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
		
	}
	
	public void onClick(View v){
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		BluetoothDevice arduino = null;
		//String adress = "0CBD51B6F4BF";   C'est celle de Franck.
	//Il faut y mettre l'adresse MAC de l'arduino.
		String adress = "000666631F82";
		
		//CECI NE MARCHE PAS !!!
		//arduino = adapter.getRemoteDevice(adress);
		
		Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
		for (BluetoothDevice device : pairedDevices) {
		arduino = device;
		}
		//new ConnectThread(arduino);
		
		button.setText(arduino.getName());
		
		//test.allumerBluetooth();
		//test.connexionArduino();
		
		//thread.transmettre(new byte[] {(byte)1,(byte)2,(byte)3,(byte)4});
		
		//Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		//startActivityForResult(enableBtIntent, 1);
	}
	
	public static void signal(ConnectedThread ct){
		thread = ct;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

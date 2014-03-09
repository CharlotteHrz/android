package communication;

import java.util.Set;

import pact.ledopiano.MainActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

public class Com extends Activity {
	MainActivity main;
	final BluetoothAdapter adapter;
	
	public Com(MainActivity activity) {
		main = activity;
		adapter = BluetoothAdapter.getDefaultAdapter();
		if (adapter.isEnabled()){
			MainActivity.etatBluetooth(true);}
		else{
			MainActivity.etatBluetooth(false);
		}
	}
	
	public void connexionArduino() {
		BluetoothDevice arduino = null;
		//String adress = "000666631F82";
	//Il faut y mettre l'adresse MAC de l'arduino.
		//car cette méthode ne marche pas pour l'instant
		//arduino = adapter.getRemoteDevice(adress);
		
		Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
		for (BluetoothDevice device : pairedDevices) {
		arduino = device;
		}
		new ConnectThread(arduino, main);
	}
	
	
}

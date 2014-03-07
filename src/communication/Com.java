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
	final static int code_bluetooth = 1;
	
	public Com(MainActivity activity) {
		main = activity;
		adapter = BluetoothAdapter.getDefaultAdapter();
		if (adapter.isEnabled()){
			main.etatBluetooth(1);}
		else{
			main.etatBluetooth(0);
		}
	}


	public void allumerBluetooth() {
	    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	    startActivityForResult(enableBtIntent, code_bluetooth);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == code_bluetooth) {
	        if (resultCode == RESULT_OK) {
	        	main.etatBluetooth(1);
	        }
	    }
	}
	
	public void connexionArduino() {
		BluetoothDevice arduino = null;
		String adress = "000666631F82";
	//Il faut y mettre l'adresse MAC de l'arduino.
		//car cette m�thode ne march pas pour l'instant
		//arduino = adapter.getRemoteDevice(adress);
		
		Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
		for (BluetoothDevice device : pairedDevices) {
		arduino = device;
		}
		arduino = adapter.getRemoteDevice(adress);
		new ConnectThread(arduino, main);
	}
	
	
}

package communication;

import java.util.Set;

import pact.ledopiano.MainActivity;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

public class Com extends Activity {
	final BluetoothAdapter adapter;
	
	public Com() {
		adapter = BluetoothAdapter.getDefaultAdapter();
		if (adapter.isEnabled()){
			MainActivity.etatBluetooth(true);}
		else{
			MainActivity.etatBluetooth(false);
		}
	}
	
	public void connexionArduino() {
		BluetoothDevice arduino = null;
		String adress = "00:06:66:63:1F:82";
	//Il faut y mettre l'adresse MAC de l'arduino.
		arduino = adapter.getRemoteDevice(adress);
		new ConnectThread(arduino);
	}
	
	
}

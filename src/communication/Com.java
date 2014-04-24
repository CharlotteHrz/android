package communication;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

public class Com extends Activity {
	final BluetoothAdapter adapter;
	
	public Com() {
		adapter = BluetoothAdapter.getDefaultAdapter();
	}
	
	public void connexionArduino() {
		BluetoothDevice arduino = null;
		//String adress = "000666631F82";
	//Il faut y mettre l'adresse MAC de l'arduino.
		//car cette m�thode ne marche pas pour l'instant
		//arduino = adapter.getRemoteDevice(adress);
		
		Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
		for (BluetoothDevice device : pairedDevices) {
				arduino = device;
		Log.e("Ici","Appareil : "+device.getName());
		}
		new ConnectThread(arduino);
	}
	
	
}

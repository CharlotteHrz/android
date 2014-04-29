package communication;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

public class Com extends Activity {
	final BluetoothAdapter adapter;
	
	public Com() {
		adapter = BluetoothAdapter.getDefaultAdapter();
	}
	
	public void connexionArduino() {
		BluetoothDevice arduino = null;
		String adress = "00:06:66:63:1F:82";
	//Il faut y mettre l'adresse MAC de l'arduino.
		arduino = adapter.getRemoteDevice(adress);
		new ConnectThread(arduino);
	}
	
	
}

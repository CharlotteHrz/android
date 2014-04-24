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
	//on se connecte grâce à l'adresse MAC de l'arduino
		String address = "00:06:66:63:1F:82";
		arduino = adapter.getRemoteDevice(address);
		
		new ConnectThread(arduino);
	}
	
	
}

package communication;

import java.io.IOException;
import java.util.UUID;

import pact.ledopiano.MainActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    
	public ConnectThread(BluetoothDevice arduino) {
        BluetoothSocket tmp = null;
        mmDevice = arduino;
    	
        try {
        	tmp = arduino.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        } catch (Exception e) {
        	this.cancel();
        }
        mmSocket = tmp;
        //c'est MainActivity qui appelle run()
        MainActivity.signal(this);
    }

    
    public void run() {
        try {
        	BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        	adapter.cancelDiscovery();
            mmSocket.connect();
        } catch (IOException connectException) {
        	Log.e("ConnectThread","Erreur ouverture Socket\n"+connectException);
            this.cancel();
            
        }
        
        new ConnectedThread(mmSocket);
    }
 
    //Will cancel an in-progress connection, and close the socket
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        	MainActivity.problemeDeConnexion();
        }
    }

}

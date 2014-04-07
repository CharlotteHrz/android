package communication;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

import pact.ledopiano.MainActivity;
import android.annotation.SuppressLint;
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
    	
        	//envoyer qqc pour réveiller le bluetooth
        try {
        	while (tmp==null){
				tmp = arduino.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        	}
        } catch (IOException e) {
				e.printStackTrace();
			}
        
        mmSocket = tmp;
        MainActivity.signal(this);
        
        try {
        	BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        	adapter.cancelDiscovery();
            mmSocket.connect();
        } catch (IOException connectException) {
        	Log.e("ConnectThread","Erreur ouverture Socket\n"+connectException);
            try {
                mmSocket.close();
            } catch (IOException closeException) {
            	MainActivity.problemeDeConnexion();
            }
            
        }
        
        new ConnectedThread(mmSocket);
    }

    
    public void run() {
 /*
        try {
            mmSocket.connect();
        } catch (IOException connectException) {
        	Log.e("ConnectThread","Erreur ouverture Socket\n"+connectException);
            try {
                mmSocket.close();
            } catch (IOException closeException) {
            	MainActivity.problemeDeConnexion();
            }
            
        }
        
        new ConnectedThread(mmSocket);*/
    }
 
    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        	MainActivity.problemeDeConnexion();
        }
    }

}

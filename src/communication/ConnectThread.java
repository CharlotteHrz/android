package communication;

import java.io.IOException;
import java.util.UUID;

import pact.ledopiano.MainActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;

public class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    
	public ConnectThread(BluetoothDevice arduino) {
        BluetoothSocket tmp = null;
        mmDevice = arduino;
    	
        try {
            tmp = arduino.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        } catch (IOException e) {
        	MainActivity.problemeDeConnexion();
        }
        mmSocket = tmp;
        MainActivity.signal(this);
    }

    
    public void run() {
 
        try {
            mmSocket.connect();
        } catch (IOException connectException) {

            try {
                mmSocket.close();
            } catch (IOException closeException) {
            	MainActivity.problemeDeConnexion();
            }
            
        }
        
        new ConnectedThread(mmSocket);
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

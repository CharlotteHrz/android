package communication;

import java.io.IOException;
import java.util.UUID;

import pact.ledopiano.MainActivity;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class ConnectThread extends Thread {
	private MainActivity main;
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private final UUID uuid = UUID.fromString("000666631F82");
 
    public ConnectThread(BluetoothDevice arduino, MainActivity activity) {
    	main = activity;
        BluetoothSocket tmp = null;
        mmDevice = arduino;
 
        try {
            tmp = arduino.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
        	main.problemeDeConnexion();
        }
        mmSocket = tmp;
    }

    
    public void run() {
 
        try {
            mmSocket.connect();
        } catch (IOException connectException) {

            try {
                mmSocket.close();
            } catch (IOException closeException) {
            	main.problemeDeConnexion();
            }
            
        }
 
        new ConnectedThread(mmSocket, main);
    }
 
    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        	main.problemeDeConnexion();
        }
    }

}

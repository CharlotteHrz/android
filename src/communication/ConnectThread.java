package communication;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import smartphone.Pipeau;

public class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private final UUID uuid = UUID.fromString("000666631F82");
 
    public ConnectThread(BluetoothDevice arduino) {
        BluetoothSocket tmp = null;
        mmDevice = arduino;
 
        try {
            tmp = arduino.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
        	Pipeau.problemeDeConnexion();
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
            	Pipeau.problemeDeConnexion();
            }
            
        }
 
        new ConnectedThread(mmSocket);
    }
 
    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        	Pipeau.problemeDeConnexion();
        }
    }

}

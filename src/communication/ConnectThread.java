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
        	//on essaye 2 fois pour r�veiller la carte bluetooth
        try {
				BluetoothSocket inutile = mmDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
				System.out.println("Socket inutile : "+inutile);
		for(int i=1; i<1000000; i++){}
				tmp = mmDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
				System.out.println("Socket utilis�e : "+tmp);

        } catch (IOException e) {
				System.out.println("pb cr�ation Socket");
				MainActivity.problemeDeConnexion();
			}
        mmSocket = tmp;
        System.out.println("Socket : "+mmSocket.toString());
        MainActivity.signal(this);
	}
	
	public void run(){
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        adapter.cancelDiscovery();
    	for(int i = 1; i<4; i++){
        try {
        		System.out.println("connexion Socket num�ro " + i);
        		mmSocket.connect();
        	} catch (IOException connectException) {
        	Log.e("ConnectThread","Erreur ouverture Socket "+i);
        	continue;
        	}
        new ConnectedThread(mmSocket);
		}
        //on n'execute ce code que si la connection a rat� 3 fois
        this.cancel();
    }

 
//si pb, on arr�te tout
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        	MainActivity.problemeDeConnexion();
        }
    }

}

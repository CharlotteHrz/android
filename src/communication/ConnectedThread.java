package communication;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;

import pact.ledopiano.MainActivity;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class ConnectedThread extends Thread {
	private final BluetoothSocket socket;
	private final OutputStream stream;
	//Peut-être mettre un attribut supplémentaire = buffer ?
	
	public ConnectedThread(BluetoothSocket bs) {
		socket = bs;
        System.out.println("La Socket est " + socket);
		OutputStream toolStream = null;
		try{
			toolStream = socket.getOutputStream();
			} catch(IOException e){
				MainActivity.problemeDeConnexion();
			}
		stream = toolStream;
		System.out.println("Au début de CtedThread, le stream est " + stream);
		
		//Se signaler à la méthode main
		MainActivity.signal(this);
		
	}
	
	
	public void run() {
		try {
			this.transmettre("bonjour".getBytes("ASCII"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Dans run(), le stream est " + stream);
	}
	
	
	public void transmettre(byte[] buffer){
		//éventuellement gérer les indices de début et de fin d'écriture,
		//	ainsi que l'effacement des données écrites
		//  (si utilisation d'un attribut buffer
		
		for(byte a : buffer){
			System.out.println((char) a);
		}
		
		System.out.println("Avant le bug, le stream est " + stream);
		try{
			stream.write(new byte[] {1,2});
			} catch(IOException e){
				Log.e("My activity", "Erreur lors de l'écriture dans la socket");
			}
	}



	public void cancel(){
		//à compléter
	}

}
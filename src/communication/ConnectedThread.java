package communication;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;

import pact.ledopiano.MainActivity;

import android.bluetooth.BluetoothSocket;

public class ConnectedThread extends Thread {
	private final BluetoothSocket socket;
	private final OutputStream stream;
	//Peut-être mettre un attribut supplémentaire = buffer ?
	
	
	public ConnectedThread(BluetoothSocket bs) {
		socket = bs;
		OutputStream toolStream = null;
		ConnectedThread.setDefaultUncaughtExceptionHandler(new ThreadHandler());
		try{
			OutputStream tool = socket.getOutputStream();
			} catch(IOException e){
				MainActivity.problemeDeConnexion();
			}
		stream = toolStream;
		
		//Se signaler à la méthode main
		MainActivity.signal(this);
		
	}
	
	
	//rajouter des méthodes run() et cancel() !!!
	
	
	public void transmettre(byte[] buffer){
		//éventuellement gérer les indices de début et de fin d'écriture,
		//	ainsi que l'effacement des données écrites
		//  (si utilisation d'un attribut buffer
		
		for(byte a : buffer){
			System.out.println((char) a);
		}
		
		//try{
		//	stream.write(buffer);
		//	} catch(IOException e){
		//		MainActivity.problemeDeConnexion();
		//	}
	}

}

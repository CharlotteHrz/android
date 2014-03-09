package communication;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;

import pact.ledopiano.MainActivity;

import android.bluetooth.BluetoothSocket;

public class ConnectedThread extends Thread {
	private final BluetoothSocket socket;
	private final OutputStream stream;
	//Peut-�tre mettre un attribut suppl�mentaire = buffer ?
	
	
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
		
		//Se signaler � la m�thode main
		MainActivity.signal(this);
		
	}
	
	
	//rajouter des m�thodes run() et cancel() !!!
	
	
	public void transmettre(byte[] buffer){
		//�ventuellement g�rer les indices de d�but et de fin d'�criture,
		//	ainsi que l'effacement des donn�es �crites
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

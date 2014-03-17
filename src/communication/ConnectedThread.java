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
	//Peut-�tre mettre un attribut suppl�mentaire = buffer ?
	
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
		System.out.println("Au d�but de CtedThread, le stream est " + stream);
		
		//Se signaler � la m�thode main
		MainActivity.signal(this);
		
	}
	
	
	public void run() {
		this.transmettre(new byte[] {1,2,1,1});
		
		System.out.println("Dans run(), le stream est " + stream);
	}
	
	
	public void transmettre(byte[] buffer){
		//�ventuellement g�rer les indices de d�but et de fin d'�criture,
		//	ainsi que l'effacement des donn�es �crites
		//  (si utilisation d'un attribut buffer
		
		System.out.println("Avant le bug, le stream est " + stream);
		try{
			stream.write(buffer);
			stream.flush();
			} catch(IOException e){
				Log.e("My activity", "Erreur lors de l'�criture dans la socket\n"+e.getLocalizedMessage());
			}
	}



	public void cancel(){
		//� compl�ter
	}

}
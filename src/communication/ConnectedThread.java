package communication;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;

import com.example.test.MainActivity;

import android.bluetooth.BluetoothSocket;

import smartphone.Pipeau;

public class ConnectedThread extends Thread {
	private final BluetoothSocket socket;
	private final OutputStream stream;
	//Peut-�tre mettre un attribut suppl�mentaire = buffer ?
	
	public ConnectedThread(BluetoothSocket bs) {
		socket = bs;
		OutputStream toolStream = null;
		try{
			OutputStream tool = socket.getOutputStream();
			} catch(IOException e){
				Pipeau.problemeDeConnexion();
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
		try{
			stream.write(buffer);
			} catch(IOException e){
				Pipeau.problemeDeConnexion();
			}
	}

}

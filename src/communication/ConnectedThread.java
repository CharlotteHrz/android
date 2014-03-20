package communication;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;

import pact.ledopiano.MainActivity;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class ConnectedThread extends Thread {
	private final BluetoothSocket socket;
	private final OutputStream stream;
	private final BufferedReader RXstream;
	//Peut-�tre mettre un attribut suppl�mentaire = buffer ?
	
	public ConnectedThread(BluetoothSocket bs) {
		socket = bs;
        System.out.println("La Socket est " + socket);
		OutputStream toolStream = null;
		InputStream RXstreamtmp=null;
		try{
			toolStream = socket.getOutputStream();
			RXstreamtmp= socket.getInputStream();
			} catch(IOException e){
				MainActivity.problemeDeConnexion();
			}
		stream = toolStream;
		RXstream=new BufferedReader(new InputStreamReader(RXstreamtmp));
		System.out.println("Au d�but de CtedThread, le stream est " + stream);
		
		//Se signaler � la m�thode main
		MainActivity.signal(this);
		
	}
	
	
	public void run() {
		this.transmettre(new byte[] {1,2,1,1});
		try {
			System.out.println(this.RXstream.readLine()+" Fin lecture\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("erreur lecture");
		}
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
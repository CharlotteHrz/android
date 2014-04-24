package communication;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import pact.ledopiano.MainActivity;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class ConnectedThread extends Thread {
	private final BluetoothSocket socket;
	private static OutputStream stream;
	private final BufferedReader InputStream;
	//Peut-�tre mettre un attribut suppl�mentaire = buffer ?
	
	public ConnectedThread(BluetoothSocket bs) {
		socket = bs;
        System.out.println("La Socket est " + socket);
		OutputStream toolStream = null;
		InputStream RXstreamtmp = null;
		try{
			toolStream = socket.getOutputStream();
			RXstreamtmp= socket.getInputStream();
			} catch(IOException e){
				MainActivity.problemeDeConnexion();
			}
		stream = toolStream;
		InputStream = new BufferedReader(new InputStreamReader(RXstreamtmp));
		System.out.println("Au d�but de CtedThread, le stream est " + stream);
		
		//Se signaler � la m�thode main
		MainActivity.signal(this);
		
	}
	
	
	public void run() {
		String ACK = null;
		this.transmettre(new byte[] {1,2,3,4});
//� enlever :
		System.out.println("envoi de donn�es");
		try {
			ACK = InputStream.readLine();
			System.out.println(ACK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("erreur lecture");
			this.cancel();
			this.run();
		}
		
		//si ce premier message est bien re�u, cocher le bouton "connection"
		if (ACK=="1234"){
			System.out.println("bonne forme de ACK");
			MainActivity.etatBluetooth(true);
			}else{
				this.cancel();
				this.run();
		}
		
		//attente en boucle de message de la part de l'arduino
/*		while (true) {
            try {
                bytes = mmInStream.read(buffer);
//envoyer qqc � la classe Lecture

            } catch (IOException e) {
                break;
            }
        }
*/	}
	
	
	public void transmettre(byte[] buffer){
		//�ventuellement g�rer les indices de d�but et de fin d'�criture,
		//	ainsi que l'effacement des donn�es �crites
		//  (si utilisation d'un attribut buffer
		
		try{
			stream.write(buffer);
			stream.flush();
			} catch(IOException e){
				this.cancel();
				Log.e("My activity", "Erreur lors de l'�criture dans la socket\n"+e.getLocalizedMessage());
			}
	}



	public void cancel(){
		try {
			this.wait(200);
		} catch (InterruptedException e) {
			MainActivity.problemeDeConnexion();
		}
	}

}
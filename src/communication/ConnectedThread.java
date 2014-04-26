package communication;

import harmonie.Commande;

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
	//Peut-être mettre un attribut supplémentaire = buffer ?
	
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
		System.out.println("Au début de CtedThread, le stream est " + stream);
		
		//Se signaler à la méthode main
		MainActivity.signal(this);
		
	}
	
	
	public void run() {
		this.transmettre(new byte[] {1,2,1,1});
		try {
			System.out.println("essai de transmission");
			System.out.println(this.RXstream.readLine()+" Fin lecture\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("erreur lecture");
		}
	}

	public void lecture() {
		byte[] commandePause = {0};
		transmettre(commandePause);
	}
	
	public void pause() {
		byte[] commandePause = {10};
		transmettre(commandePause);
	}
	
	public void stopp() {
		byte[] commandePause = {50};
		transmettre(commandePause);
	}

	public void envoyerCommande(Commande commande) {
		byte[] bytesAEnvoyer = new byte[93]; // 6 : 1 pour l'ID commande, 4 pour le temps, 88 pour la couleur de chaque note
		bytesAEnvoyer[0] = 126;
		
		// Conversion d'un int en 4 bytes		
		int time = commande.getDebut();
		bytesAEnvoyer[4] = (byte) (time % 256);
		time /= 256;
		bytesAEnvoyer[3] = (byte) (time % 256);
		time /= 256;
		bytesAEnvoyer[2] = (byte) (time % 256);
		time /= 256;
		bytesAEnvoyer[1] = (byte) time;
		
		// Copie du tableau de couleurs
		char[] couleurs = commande.getCouleurs();
		for (int i = 0 ; i < 88 ; i++) {
			bytesAEnvoyer[i+5] = (byte) couleurs[i];
		}
		
		transmettre(bytesAEnvoyer);
	}

	public void transmettre(byte[] buffer){
		//ï¿½ventuellement gï¿½rer les indices de dï¿½but et de fin d'ï¿½criture,
		//	ainsi que l'effacement des donnï¿½es ï¿½crites
		//  (si utilisation d'un attribut buffer

		try{
			stream.write(buffer);
			stream.flush();
		} catch(IOException e){
			this.cancel();
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
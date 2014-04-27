package communication;

import harmonie.Commande;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.channels.ClosedByInterruptException;

import pact.ledopiano.MainActivity;

import android.bluetooth.BluetoothSocket;

public class ConnectedThread extends Thread {
	private final BluetoothSocket socket;
	private final OutputStream stream;
	private final BufferedReader RXstream;
	
	public ConnectedThread(BluetoothSocket bs) {
		socket = bs;
        System.out.println("arrivée dans ConnectedThread");
		OutputStream toolStream = null;
		InputStream RXstreamtmp=null;
		try{
			toolStream = socket.getOutputStream();
			RXstreamtmp= socket.getInputStream();
			} catch(IOException e){
				MainActivity.problemeDeConnexion();
			}
		stream = toolStream;
		RXstream = new BufferedReader(new InputStreamReader(RXstreamtmp));
		//Se signaler à la méthode main
		MainActivity.signal(this);
	}
	
	
	public void run() {
		while(true){
			try {
				System.out.println(this.RXstream.readLine()+" Fin lecture\n");
			} catch (ClosedByInterruptException ex){
				Thread.currentThread().interrupt();
			} catch (IOException e) {
				System.out.println("erreur lecture");
			}
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
package lecture;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.Scanner;

// classe à exectuer pour tester les fonctions d'enregistrement et de lecture des fichiers wav

public class TestLectureEnregistrement {

	public static void main(String[] args) throws Exception {


		System.out.println("Que faire ? (tapez \"play\" pour jouer un morceau, \"record\" pour enregister un son, \"fourier\" pour faire la transformée de fourier, \"synth\" pour synthétiser un son et \"fin\" pour arrêter le programme)" );		
		Scanner sc1 = new Scanner(System.in);
		String choix = sc1.nextLine();

		while(choix.equalsIgnoreCase("fin") ==false){

			if (choix.equalsIgnoreCase("play") == true){

				
				Scanner scan = new Scanner(System.in);
				System.out.println("Voulez vous choisir un fichier WAV ou un fichier MP3 ?");
				String choixFormat = scan.nextLine();
				
				FichierAudioAndroid audio = new FichierAudioAndroid();
				

		//		audio.lire();
			}

			/* else if (choix.equalsIgnoreCase("record") == true){ 
				FichierAudioAndroid audio = new FichierAudioAndroid();
				audio.record();
			}
			*/

			else if (choix.equalsIgnoreCase("fourier") == true){
				//Choix Format
				Scanner scan = new Scanner(System.in);
				System.out.println("Voulez vous choisir un fichier WAV ou un fichier MP3 ?");
				String choixFormat = scan.nextLine();
				
				FichierAudioAndroid audio = new FichierAudioAndroid();
				audio.transformeeDeFourier();
			}

			else if (choix.equalsIgnoreCase("synth") == true){
				//Choix Fichier

				FichierAudioAndroid audio = new FichierAudioAndroid();
				//audio.audioSynth();
			}

			else if (choix.equalsIgnoreCase("mp3") == true){
				Scanner scan = new Scanner(System.in);
				System.out.println("Voulez vous choisir un fichier WAV ou un fichier MP3 ?");
				String choixFormat = scan.nextLine();

				FichierAudioAndroid fich = new FichierAudioAndroid();

				//fich.lire();



			}


			System.out.println("\n\nQue faire ? (tapez \"play\" pour jouer un morceau, \"record\" pour enregister un son, \"fourier\" pour faire la transformée de fourier, \"synth\" pour synthétiser un son et \"fin\" pour arrêter le programme)" );		
			choix = sc1.nextLine();	

		}

	}

}

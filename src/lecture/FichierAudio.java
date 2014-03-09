package lecture;

import Analyse.Chroma;
import Analyse.ChromasTheoriques;
import Analyse.TableauNotes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import org.apache.commons.math3.analysis.function.Log;
import org.apache.commons.math3.analysis.function.Log10;
import org.apache.commons.math3.analysis.function.Log1p;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.math.plot.*;;



public class FichierAudio {

	private File son;
	private AudioFileFormat audioFileFormat;
	private AudioFormat format;
	private Complex[]  transfoFourDiscrete;
	private AudioInputStream audioInputStream;
	private SourceDataLine sdc;
	private int total;
	private int numBytesRead;
	private int numBytesToRead;
	private byte[] myData;
	private int freqEchantillon;
	private WavFile wavFile;
	private AudioFormat baseFormat;
	int test = 0;

	// classe qui contient les informations d'un fichier audio sous forme de tableau de points.


	//CONSTRUCTEURS :

	//constructeur 
	
	public FichierAudio(String formatFichier) throws UnsupportedAudioFileException, IOException{

		if ( formatFichier.equalsIgnoreCase("wav") == true) //Ouvrir un son WAV
		{
			Scanner scan  = new Scanner(System.in);
			System.out.println("Rentrez le nom du fichier WAV : ");
			String nomFichier = scan.nextLine();
			son = new File("data/fichiers wav/"+nomFichier+".wav");

			//Ouverture du fichier + obtenir format

			audioFileFormat = AudioSystem.getAudioFileFormat(son);
			format = audioFileFormat.getFormat();
			audioInputStream = AudioSystem.getAudioInputStream(son);

			//Ouvrir la dataline
			DataLine.Info dataLine = new DataLine.Info(SourceDataLine.class, format);


			//Ouvrir la source data line
			try {
				sdc = AudioSystem.getSourceDataLine(format);
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				sdc.open(format,20000);
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else if ( formatFichier.equalsIgnoreCase("mp3") == true)
		{
			Scanner scan1  = new Scanner(System.in);
			System.out.println("Rentrez le nom du fichier MP3 : ");
			String nomFichier = scan1.nextLine();
			son = new File("data/fichiers_mp3/"+nomFichier+".mp3");

			AudioInputStream in= AudioSystem.getAudioInputStream(son);
			baseFormat = in.getFormat();
			AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
					baseFormat.getSampleRate(),
					16,
					baseFormat.getChannels(),
					baseFormat.getChannels() * 2,
					baseFormat.getSampleRate(),
					false);
			audioInputStream = AudioSystem.getAudioInputStream(decodedFormat, in);

			// Play now. 			  
			SourceDataLine res = null;
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);
			try {
				res = (SourceDataLine) AudioSystem.getLine(info);
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				res.open(decodedFormat);
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			audioFileFormat = null;
			sdc = res; 

		}

		else
		{
		}

	}

	//constructeur pour faire enregistrement ou synthese (Cf méthodes associées)
	public FichierAudio() throws LineUnavailableException, IOException, InterruptedException, UnsupportedAudioFileException
	{

	}


	//METHODES :


	public void lire() throws IOException, InterruptedException, LineUnavailableException, UnsupportedAudioFileException
	{

		total = 0;
		numBytesToRead = sdc.available();
		numBytesRead =0;
		myData = new byte[numBytesToRead];

		//Lecture, frame by frame
		sdc.start();		
		while (numBytesRead != -1){
			numBytesRead = audioInputStream.read(myData, 0, numBytesToRead);
			if (numBytesRead == -1) break;
			total += numBytesRead;
			sdc.write(myData, 0,numBytesRead);

		}

		Thread.sleep(1000);

		//Fermeture ligne		
		sdc.flush();
		sdc.stop();
		sdc.close();
	}

	public ArrayList<Chroma> transformeeDeFourier() throws InvalidMidiDataException, Exception 


	{
		TableauNotes tabNote = new TableauNotes();
		transfoFourDiscrete = new Complex[10000];
		ArrayList<Chroma> chromaMorceau= new ArrayList<Chroma>();
		FastFourierTransformer fourier = new FastFourierTransformer(DftNormalization.STANDARD); // différence entre Unitary et Standard?

		if (audioFileFormat != null) //Gere le format WAVE
		{
			// Open the wav file specified as the first argument
			wavFile = WavFile.openWavFile(son);
			freqEchantillon = (int) wavFile.getSampleRate();
		}

		else // Gere le format MP3 (<=> audioFileFormat nul !)
		{
			freqEchantillon = (int) baseFormat.getSampleRate();
		}


		// calcul de la puissance de 2 suivante (parce que la transfo de fourier veut une puissance de 2)

		Log10 log = new Log10();
		int n = (int) (log.value((double)2*freqEchantillon)*log.value(10.0)/log.value(2.0)) + 1;

		// Create a buffer 
		double[] buffer = new double[freqEchantillon];
		double[] element = new double[(int) Math.pow(2, n)];

		if (audioFileFormat != null) //Gere le format WAVE
		{

			int framesRead;

			do
			{				
				int numChannels = wavFile.getNumChannels();
				// Read frames into buffer
				framesRead = wavFile.readFrames(buffer,freqEchantillon/numChannels);
				for (int i = 0; i<freqEchantillon;i++)
				{			
					element[i]=buffer[(int) (i/2.972)];
				}
				transfoFourDiscrete = fourier.transform(element, TransformType.FORWARD);

				chromas(transfoFourDiscrete,tabNote);

			}
			while (framesRead != 0);


			
			//CREATION DU TABLEAU DE CHROMAS EXP

			
			chromaMorceau=tabNote.TableChroma();
			// Close the wavFile
			wavFile.close();
		}

		else
		{
			total = 0;
			numBytesToRead = 44100;
			numBytesRead =0;
			myData = new byte[numBytesToRead];

			//Lecture, frame by frame
			sdc.start();		
			while (numBytesRead != -1){
				
				for (int j = 0 ; j < 8 ; j++)
				{
				numBytesRead = audioInputStream.read(myData, 0, numBytesToRead);
				if (numBytesRead == -1) break;
				total += numBytesRead;
				double[] myDataDouble = new double[numBytesToRead];
				
				myDataDouble = toDoubleArray(myData);
				
				for (int i = j*numBytesRead; i<numBytesRead * (j+1);i++)
				{	
					//if ((int) (i/2.972) < myDataDouble.length)
					element[i]= myDataDouble[(int) (i/2.972)];
				}
				}
				transfoFourDiscrete = fourier.transform(element, TransformType.FORWARD);
<<<<<<< HEAD
				
				System.out.println(total);
				test++;
				/*System.out.println("\n\n\n" + test + "\n\n\n");
				for(int j = 0 ; j < element.length ; j++)
				{
					if (element[j] != 0)
						System.out.println(element[j]);
				}
				
				if (test == 20)
					trace(element);
					*/
				
				//chromas(transfoFourDiscrete);
				
				
				}
=======

				chromas(transfoFourDiscrete, tabNote);
			}
>>>>>>> refs/remotes/origin/master

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//Fermeture ligne		
			sdc.flush();
			sdc.stop();
			sdc.close();

		}
		return chromaMorceau;
	}

	public ArrayList<double[]> detectionPics(Complex[] trans)
	{

		ArrayList<double[]> pics = new ArrayList<double[]>();

		//ordonnee

		double[] points = new double [10000];

		ArrayList<Integer> seuil = new ArrayList<Integer>();
		ArrayList<Integer> bornessup = new ArrayList<Integer>();
		ArrayList<Integer> bornesinf = new ArrayList<Integer>();
		double max = 0;

		for (int i =0; i< 10000; i++)
		{
			points[i] = trans[i].abs();

			if(points[i] > max) {max = points[i];} 
		}

		//On trouve les fréquences correspondant à des pics sur le spectre de Fourier ==> freqcentrales contient les fréquences centrales des pics et 

		for (int i =0 ; i <10000 ; i++)
		{
			if (points[i]> max/2) { seuil.add(i);}
		}

		if(seuil.size() != 0){
			bornesinf.add(seuil.get(0));

			for (int i = 0 ; i< seuil.size()-1;i++)
			{
				if (seuil.get(i+1) == seuil.get(i)+1){}
				else {bornessup.add(seuil.get(i));}
			}

			bornessup.add(seuil.get(seuil.size()-1));

			for (int i = 1 ; i< seuil.size();i++)
			{
				if (seuil.get(i-1) == seuil.get(i)-1){}
				else {bornesinf.add(seuil.get(i));}
			}



			for (int i = 0 ; i < bornessup.size() ; i++)
			{
				//freqcentrales.add((bornessup.get(i)+bornesinf.get(i))/2);
				//System.out.println("Freq Centrale : " + freqcentrales.get(i));
				double maxloc = points[i];
				int freqcent = 0;

				for(int j = bornesinf.get(i) ; j <= bornessup.get(i) ; j++)
				{
					if (points[j] > maxloc){maxloc = points[j]; freqcent = j;}										
				}

				double[] ajout = new double [2];
				ajout[0] = (double) freqcent;
				ajout[1] = maxloc;

				pics.add(ajout);

				//System.out.println("Freq Centrale : " + pics.get(i)[0] + "// Poids : " + pics.get(i)[1]);
			}


					//System.out.println("Freq Centrale : " + pics.get(i)[0] + "// Poids : " + pics.get(i)[1]);
<<<<<<< HEAD
				}
=======
				
>>>>>>> refs/remotes/origin/master

		}

		return pics;

	}

	public void chromas(Complex[] transFour, TableauNotes tabNote)
	{
		tabNote.TabParEchantillon(detectionPics(transFour));
	}

	public void tracerLesTransformeesDeFourier(){


		Plot2DPanel plot = new Plot2DPanel();

		// abscisse
		double[] abscisse = new double[10000]; // bande de freq audible
		for(int i=0; i<10000; i++) { abscisse[i] = i;}

		//ordonnee

		double[] points = new double [10000];

		ArrayList<Integer> seuil = new ArrayList<Integer>();
		ArrayList<Integer> bornessup = new ArrayList<Integer>();
		ArrayList<Integer> bornesinf = new ArrayList<Integer>();
		double max = 0;

		for (int i =0; i< 10000; i++)
		{
			points[i] = this.transfoFourDiscrete[i].abs();
		}



		//trace
		plot.addLinePlot("tracé", abscisse, points);
		JFrame frame = new JFrame("fenetre de tracé");
		frame.setSize(600, 600);
		frame.setContentPane(plot);
		frame.setVisible(true);
	}


	public void trace(){

		double[] points = null;
		Plot2DPanel plot = new Plot2DPanel();

		double[] abscisse = new double[points.length];
		for(int i=0; i< points.length; i++) { abscisse[i] = i;}

		plot.addLinePlot("my plot", abscisse, points);
		JFrame frame = new JFrame("a plot panel");
		frame.setSize(600, 600);
		frame.setContentPane(plot);
		frame.setVisible(true);
	}

	public static void trace(double[] pointsATracer)
	{
		Plot2DPanel plot = new Plot2DPanel();

		double[] abscisse = new double[pointsATracer.length];
		for(int i=0; i< pointsATracer.length; i++) { abscisse[i] = i;}

		plot.addLinePlot("my plot", abscisse, pointsATracer);
		JFrame frame = new JFrame("a plot panel");
		frame.setSize(600, 600);
		frame.setContentPane(plot);
		frame.setVisible(true);
	}

	
	public void audioSynth()
	{

		try
		{
			int sampleRate = 44100;		// Samples per second
			int duration;		// Seconds



			Scanner sc1 = new Scanner(System.in);
			System.out.println("Rentrez le nom du fichier à sauvegarder : ");
			String nom = sc1.nextLine();

			Scanner sc2 = new Scanner(System.in);
			System.out.println("Rentrez le durée du son (en secondes) : ");
			duration = sc1.nextInt();

			// Calculate the number of frames required for specified duration
			long numFrames = (long)(duration * sampleRate);

			// Create a wav file with the name specified as the first argument
			WavFile wavFile = WavFile.newWavFile(new File("data/fichiers wav/"+nom+".wav"), 2, numFrames, 16, sampleRate);

			// Create a buffer of 100 frames
			double[][] buffer = new double[2][100];

			// Initialise a local frame counter
			long frameCounter = 0;

			Scanner sc  = new Scanner(System.in);
			System.out.println("Rentrez la fréquence de la sinusoïde (en Hz) : ");
			int freq =sc.nextInt();
			// Loop until all frames written
			while (frameCounter < numFrames)
			{
				// Determine how many frames to write, up to a maximum of the buffer size
				long remaining = wavFile.getFramesRemaining();
				int toWrite = (remaining > 100) ? 100 : (int) remaining;

				// Fill the buffer, one tone per channel
				for (int s=0 ; s<toWrite ; s++, frameCounter++)
				{
					buffer[0][s] = Math.sin( 2.0 * Math.PI * freq * frameCounter / sampleRate);
					buffer[1][s] = Math.sin( 2.0 * Math.PI * freq * frameCounter / sampleRate);
				}

				// Write the buffer
				wavFile.writeFrames(buffer, toWrite);
			}

			// Close the wavFile
			wavFile.close();
		}
		catch (Exception e)
		{
			System.err.println(e);
		}
	}




	public void record() throws LineUnavailableException, InterruptedException, IOException{
		final AudioFormat formatRec = new AudioFormat((float) 44100.0,8,1,false,true);
		TargetDataLine tdc;
		AudioInputStream audioInputStream;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int total;
		int totalToWrite;
		int numBytesToRead;
		int numBytesRead;
		byte[] myData;
		byte[] audio;
		InputStream input;
		String nomFichier;

		//Format
		System.out.println("Renseignements fichier : " + "\n          " +formatRec.toString());		

		//Ouvrir la dataline
		DataLine.Info dataLine = new DataLine.Info(TargetDataLine.class, formatRec);
		System.out.println("Renseignements ligne : " + "\n          " +dataLine.toString());
		System.out.println("\nFormat supporté : " + dataLine.isFormatSupported(formatRec));

		//Ouvrir la target data line
		Line.Info info = new Line.Info(TargetDataLine.class);
		tdc = (TargetDataLine) AudioSystem.getLine(info);
		tdc.open(formatRec, 10000);
		System.out.println("Ligne Ouverte : " + tdc.isOpen());
		//Initialisations variables
		total = 0;
		totalToWrite = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("\n\nRentrez la durée (en secondes) de l'enregistrement : ");
		totalToWrite = (sc.nextInt()*(int)formatRec.getSampleRate());
		numBytesToRead = tdc.getBufferSize();
		System.out.println("Taille du buffer : " + tdc.getBufferSize());
		numBytesRead = 0;

		myData = new byte[numBytesToRead];

		Thread.sleep(1000);

		System.out.println("Début enregistrement !");

		//Acquisition des données du microphone
		tdc.start();		
		while (total < totalToWrite){
			numBytesRead = tdc.read(myData, 0,myData.length);
			if (numBytesRead == -1) break;
			total += numBytesRead;
			out.write(myData, 0, numBytesRead);
		}

		System.out.println("Enregistrement Terminé");

		Thread.sleep(500);

		//Création du fichier dans le dossier data
		byte[] audio1 = out.toByteArray();
		System.out.println("taille audio :" + audio1.length);
		input = new ByteArrayInputStream(audio1);
		audioInputStream = new AudioInputStream(input, formatRec, audio1.length / formatRec.getFrameSize() );
		System.out.println("Rentrez le nom du fichier à sauvegarder : ");
		Scanner sc2 = new Scanner(System.in);
		nomFichier = sc2.nextLine();
		AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File("data/fichiers wav/"+nomFichier+".wav"));

		//Fermeture ligne		
		tdc.flush();

		tdc.stop();

		tdc.close();


	}
	
	public static double[] toDoubleArray(byte[] byteArray){
	    int times = 8;
	    double[] doubles = new double[byteArray.length/times];
	    for(int i=0;i<doubles.length;i++){
	        //doubles[i] = ByteBuffer.wrap(byteArray, i * times, times).getDouble();
	    	try {
				doubles[i] = toDouble(byteArray,i*times);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
	    }
	    return doubles;
	}
	
	public static double toDouble(byte[] bytes, int index) throws Exception
    {
    	if(bytes.length != 8) 
    		throw new Exception("The length of the byte array must be at least 8 bytes long.");
            return Double.longBitsToDouble(
                    toInt64(bytes, index));
    }
	public static long toInt64(byte[] bytes, int index) throws Exception
    {
    	if(bytes.length != 8) 
    		throw new Exception("The length of the byte array must be at least 8 bytes long.");
    	return (long)(
        	(long)(0xff & bytes[index]) << 56  |
        	(long)(0xff & bytes[index + 1]) << 48  |
        	(long)(0xff & bytes[index + 2]) << 40  |
        	(long)(0xff & bytes[index + 3]) << 32  |
        	(long)(0xff & bytes[index + 4]) << 24  |
        	(long)(0xff & bytes[index + 5]) << 16  |
        	(long)(0xff & bytes[index + 6]) << 8   |
        	(long)(0xff & bytes[index + 7]) << 0
        	);
    }





}




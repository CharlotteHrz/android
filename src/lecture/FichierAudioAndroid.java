package lecture;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


import org.apache.commons.math3.analysis.function.Log;
import org.apache.commons.math3.analysis.function.Log10;
import org.apache.commons.math3.analysis.function.Log1p;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.math.plot.*;

import Analyse.Chroma;
import Analyse.ChromasTheoriques;
import Analyse.TableauNotes;

public class FichierAudioAndroid {
	
	private File son;
	private Complex[]  transfoFourDiscrete;
	private int freqEchantillon;
	private WavFile wavFile;
	
	public FichierAudioAndroid() throws IOException
	{
		
		son = new File("nom Fichier");
	}
	
	public ArrayList<Chroma> transformeeDeFourier() throws Exception 


	{
		TableauNotes tabNote = new TableauNotes();
		transfoFourDiscrete = new Complex[10000];
		ArrayList<Chroma> chromaMorceau= new ArrayList<Chroma>();
		FastFourierTransformer fourier = new FastFourierTransformer(DftNormalization.STANDARD); // différence entre Unitary et Standard?

		
		// Open the wav file specified as the first argument
		wavFile = WavFile.openWavFile(son);
		freqEchantillon = (int) wavFile.getSampleRate();
		

		
		// calcul de la puissance de 2 suivante (parce que la transfo de fourier veut une puissance de 2)

		Log10 log = new Log10();
		int n = (int) (log.value((double)2*freqEchantillon)*log.value(10.0)/log.value(2.0)) + 1;

		// Create a buffer 
		double[] buffer = new double[freqEchantillon];
		double[] element = new double[(int) Math.pow(2, n)];

		chromaMorceau = null;
	

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

			ChromasTheoriques theorie = new ChromasTheoriques();

			for (int i= 0; i<chromaMorceau.size();i++)
			{
				//chromaMorceau.get(i).apercu();
				
				System.out.println(chromaMorceau.get(i).closerChromaTheoric()[0] +" ou "+chromaMorceau.get(i).closerChromaTheoric()[1] );
			}
			
		

			chromaMorceau=tabNote.TableChroma();
			// Close the wavFile
			wavFile.close();
		

		
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

				}

		
		return pics;
		
	
	}
	
	public void chromas(Complex[] transFour, TableauNotes tabNote)
	{
		tabNote.TabParEchantillon(detectionPics(transFour));
	}

	
	

		
}


package Analyse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TableauNotes {


	/* MODELE A 3 HARMONIQUES

	C'est un tableau qui contient des vecteurs � 12 chiffres.
	Chaque vecteur repr�sente les notes jou�es � l'instant t (le premier �tant le Do)
	ex Do [1,0,0,0,0,0,0,0,0,0,0,0]
	Re# [0,1,0,0,0,0,0,0,0,0,0,0]
	On change de vecteur � chaque battue = tick (definie de mani�re obscure par Midi)

	On les contruit � partir d'un fichier midi et du numero du Track (1 s'il n'y a qu'un instrument)
	Pour les visualiser sur la console on utilise la m�thode apercu

	 */



	private ArrayList<double[]> tableau;
	private double[] calculBasse;


	public TableauNotes() 
	{
		this.tableau = new ArrayList<double[]>(); 
		calculBasse = new double[12];
	}

	// methodes



	public void TabParEchantillon(ArrayList<double[]> echantillon)
	{

		ArrayList<Double> freqNote=new ArrayList<Double>(12);
		double[] amplitude= new double[12];
		double[] amplitudeb= new double[12];
		double ecart=1.;

		freqNote.add( (Double) 16.3);
		freqNote.add((Double) 17.3);
		freqNote.add( (Double) 18.3);
		freqNote.add((Double) 19.4);
		freqNote.add((Double) 20.5);
		freqNote.add((Double) 21.5);
		freqNote.add((Double) 23.1);
		freqNote.add((Double) 24.5);
		freqNote.add((Double) 26.);
		freqNote.add((Double) 27.5);
		freqNote.add((Double) 29.1);
		freqNote.add((Double) 30.5);

		int nbrFreq= echantillon.size();
		for(int note=0; note<12;note++) // itération sur les notes
		{
			for(int octave=0; octave<9; octave++) // itération sur les octaves
			{
				for(int freq=0;freq<nbrFreq; freq++)
				{

					ecart=freqNote.get(note)*0.05;

					if((echantillon.get(freq)[0])<(freqNote.get(note)*(Math.pow(2.,(double)octave))+ecart) && ((freqNote.get(note)*(Math.pow(2,(double) octave))-ecart)<(echantillon.get(freq)[0]) ))
					{
						amplitude[note]=amplitude[note] + echantillon.get(freq)[1];
						if(octave<=3)
						{
							amplitudeb[note]=amplitudeb[note] + echantillon.get(freq)[1];
						}
					}
				}
			}

		}
		calculBasse=amplitudeb;
		tableau.add(amplitude);
	}

	public boolean traiterNote(byte[] b, int indice) //on traite le tableau � 3 bytes et si il s'agit d'ajouter ou d'enlever une note on les fait
	{ 
		if (Math.abs(b[0]) == 112) //ajout
		{

			int valeurNote = Math.abs(b[1]%12);
			int valeurQuinte = Math.abs((b[1]+7)%12);
			tableau.get(indice)[valeurNote]= tableau.get(indice)[valeurNote] + 3*b[2];
			tableau.get(indice)[valeurQuinte]= tableau.get(indice)[valeurQuinte] + b[2];
			return true;
		} 

		if (Math.abs(b[0]) == 128) //enlever
		{
			int valeurNote = Math.abs(b[1]%12);

			int valeurQuinte = Math.abs((b[1]+7)%12);
			tableau.get(indice)[valeurNote]= tableau.get(indice)[valeurNote] - 3*b[2];
			tableau.get(indice)[valeurQuinte]= tableau.get(indice)[valeurQuinte] - b[2];

			return true;

		}

		return false;

	}

	public ArrayList<Chroma> TableChroma() 
	{

		ArrayList<Chroma> tableChroma = new ArrayList<Chroma> ();


		for (int i = 0; i<this.tableau.size(); i++)

		{	

			Chroma chroma= new Chroma();

			double normeTableau=0;

			// Calcul de la norme
			for(int note=0;note<12; note++)
			{
				normeTableau=normeTableau+Math.pow(this.tableau.get(i)[note],2);
			}

			normeTableau=Math.sqrt(normeTableau);


			if (normeTableau!=0)
			{
				for(int note=0;note<12;note++)
				{
					chroma.getTable()[note]=this.tableau.get(i)[note]/normeTableau;
				}	

			}
			tableChroma.add(chroma);
		}

		return(tableChroma);
	}

	public int donneBasseEch()
	{
		int indiceMax=0;
		double max= calculBasse[0];
		for(int i=0;i<12;i++)
		{
			if(calculBasse[i]>max)
			{
				max=calculBasse[i];
				indiceMax=i;
			}
		}
		if(max<=0)
		{
			return -1;
		}
		else
		{
			return indiceMax;
		}
	}
	public void apercu()
	{
		for(int i =0; i<this.tableau.size(); i++) {



			System.out.println("[ "+ tableau.get(i)[0]+", "+ tableau.get(i)[1]+", "+tableau.get(i)[2]+", "+tableau.get(i)[3]+", "+tableau.get(i)[4]+", "+tableau.get(i)[5]+", "+tableau.get(i)[6]+", "+tableau.get(i)[7]+", "+tableau.get(i)[8]+", "+tableau.get(i)[9]+", "+tableau.get(i)[10]+", "+tableau.get(i)[11]+" "+ "]");


		}
	}

	public ArrayList<double[]> getTableau()
	{
		return this.tableau;
	}
	
	public double[] getBasse()
	{
		return calculBasse;
	}		
}

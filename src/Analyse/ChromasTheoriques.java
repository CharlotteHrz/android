package Analyse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ChromasTheoriques {

	static  ArrayList<Chroma> chromasTheoriques;

	public ChromasTheoriques() throws IOException 
	{		

		FileReader aLire = new FileReader("chromastheoriques.txt");
		
		BufferedReader reader = new BufferedReader(aLire);

		//lecture du fichier contenant les chromas

		double[][] vecteur = new double[24][12];

		for (int i1=0; i1<24; i1++)
		{
			for (int k = 0; k<12; k++)
			{
				String lignes = reader.readLine();
				vecteur[i1][k] = Double.parseDouble(lignes);

			}
		}

		chromasTheoriques = new ArrayList<Chroma>();

		for(int i=0; i<12; i++) //chromas theoriques majeurs
		{


			Note note = new Note(i);

			Chroma chroma = new Chroma(vecteur[i] , note.getNomNote()+"M");

			chromasTheoriques.add(chroma);


		}
		for(int i=0; i<12; i++) //chromas theoriques mineurs
		{


			Note note = new Note(i);

			Chroma chroma = new Chroma(vecteur[i+12] , note.getNomNote()+"m");

			chromasTheoriques.add(chroma);		

		}




	}
// Permet d'accéder un chroma théorique à partir de son nom
	public static Chroma getChromaFromNom(String nom)
	{
		int i=0;
		while(nom.equals(ChromasTheoriques.chromasTheoriques.get(i).getNom())==false)
		{
			i++;
		}
		return ChromasTheoriques.chromasTheoriques.get(i);
	}
	
	public void apercu()
	{
		for(int i =0; i<this.chromasTheoriques.size(); i++) {


			System.out.println(chromasTheoriques.get(i).getNom()+"[ "+chromasTheoriques.get(i).getTable()[0]+","+chromasTheoriques.get(i).getTable()[1]+","+chromasTheoriques.get(i).getTable()[2]+","+chromasTheoriques.get(i).getTable()[3]+","+chromasTheoriques.get(i).getTable()[4]+","+chromasTheoriques.get(i).getTable()[5]+","+chromasTheoriques.get(i).getTable()[6]+","+chromasTheoriques.get(i).getTable()[7]+","+chromasTheoriques.get(i).getTable()[8]+","+chromasTheoriques.get(i).getTable()[9]+","+chromasTheoriques.get(i).getTable()[10]+","+chromasTheoriques.get(i).getTable()[11]+"]");
		}
	}
}

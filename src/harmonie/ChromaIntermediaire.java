package harmonie;

import java.util.ArrayList;
import Analyse.Chroma;
import Analyse.ChromasTheoriques;
import Analyse.Note;

public class ChromaIntermediaire 
{
	public static GrilleAccords AnalyseChroma(ArrayList<Chroma> chromaMorceau,ArrayList<Integer> listeBasses) throws Exception
	{
		ChromasTheoriques theorie = new ChromasTheoriques();
		ArrayList<String> chromaTheoMorceau=new ArrayList<String>();
		ArrayList<String[]> chromaInter=new ArrayList<String[]>();
		
		for(int i=0;i<chromaMorceau.size();i++)
		{
			chromaInter.add(chromaMorceau.get(i).closerChromaTheoric());
		}
		
		int depart=0;// Au cas où la musique ne commence pas dï¿½s le dï¿½but du fichier son

		for (int i= 0; i<chromaInter.size();i++)
		{
			//chromaMorceau.get(i).apercu();
			if(chromaInter.get(i)[0].equals("null")==false)
			{
				int[] choixValeur =Chroma.traitementBasse(chromaInter.get(i), listeBasses.get(i));
				chromaTheoMorceau.add(Chroma.chooseBest(chromaInter,i,choixValeur));
			}
			else
			{
				if(chromaTheoMorceau.size()!=0)
				{
					chromaTheoMorceau.add(chromaTheoMorceau.get(i-1));
				}
				else
				{
					depart++;
				}
			}
		}
		
		/*for(int i=0;i<chromaTheoMorceau.size();i++)
		{
			System.out.println(chromaTheoMorceau.get(i));
		}
		
		System.out.println("taille de ChromaTheoMorceau : "+chromaTheoMorceau.size());*/

		System.out.println(chromaTheoMorceau.size());
		Note note= new Note(chromaTheoMorceau.get(0));//juste pour respecter la tonalite

		int tonalite=note.getValeur();
		//System.out.println(""+tonalite);
		boolean majeur=(chromaTheoMorceau.get(0).indexOf('M')!=-1);

		GrilleAccords grille= new GrilleAccords(tonalite, majeur);
		int duree=500;// duree minimale d'un accord, selon la periode de calcul des transformï¿½e de Fourier
		int indice=0;
		//System.out.println("indice :"+indice);

		while(indice<chromaTheoMorceau.size())
		{
			int k=1;
			while(indice+k<chromaTheoMorceau.size() && chromaTheoMorceau.get(indice).equals(chromaTheoMorceau.get(indice+k)))
			{
				k++;
			}
			chromaTheoMorceau.set(indice,chromaTheoMorceau.get(indice).replace("M", ""));
			chromaTheoMorceau.set(indice,chromaTheoMorceau.get(indice).replace("m", "-"));

			int dureeAccord=k*duree;
			Accord accord= new Accord(chromaTheoMorceau.get(indice));
			accord.setDebut(indice*duree+depart*duree);
			accord.setFin(accord.getDebut()+dureeAccord);
			indice=indice+k;
			//System.out.println(accord.getFondamentale()+accord.getChiffrage()+" "+accord.getDebut()+" "+accord.getFin());
			grille.getAccords().add(accord);
		}
		return grille;
	}
}

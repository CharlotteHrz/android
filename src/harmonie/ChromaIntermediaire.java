package harmonie;
import java.util.ArrayList;
import javax.sound.midi.InvalidMidiDataException;
import Analyse.Chroma;
import Analyse.ChromasTheoriques;
import Analyse.Note;

public class ChromaIntermediaire 
{
	public static GrilleAccords AnalyseChroma(ArrayList<Chroma> chromaMorceau) throws InvalidMidiDataException, Exception
	{
		System.out.println("taille de ChromaMorceau : "+chromaMorceau.size());
		ChromasTheoriques theorie = new ChromasTheoriques();
		ArrayList<String> chromaTheoMorceau=new ArrayList<String>();
		ArrayList<String[]> chromaInter=new ArrayList<String[]>();
		
		for(int i=0;i<chromaMorceau.size();i++)
		{
			chromaInter.add(chromaMorceau.get(i).closerChromaTheoric());
		}System.out.println("taille de ChromaInter : "+chromaInter.size());
		
		int depart=0;// Au cas o� la musique ne commence pas d�s le d�but du fichier son

		for (int i= 0; i<chromaInter.size();i++)
		{
			//chromaMorceau.get(i).apercu();
			if(chromaInter.get(i)[0].equals("null")==false)
			{
				chromaTheoMorceau.add(Chroma.chooseBest(chromaInter, i));
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
			System.out.println(chromaMorceau.get(i).closerChromaTheoric()[0] +" ou "+chromaMorceau.get(i).closerChromaTheoric()[1]);
		}
		for(int i=0;i<chromaTheoMorceau.size();i++)
		{
			System.out.println(chromaTheoMorceau.get(i));
		}
		
		System.out.println("taille de ChromaTheoMorceau : "+chromaTheoMorceau.size());

		System.out.println(chromaTheoMorceau.size());
		Note note= new Note(chromaTheoMorceau.get(0));//juste pour r�cup�rer la tonalit�

		int tonalite=note.getValeur();
		//System.out.println(""+tonalite);
		boolean majeur=(chromaTheoMorceau.get(0).indexOf('M')!=-1);

		GrilleAccords grille= new GrilleAccords(tonalite, majeur);
		int duree=500;// dur�e minimale d'un accord, selon la p�riode de calcul des transform�e de Fourier
		int indice=0;System.out.println("indice :"+indice);

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
			System.out.println(accord.getFondamentale()+accord.getChiffrage()+" "+accord.getDebut()+" "+accord.getFin());
			grille.getAccords().add(accord);
		}
		return grille;
	}
}

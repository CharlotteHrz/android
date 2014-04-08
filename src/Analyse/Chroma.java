package Analyse;

import java.io.IOException;
import java.util.ArrayList;
import Analyse.TableauNotes;

public class Chroma {

	
	// il s'agit d'un vecteur symbolisant les notes jou�es � un instant t.
	// On y associe un nom qui represente le nom de l'accord joue s'il y en a.
	
	private double[] table;
	private String nom;


	public Chroma(double[] table,String nom) 
	{
		this.nom=nom;
		this.table=table;
	}

	public Chroma() 
	{
		this.nom="Chroma inconnu";

		this.table = new double[12];
	}



	public double[] getTable() {
		return table;
	}


	public void setTable(double[] table) {
		this.table = table;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}

	public double compare(Chroma otherChroma)
	{
		double ecart=0.;
		
		//on calcule l'ecart avec une norme euclidienne 
		
		for(int note=0;note<12;note++)
		{
			ecart=ecart+Math.pow(this.table[note]-otherChroma.getTable()[note],2.);
		}
		ecart=Math.sqrt(ecart);
		
		return ecart;
	}

	public void apercu()
	{
		System.out.println("["+this.table[0]+"," + this.table[1]+","+this.table[2]+","+this.table[3]+","+this.table[4]+","+this.table[5]+","+this.table[6]+","+this.table[7]+","+this.table[8]+","+this.table[9]+","+this.table[10]+","+this.table[11]+"]");
		System.out.println(this.nom);
	}

	public String[] closerChromaTheoric()
	{

		String[] retour = new String[2];

		int j = 0;
		while (j<12 && this.table[j]==0)
		{
			j++;
		}
		
		
		if (j==12) // il n'y a aucune note jou�e
		{	
			retour[0] ="null";
			retour[1] = "null";
			return(retour);
		}
		else //il y a une note jou�e ?
		{
			int i=0;
			while (i<12 && this.table[i]!=1)
			{ 
				i++; 
			}

			if (i ==12) //plusieurs notes
			{

				Double mini=Double.POSITIVE_INFINITY;

				int indexChroma=0;

				int theoSize=ChromasTheoriques.chromasTheoriques.size();
				
				ArrayList<Double> listEcart=new ArrayList<Double>();

				for(int theo=0;theo<theoSize;theo++)
				{
					listEcart.add(this.compare(ChromasTheoriques.chromasTheoriques.get(theo)));
				}

				for(int theo=0;theo<theoSize;theo++)
				{
					mini=Math.min(mini,listEcart.get(theo));
				}
				indexChroma=listEcart.indexOf(mini);

				mini = Double.POSITIVE_INFINITY;

				for(int theo=0;theo<indexChroma;theo++)
				{
					mini=Math.min(mini,listEcart.get(theo));
				}
				for(int theo=indexChroma+1;theo<theoSize;theo++)
				{
					mini=Math.min(mini,listEcart.get(theo));
				}

				int indexChroma2=listEcart.indexOf(mini);
				
				retour[0] = ChromasTheoriques.chromasTheoriques.get(indexChroma).getNom() ;
				retour[1] = ChromasTheoriques.chromasTheoriques.get(indexChroma2).getNom() ;
				return(retour);
			}

			else //une seule note est jouee
			{
				Note note = new Note(i);

				retour[0] =note.getNomNote()+"M";
				retour[1] = note.getNomNote()+"m";
				return(retour);
			}
		}
	}




	//m�thode qui sert pour choisir parmi les deux possibilit�s de chromas th�oriques, peut �ventuellement �tre mise ailleurs
	
	public static int[] traitementBasse(String[] choix,int basse)
	{
		Chroma choix1= ChromasTheoriques.getChromaFromNom(choix[2]);
		Chroma choix2= ChromasTheoriques.getChromaFromNom(choix[1]);
		int[] choixValeur=new int[2];

		if(choix1.getTable()[basse]>choix2.getTable()[basse])
		{
			choixValeur[0]=1;
			choixValeur[1]=0;
		}
		else if(choix1.getTable()[basse]<choix2.getTable()[basse])
		{
			choixValeur[0]=0;
			choixValeur[1]=1;
		}

		return choixValeur;

	}

	public static String chooseBest(ArrayList<String[]> chromaMorceau,int index,int[] choix)
	{
		int taille=chromaMorceau.size();
		int choix1=choix[0];
		int choix2=choix[1];

		String chroma=chromaMorceau.get(index)[0];
		if(index<taille-3)
		{
			for(int i=1;i<3;i++)
			{
				for(int k=0;k<2;k++)
				{
					if(chromaMorceau.get(index+i)[k].equals(chromaMorceau.get(index)[0]))
					{
						choix1++;
					}
					if(chromaMorceau.get(index+i)[k].equals(chromaMorceau.get(index)[1]))
					{
						choix2++;
					}
				}
			}
		}
		if(index>1)
		{
			for(int i=1;i<3;i++)
			{
				for(int k=0;k<2;k++)
				{
					if(chromaMorceau.get(index-i)[k].equals(chromaMorceau.get(index)[0]))
					{
						choix1++;
					}
					if(chromaMorceau.get(index-i)[k].equals(chromaMorceau.get(index)[1]))
					{
						choix2++;
					}
				}
			}
		}
		if(choix2>choix1){chroma=chromaMorceau.get(index)[1];}
		return chroma;
	}
}

package pact.ledopiano;

import harmonie.ChromaIntermediaire;
import harmonie.Commande;
import harmonie.CommandeAllumage;
import harmonie.GrilleAccords;

import java.util.ArrayList;

import Analyse.Chroma;
import lecture.FichierAudio;
/*
public class Programme {



	public ArrayList<Commande> general () throws Exception {

		/*besoin de créer un objet global choix avec comme attribut 
		 * le path par exemple, créer le getter qui permet d'avoir le path 
		 * comme attribut methode)	
		 */
		
		/*String path = new String getPath;
		FichierAudio fichier = new FichierAudio(path);
		//name est le path du fichier pické
		ArrayList<Chroma> tableChroma = fichier.transformeeDeFourier();
		//ArrayList<Integer> listeBasse = fichier.getListeBasses();
		GrilleAccords grilleAccords = ChromaIntermediaire.AnalyseChroma(tableChroma, listeBasse);
		
		
		CommandeAllumage commandeAllumage = new CommandeAllumage (grilleAccords);
		commandeAllumage.calculerCommandes();
		ArrayList<Commande> commandes = commandeAllumage.getCommandes() ;
		return commandes;	
		

		/*envoie de commandes en bluetooth 
		 * lancement de la lecture du morceau par MediaPlayer
		 * (+ sémaphore ?)
		 * + synchronisation pause/play
		 */
	//} 
	
 //} 

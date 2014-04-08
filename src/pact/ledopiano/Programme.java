package pact.ledopiano;

import harmonie.ChromaIntermediaire;
import harmonie.Commande;
import harmonie.CommandeAllumage;
import harmonie.GrilleAccords;

import java.util.ArrayList;

import Analyse.Chroma;
import lecture.FichierAudio;

public class Programme {



public ArrayList<Commande> general (String name) throws Exception {
	
FichierAudio fichier = new FichierAudio(name);
//name est le nom du fichier pické
ArrayList<Chroma> tableChroma = fichier.transformeeDeFourier();
ArrayList<Integer> listeBasse = fichier.getListeBasses();
GrilleAccords grilleAccords = ChromaIntermediaire.AnalyseChroma(tableChroma, listeBasse);

CommandeAllumage commandeAllumage = new CommandeAllumage (grilleAccords);
commandeAllumage.calculerCommandes();
ArrayList<Commande> commandes = commandeAllumage.getCommandes() ;
return commandes;	
	
}
}

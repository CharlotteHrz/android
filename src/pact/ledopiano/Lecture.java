package pact.ledopiano;

//import lecture.FichierAudio;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import lecture.FichierAudio;

import harmonie.ChromaIntermediaire;
import harmonie.Commande;
import harmonie.CommandeAllumage;
import harmonie.GrilleAccords;
import Analyse.Chroma;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class Lecture extends Activity implements View.OnClickListener {
	private Button play;
	private Button stop;
	private Button retour;
	private TextView nom_morceau;
	private Uri uri;
	
	//FichierAudio audio;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lecture);
		
		play = (Button) findViewById(R.id.button1);
		play.setOnClickListener(this);
		stop = (Button) findViewById(R.id.button2);
		stop.setOnClickListener(this);
		
		nom_morceau = (TextView) findViewById(R.id.textView2);
		//Uri uri = getIntent().getParcelableExtra("morceau");
        //nom_morceau.setText(uri.getLastPathSegment());
		//nom_morceau.setText(uri.toString());
        
        //audio = new FichierAudio("wav");
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button1:
			//if (audio.isRunning()){
				//MainActivity.thread.pause();
			//}
			MainActivity.getThread().lecture();
			break;
		case R.id.button2:
			// Chargement du fichier
			//FichierAudio audio = null;
			try {
				/*audio = new FichierAudio (uri.getPath());
				ArrayList<Chroma> chromaMorceau=audio.transformeeDeFourier();
				// Calcul des chromas
				ArrayList<Integer> bassePipeau = new ArrayList<Integer>();
				for (int i = 0 ; i < 12 ; i++) {
					bassePipeau.add(-1);
				}
				GrilleAccords grille = ChromaIntermediaire.AnalyseChroma(chromaMorceau, bassePipeau);
				*/
				
				//MainActivity.getThread().stopp();
				
				GrilleAccords grille = null;
				grille = new GrilleAccords(this.getResources().openRawResource(R.raw.let_it_be));
				CommandeAllumage trouverAllumage = new CommandeAllumage(grille);
				trouverAllumage.calculerCommandes();
				ArrayList<Commande> commandes = trouverAllumage.getCommandes();
				
				// Envoi des commandes
				for (Commande commande : commandes) {
					Thread.sleep(200);
					MainActivity.getThread().envoyerCommande(commande);
				}
				
				MainActivity.getThread().transmissionFinie();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		}
		
	}

}

package harmonie;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFrame;

public class TestAllumage {

	public static void main(String[] args) {
		GrilleAccords grille;
		grille = null;
		
		try {
			grille = new GrilleAccords("data/grilles/let_it_be.txt");
		} catch (FileNotFoundException e) {
			System.out.print(e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.print(e);
			e.printStackTrace();
		}
		
		CommandeAllumage trouverAllumage = new CommandeAllumage(grille);
		trouverAllumage.calculerCommandes();
		ArrayList<Commande> commandes = trouverAllumage.getCommandes();
		
		PrintWriter pw = null;		
		try {
			pw = new PrintWriter("out.txt");
			
			for(Commande commande : commandes) {
				char[] couleurs = commande.getCouleurs();
				
				for (int i = 0 ; i < 88 ; i++) {
					pw.append(Integer.toString((int) couleurs[i]));
					pw.append(' ');
				}
				
				pw.println();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pw.close();
			} catch (Exception e) {}
		}
		
		PianoVirtuel piano = new PianoVirtuel("data/piano_virtuel/piano.png");
        piano.setSize(piano.getWidth(),piano.getHeight());
        piano.setVisible(true);
        piano.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        piano.setCommandes(commandes);
        piano.lecture();
	}

}

package accompagnement;

import com.leff.midi.MidiTrack;

import harmonie.GrilleAccords;

public abstract class Generateur {
	
	GrilleAccords grille;
	
	public Generateur(GrilleAccords grille) {
		this.grille = grille;
	}
	
	public abstract MidiTrack generate();

	public GrilleAccords getGrille() {
		return grille;
	}
}

package harmonie;

public class Commande {
	private int debut; // Moment en ms par rapport au début du morceau du déclenchement de la commande
	private byte couleurs[]; // Tableau donnant la couleur de chaque note (char pour l'envoi par bluetooth)
	
	Commande (int debut) {
		this.debut = debut;
		couleurs = new byte[88];
		
		for (int i = 0 ; i < couleurs.length ; i++) { // Initialisation : toutes les touches sont éteintes
			couleurs[i] = 0;
		}
	}

	public int getDebut() {
		return debut;
	}

	public void setDebut(int debut) {
		this.debut = debut;
	}

	public byte[] getCouleurs() {
		return couleurs;
	}
	
	public void setCouleurs(int note, byte couleur) { // Set for example all the Cs of the keyboard to a given color
		for (int i = note ; i < 88 ; i+=12) {
			couleurs[i] = couleur;
		}
		
		for (int i = note ; i >= 0 ; i-=12) {
			couleurs[i] = couleur;
		}
	}
	
	public void setCouleurs(int notes[], byte couleur, int fondamentale) {
		for (int i = 0 ; i < notes.length ; i++ ) {
			setCouleurs(fondamentale + notes[i], couleur);
		}
	}
}

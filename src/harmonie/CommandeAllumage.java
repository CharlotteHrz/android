package harmonie;

import java.util.ArrayList;

public class CommandeAllumage {
	private GrilleAccords grille;
	private ArrayList<Commande> commandes;
	
	/* Couleurs de notes par défaut.
	 * Elles sont données par ordre de priorité croissant 	
	 */
	private byte couleurGamme;
	private byte couleurAccord; // Moins utile
	private byte couleurNotesFortes;
	private byte couleurFondamentale;

	public CommandeAllumage(GrilleAccords grille) {
		this.grille = grille;
		
		commandes = new ArrayList<Commande>();
		
		couleurGamme = (byte) 127;
		couleurAccord = (byte) 80;
		couleurNotesFortes = (byte) 60;
		couleurFondamentale = (byte) 35;
	}
	
	public void calculerCommandes() {
		grille.setDebuts();
		Commande commande;
		
		// Calcul des notes à allumer TODO gamme
		for (Accord accord : grille.getAccords()) {
			commande = new Commande(accord.getDebut());
			
			if (accord.getChiffrage().equals("")) // Gamme majeure
					commande.setCouleurs(new int[]{0,2,4,5,7,9,11}, couleurGamme, accord.getFondamentale());
			if (accord.getChiffrage().equals("-")) // Gamme mineure TODO lire bouquin d'harmonie
					commande.setCouleurs(new int[]{0,2,3,5,7,8,11}, couleurGamme, accord.getFondamentale());
			else {
					System.out.print("Erreur: Le chiffrage est invalide, on met une gamme majeure par défaut");
					commande.setCouleurs(new int[]{0,2,4,5,7,9,11}, couleurGamme, accord.getFondamentale());
			}
			
			for (int i = 0 ; i < accord.getNotes().length ; i++) {
				if (accord.getNotes()[i])
					commande.setCouleurs(accord.getFondamentale(), couleurAccord);
			}
			
			if (accord.getChiffrage().equals(""))
					commande.setCouleurs(new int[]{0,4,7}, couleurNotesFortes, accord.getFondamentale());
			if (accord.getChiffrage().equals("-"))
					commande.setCouleurs(new int[]{0,3,7}, couleurNotesFortes, accord.getFondamentale());
			if (accord.getChiffrage().equals("M7"))
					commande.setCouleurs(new int[]{4,11}, couleurNotesFortes, accord.getFondamentale());
			if (accord.getChiffrage().equals("6"))
					commande.setCouleurs(new int[]{4,9}, couleurNotesFortes, accord.getFondamentale());
			if (accord.getChiffrage().equals("7"))
					commande.setCouleurs(new int[]{4,10}, couleurNotesFortes, accord.getFondamentale());
			if (accord.getChiffrage().equals("7sus4"))
					commande.setCouleurs(new int[]{5,10}, couleurNotesFortes, accord.getFondamentale());
			if (accord.getChiffrage().equals("-7(b5)"))
					commande.setCouleurs(new int[]{3,6,10}, couleurNotesFortes, accord.getFondamentale());
			if (accord.getChiffrage().equals("+7"))
					commande.setCouleurs(new int[]{4,8,10}, couleurNotesFortes, accord.getFondamentale());
			else
				System.out.println("Le chiffrage n'est pas (encore) reconnu par le système");
			
			commande.setCouleurs(accord.getFondamentale(), couleurFondamentale);
			
			commandes.add(commande);
		}
		
		// Ajout d'une commande qui efface les notes à la fin du morceau
		commandes.add(new Commande(grille.getAccords().get(grille.getAccords().size()-1).getFin()));
	}
	
	public ArrayList<Commande> getCommandes() {
		return commandes;
	}
}

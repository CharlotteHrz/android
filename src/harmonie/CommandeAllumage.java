package harmonie;

import java.util.ArrayList;

public class CommandeAllumage {
	private GrilleAccords grille;
	private ArrayList<Commande> commandes;
	
	/* Couleurs de notes par défaut.
	 * Elles sont données par ordre de priorité croissant 	
	 */
	private char couleurGamme;
	private char couleurAccord; // Moins utile
	private char couleurNotesFortes;
	private char couleurFondamentale;

	public CommandeAllumage(GrilleAccords grille) {
		this.grille = grille;
		
		commandes = new ArrayList<Commande>();
		
		couleurGamme = (char) 127;
		couleurAccord = (char) 80;
		couleurNotesFortes = (char) 60;
		couleurFondamentale = (char) 35;
	}
	
	public void calculerCommandes() {
		grille.setDebuts();
		Commande commande;
		
		// Calcul des notes à allumer TODO gamme
		for (Accord accord : grille.getAccords()) {
			commande = new Commande(accord.getDebut());
			
			switch (accord.getChiffrage()) {
				case "": // Gamme majeure
					commande.setCouleurs(new int[]{0,2,4,5,7,9,11}, couleurGamme, accord.getFondamentale());
					break;
				case "-": // Gamme mineure TODO lire bouquin d'harmonie
					commande.setCouleurs(new int[]{0,2,3,5,7,8,11}, couleurGamme, accord.getFondamentale());
					break;
				default :
					System.out.print("Erreur: Le chiffrage est invalide, on met une gamme majeure par défaut");
					commande.setCouleurs(new int[]{0,2,4,5,7,9,11}, couleurGamme, accord.getFondamentale());
					break;
			}
			
			for (int i = 0 ; i < accord.getNotes().length ; i++) {
				if (accord.getNotes()[i])
					commande.setCouleurs(accord.getFondamentale(), couleurAccord);
			}
			
			switch (accord.getChiffrage()) {
				case "":
					commande.setCouleurs(new int[]{0,4,7}, couleurNotesFortes, accord.getFondamentale());
					break;
				case "-":
					commande.setCouleurs(new int[]{0,3,7}, couleurNotesFortes, accord.getFondamentale());
					break;
				case "M7":
					commande.setCouleurs(new int[]{4,11}, couleurNotesFortes, accord.getFondamentale());
					break;
				case "6":
					commande.setCouleurs(new int[]{4,9}, couleurNotesFortes, accord.getFondamentale());
					break;
				case "7":
					commande.setCouleurs(new int[]{4,10}, couleurNotesFortes, accord.getFondamentale());
					break;
				case "7sus4":
					commande.setCouleurs(new int[]{5,10}, couleurNotesFortes, accord.getFondamentale());
					break;
				case "-7(b5)":
					commande.setCouleurs(new int[]{3,6,10}, couleurNotesFortes, accord.getFondamentale());
					break;
				case "+7":
					commande.setCouleurs(new int[]{4,8,10}, couleurNotesFortes, accord.getFondamentale());
					break;
				default:
					System.out.println("Le chiffrage n'est pas (encore) reconnu par le système");
					break;
			}
			
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

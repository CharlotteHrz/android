package harmonie;

public class Accord {
	private int fondamentale; // 0 = A, 1 = A#/Bb, 2 = B ...
	private boolean notes[]; /* Tableau de taille 12 indiquant si note[i] est présente dans l'accord ou non
							  * Le chiffrage se déduira de ce tableau
							  */
	private String chiffrage; // Ne donne pas la lettre correspondant à la fondamentale de l'accord
	private String lettreFondamentale;
	
	/* Indique la durée en ms à laquelle l'accord commence et celle à laquelle il finit
	 * Utilisé uniquement pour les accords déterminés par des chromas
	 */
	
	private int debut;
	private int fin;
	
	// Constructeurs
	
	public Accord(int fondamentale, boolean notes[]) {
		this.setFondamentale(fondamentale);
		
		notes = new boolean[12];
		for (int i = 0 ; i < 12 ; i++) {
			this.notes[i] = notes[i];
		}
		
		findChiffrage();
	}
	
	public Accord(String chiffrage) {
		// Séparation de C# et m7 pour C#m7
		if (chiffrage.length() > 1 && (chiffrage.charAt(1) == '#' || chiffrage.charAt(1) == 'b')) {
			lettreFondamentale = chiffrage.substring(0,2);
			this.chiffrage = chiffrage.substring(2);
		}
		
		else {
			lettreFondamentale = chiffrage.substring(0,1);
			this.chiffrage = chiffrage.substring(1);
		}
		
		notes = new boolean[12];
		for (int i = 0 ; i < 12 ; i++) {
			this.notes[i] = false;
		}
		
		trouverFondamentale();
		trouverNotes();
	}
	
	private void trouverFondamentale() {
		if (lettreFondamentale.equals(new String("A")))
			fondamentale = 0;
		else if (lettreFondamentale.equals("Bb"))
			fondamentale = 1;
		else if (lettreFondamentale.equals("B"))
			fondamentale = 2;
		else if (lettreFondamentale.equals("C"))
			fondamentale = 3;
		else if (lettreFondamentale.equals("C#") || lettreFondamentale.equals("Db"))
			fondamentale = 4;
		else if (lettreFondamentale.equals("D"))
			fondamentale = 5;
		else if (lettreFondamentale.equals("Eb"))
			fondamentale = 6;
		else if (lettreFondamentale.equals("E"))
			fondamentale = 7;
		else if (lettreFondamentale.equals("F"))
			fondamentale = 8;
		else if (lettreFondamentale.equals("F#") || lettreFondamentale.equals("Gb"))
			fondamentale = 9;
		else if (lettreFondamentale.equals("G"))
			fondamentale = 10;
		else if (lettreFondamentale.equals("Ab"))
			fondamentale = 11;
		else {
			System.out.println("Tonalité inconnue :" + "'" + lettreFondamentale + "'");
		}
	}
	
	private void trouverNotes() { // En fonction du chiffrage
		if (chiffrage.equals(""))
			insererNotes(new int[]{0,4,7});
		else if (chiffrage.equals("-"))
			insererNotes(new int[]{0,3,7});
		else if (chiffrage.equals("M7"))
			insererNotes(new int[]{0,4,7,11});
		else if (chiffrage.equals("6"))
			insererNotes(new int[]{0,4,9});
		else if (chiffrage.equals("7"))
			insererNotes(new int[]{0,4,7,10});
		else if (chiffrage.equals("7sus4"))
			insererNotes(new int[]{0,5,7,10});
		else if (chiffrage.equals("-7(b5)"))
			insererNotes(new int[]{0,4,6,10});
		else if (chiffrage.equals("+7"))
			insererNotes(new int[]{0,4,8,10});
		else
			System.out.println("Le chiffrage entré est foireux");
	}
	
	private void insererNotes(int tableauNotes[]) {
		for (int i = 0 ; i < tableauNotes.length ; i++) {
			notes[tableauNotes[i]] = true;
		}
	}
	
	private void findChiffrage() { // TODO
		chiffrage = "Détermination du chiffrage non implémentée";
	}
	
	// Accesseurs
	
	public int getDebut() {
		return debut;
	}

	public void setDebut(int debut) { // Le début est à changer avant la fin. Mettre un début après la fin repousse la fin
		if (debut > fin)
			fin = debut+1;
		
		this.debut = debut;
	}

	public int getFin() {
		return fin;
	}

	public void setFin(int fin) {
		if (fin < debut) {
			fin = debut+1;
			System.out.print("Erreur : la fin entree est superieure au debut");
		}

		else
			this.fin = fin;
	}
	
	public String getChiffrage() {
		return chiffrage;
	}
	
	public String getVraiChiffrage() {
		return lettreFondamentale + chiffrage;
	}

	public int getFondamentale() {
		return fondamentale;
	}

	public void setFondamentale(int fondamentale) {
		this.fondamentale = fondamentale;
	}

	public boolean[] getNotes() {
		return notes;
	}

	public void setNotes(boolean[] notes) {
		this.notes = notes;
	}
}

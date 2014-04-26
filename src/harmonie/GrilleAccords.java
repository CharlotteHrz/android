package harmonie;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class GrilleAccords {
	private boolean entreeMain;

	private int tonalite; // fondamentale de la tonalité : 0 = C, 1 = C#/Db, 2 = D ...
	private boolean majeur;

	// Détermination de la vitesse de lecture par une entrée à la main
	private double tempo; // Nombre de temps forts par minute
	private int chiffrage; // Nombre de temps forts dans une mesure (commun au binaire et au ternaire)

	private ArrayList<Accord> accords;

	/* TODO Pour l'instant on a besoin d'envoyer la tonalité au constructeur
	 * Si on implémente un algorithme de détermination de la tonalité, voir si on le met dans cette classe ou non
	 */
	
	public GrilleAccords(String filename) throws FileNotFoundException, IOException {
		accords = new ArrayList<Accord>();
		loadGrille(filename);
	}
	
	public GrilleAccords(int tonalite, boolean majeur) {
		this.tonalite = tonalite;
		this.majeur = majeur;
		this.tempo = -1;
		this.chiffrage = -1;

		accords = new ArrayList<Accord>();
		entreeMain = false;
	}
	
	public GrilleAccords(int tonalite, boolean majeur, double tempo, int chiffrage) {
		this.tonalite = tonalite;
		this.majeur = majeur;
		this.tempo = tempo;
		this.chiffrage = chiffrage;
		
		accords = new ArrayList<Accord>();
		entreeMain = true;
	}
	
	// Accesseurs
	
	public void ajouterAccord(Accord accord) {
		accords.add(accord);
	}

	public int getTonalite() {
		return tonalite;
	}

	public void setTonalite(int tonalite) {
		this.tonalite = tonalite;
	}

	public boolean isMajeur() {
		return majeur;
	}

	public ArrayList<Accord> getAccords() {
		return accords;
	}
	
	public double getTempo() {
		if (!entreeMain)
			System.out.println("La grille n'a pas été rentrée à la main");
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	
	public int getChiffrage() {
		if (!entreeMain)
			System.out.println("La grille n'a pas été rentrée à la main");
		return chiffrage;
	}
	
	public boolean getEntreeMain() {
		return entreeMain;
	}
	
	// Entrées / sorties
	
	public void saveGrille(String filename) {
		PrintWriter pw = null;		
		try {
			pw = new PrintWriter(filename);
			
			if (entreeMain) {
				// Informations générales
				pw.println("EntreeMain");
				
				pw.println(tonalite);
				pw.println(majeur);
				pw.println(tempo);
				pw.println(chiffrage);
				
				// Accords
				for(Accord accord : accords) {
					pw.println(accord.getVraiChiffrage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pw.close();
			} catch (Exception e) {}
		}
	}
	
	public void loadGrille(String filename) throws FileNotFoundException, IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			throw e1;
		}
		
		try {
			if (br.readLine().equals("EntreeMain")) {
				entreeMain = true;
				tonalite = Integer.valueOf(br.readLine()).intValue();
				majeur = Boolean.valueOf(br.readLine()).booleanValue();
				tempo = Double.valueOf(br.readLine()).doubleValue();
				chiffrage = Integer.valueOf(br.readLine()).intValue();
				
				accords.clear();
				
				String line = br.readLine();
				while (line != null) {
					accords.add(new Accord(line));
					line = br.readLine();
				}
			}
			
			else {
				br.close();
				throw new IOException("Grille invalide");
			}
			
		} catch (NumberFormatException e) {
			System.out.println("Fichier grille corrompu");
			e.printStackTrace();
			br.close();
			throw new IOException("Grille invalide");
		} catch (IOException e) {
			System.out.println("Je sais pas ce que c'est cette erreur");
			e.printStackTrace();
			throw new IOException("Grille invalide");
		}
		
		br.close();
		
	}
	
	// Autres méthodes
	
	public void setDebuts() {
		if (entreeMain) { // Si la grille a été entrée à la main, on a besoin de calculer les temps de début de chaque accord
			int dureeAccord = (int) (60000 * chiffrage / tempo);
			for (int i = 0 ; i < accords.size() ; i++) {
				accords.get(i).setDebut(i*dureeAccord);
				accords.get(i).setFin((i+1)*dureeAccord);
			}
		}
	}
}

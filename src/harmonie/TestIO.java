package harmonie;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestIO {
	public static void main(String[] args) {
		GrilleAccords grille;
		
		try {
			grille = new GrilleAccords("data/grilles/grille.txt");
			grille.saveGrille("data/grilles/grille2.txt");
			
		} catch (FileNotFoundException e) {
			System.out.print(e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.print(e);
			e.printStackTrace();
		}
		
		System.out.println("Hello world");
		
	}

}

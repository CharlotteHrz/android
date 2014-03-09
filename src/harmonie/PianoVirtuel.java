package harmonie;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class PianoVirtuel extends JFrame {
	private BufferedImage piano;
	private int width;
	private int height;
    private int tailleBarre;
    private Timer planificateur;
    private char couleurs[];
    private ArrayList<Commande> commandes;
	
    class LireCommande extends TimerTask {
    	private int index;
    	
    	public LireCommande(int index) {
    		this.index = index;
    	}
    	
		@Override
		public void run() {
			couleurs = commandes.get(index).getCouleurs();
			paint(getGraphics());
		}
	}
    
	public PianoVirtuel(String filename) {
        try {
            piano = ImageIO.read(new File(filename));
        } catch (IOException e) {
        	System.out.println("Impossible de charger l'image du piano");
        	e.printStackTrace();
        	System.exit(0);
        }
       
        commandes = new ArrayList<Commande>();
        couleurs = new char[88];
        
        planificateur = new Timer();
       
        for (int i = 0 ; i < couleurs.length ; i++) { // Initialisation : toutes les touches sont éteintes
			couleurs[i] = 0;
        }
       
        height = piano.getHeight();
        width = piano.getWidth();
       
        tailleBarre = 32;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height+tailleBarre;
	}
	
	public void setCommandes(ArrayList<Commande> commandes) {
		this.commandes = commandes;
		//LireCommande lire = new LireCommande(0);
		//lire.run();
	}
	
	public void lecture() {
		for (int i = 0 ; i < commandes.size() ; i++) {
			planificateur.schedule(new LireCommande(i), commandes.get(i).getDebut());
		}
	}
	
	public void pause() {
	}
	
	public void stop() {
	}

	@Override
	public void paint(Graphics g) {
		setBackground(Color.black);
		g.drawImage(piano, 0, tailleBarre, null);
		
		for (int i = 0 ; i < 88 ; i++) {
			switch (couleurs[i]) {
			case (char) 127: // Gamme
				g.setColor(Color.blue);
				break;
			
			case (char) 80: // Accord
				g.setColor(Color.red);
				break;
			
			case (char) 60: // Notes fortes
				g.setColor(Color.yellow);
				break;
			
			case (char) 35: // Fondamentale
				g.setColor(Color.green);
				break; 
			
			case 0:
				break;
			
			default:
				System.out.println("Couleur non reconnue: " + (int) couleurs[i]);
				break;
			}
			
			if (couleurs[i] != 0)
				g.fillRect(1 + 14*i, tailleBarre + 1, 13, 60);
		}
	}

}

package accompagnement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;

import harmonie.GrilleAccords;

public class MidiCreator {
	// Booléens pour dire si on génère chaque instrument ou non
	boolean accords;
	boolean basse;
	boolean rythme;
	
	GenAccords 	genAccords;
	GenBasse 	genBasse;
	GenRythme 	genRythme;
	
	GrilleAccords grille;
	
	// Nom du fichier midi généré
	String outputName;
	
	public boolean isAccords() {
		return accords;
	}

	public void setAccords(boolean accords) {
		this.accords = accords;
	}

	public boolean isBasse() {
		return basse;
	}

	public void setBasse(boolean basse) {
		this.basse = basse;
	}

	public boolean isRythme() {
		return rythme;
	}

	public void setRythme(boolean rythme) {
		this.rythme = rythme;
	}

	public String getOutputName() {
		return outputName;
	}

	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

	public MidiCreator (GrilleAccords grille) {
		accords = true;
		basse 	= true;
		rythme 	= true;
		
		this.grille = grille;
		
		genAccords 	= new GenAccords(grille);
		genBasse 	= new GenBasse	(grille);
		genRythme 	= new GenRythme	(grille);
		
		outputName = "out.mid";
	}
	
	public MidiCreator (GrilleAccords grille, boolean accords, boolean basse, boolean rythme) {
		this.accords = accords;
		this.basse = basse;
		this.rythme = rythme;
		
		this.grille = grille;
		
		genAccords 	= null;
		genBasse 	= null;
		genRythme 	= null;
		
		if (accords)
			genAccords 	= new GenAccords(grille);
		if (basse)
			genBasse 	= new GenBasse	(grille);
		if (rythme)
			genRythme 	= new GenRythme	(grille);
		
		outputName = "out.mid";
	}
	
	public void generateMidiFile() throws FileNotFoundException, IOException {
		ArrayList<MidiTrack> tracks = new ArrayList<MidiTrack>();
		
		// La track 0 contient simplement des métadonnées
		MidiTrack tempoTrack = new MidiTrack();
		
		int chiffrage = grille.getChiffrage();
		if (chiffrage == -1)
			chiffrage = 4;
		
		int tempo = (int) grille.getTempo();
		if (tempo == -1)
			tempo = 120;
		
        TimeSignature ts = new TimeSignature();
        ts.setTimeSignature(chiffrage, 4, TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION);

	    Tempo t = new Tempo();
	    t.setBpm(tempo);

	    tempoTrack.insertEvent(ts);
	    tempoTrack.insertEvent(t);
	    
	    tracks.add(tempoTrack);

	    // Ajout des autres pistes
		
		if (accords)
			tracks.add(genAccords.generate());
		if (basse)
			tracks.add(genBasse.generate());
		if (rythme)
			tracks.add(genRythme.generate());
		
        MidiFile midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);
        
        File output = new File(outputName);
        
        midi.writeToFile(output);
	}
}

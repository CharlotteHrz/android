package accompagnement;

import harmonie.Accord;
import harmonie.GrilleAccords;

import com.leff.midi.MidiTrack;

public class GenAccords extends Generateur {
	private int defaultVelocity;
	
	public GenAccords(GrilleAccords grille) {
		super(grille);
		defaultVelocity = 100;
	}

	@Override
	public MidiTrack generate() {
		MidiTrack track = new MidiTrack();
		
		// On joue pour une première version simplement les notes de l'accord,
		// ça sonnera mieux avec des voicings
		
		for (Accord accord : grille.getAccords()) {
			track.insertNote(0, 48 + accord.getFondamentale(), defaultVelocity, accord.getDebut(), accord.getFin() - accord.getDebut());
			for (int i = 0 ; i < 12 ; i++) {
				if (accord.getNotes()[i]) // The default used channel is 0
					track.insertNote(0, 60 + accord.getFondamentale() + i, defaultVelocity, accord.getDebut(), accord.getFin() - accord.getDebut());
			}
		}
		
		return track;
	}

}

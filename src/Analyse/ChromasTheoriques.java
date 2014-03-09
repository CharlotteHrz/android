package Analyse;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;

public class ChromasTheoriques {
	
	static ArrayList<Chroma> chromasTheoriques;

	
	public ChromasTheoriques() throws InvalidMidiDataException, Exception
	{
	chromasTheoriques = new ArrayList<Chroma>();
		for(int i=0; i<12; i++){
			Note note = new Note(i);
			Chroma chroma = new Chroma("data/chromastheoriques/majeur/"+i+".mid",note.getNomNote()+"M");
			chromasTheoriques.add(chroma);
			}
		
		for(int j1=0; j1<12; j1++){
				Note noteM = new Note(j1);
				Chroma chromaM = new Chroma("data/chromastheoriques/mineur/"+j1+".mid",noteM.getNomNote()+"m");
				chromasTheoriques.add(chromaM);
		
	}
	
	}
	public void apercu()
	{
		for(int i =0; i<this.chromasTheoriques.size(); i++) {
	
				
				System.out.println(chromasTheoriques.get(i).getNom()+"[ "+chromasTheoriques.get(i).getTable()[0]+","+chromasTheoriques.get(i).getTable()[1]+","+chromasTheoriques.get(i).getTable()[2]+","+chromasTheoriques.get(i).getTable()[3]+","+chromasTheoriques.get(i).getTable()[4]+","+chromasTheoriques.get(i).getTable()[5]+","+chromasTheoriques.get(i).getTable()[6]+","+chromasTheoriques.get(i).getTable()[7]+","+chromasTheoriques.get(i).getTable()[8]+","+chromasTheoriques.get(i).getTable()[9]+","+chromasTheoriques.get(i).getTable()[10]+","+chromasTheoriques.get(i).getTable()[11]+"]");
	}}
	}

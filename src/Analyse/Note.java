package Analyse;

public class Note {
		private int valeur;
		private String nomNote;
		
		public Note(int valeur)
		{
			this.valeur=Math.abs(valeur%12);
			if (valeur==0){this.nomNote = "C";}
			else if (valeur==1){this.nomNote = "C#";}
			else if (valeur==2){this.nomNote = "D";}
			else if (valeur==3){this.nomNote = "D#";}
			else if (valeur==4){this.nomNote = "E";}
			else if (valeur==5){this.nomNote = "F";}
			else if (valeur==6){this.nomNote = "F#";}
			else if (valeur==7){this.nomNote = "G";}
			else if (valeur==8){this.nomNote = "G#";}
			else if (valeur==9){this.nomNote = "A";}
			else if (valeur==10){this.nomNote = "A#";}
			else if (valeur==11){this.nomNote = "B";}
		}
		
		public Note(String nomNote)
		{
			this.nomNote=nomNote;
			if (nomNote.equals("C")){this.valeur = 0;}
			else if (nomNote.equals("C#")){this.valeur = 1;}
			else if (nomNote.equals("D")){this.valeur = 2;}
			else if (nomNote.equals("D#")){this.valeur = 3;}
			else if (nomNote.equals("E")){this.valeur = 4;}
			else if (nomNote.equals("F")){this.valeur = 5;}
			else if (nomNote.equals("F#")){this.valeur = 6;}
			else if (nomNote.equals("G")){this.valeur = 7;}
			else if (nomNote.equals("G#")){this.valeur = 8;}
			else if (nomNote.equals("A")){this.valeur = 9;}
			else if (nomNote.equals("A#")){this.valeur = 10;}
			else if (nomNote.equals("B")){this.valeur = 11;}
		}
		
		//méthodes
		public String getNomNote()
		{
			return(this.nomNote);
		}
		public int getValeur()
		{
			return(this.valeur);
		}

}

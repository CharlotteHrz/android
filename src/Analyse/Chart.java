/* package Analyse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
 
public class Chart extends Application {


//visualisation de chromas sous forme de graphique 
	
// Pour le lancer, il suffit d'écrire l'instruction  "chart.main(null);"
	
	@Override public void start(Stage stage) throws InvalidMidiDataException, IOException {           
   
	

		System.out.println("nom du fichier midi (à ranger dans le dossier 'data/exemples de fichiers midi') " );		
		Scanner sc1 = new Scanner(System.in);
		String path = "data/Exemples de Midi/"+ sc1.nextLine() + ".mid";
		System.out.println("\n nurméro de la piste ");
		Scanner sc2 = new Scanner(System.in);
		int track = sc2.nextInt();
		TableauNotes t = new TableauNotes(path,track);
	   

        ArrayList<double[]> tableau = t.getTableau();

        
        stage.setTitle("Chart chromas");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("temps");
        xAxis.setLabel("t");       
        yAxis.setLabel("valeur");
        XYChart.Series[] series = new XYChart.Series[12];
             

  for (int i=0; i<12; i++)
  	{  series[i] = new XYChart.Series();
       Note n = new Note(i); String s = n.getNomNote();
       series[i].setName(s);       
       int k=0; while (k<tableau.size()-1){
       series[i].getData().add(new XYChart.Data("t="+k+" ",tableau.get(k)[i])); 
       k++;} } 

  Scene scene  = new Scene(bc,800,600);
        
        bc.getData().addAll(series[0],series[1],series[2],series[3],series[4],series[5],series[6],series[7],series[8],series[9],series[10],series[11]);
        stage.setScene(scene);
        stage.show();
     
        }
 
 

  public static void main(String[] args) {
    launch(args);

  }

}
*/
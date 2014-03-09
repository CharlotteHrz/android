package pact.ledopiano;

//import lecture.FichierAudio;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class Lecture extends Activity implements View.OnClickListener {
	private Button play;
	private Button stop;
	private Button retour;
	private TextView nom_morceau;
	private Uri uri;
	
	//FichierAudio audio;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lecture);
		
		play = (Button) findViewById(R.id.button1);
		play.setOnClickListener(this);
		stop = (Button) findViewById(R.id.button2);
		stop.setOnClickListener(this);
		retour = (Button) findViewById(R.id.button3);
		retour.setOnClickListener(this);
		
		nom_morceau = (TextView) findViewById(R.id.textView2);
		Uri uri = getIntent().getParcelableExtra("morceau");
        nom_morceau.setText(uri.getLastPathSegment());
		//nom_morceau.setText(uri.toString());
        
        //audio = new FichierAudio("wav");
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button1:
			//if (audio.isRunning()){
			//	audio.lire();
			//}
			//else audio.pause();
			break;
		case R.id.button2:
			//audio.stop();
			break;
		case R.id.button3:
			this.onDestroy();
		}
		
	}

}

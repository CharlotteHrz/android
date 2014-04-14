package pact.ledopiano;

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
        
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button1:
			//mettre en play/pause
			
			Intent intent = new Intent(Intent.ACTION_RUN, uri);
			startActivity(intent);
	        
			//Intent.CATEGORY_APP_MUSIC seulement à partir de l'api 15
	        //garder en mémoire : Context -> bindService()
			
			break;
		case R.id.button2:
			//faire stop
			break;
		case R.id.button3:
			Intent intent2 = new Intent(this, MainActivity.class);
//			intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent2);
		}
		
	}

}

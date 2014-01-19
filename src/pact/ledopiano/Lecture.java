package pact.ledopiano;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class Lecture extends Activity implements View.OnClickListener {
	private Button play;
	private Button stop;
	private Button retour;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lecture);
		
		play = (Button) findViewById(R.id.button1);
		play.setOnClickListener(this);
		stop = (Button) findViewById(R.id.button2);
		stop.setOnClickListener(this);
		retour = (Button) findViewById(R.id.button3);
		retour.setOnClickListener(this);		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button1:
			//mettre en play/pause
			break;
		case R.id.button2:
			//faire stop
			break;
		case R.id.button3:
			//retour à MainActivity
		}
		
	}

}

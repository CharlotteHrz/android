package pact.ledopiano;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class Grille extends Activity implements View.OnClickListener {
	
	private Button play;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grille);
		
		play = (Button) findViewById(R.id.button9);
		play.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, Lecture.class);
		startActivity(intent);
		
	}


}

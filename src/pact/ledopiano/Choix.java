package pact.ledopiano;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Choix extends Activity implements View.OnClickListener {
	private Button base;
	private Button bibli;
	private Button continuer;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choix);
		
		base = (Button) findViewById(R.id.button6);
		base.setOnClickListener(this);
		bibli = (Button) findViewById(R.id.button7);
		bibli.setOnClickListener(this);
		continuer = (Button) findViewById(R.id.button8);
		continuer.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v == findViewById(R.id.button8)){
			Intent intent = new Intent(this, Grille.class);
			startActivity(intent);
		}
		else{
			Intent intent = new Intent(this, Bibliotheque.class);
			startActivity(intent);
		}
	}
	

	
}

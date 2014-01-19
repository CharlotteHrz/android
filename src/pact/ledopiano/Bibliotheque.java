package pact.ledopiano;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class Bibliotheque extends Activity implements View.OnClickListener {
	private Button b;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bibliotheque);
		
		b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(this);
		
		//il faudra retenir le nom du morceau choisi:
		//code à mettre dans onStop()
	}

	@Override
	public void onClick(View arg0) {
		this.finish();
		
	}

}

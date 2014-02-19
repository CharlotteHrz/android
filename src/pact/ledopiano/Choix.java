package pact.ledopiano;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
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
			Intent intent = new Intent(this, Lecture.class);
		    startActivity(intent);
		}
		
		else{
			Intent intent = new Intent(this, Bibliotheque.class);
			startActivity(intent);
			
			Uri bibli = Uri.parse("Explorateur de fichiers");
			Intent intentBibli = new Intent(Intent.ACTION_VIEW, bibli);
			
			PackageManager packageManager = getPackageManager();
			List<ResolveInfo> activities = packageManager.queryIntentActivities(intentBibli, 0);
			if (activities.size() > 0) {
				startActivity(intentBibli);
			}
			
			
			//Ceci nous mène à un menu de sélection d'appli :)
			Intent intent2 = new Intent(Intent.ACTION_PICK);
		    intent2.getData();
		    if (intent.resolveActivity(getPackageManager()) != null) {
		        startActivityForResult(intent, 1);
		    }
			
		}
	}
	

	
}

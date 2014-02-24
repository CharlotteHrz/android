package pact.ledopiano;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class Choix extends Activity implements View.OnClickListener {
	private Button base;
	private Button bibli;
	private Button continuer;
	private int code_pour_appel_bibli = 1;
	
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
		
		
		//gros bazar car je suis en phase de recherche...
		else{
			
			//Ceci marche !!!
			Uri uri_music = MediaStore.Audio.Playlists.getContentUri("Music");
			Intent intent = new Intent(Intent.ACTION_PICK, uri_music);
			startActivityForResult(intent, code_pour_appel_bibli);
			
			
			Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath()));
			Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT, uri);
			
				if (intent1.resolveActivity(getPackageManager()) != null) {
					startActivityForResult(intent1, code_pour_appel_bibli);
					
				}	
				
				
			Uri bibli = Uri.parse("/sdcard/download/LEDoPiano");
			Intent intentBibli = new Intent(Intent.ACTION_VIEW, bibli);
			
			PackageManager packageManager = getPackageManager();
			List<ResolveInfo> activities = packageManager.queryIntentActivities(intentBibli, 0);
			if (activities.size() > 0) {
				startActivity(intentBibli);
			}
						
			
			Intent intent2 = new Intent(this, Bibliotheque.class);
			startActivity(intent2);
					
			
			//Ceci nous mène à un menu de sélection d'appli :)
			Intent intent3 = new Intent(Intent.ACTION_PICK);
		    intent3.getData();
		    if (intent3.resolveActivity(getPackageManager()) != null) {
		        startActivityForResult(intent3, 1);
		    }
			
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request it is that we're responding to
	    if (requestCode == code_pour_appel_bibli) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	
	        	startActivity(new Intent(this, MainActivity.class));
	        	
	            // Get the URI that points to the selected contact
	            Uri resultUri = data.getData();
	            // Do something with the phone number...
	            startActivity(data);
	        }
	    }
	}
	

	
}

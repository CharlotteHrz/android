package pact.ledopiano;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Choix extends Activity implements View.OnClickListener {
	private Button base;
	private Button bibli;
	private Button continuer;
	private int code_bibli = 1;
	private int code_bdd = 2;
	private TextView textView;
	private Uri resultUri;
	private String path;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choix);

		base = (Button) findViewById(R.id.button6);
		base.setOnClickListener(this);
		bibli = (Button) findViewById(R.id.button7);
		bibli.setOnClickListener(this);
		continuer = (Button) findViewById(R.id.button8);
		continuer.setOnClickListener(this);
		textView = (TextView) findViewById(R.id.textView2);
		path = (String) getString(R.string.choix_text);
	}

	@Override
	public void onClick(View v) {
		if(v == findViewById(R.id.button8)){
			Intent intent = new Intent(this, Lecture.class);

			intent.putExtra("morceau", resultUri);
			startActivity(intent);
		}


		else{
			Uri uri_music = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
			Intent intent = new Intent(Intent.ACTION_PICK, uri_music);

			if(v == findViewById(R.id.button7))
				startActivityForResult(intent, code_bibli);
			else startActivityForResult(intent, code_bdd);

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request it is that we're responding to
		if (requestCode == code_bibli) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {

				resultUri = data.getData();
				//Ce n'est malheureusement pas le nom mais le path.
				path = resultUri.toString();
				textView.setText(path);

			}
		}
	}
	public String getPath() {
		return this.path;
	}
}




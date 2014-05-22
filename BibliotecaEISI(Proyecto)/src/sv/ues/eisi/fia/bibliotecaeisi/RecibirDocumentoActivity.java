package sv.ues.eisi.fia.bibliotecaeisi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RecibirDocumentoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recibir_documento);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.entregar_documento, menu);
		return true;
	}

}

package sv.ues.eisi.fia.bibliotecaeisi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ActualizarDocumentoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actualizar_documento);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actualizar_documento, menu);
		return true;
	}

}

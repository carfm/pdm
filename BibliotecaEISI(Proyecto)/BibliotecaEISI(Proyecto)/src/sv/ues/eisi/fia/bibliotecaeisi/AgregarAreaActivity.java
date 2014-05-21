package sv.ues.eisi.fia.bibliotecaeisi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AgregarAreaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_area);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.insertar_area, menu);
		return true;
	}

}

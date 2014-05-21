package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.clases.Editorial;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActualizarEditorialActivity extends Activity {

	ControlBaseDatos helper;
	EditText editNombreEditorial;
	EditText editId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actualizar_editorial);
		helper = new ControlBaseDatos(this);
		editNombreEditorial = (EditText) findViewById(R.id.editTextNombreEditorial);
		editId = (EditText) findViewById(R.id.editTextIdEditorial);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actualizar_editorial, menu);
		return true;
	}

	public void actualizarEditorial(View v) {
		Editorial e = new Editorial();
		e.setIdEditorial(Integer.parseInt(editId.getText().toString()));
		e.setNombreEditorial(this.editNombreEditorial.getText().toString());
		helper.abrir();
		String regInsertados = helper.actualizar(e);
		helper.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
	}

	public void limpiarTexto(View v) {
		editId.setText("");
		editNombreEditorial.setText("");
	}

}

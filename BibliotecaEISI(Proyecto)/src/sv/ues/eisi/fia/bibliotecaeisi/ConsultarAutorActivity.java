package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.clases.Autor;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ConsultarAutorActivity extends Activity {
	ControlBaseDatos helper;
	EditText editIdautor;
	EditText editCodigo;
	EditText editNombre;
	EditText editApellido;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultar_autor);
		helper = new ControlBaseDatos(this);
		editIdautor = (EditText) findViewById(R.id.editIdautor);
		editCodigo = (EditText) findViewById(R.id.editCodigo);
		editNombre = (EditText) findViewById(R.id.editNombre);
		editApellido = (EditText) findViewById(R.id.editApellido);

	}

	public void consultarAutor(View v) {
		helper.abrir();
		Autor autor = helper.consultarAutor(Integer.parseInt(editIdautor.getText().toString()));
		helper.cerrar();
		if (autor == null)
			Toast.makeText(
					this,
					"Autor con Id: " + editIdautor.getText().toString()
							+ " no encontrado", Toast.LENGTH_LONG).show();
		else {

			editNombre.setText(autor.getNombre());
			editApellido.setText(autor.getApellido());
			editCodigo.setText(autor.getCodigoPais());

		}
	}

	public void limpiarTexto(View v) {
		editIdautor.setText("");
		editCodigo.setText("");
		editNombre.setText("");
		editApellido.setText("");

	}
}

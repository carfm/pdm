package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.clases.Autor;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActualizarAutorActivity extends Activity {
	ControlBaseDatos helper;
	EditText editIdautor;
	EditText editCodigo;
	EditText editNombre;
	EditText editApellido;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actualizar_autor);
		helper = new ControlBaseDatos(this);
		editIdautor = (EditText) findViewById(R.id.editIdautor);
		editCodigo = (EditText) findViewById(R.id.editCodigo);
		editNombre = (EditText) findViewById(R.id.editNombre);
		editApellido = (EditText) findViewById(R.id.editApellido);

	}

	public void actualizarAlumno(View v) {
		Autor autor = new Autor();
		autor.setIdAutor(Integer.parseInt(editIdautor.getText().toString()));
		autor.setCodigoPais(editCodigo.getText().toString());
		autor.setNombre(editNombre.getText().toString());
		autor.setApellido(editApellido.getText().toString());
		helper.abrir();
		String estado = helper.actualizar(autor);
		helper.cerrar();
		Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
	}

	public void limpiarTexto(View v) {
		editIdautor.setText("");
		editCodigo.setText("");
		editNombre.setText("");
		editApellido.setText("");

	}
}

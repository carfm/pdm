package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.controlbase.*;
import sv.ues.eisi.fia.bibliotecaeisi.clases.*;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarAutorActivity extends Activity {

	ControlBaseDatos helper;
	EditText editIdAutor;
	EditText editCodPais;
	EditText editNombre;
	EditText editApellido;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_autor);
		helper = new ControlBaseDatos(this);
		editIdAutor = (EditText) findViewById(R.id.editIdautor);
		editCodPais = (EditText) findViewById(R.id.editCodigo);
		editNombre = (EditText) findViewById(R.id.editNombre);
		editApellido = (EditText) findViewById(R.id.editApellido);
	}

	public void insertarAutor(View v) {
		Integer idAutor = Integer.parseInt(editIdAutor.getText().toString());
		String codpais = editCodPais.getText().toString();
		String nombre = editNombre.getText().toString();
		String apellido = editApellido.getText().toString();
		String regInsertados;
		Autor autor = new Autor();
		autor.setIdAutor(idAutor);
		autor.setCodigoPais(codpais);
		autor.setNombre(nombre);
		autor.setApellido(apellido);
		helper.abrir();
		regInsertados = helper.insertar(autor);
		helper.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
	}

	public void limpiarTexto(View v) {
		editIdAutor.setText("");
		editNombre.setText("");
		editApellido.setText("");
		editCodPais.setText("");
	}

}

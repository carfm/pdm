package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.clases.Pais;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActualizarPaisActivity extends Activity {
	ControlBaseDatos helper;
	EditText editCodigo;
	EditText editNombre;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actualizar_pais);
		helper = new ControlBaseDatos(this);
		editCodigo = (EditText) findViewById(R.id.editCodigopais);
		editNombre = (EditText) findViewById(R.id.editNombrepais);

	}

	public void actualizar(View v) {
		Pais pais = new Pais();
		pais.setCodigoPais(editCodigo.getText().toString());
		pais.setNombrePais(editNombre.getText().toString());
		helper.abrir();
		String estado = helper.actualizar(pais);
		helper.cerrar();
		Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
	}

	public void limpiarTexto(View v) {
		editCodigo.setText("");
		editNombre.setText("");
	}
}

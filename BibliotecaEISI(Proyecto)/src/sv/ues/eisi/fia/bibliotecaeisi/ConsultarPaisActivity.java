package sv.ues.eisi.fia.bibliotecaeisi;


import sv.ues.eisi.fia.bibliotecaeisi.clases.Pais;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ConsultarPaisActivity extends Activity {
	ControlBaseDatos helper;
	EditText editcodigo;
	EditText editCodigopais;
	EditText editNombre;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultar_pais);
		helper = new ControlBaseDatos(this);
		editcodigo = (EditText) findViewById(R.id.editCodigo1);
		editCodigopais = (EditText) findViewById(R.id.editCodigopais);
		editNombre = (EditText) findViewById(R.id.editNombrepais);

	}

	public void consultar(View v) {
		helper.abrir();
		Pais pais = helper.consultarPais(editcodigo.getText().toString());
		helper.cerrar();
		if (pais == null)
			Toast.makeText(this,
					"Pais con codigo: " + editcodigo.getText().toString()
							+ " no encontrado", Toast.LENGTH_LONG).show();
		else {

			editNombre.setText(pais.getNombrePais());
			editCodigopais.setText(pais.getCodigoPais());

		}
	}

	public void limpiarTexto(View v) {
		editcodigo.setText("");
		editCodigopais.setText("");
		editNombre.setText("");
		

	}
}

package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.controlbase.*;
import sv.ues.eisi.fia.bibliotecaeisi.clases.*;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarPaisActivity extends Activity {

	ControlBaseDatos helper;
	EditText editCodPais;
	EditText editNombrepais;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_pais);
		helper = new ControlBaseDatos(this);
		editCodPais = (EditText) findViewById(R.id.editCodigopais);
		editNombrepais = (EditText) findViewById(R.id.editNombrepais);
	}

	public void insertarPais(View v) {

		String codpais = editCodPais.getText().toString();
		String nombre = editNombrepais.getText().toString();
		String regInsertados;
		Pais pais = new Pais();

		pais.setCodigoPais(codpais);
		pais.setNombrePais(nombre);

		helper.abrir();
		regInsertados = helper.insertar(pais);
		helper.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
	}

	public void limpiarTexto(View v) {
		editCodPais.setText("");
		editNombrepais.setText("");

	}

}


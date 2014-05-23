package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.clases.Pais;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EliminarPaisActivity extends Activity {
	EditText editcodigo;
	ControlBaseDatos controlhelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eliminar_pais);
		controlhelper = new ControlBaseDatos(this);
		editcodigo = (EditText) findViewById(R.id.editCodigopais);
	}

	public void eliminarAlumno(View v) {
		String regEliminadas;
		Pais pais = new Pais();
		pais.setCodigoPais(editcodigo.getText().toString());
		controlhelper.abrir();
		regEliminadas = controlhelper.eliminar(pais);
		controlhelper.cerrar();
		Toast.makeText(this, "cantidad de registros eliminados:"+regEliminadas, Toast.LENGTH_SHORT).show();
	}
}
package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.clases.Editorial;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EliminarEditorialActivity extends Activity {

	EditText edtxId;
	ControlBaseDatos control;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eliminar_editorial);
		control = new ControlBaseDatos(this);
		edtxId = (EditText)findViewById(R.id.editTextIdEditorial);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.eliminar_editorial, menu);
		return true;
	}

	public void eliminarEditorial(View v){
		Editorial e = new Editorial();
		e.setIdEditorial(Integer.parseInt(edtxId.getText().toString()));
		control.abrir();
		String regEliminado = control.eliminar(e);
		control.cerrar();
		Toast.makeText(this, regEliminado, Toast.LENGTH_SHORT).show();
	}
}

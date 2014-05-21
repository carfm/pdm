package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.clases.Area;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ConsultarAreaActivity extends Activity {

	ControlBaseDatos helper;
	EditText editArea;
	EditText editNombre;
	EditText editDescripcion;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultar_area);
		helper = new ControlBaseDatos(this);

		editArea = (EditText) findViewById(R.id.editArea);
		editNombre = (EditText) findViewById(R.id.editNombre);
		editDescripcion = (EditText) findViewById(R.id.editDescripcion);
	}

	public void consultarArea(View v) {

		helper.abrir();
		Area area = helper.consultarArea(Integer.parseInt(editArea.getText().toString()));

		helper.cerrar();
		if (area == null)
			Toast.makeText(this,"Area con Id: " + editArea.getText().toString()+ " no encontrado", Toast.LENGTH_LONG).show();
		else {
			editNombre.setText(area.getNombreArea());
			editDescripcion.setText(area.getDescripcion());
		}
	}

	public void limpiarTexto(View v) {
		editArea.setText("");
		editNombre.setText("");
		editDescripcion.setText("");
		
	}

}

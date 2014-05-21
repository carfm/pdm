package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.clases.Area;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActualizarAreaActivity extends Activity {

	ControlBaseDatos helper;
	EditText editArea;
	EditText editNombre;
	EditText editDescripcion;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actualizar_area);
		helper = new ControlBaseDatos(this);
		editArea=(EditText) findViewById(R.id.editArea);
		editNombre=(EditText) findViewById(R.id.editNombre);
		editDescripcion=(EditText) findViewById(R.id.editDescripcion);
	}

	public void actualizarArea(View v){
		Area area = new Area();
		area.setIdArea(Integer.parseInt(editArea.getText().toString()));
		area.setNombreArea(editNombre.getText().toString());
		area.setDescripcion(editDescripcion.getText().toString());
		
		helper.abrir();
		String estado = helper.actualizarArea(area);
		helper.cerrar();
		
		Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
	}
	
	public void limpiarTexto(View v){
		
		editArea.setText("");
		editNombre.setText("");
		editDescripcion.setText("");
		
	}
	

}

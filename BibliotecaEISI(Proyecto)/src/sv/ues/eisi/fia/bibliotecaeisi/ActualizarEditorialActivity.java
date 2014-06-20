package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.clases.Documento;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Editorial;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Pais;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import sv.ues.eisi.fia.bibliotecaeisi.controlwebservice.ControladorWebService;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActualizarEditorialActivity extends Activity {
	ControlBaseDatos BDhelper;
	ControlBaseDatos helper;
	EditText editNombreEditorial;
	EditText editId;
	Button butActualizarEditorial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actualizar_editorial);
		helper = new ControlBaseDatos(this);
		editNombreEditorial = (EditText) findViewById(R.id.editTextNombreEditorial);
		editId = (EditText) findViewById(R.id.editTextIdEditorial);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actualizar_editorial, menu);
		return true;
	}

	
	public void actualizarEditorial(View v) {
		Editorial e = new Editorial();
		e.setIdEditorial(Integer.parseInt(editId.getText().toString()));
		e.setNombreEditorial(this.editNombreEditorial.getText().toString());
		helper.abrir();
		String regInsertados = helper.actualizar(e);
		helper.cerrar();
		
/*		Gson gson = new Gson();
        //MiObjeto obj = new MiObjeto("Juan", "Madrid", null);
        String jsonString = gson.toJson(new Pais("ESA","El Salvador"));
        System.out.println("JSON: " + jsonString);*/
        ControladorWebService parser = new ControladorWebService();
        try {
			parser.sendHttpRequest(e,"sv.fia.ues.bibliotecaeisi.entidad.editorial","POST");
			//salidaHost.setText("se realizo correctamente ");
		} catch (Exception ee) {
			// TODO Auto-generated catch block
			//salidaHost.setText(ControladorWebService.informacionError);
		}
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
	}

	public void limpiarTexto(View v) {
		editId.setText("");
		editNombreEditorial.setText("");
	}

}

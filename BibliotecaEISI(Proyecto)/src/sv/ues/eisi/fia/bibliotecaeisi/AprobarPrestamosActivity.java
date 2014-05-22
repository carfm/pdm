package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.clases.Prestamo;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;

public class AprobarPrestamosActivity extends Activity {

	ControlBaseDatos helper;
	EditText editNumPrestamo;
	//EditText editIdSecretaria;
	//EditText editIdUsuario;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aprobar_prestamos);
		helper = new ControlBaseDatos(this);		
		editNumPrestamo=(EditText) findViewById(R.id.editNumPrestamo);		
	}

	public void aprobarPrestamo(View v){		
		Prestamo prest = new Prestamo();
		prest.setNumPrestamo(Integer.parseInt(editNumPrestamo.getText().toString()));
		prest.setAprobado(1);
		helper.abrir();
		String estado = helper.aprobarPrestamo(prest);
		helper.cerrar();
		Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
	}
	
	public void limpiarTexto(View v){
		editNumPrestamo.setText("");
		
	}
	
}

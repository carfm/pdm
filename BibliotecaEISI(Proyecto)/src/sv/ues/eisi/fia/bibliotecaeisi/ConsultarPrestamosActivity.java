package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.clases.Prestamo;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;

public class ConsultarPrestamosActivity extends Activity {

	ControlBaseDatos helper;
	EditText editNumPrestamo;
	EditText editIniPrestamo;
	EditText editFinPrestamo;
	EditText editNumLibros;
	EditText editEstado;
	
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultar_prestamos);
		helper=new ControlBaseDatos(this);
		
		editNumPrestamo=(EditText) findViewById(R.id.editNumPrestamo);
		editIniPrestamo=(EditText) findViewById(R.id.editIniPrestamo);
		editFinPrestamo=(EditText) findViewById(R.id.editFinPrestamo);
		editNumLibros=(EditText) findViewById(R.id.editNumLibros);
		editEstado=(EditText) findViewById(R.id.editEstado);
		
	}
	
	public void consultarPrestamo(View v) {
		helper.abrir();
		
		Prestamo prestamo =
		helper.consultarPrestamo(Integer.parseInt(editNumPrestamo.getText().toString()));
		helper.cerrar();
		if(prestamo == null)
		Toast.makeText(this, "Prestamo con Numero: " +
		editNumPrestamo.getText().toString() +
		" no encontrado", Toast.LENGTH_LONG).show();
		else{
		editIniPrestamo.setText(prestamo.getFechaPrestamo());
		editFinPrestamo.setText(prestamo.getFechaEntrega());
		editNumLibros.setText(String.valueOf(prestamo.getCantidadLibros()));
		if(prestamo.getAprobado()==1){
		editEstado.setText("Aprobado");
		}else editEstado.setText("Reprobado");
		
			}
		}
	
	public void limpiarTexto(View v){
		editNumPrestamo.setText("");
		editIniPrestamo.setText("");
		editFinPrestamo.setText("");
		editNumLibros.setText("");
		editEstado.setText("");
	}
	
}

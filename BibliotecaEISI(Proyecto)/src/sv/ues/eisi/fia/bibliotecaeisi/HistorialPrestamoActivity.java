package sv.ues.eisi.fia.bibliotecaeisi;

import java.util.ArrayList;

import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HistorialPrestamoActivity extends Activity {

	private ListView lista;
	ControlBaseDatos helper;
	private ArrayList<String> l;
	private String idUsuario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historial_prestamo);
		Bundle getDatos = getIntent().getExtras();
		idUsuario = getDatos.getString("idUsuario");
		helper = new ControlBaseDatos(this);
		helper.abrir();
		Cursor c = helper
				.consulta(
						"DetallePrestamo, Documento, Prestamo",
						"tema, fechaPrestamo,estado,aprobado,Prestamo.numeroPrestamo",
						"DetallePrestamo.idDocumento= Documento.idDocumento "
								+ "AND DetallePrestamo.numeroPrestamo = Prestamo.numeroPrestamo "
								+ "AND idUsuario='" + idUsuario
								+ "' and DetallePrestamo.estado='ENTREGADO'");
		l = new ArrayList<String>();
		// String fechaen = c.getString(2);
		// Nos aseguramos de que existe al menos un registro
		// if(fechaen!=null)
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya más registros
			do {
				String tema = c.getString(0);
				String fechaprestamo = c.getString(1);
				String estado = c.getString(2);
				String aprobado;
				if(c.getInt(3)==0){
					aprobado="NO APROBADO";
				}else{
					aprobado="APROBADO";
				}
				String num= c.getString(4);
				l.add("Numero de Prestamo:" + num
						+"\nTituto:" + tema + "\nFecha Prestamo:" + fechaprestamo
						+ "\nEstado:" + estado+ "\n" + aprobado);
			} while (c.moveToNext());
		}
		helper.cerrar();
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, l);
		lista = (ListView) findViewById(R.id.listViewHistorialPrestamo);
		lista.setAdapter(adaptador);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.historial_prestamo, menu);
		return true;
	}

}

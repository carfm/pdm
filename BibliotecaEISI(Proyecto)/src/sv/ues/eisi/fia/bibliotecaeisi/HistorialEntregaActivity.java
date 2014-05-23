package sv.ues.eisi.fia.bibliotecaeisi;

import java.util.ArrayList;

import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HistorialEntregaActivity extends Activity {

	private ListView lista;
	ControlBaseDatos helper;
	private ArrayList<String> l;
	private String idUsuario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historial_entrega);
		Bundle getDatos = getIntent().getExtras();
		idUsuario = getDatos.getString("idUsuario");
		helper = new ControlBaseDatos(this);
		helper.abrir();
		Cursor c = helper
				.consulta(
						"DetallePrestamo, Documento, Prestamo",
						"tema, fechaPrestamo,fechaEntrega",
						"DetallePrestamo.idDocumento= Documento.idDocumento " +
						"AND DetallePrestamo.numeroPrestamo = Prestamo.numeroPrestamo " +
						"AND idUsuario='"+idUsuario+"' and DetallePrestamo.estado='ENTREGADO'");
		l = new ArrayList<String>();
		// String fechaen = c.getString(2);
		// Nos aseguramos de que existe al menos un registro
		// if(fechaen!=null)
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya más registros
			do {
				String tema = c.getString(0);
				String fechaprestamo = c.getString(1);
				String fechaentrega = c.getString(2);
				l.add("Tituto:" + tema + "\nFecha Prestamo:" + fechaprestamo
						+ "\nFecha Entrega:" + fechaentrega);
			} while (c.moveToNext());
		}
		helper.cerrar();
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, l);
		lista = (ListView) findViewById(R.id.listViewConsultaPrestamo);
		lista.setAdapter(adaptador);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.consultar_autor, menu);
		return true;
	}
}

package sv.ues.eisi.fia.bibliotecaeisi;

import java.util.ArrayList;

import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ConsultarEditorialActivity extends Activity {

	private ListView lista;
	ControlBaseDatos helper;
	private ArrayList<String> l;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultar_editorial);
		helper = new ControlBaseDatos(this);
		helper.abrir();
		Cursor c = helper.consulta("Editorial", "*");
		l= new ArrayList<String>();
		// Nos aseguramos de que existe al menos un registro
		if (c.moveToFirst()) {
			// Recorremos el cursor hasta que no haya más registros
			do {
				String codigo = c.getString(0);
				String nombre = c.getString(1);
				l.add("ID:"+codigo+"\nNOMBRE EDITORIAL:"+nombre+"");
			} while (c.moveToNext());
		}
		helper.cerrar();
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1,l);
		lista = (ListView) findViewById(R.id.listViewConsultaEditorial);
		lista.setAdapter(adaptador);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.consultar_editorial, menu);
		return true;
	}
}

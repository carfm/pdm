package sv.ues.eisi.fia.bibliotecaeisi;

///holaa
import java.util.LinkedList;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Editorial;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Pais;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AgregarEditorialActivity extends Activity {

	ControlBaseDatos helper;
	EditText editNombreEditorial;
	EditText editId;
	Spinner cbxPaises;
	LinkedList<Pais> paises;
	private int posicion;
	String codigo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_editorial);
		helper = new ControlBaseDatos(this);
		editNombreEditorial = (EditText) findViewById(R.id.editTextNombreEditorial);
		editId = (EditText) findViewById(R.id.editTextIdEditorial);
		cbxPaises = (Spinner) findViewById(R.id.spinner1);
		helper.abrir();
		Cursor c = helper.consulta("Pais", "codigoPais,nombrePais", "");
		paises = new LinkedList<Pais>();
		if (c.moveToFirst()) {
			do {
				Pais p = new Pais();
				p.setCodigoPais(c.getString(0));
				p.setNombrePais(c.getString(1));
				paises.add(p);
			} while (c.moveToNext());
		}
		helper.cerrar();
		ArrayAdapter<Pais> adaptador = new ArrayAdapter<Pais>(this,
				android.R.layout.simple_spinner_item, paises);
		adaptador
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cbxPaises.setAdapter(adaptador);
		cbxPaises.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long arg3) {
				System.out.println(pos);
				posicion = pos;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		//Toast.makeText(this, posicion, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.agregar_editorial, menu);
		return true;
	}

	public void agregarEditorial(View v) {
		Editorial e = new Editorial();
		e.setIdEditorial(Integer.parseInt(editId.getText().toString()));
		e.setNombreEditorial(this.editNombreEditorial.getText().toString());
		e.setCodigoPais(paises.get(posicion).getCodigoPais());		
		helper.abrir();
		String regInsertados = helper.insertar(e);
		helper.cerrar();
		Toast.makeText(this,regInsertados, Toast.LENGTH_SHORT).show();
	}

	public void limpiarTexto(View v) {
		editId.setText("");
		editNombreEditorial.setText("");
	}
}

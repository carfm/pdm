package sv.ues.eisi.fia.bibliotecaeisi;

import java.util.ArrayList;

import sv.ues.eisi.fia.bibliotecaeisi.clases.Documento;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Prestamo;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RecibirDocumentoActivity extends Activity {

	private ListView lstListado;
	ControlBaseDatos control;
	private TextView resultados;
	private EditText busqueda;
	Prestamo p;
	ArrayList<Documento> l = new ArrayList<Documento>();
	ArrayAdapter<Documento> adaptador;
	Documento docSeleccionado;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recibir_documento);
		control = new ControlBaseDatos(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.entregar_documento, menu);
		control = new ControlBaseDatos(this);
		lstListado = (ListView) findViewById(R.id.listView1);

		lstListado.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list, View view, int pos,
					long id) {
				docSeleccionado = (Documento) list.getAdapter().getItem(pos);
				AlertDialog.Builder dialogo1 = new AlertDialog.Builder(view
						.getContext());
				dialogo1.setTitle("Entrega de libro");
				dialogo1.setMessage("¿ Libro entregado ?");
				dialogo1.setCancelable(false);
				dialogo1.setPositiveButton("Confirmar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialogo1, int id) {
								aceptar();
							}
						});
				dialogo1.setNegativeButton("Cancelar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialogo1, int id) {
								// return;
								dialogo1.cancel();
							}
						});
				dialogo1.show();
			}
		});
		return true;
	}

	public void buscar(View v) {

		resultados = (TextView) findViewById(R.id.textViewNoResultados);
		busqueda = (EditText) findViewById(R.id.editNumPrestamo);
		if (!busqueda.getText().equals("")) {

			p = new Prestamo();
			p.setNumPrestamo(Integer.parseInt(busqueda.getText().toString()));
			control.abrir();
			if (control.verificarPrestamo(p)) {
				control.cerrar();
				actualizarLista();

			} else {
				Toast.makeText(this, "No existe el id del prestamo",
						Toast.LENGTH_SHORT).show();
			}

		} else {
			Toast.makeText(this, "Campo vacio", Toast.LENGTH_SHORT).show();
		}
	}

	public void aceptar() {
		control.abrir();
		control.actualizar("DetallePrestamo", "estado='ENTREGADO'",
				"numeroPrestamo=" + p.getNumPrestamo() + " and idDocumento="
						+ docSeleccionado.getIdDocumento());
		control.cerrar();
		actualizarLista();
	}

	public void actualizarLista() {
		l.clear();
		Cursor c;
		control.abrir();
		c = control.consulta("Prestamo", "numeroPrestamo", "numeroPrestamo="
				+ p.getNumPrestamo() + " and aprobado=1");
		if (c.moveToFirst()) {
			c = control
					.consulta("DetallePrestamo p,Documento d",
							"d.idDocumento,tema,idTipoDocumento",
							"d.idDocumento=p.idDocumento and numeroPrestamo="
									+ p.getNumPrestamo()
									+ " and estado='NO ENTREGADO'");
			if (c.moveToFirst()) {
				resultados.setText("Resultados de busqueda");
				do {
					Documento d = new Documento();
					d.setIdDocumento(Integer.parseInt(c.getString(0)));
					d.setTema(c.getString(1));
					d.setIdTipoDocumento(Integer.parseInt(c.getString(2)));
					l.add(d);
				} while (c.moveToNext());
				adaptador = new ArrayAdapter<Documento>(this,
						android.R.layout.simple_list_item_1, l);
				lstListado.setAdapter(adaptador);
			} else {
				control.actualizar("Prestamo", "fechaEntrega=date('now')",
						"numeroPrestamo=" + p.getNumPrestamo());
				resultados
						.setText("El prestamo ya no tiene ningun documento pendiente");
			}
		} else {
			resultados.setText("El prestamo no ha sido aprobado aun");
		}
		control.cerrar();
	}
}

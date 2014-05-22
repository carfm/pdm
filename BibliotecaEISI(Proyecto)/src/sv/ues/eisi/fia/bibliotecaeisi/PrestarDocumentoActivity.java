package sv.ues.eisi.fia.bibliotecaeisi;

import java.util.ArrayList;

import sv.ues.eisi.fia.bibliotecaeisi.clases.DetallePrestamo;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Documento;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Prestamo;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import sv.ues.eisi.fia.bibliotecaeisi.fragments.FragmentDialogConfirmarLibros;
import sv.ues.eisi.fia.bibliotecaeisi.fragments.FragmentPrestamoDocumento;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.*;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PrestarDocumentoActivity extends FragmentActivity {

	private ArrayList<DetallePrestamo> detallesPrestamos;
	ControlBaseDatos control;
	private EditText busqueda;
	private TextView idPrestamo;
	private ListView lstListado;
	private TextView resultados;
	private Documento d;
	private Prestamo p;
	private String idUsuario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prestar_documento);
		Bundle args = new Bundle();
		args.putInt(FragmentPrestamoDocumento.fragment, 0);
		Fragment fragment = new FragmentPrestamoDocumento();
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame5, fragment).commit();
		control = new ControlBaseDatos(this);
		Bundle getDatos = getIntent().getExtras();
		idUsuario = getDatos.getString("idUsuario");
		detallesPrestamos = new ArrayList<DetallePrestamo>();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.prestar_documento, menu);
		return true;
	}

	public void agregarDocumentoDetalle(View v) {
		boolean repetido = false;
		if (this.d.getCantidadDisponible() != 0) {
			if (detallesPrestamos.isEmpty()) {
				p = new Prestamo();
				p.setIdUsuario(idUsuario);
			} else {
				// comprobando que no este repetido el documento
				for (int i = 0; i < detallesPrestamos.size(); i++) {
					DetallePrestamo d = new DetallePrestamo();
					d = detallesPrestamos.get(0);
					if (this.d.getIdDocumento() == d.getIdDocumento()) {
						repetido = true;
					}
				}
			}
			if (!repetido) {
				DetallePrestamo det = new DetallePrestamo();
				det.setIdDocumento(d.getIdDocumento());
				det.setIdDetallePrestamo(null);
				detallesPrestamos.add(det);
				Toast.makeText(this, "Documento agregado exitosamente",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this,
						"Este documento ya esta agregado a su prestamo",
						Toast.LENGTH_SHORT).show();
			}
		}else{
			Toast.makeText(this,
					"Este documento no esta disponible",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void terminarPrestamoDocumento(View v) {
		idPrestamo = (TextView) findViewById(R.id.textIdPrestamo);
		if (idPrestamo.getText().equals("")) {
			if (!detallesPrestamos.isEmpty()) {
				p.setNumPrestamo(null);
				p.setAprobado(0);
				p.setCantidadLibros(detallesPrestamos.size());
				p.setFechaEntrega("");
				p.setFechaPrestamo("14-01-12");
				p.setIdSecretaria(null);
				p.setIdPenalizacion(null);
				control.abrir();
				control.insertar(p);
				control.cerrar();
				control.abrir();
				Cursor c = control.consulta("Prestamo",
						"MAX(numeroPrestamo) as num", "");
				int num = Integer.parseInt(c.getString(0));
				p.setNumPrestamo(num);
				control.cerrar();
				for (int i = 0; i < detallesPrestamos.size(); i++) {
					control.abrir();
					detallesPrestamos.get(i).setIdPrestamo(p.getNumPrestamo());
					control.insertar(detallesPrestamos.get(i));
					control.cerrar();
				}
				idPrestamo.setText("Id de prestamo: " + p.getNumPrestamo());
				detallesPrestamos.clear();

				Toast.makeText(
						this,
						"Prestamo generado exitosamente.\nVaya donde la "
								+ "secretaria con el id de prestamo "
								+ "para su aprobacion", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(this, "No ha seleccionado ningun libro",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentDialogConfirmarLibros dialogo = new FragmentDialogConfirmarLibros();
			dialogo.show(fragmentManager, "tagAlerta");
		}
	}

	public void buscar(View v) {
		resultados = (TextView) findViewById(R.id.textViewNoResultados);
		busqueda = (EditText) findViewById(R.id.editText1);
		lstListado = (ListView) findViewById(R.id.ListadoBusquedaPrestamo);
		control.abrir();
		Cursor c = control.consulta("Documento",
				"idDocumento,tema,idTipoDocumento", "tema like '"
						+ busqueda.getText().toString() + "%'");
		ArrayList<Documento> l = new ArrayList<Documento>();
		if (c.moveToFirst()) {
			resultados.setText("Resultados de busqueda");
			do {
				Documento d = new Documento();
				d.setIdDocumento(Integer.parseInt(c.getString(0)));
				d.setTema(c.getString(1));
				d.setIdTipoDocumento(Integer.parseInt(c.getString(2)));
				l.add(d);
			} while (c.moveToNext());
		} else {
			resultados.setText("No hay resultados");
			// l.add("No hay resultados");
		}
		control.cerrar();
		ArrayAdapter<Documento> adaptador = new ArrayAdapter<Documento>(this,
				android.R.layout.simple_list_item_1, l);
		lstListado.setAdapter(adaptador);
		lstListado.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list, View view, int pos,
					long id) {
				Bundle args = new Bundle();
				args.putInt(FragmentPrestamoDocumento.fragment, 1);
				d = (Documento) list.getAdapter().getItem(pos);

				args.putInt(FragmentPrestamoDocumento.documento,
						d.getIdDocumento());
				Fragment fragment = new FragmentPrestamoDocumento();
				fragment.setArguments(args);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.content_frame5, fragment)
						.addToBackStack(null).commit();
			}
		});
	}

	protected void onPause() {
		super.onPause();
		System.out.println("Salio");
		finish(); // termina la actividad
	}
}

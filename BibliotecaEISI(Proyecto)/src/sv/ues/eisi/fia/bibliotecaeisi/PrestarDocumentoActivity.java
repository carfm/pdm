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
		if (detallesPrestamos.isEmpty()) {
			p = new Prestamo();
			control.abrir();
			Cursor c = control.consulta("Prestamo", "MAX(numeroPrestamo)", "");
			if (c.moveToFirst()) {
				p.setNumPrestamo(1);
			} else {
				p.setNumPrestamo(Integer.parseInt(c.getString(0)));
			}
			p.setIdUsuario(idUsuario);
			control.cerrar();
		}
		DetallePrestamo det = new DetallePrestamo();
		det.setIdDocumento(d.getIdDocumento());
		det.setIdPrestamo(p.getNumPrestamo());
		det.setIdDetallePrestamo(null);
		detallesPrestamos.add(det);
		
		Toast.makeText(this, "Documento agregado exitosamente",
				Toast.LENGTH_SHORT).show();
	}

	public void terminarPrestamoDocumento(View v) {
		if (!detallesPrestamos.isEmpty()) {
			p.setAprobado(0);
			p.setCantidadLibros(detallesPrestamos.size());
			p.setFechaEntrega("");
			p.setFechaPrestamo("14-01-12");
			p.setIdSecretaria(null);
			p.setIdPenalizacion(null);
			control.abrir();
			control.insertar(p);
			control.cerrar();
			for (int i = 0; i < detallesPrestamos.size(); i++) {
				control.abrir();
				control.insertar(detallesPrestamos.get(i));
				control.cerrar();
			}
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentDialogConfirmarLibros dialogo = new FragmentDialogConfirmarLibros();
			dialogo.show(fragmentManager, "tagAlerta");
		}else{
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentDialogConfirmarLibros dialogo = new FragmentDialogConfirmarLibros();
			dialogo.show(fragmentManager, "tagAlerta");
			//Toast.makeText(this,"No ha seleccionado ningun libro",Toast.LENGTH_SHORT).show();
		}

		// Toast.makeText(this,"Prestamo generado exitosamente.\nVaya donde la secretaria para su aprobacion",Toast.LENGTH_SHORT).show();
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
}

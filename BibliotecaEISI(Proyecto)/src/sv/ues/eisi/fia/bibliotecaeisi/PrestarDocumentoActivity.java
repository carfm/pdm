package sv.ues.eisi.fia.bibliotecaeisi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import sv.ues.eisi.fia.bibliotecaeisi.clases.DetallePrestamo;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Documento;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Penalizacion;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Prestamo;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import sv.ues.eisi.fia.bibliotecaeisi.fragments.FragmentDialogConfirmarLibros;
import sv.ues.eisi.fia.bibliotecaeisi.fragments.FragmentPrestamoDocumento;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.*;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
	private Button buscar;
	private Documento d;
	private Prestamo p;
	private String idUsuario;
	static final int check = 1111;

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
		boolean sinPenalizacion = true;
		Penalizacion pena;
		control.abrir();
		Cursor c = control
				.consulta(
						"Prestamo",
						"numeroPrestamo,fechaEntrega,idPenalizacion,date('now') as fecha",
						"idUsuario='" + idUsuario
								+ "' and idPenalizacion is not null");
		if (c.moveToFirst()) {
			String fEntrega[];
			Calendar ca = Calendar.getInstance();
			Calendar fechaInicio;
			Calendar fechaFin = new GregorianCalendar();
			// fechaFin.set(2014,5,31);
			fEntrega = c.getString(3).split("-");
			fechaFin.set(Integer.parseInt(fEntrega[0]),
					Integer.parseInt(fEntrega[1]),
					Integer.parseInt(fEntrega[2]));
			do {
				pena = control.consultarPenalizacion(c.getInt(2));
				fEntrega = c.getString(1).split("-");
				// fecha inicio
				fechaInicio = new GregorianCalendar();
				fechaInicio.set(Integer.parseInt(fEntrega[0]),
						Integer.parseInt(fEntrega[1]),
						Integer.parseInt(fEntrega[2]));
				ca.setTimeInMillis(fechaFin.getTime().getTime()
						- fechaInicio.getTime().getTime());
				int dias = ca.get(Calendar.DAY_OF_YEAR);

				if (dias <= pena.getDiasPenalizacion() && dias != 365) {
					sinPenalizacion = false;
				}
			} while (c.moveToNext());
		}
		if (sinPenalizacion) {
			control.cerrar();
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
					det.setEstado("NO ENTREGADO");
					detallesPrestamos.add(det);
					Toast.makeText(this, "Documento agregado exitosamente",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this,
							"Este documento ya esta agregado a su prestamo",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(this, "Este documento no esta disponible",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(
					this,
					"Tiene penalizacion por entrega tardia no puede prestar libros",
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
				p.setFechaPrestamo("");
				p.setIdSecretaria(null);
				p.setIdPenalizacion(null);
				control.abrir();
				control.insertar(p);
				control.cerrar();
				control.abrir();
				String parametros = "numeroprestamo=NULL" +
						"&idusuario="+p.getIdUsuario() +
						"&idpenalizacion=NULL" +
						"&idsecretaria=NULL" +
						"&fechaprestamo="+p.getFechaPrestamo() +
						"&fechaentrega=NULL" +
						"&cantidadlibros="+p.getCantidadLibros() +
						"&aprobado=0";
				Toast.makeText(
						this,
						control.insertarWS(parametros), Toast.LENGTH_SHORT)
						.show();
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

				/*Toast.makeText(
						this,
						"Prestamo generado exitosamente.\nVaya donde la "
								+ "secretaria con el id de prestamo "
								+ "para su aprobacion", Toast.LENGTH_SHORT)
						.show();*/
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
				"idDocumento,tema,idTipoDocumento,cantidadDisponible",
				"tema like '" + busqueda.getText().toString() + "%'");
		ArrayList<Documento> l = new ArrayList<Documento>();
		if (c.moveToFirst()) {
			resultados.setText("Resultados de busqueda");
			do {
				Documento d = new Documento();
				d.setIdDocumento(Integer.parseInt(c.getString(0)));
				d.setTema(c.getString(1));
				d.setIdTipoDocumento(Integer.parseInt(c.getString(2)));
				d.setCantidadDisponible(Integer.parseInt(c.getString(3)));
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
	public void busquedaSpeech(View v){
		//if (v.getId() == R.id.bvoice) {
			// Si entramos a dar clic en el boton
			Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
					RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hable ahora ");
			startActivityForResult(i, check);
		//}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		lstListado = (ListView) findViewById(R.id.ListadoBusquedaPrestamo);
		if (requestCode == check && resultCode == RESULT_OK) {
			ArrayList<String> results = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			lstListado.setAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, results));
			lstListado.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> list, View view, int pos,
						long id) {
					busqueda = (EditText) findViewById(R.id.editText1);
					buscar = (Button)findViewById(R.id.button1);
					String s;
					s = (String) list.getAdapter().getItem(pos);
					busqueda.setText(s);
					buscar.performClick();
				}
			});
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/*protected void onPause() {
		super.onPause();
		System.out.println("Salio");
		finish(); // termina la actividad
	}*/
}

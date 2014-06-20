package sv.ues.eisi.fia.bibliotecaeisi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import sv.ues.eisi.fia.bibliotecaeisi.clases.DetallePrestamo;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Documento;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Penalizacion;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Prestamo;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import sv.ues.eisi.fia.bibliotecaeisi.especiales.LibreriasEspeciales;
import sv.ues.eisi.fia.bibliotecaeisi.fragments.FragmentDialogConfirmarLibros;
import sv.ues.eisi.fia.bibliotecaeisi.fragments.FragmentPrestamoDocumento;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
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
	ArrayList<Documento> documentos;
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
		documentos = new ArrayList<Documento>();
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
					documentos.add(d);
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
				Cursor c = control.consulta("Prestamo",
						"MAX(numeroPrestamo) as num,fechaprestamo", "");
				int num = Integer.parseInt(c.getString(0));
				p.setFechaPrestamo(c.getString(1));
				p.setNumPrestamo(num);
				String parametros = "numeroprestamo=NULL" + "&idusuario="
						+ p.getIdUsuario() + "&idpenalizacion=NULL"
						+ "&idsecretaria=NULL" + "&fechaprestamo="
						+ p.getFechaPrestamo() + "&fechaentrega=NULL"
						+ "&cantidadlibros=" + p.getCantidadLibros()
						+ "&aprobado=0";
				Toast.makeText(this, control.insertarWS(parametros),
						Toast.LENGTH_SHORT).show();				
				control.cerrar();
				for (int i = 0; i < detallesPrestamos.size(); i++) {
					control.abrir();
					detallesPrestamos.get(i).setIdPrestamo(p.getNumPrestamo());
					control.insertar(detallesPrestamos.get(i));
					control.cerrar();
				}
				idPrestamo.setText("Id de prestamo: " + p.getNumPrestamo());
				detallesPrestamos.clear();
				LibreriasEspeciales.reproducirAudio(getApplicationContext(), 1);
				/*
				 * Toast.makeText( this,
				 * "Prestamo generado exitosamente.\nVaya donde la " +
				 * "secretaria con el id de prestamo " + "para su aprobacion",
				 * Toast.LENGTH_SHORT) .show();
				 */
				// preguntamos si queremos imprimir
				AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
				dialogo1.setTitle("Imprimir hoja de prestamo");
				dialogo1.setMessage("¿ Desea imprimir la hoja de este prestamo ?");
				dialogo1.setCancelable(false);
				dialogo1.setPositiveButton("Si",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialogo1, int id) {
								aceptar();// si
								// preparamos el print Manager
							}
						});
				dialogo1.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialogo1, int id) {
								// return;// no
							}
						});
				dialogo1.show();
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

	@TargetApi(19)
	@SuppressLint("NewApi")
	public void aceptar() {
		// Print Manager
		PrintManager printManager = (PrintManager) this
				.getSystemService(Context.PRINT_SERVICE);
		// creamos el nombre del archivo
		String jobName = this.getString(R.string.app_name) + " Prestamo "
				+ this.p.getNumPrestamo();
		// comenzamos el proceso de impresion
		printManager.print(jobName, new MyPrintDocumentAdapter(this, p,
				documentos), null);
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

	public void busquedaSpeech(View v) {
		// if (v.getId() == R.id.bvoice) {
		// Si entramos a dar clic en el boton
		Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hable ahora ");
		startActivityForResult(i, check);
		// }
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
				public void onItemClick(AdapterView<?> list, View view,
						int pos, long id) {
					busqueda = (EditText) findViewById(R.id.editText1);
					buscar = (Button) findViewById(R.id.button1);
					String s;
					s = (String) list.getAdapter().getItem(pos);
					busqueda.setText(s);
					buscar.performClick();
				}
			});
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/*
	 * protected void onPause() { super.onPause(); System.out.println("Salio");
	 * finish(); // termina la actividad }
	 */
	// Clase adpater para poder imprimir

	@SuppressLint("NewApi")
	public class MyPrintDocumentAdapter extends PrintDocumentAdapter {
		Context context;
		private int pageHeight;
		private int pageWidth;
		public PdfDocument myPdfDocument;
		public int totalpages = 1;
		Prestamo prestamo;
		ArrayList<Documento> documentos;

		public MyPrintDocumentAdapter(Context context, Prestamo p,
				ArrayList<Documento> docs) {
			this.context = context;
			prestamo = p;
			documentos = docs;
		}

		@Override
		public void onLayout(PrintAttributes oldAttributes,
				PrintAttributes newAttributes,
				CancellationSignal cancellationSignal,
				LayoutResultCallback callback, Bundle metadata) {

			myPdfDocument = new PrintedPdfDocument(context, newAttributes);
			// medidas de las paginas
			pageHeight = newAttributes.getMediaSize().getHeightMils() / 1000 * 72;
			pageWidth = newAttributes.getMediaSize().getWidthMils() / 1000 * 72;

			if (cancellationSignal.isCanceled()) {
				callback.onLayoutCancelled();
				return;
			}

			if (totalpages > 0) {
				PrintDocumentInfo.Builder builder = new PrintDocumentInfo.Builder(
						"print_output.pdf").setContentType(
						PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).setPageCount(
						totalpages);

				PrintDocumentInfo info = builder.build();
				callback.onLayoutFinished(info, true);
			} else {
				callback.onLayoutFailed("Page count is zero.");
			}
		}

		@Override
		public void onWrite(final PageRange[] pageRanges,
				final ParcelFileDescriptor destination,
				final CancellationSignal cancellationSignal,
				final WriteResultCallback callback) {

			for (int i = 0; i < totalpages; i++) {
				if (pageInRange(pageRanges, i)) {
					PageInfo newPage = new PageInfo.Builder(pageWidth,
							pageHeight, i).create();

					PdfDocument.Page page = myPdfDocument.startPage(newPage);

					if (cancellationSignal.isCanceled()) {
						callback.onWriteCancelled();
						myPdfDocument.close();
						myPdfDocument = null;
						return;
					}
					drawPage(page, i);
					myPdfDocument.finishPage(page);
				}
			}

			try {
				myPdfDocument.writeTo(new FileOutputStream(destination
						.getFileDescriptor()));
			} catch (IOException e) {
				callback.onWriteFailed(e.toString());
				return;
			} finally {
				myPdfDocument.close();
				myPdfDocument = null;
			}

			callback.onWriteFinished(pageRanges);
		}

		private boolean pageInRange(PageRange[] pageRanges, int page) {
			for (int i = 0; i < pageRanges.length; i++) {
				if ((page >= pageRanges[i].getStart())
						&& (page <= pageRanges[i].getEnd()))
					return true;
			}
			return false;
		}

		private void drawPage(PdfDocument.Page page, int pagenumber) {
			Canvas canvas = page.getCanvas();

			pagenumber++; // Make sure page numbers start at 1

			int titleBaseLine = 72;
			int leftMargin = 54;

			Paint paint = new Paint();
			paint.setColor(Color.BLACK);
			paint.setTextSize(40);

			canvas.drawText("Hoja de Prestamo # "+ prestamo.getNumPrestamo(), leftMargin, titleBaseLine,
					paint);

			paint.setTextSize(14);

			canvas.drawText("Carnet de estudiante: " + prestamo.getIdUsuario(),
					leftMargin, titleBaseLine + 35, paint);
			canvas.drawText("Fecha prestamo: " + prestamo.getFechaPrestamo(),
					leftMargin, titleBaseLine + 70, paint);

			canvas.drawText("Cantidad Libros: " + prestamo.getCantidadLibros(),
					leftMargin, titleBaseLine + 105, paint);
			canvas.drawText("Libros prestados: ", leftMargin,
					titleBaseLine + 140, paint);
			int base = titleBaseLine + 140;
			for (int i = 0; i < documentos.size(); i++) {
				Documento d = documentos.get(0);
				base = base + 35;
				canvas.drawText("-" + d.getTema(), leftMargin, base, paint);
			}

			if (pagenumber % 2 == 0)
				paint.setColor(Color.RED);
			else
				paint.setColor(Color.GREEN);

			// PageInfo pageInfo = page.getInfo();

			// canvas.drawCircle(pageInfo.getPageWidth() / 2,
			// pageInfo.getPageHeight() / 2, 150, paint);
		}
	}
}

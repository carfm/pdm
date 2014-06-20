package sv.ues.eisi.fia.bibliotecaeisi;

import java.net.URLEncoder;

import sv.ues.eisi.fia.bibliotecaeisi.clases.Documento;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActualizarDocumentoActivity extends Activity {
	ControlBaseDatos control;
	ControlBaseDatos BDhelper;

	EditText edIdDocumento;
	EditText edIdEditorial;
	EditText edTipoDocumento;
	EditText edTema;
	EditText edDescripcion;
	EditText edAnio;
	EditText edPaginas;
	EditText edCantidad;
	EditText edEdicion;
	Button btnActualizar;

    String a;
    private static String urlHostingActualiza = "actualizar_doc.php?";
    private static String urlHosting = "http://pdm115.freeiz.com/";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actualizar_documento);
		BDhelper = new ControlBaseDatos(this);
		edIdDocumento = (EditText) findViewById(R.id.edIdDocumento);
		edIdEditorial = (EditText) findViewById(R.id.edIdEditorial);
		edTipoDocumento = (EditText) findViewById(R.id.edTipoDocumento);
		edTema = (EditText) findViewById(R.id.edTema);
		edDescripcion = (EditText) findViewById(R.id.edDescripcion);
		edAnio = (EditText) findViewById(R.id.edAnio);
		edPaginas = (EditText) findViewById(R.id.edNumPag);
		edCantidad = (EditText) findViewById(R.id.edCantidad);
		edEdicion = (EditText) findViewById(R.id.edEdicion);
		btnActualizar = (Button) findViewById(R.id.btnActualizar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actualizar_documento, menu);
		control = new ControlBaseDatos(this);
		return true;
	}

	public void ExtraerInfo(View v) {
		BDhelper.abrir();
		Documento documento = BDhelper.consultarDocumento(edIdDocumento
				.getText().toString());
		BDhelper.cerrar();
		if (documento == null) {
			Toast.makeText(
					this,
					"Documento con Id: " + edIdDocumento.getText().toString()
							+ " no encontrado", Toast.LENGTH_LONG).show();
			btnActualizar.setEnabled(false);
		} else {
			edIdEditorial.setText(String.valueOf(documento.getIdEditorial()));
			edTipoDocumento.setText(String.valueOf(documento
					.getIdTipoDocumento()));
			edTema.setText(documento.getTema());
			edDescripcion.setText(documento.getDescripcion());
			edAnio.setText(documento.getAnio());
			edPaginas.setText(documento.getNumeroPagina());
			edCantidad
					.setText(String.valueOf(documento.getCantidadDisponible()));
			edEdicion.setText(String.valueOf(documento.getEdicion()));
			btnActualizar.setEnabled(true);
			edIdDocumento.setEnabled(false);
		}
	}

	public void ActualizaDocumento(View v) {
		Documento documento = new Documento();
		documento.setIdDocumento(Integer.valueOf(edIdDocumento.getText()
				.toString()));
		documento.setIdEditorial(Integer.valueOf(edIdEditorial.getText()
				.toString()));
		documento.setIdTipoDocumento(Integer.valueOf(edTipoDocumento.getText()
				.toString()));
		documento.setTema(edTema.getText().toString());
		documento.setDescripcion(edDescripcion.getText().toString());
		documento.setAnio(edAnio.getText().toString());
		documento.setNumeroPagina(edPaginas.getText().toString());
		documento.setCantidadDisponible(Integer.valueOf(edCantidad.getText()
				.toString()));
		documento.setEdicion(Integer.valueOf(edEdicion.getText().toString()));

		documento.setIdDocumento(Integer.parseInt(edIdDocumento.getText().toString()));
		documento.setIdEditorial(Integer.parseInt(edIdEditorial.getText().toString()));
		documento.setIdTipoDocumento(Integer.parseInt(edTipoDocumento.getText().toString()));
		documento.setTema(String.valueOf(edTema.getText().toString()));
		documento.setDescripcion(String.valueOf(edDescripcion.getText().toString()));
		documento.setAnio(String.valueOf(edAnio.getText().toString()));
		documento.setNumeroPagina(String.valueOf(edPaginas.getText().toString()));
		documento.setCantidadDisponible(Integer.parseInt(edCantidad.getText().toString()));
		documento.setEdicion(Integer.parseInt(edEdicion.getText().toString()));
		
		BDhelper.abrir();
		String estado = BDhelper.actualizar(documento);
		BDhelper.cerrar();
		BDhelper.insertarWS("iddocumento=" + documento.getIdDocumento()
				+ "&ideditorial=" + documento.getIdEditorial()
				+ "&idtipodocumento=" + documento.getIdTipoDocumento()
				+ "&tema=" + documento.getTema() + "&descripcion="
				+ documento.getDescripcion() + "&anio=" + documento.getAnio()
				+ "&numeropaginas=" + documento.getNumeroPagina()
				+ "&cantidaddisponible=" + documento.getCantidadDisponible()
				+ "&edicion=" + documento.getEdicion());	   
		@SuppressWarnings("deprecation")
		String parametros = urlHosting +urlHostingActualiza+"iddocumento="+documento.getIdDocumento() +
             	"&ideditorial="+documento.getIdEditorial() +
                "&idtipodocumento="+documento.getIdTipoDocumento() +
                "&tema="+URLEncoder.encode(documento.getTema()) +
                "&descripcion="+URLEncoder.encode(documento.getDescripcion()) +
                "&anio="+URLEncoder.encode(documento.getAnio()) +
                "&numeropaginas="+URLEncoder.encode(documento.getNumeroPagina()) +
                 "&cantidaddisponible="+documento.getCantidadDisponible() +
                 "&edicion="+documento.getEdicion();
		int resul=Integer.parseInt(control.WSactualiza(parametros));
		if(resul==1){
			Toast.makeText(	this,"Actualizado con exito en webhost ", Toast.LENGTH_SHORT).show();
			}
		//Toast.makeText(	this,"NO Actualizado en webhost ", Toast.LENGTH_SHORT).show();
		Toast.makeText(this, estado+"se actualizo en sqlite", Toast.LENGTH_SHORT).show();
	
	}

	public void LimpiarAct(View v) {
		edIdDocumento.setText("");
		edIdEditorial.setText("");
		edTipoDocumento.setText("");
		edTema.setText("");
		edDescripcion.setText("");
		edAnio.setText("");
		edPaginas.setText("");
		edCantidad.setText("");
		edEdicion.setText("");
		btnActualizar.setEnabled(false);
		edIdDocumento.setEnabled(true);
	}
}

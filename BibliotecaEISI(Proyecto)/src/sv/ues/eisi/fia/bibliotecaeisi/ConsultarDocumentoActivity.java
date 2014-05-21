package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Documento;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ConsultarDocumentoActivity extends Activity {

	ControlBaseDatos BDhelper;
	EditText edtIdDocumento;
	EditText edtIdEditorial;
	EditText edtTipoDocumento;
	EditText edtTema;
	EditText edtDescripcion;
	EditText edtAnio;
	EditText edtPaginas;
	EditText edtCantidad;
	EditText edtEdicion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultar_documento);
		BDhelper = new ControlBaseDatos(this);
		edtIdDocumento = (EditText) findViewById(R.id.edtIdDocumento);
		edtIdEditorial = (EditText) findViewById(R.id.edtEditorial);
		edtTipoDocumento = (EditText) findViewById(R.id.editTipoDocumento);
		edtTema = (EditText) findViewById(R.id.edtTema);
		edtDescripcion = (EditText) findViewById(R.id.edtDescripcion);
		edtAnio = (EditText) findViewById(R.id.edtAnio);
		edtPaginas = (EditText) findViewById(R.id.edtPagina);
		edtCantidad = (EditText) findViewById(R.id.edtCantidad);
		edtEdicion = (EditText) findViewById(R.id.edtEdicion);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.consultar_documento, menu);
		return true;
	}

	public void ConsultarDocumento(View v) {
		BDhelper.abrir();
		Documento documento = BDhelper.consultarDocumento(edtIdDocumento
				.getText().toString());
		BDhelper.cerrar();
		if (documento == null)
			Toast.makeText(
					this,
					"Documento con Id: " + edtIdDocumento.getText().toString()
							+ " no encontrado", Toast.LENGTH_LONG).show();
		else {
			edtIdEditorial.setText(String.valueOf(documento.getIdEditorial()));
			edtTipoDocumento.setText(String.valueOf(documento
					.getIdTipoDocumento()));
			edtTema.setText(documento.getTema());
			edtDescripcion.setText(documento.getDescripcion());
			edtAnio.setText(documento.getAnio());
			edtPaginas.setText(documento.getNumeroPagina());
			edtCantidad.setText(String.valueOf(documento
					.getCantidadDisponible()));
			edtEdicion.setText(String.valueOf(documento.getEdicion()));
		}
	}

	public void LimpiarEdits(View v) {
		edtIdEditorial.setText("");
		edtTipoDocumento.setText("");
		edtTema.setText("");
		edtDescripcion.setText("");
		edtAnio.setText("");
		edtPaginas.setText("");
		edtCantidad.setText("");
		edtEdicion.setText("");
	}
}

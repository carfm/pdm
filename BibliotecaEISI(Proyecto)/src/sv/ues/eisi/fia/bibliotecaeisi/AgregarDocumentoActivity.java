package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Documento;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarDocumentoActivity extends Activity {

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
		setContentView(R.layout.activity_agregar_documento);
		BDhelper = new ControlBaseDatos(this);
		edtIdDocumento = (EditText)findViewById(R.id.editIdDocumento);
		edtIdEditorial=(EditText)findViewById(R.id.editIdEditorial);
		edtTipoDocumento=(EditText)findViewById(R.id.editTipoDocumento);
		edtTema = (EditText)findViewById(R.id.editTema);
		edtDescripcion=(EditText)findViewById(R.id.editDescripcion);
		edtAnio=(EditText)findViewById(R.id.editAnio);
		edtPaginas=(EditText)findViewById(R.id.editNumero);
		edtCantidad=(EditText)findViewById(R.id.editCantidad);
		edtEdicion=(EditText)findViewById(R.id.editEdicion);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.agregar_documento, menu);
		return true;
	}
	
	public void insertarDocumento(View v){
		String regInsertados;
		if( !edtIdDocumento.getText().toString().equals("") && !edtIdEditorial.getText().toString().equals("")
				&& !edtTipoDocumento.getText().toString().equals("") && !edtTema.getText().toString().equals("")
				&& !edtDescripcion.getText().toString().equals("") && !edtAnio.getText().toString().equals("")
				&& !edtPaginas.getText().toString().equals("") && !edtCantidad.getText().toString().equals("")
				&& !edtEdicion.getText().toString().equals("")){
		int documento = (int)Integer.valueOf(edtIdDocumento.getText().toString());
		int editorial = (int)Integer.valueOf(edtIdEditorial.getText().toString());
		int tipo = (int)Integer.valueOf(edtTipoDocumento.getText().toString());
		String tema = edtTema.getText().toString();
		String descripcion = edtDescripcion.getText().toString();
		String anio = edtAnio.getText().toString();
		String paginas = edtPaginas.getText().toString();
		int cantidad = (int)Integer.valueOf(edtCantidad.getText().toString());
		int edicion = (int)Integer.valueOf(edtEdicion.getText().toString());
		Documento docu = new Documento();
		docu.setIdDocumento(documento);
		docu.setIdEditorial(editorial);
		docu.setIdTipoDocumento(tipo);
		docu.setTema(tema);
		docu.setDescripcion(descripcion);
		docu.setAnio(anio);
		docu.setNumeroPagina(paginas);
		docu.setCantidadDisponible(cantidad);
		docu.setEdicion(edicion);
		BDhelper.abrir();
		regInsertados=BDhelper.insertar(docu);
		BDhelper.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this, "Campos vacios. Debe llenar todos los campos.",Toast.LENGTH_LONG).show();
		}
	}
	
	public void Limpiarfields(View v){
		edtIdDocumento.setText("");
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

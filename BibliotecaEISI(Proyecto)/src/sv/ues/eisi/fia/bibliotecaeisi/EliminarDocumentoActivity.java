package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Documento;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;

public class EliminarDocumentoActivity extends Activity {

	ControlBaseDatos BDhelper;
    EditText eIdDocumento;
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eliminar_documento);
		BDhelper = new ControlBaseDatos(this);
		eIdDocumento = (EditText)findViewById(R.id.eEliminarIdDocumento);
	    
	}
	
	public void EliminarDocumento(View v){
		String regEliminadas;
		Documento documento=new Documento();
		documento.setIdDocumento(Integer.valueOf(eIdDocumento.getText().toString()));
		
		BDhelper.abrir();
		regEliminadas=BDhelper.eliminar(documento);
		BDhelper.cerrar();
		Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show();
	}
	
	public void LimpiarField(View v){
		eIdDocumento.setText("");
	}
}

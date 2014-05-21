package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.clases.Area;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarAreaActivity extends Activity {
	
	ControlBaseDatos helper;
	EditText editArea;
	EditText editNombre;
	EditText editDescripcion;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_area);
		helper = new ControlBaseDatos(this);
		editArea=(EditText) findViewById(R.id.editArea);
		editNombre=(EditText) findViewById(R.id.editNombre);
		editDescripcion=(EditText) findViewById(R.id.editDescripcion);
	}

	public void agregarArea(View v) { 
		 Integer idAre=Integer.parseInt(editArea.getText().toString()); 
		  String nombreAre=editNombre.getText().toString(); 
		  String descrip=editDescripcion.getText().toString();  
		  String regInsertados;   
		    Area area=new Area(); 
		    area.setIdArea(idAre); 
		    area.setNombreArea(nombreAre); 
		    area.setDescripcion(descrip); 
		        
		    helper.abrir(); 
		    regInsertados=helper.agregarArea(area); 
		    helper.cerrar(); 
		    Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
	}
	
	public void limpiarTexto(View v) {
		editArea.setText("");
		editNombre.setText("");
		editDescripcion.setText("");
		
	}
	
}

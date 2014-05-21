package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.clases.Area;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EliminarAreaActivity extends Activity {

	EditText editArea;
	ControlBaseDatos controlhelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eliminar_area);
		controlhelper=new ControlBaseDatos(this);
		editArea=(EditText) findViewById(R.id.editArea);
	}

	 public void eliminarArea(View v){ 
	      String regEliminadas; 
	      Area area=new Area(); 
	      area.setIdArea(Integer.parseInt(editArea.getText().toString())); 
	      controlhelper.abrir(); 
	      regEliminadas=controlhelper.eliminarArea(area); 
	      controlhelper.cerrar(); 
	      Toast.makeText(this, regEliminadas, Toast.LENGTH_SHORT).show(); 
	    } 

}

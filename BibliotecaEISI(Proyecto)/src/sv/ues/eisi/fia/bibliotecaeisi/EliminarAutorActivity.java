package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.clases.Autor;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EliminarAutorActivity extends Activity {
	EditText editIdautor;
	ControlBaseDatos controlhelper;
	private static String urlHostingEliminar = "ws_eliminar_autor.php?";
    private static String urlHosting = "http://pdm115.freeiz.com/";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eliminar_autor);
		controlhelper = new ControlBaseDatos(this);
		editIdautor = (EditText) findViewById(R.id.editIdautor);
	}

	public void eliminarAlumno(View v) {
		String regEliminadas;
		String respuesta="Registro borrado";
		Autor autor = new Autor();
		autor.setIdAutor(Integer.parseInt(editIdautor.getText().toString()));
		controlhelper.abrir();
		regEliminadas = controlhelper.eliminar(autor);
		controlhelper.cerrar();
		
		if(regEliminadas.equals(respuesta)){
			String parametros=urlHosting+urlHostingEliminar+"idautor="+autor.getIdAutor();
			int resul=Integer.parseInt(controlhelper.WSeliminar(parametros));
			if(resul==1){
	     Toast.makeText(this,"registro eliminado exitosamente!!!", Toast.LENGTH_SHORT).show();
	     }else { Toast.makeText(this,"error al eliminar registro!!!", Toast.LENGTH_SHORT).show();}
			}

		Toast.makeText(this, "cantidad de registros eliminados:"+regEliminadas, Toast.LENGTH_SHORT).show();
	}
}

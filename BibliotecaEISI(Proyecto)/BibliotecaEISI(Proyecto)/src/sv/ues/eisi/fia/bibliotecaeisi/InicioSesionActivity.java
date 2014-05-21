package sv.ues.eisi.fia.bibliotecaeisi;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class InicioSesionActivity extends Activity {

	String[] menu = { "Tabla Alumno", "Tabla Nota", "Tabla Materia",
	"LLenar Base de Datos" };
	String[] activities = { "AlumnoMenuActivity", "NotaMenuActivity" };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio_sesion);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inicio_sesion, menu);
		return true;
	}

	public void iniciarSesion(View v){
		try {
			Class<?> clase = Class.forName("sv.ues.eisi.fia.bibliotecaeisi.MenuSecretariaActivity");
			Intent inte = new Intent(this, clase);
			this.startActivity(inte);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

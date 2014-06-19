package sv.ues.eisi.fia.bibliotecaeisi;

import java.io.File;
import sv.ues.eisi.fia.bibliotecaeisi.especiales.LibreriasEspeciales;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class TomarFotoDocumentoActivity extends Activity {

	private final String ruta_fotos = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
			+ "/bibliotecaeisi/";
	private File file = new File(ruta_fotos);
	private Button boton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tomar_foto);
		boton = (Button) findViewById(R.id.btnTomaFoto);
		// Si no existe crea la carpeta donde se guardaran las fotos
		file.mkdirs();
		// accion para el boton
		boton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// llamamos a la funcion que nos permite tomar la foto y guardarla luego retornamos
				startActivityForResult(LibreriasEspeciales.tomarFoto(), 0);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tomar_foto, menu);
		return true;
	}

}

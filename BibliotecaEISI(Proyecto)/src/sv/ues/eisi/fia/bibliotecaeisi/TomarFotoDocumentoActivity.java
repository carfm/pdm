package sv.ues.eisi.fia.bibliotecaeisi;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import sv.ues.eisi.fia.bibliotecaeisi.especiales.LibreriasEspeciales;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
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
/*				String file = ruta_fotos + getCode() + ".jpg";
				File mi_foto = new File(file);
				try {
					mi_foto.createNewFile();
				} catch (IOException ex) {
					Log.e("ERROR ", "Error:" + ex);
				}
				//
				Uri uri = Uri.fromFile(mi_foto);
				// Abre la camara para tomar la foto
				Intent cameraIntent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				// Guarda imagen
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				// Retorna a la actividad
*/				startActivityForResult(LibreriasEspeciales.tomarFoto(), 0);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tomar_foto, menu);
		return true;
	}

	@SuppressLint("SimpleDateFormat")
	  private String getCode()
	  {
	   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
	   String date = dateFormat.format(new Date() );
	   String photoCode = "pic_" + date;  
	   return photoCode;
	  }
}

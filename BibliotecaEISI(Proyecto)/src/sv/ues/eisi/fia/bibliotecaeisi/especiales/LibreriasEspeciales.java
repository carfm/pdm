package sv.ues.eisi.fia.bibliotecaeisi.especiales;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import sv.ues.eisi.fia.bibliotecaeisi.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public final class LibreriasEspeciales {
	
	private final static String ruta_fotos = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
			+ "/bibliotecaeisi/";
	//private File file = new File(ruta_fotos);

	public static void reproducirAudio(Context ctx, int id) {
		MediaPlayer Media;
		Media = MediaPlayer.create(ctx, R.raw.arch1);
		Media.start();
	}
	
	public static Intent tomarFoto(){
		String file = ruta_fotos + getCode() + ".jpg";
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
		return cameraIntent;		
	}
	
	@SuppressLint("SimpleDateFormat")
	private static String getCode()
	  {
	   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
	   String date = dateFormat.format(new Date() );
	   String photoCode = "pic_" + date;  
	   return photoCode;
	  }
	
}

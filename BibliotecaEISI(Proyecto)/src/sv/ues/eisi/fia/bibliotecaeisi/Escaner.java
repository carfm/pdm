package sv.ues.eisi.fia.bibliotecaeisi;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class Escaner extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imprimir_hoja_prestamo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.imprimir_hoja_prestamo, menu);
		return true;
	}

	public void Scan(View v) {
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		startActivityForResult(intent, 0);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contenido = intent.getStringExtra("SCAN_RESULT");
				Intent resultado = new Intent(this, ResultadoActivity.class);
				resultado.putExtra("caracteres_leidos", contenido);
				startActivity(resultado);
				// Hacer algo con los datos obtenidos.
			} else if (resultCode == RESULT_CANCELED) {
				// Si se cancelo la captura.
				Toast.makeText(this, "Se cancelo el ESCANEO", Toast.LENGTH_LONG)
						.show();
			}
		}
	}

}

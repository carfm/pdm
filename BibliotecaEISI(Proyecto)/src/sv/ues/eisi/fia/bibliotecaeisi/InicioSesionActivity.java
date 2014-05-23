package sv.ues.eisi.fia.bibliotecaeisi;


import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Usuario;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InicioSesionActivity extends Activity {

	
	String edtContrasenia;
	EditText editTextUser;
	EditText editTextPass;
	String[] menu = { "Tabla Alumno", "Tabla Nota", "Tabla Materia",
			"LLenar Base de Datos" };
	String[] activities = { "AlumnoMenuActivity", "NotaMenuActivity" };
	ControlBaseDatos BDhelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio_sesion);
		BDhelper = new ControlBaseDatos(this);
		editTextUser = (EditText)findViewById(R.id.editText1);
		editTextPass = (EditText)findViewById(R.id.editText2);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inicio_sesion, menu);
		return true;
	}

	public void iniciarSesion(View v) {
		try {
			Intent inte = new Intent(this, MenuSecretariaActivity.class);
			this.startActivity(inte);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void iniciarSesionUsuario(View v) {
		try {
			Intent inte = new Intent(this, MenuUsuarioActivity.class);
			this.startActivity(inte);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void inicio(View v) {
		BDhelper.abrir();
		String tost = BDhelper.llenarBase();
		this.BDhelper.cerrar();
		Toast.makeText(this, tost, Toast.LENGTH_SHORT).show();
	}
	
	@SuppressLint("DefaultLocale")
	public void Verificar(View v){
		
		String edtCarnet = editTextUser.getText().toString();
		//Toast.makeText(this, ":"+edtCarnet+"", Toast.LENGTH_SHORT).show();
		if(!edtCarnet.equals("") || !editTextPass.getText().toString().equals("")){

		BDhelper.abrir();
		Usuario usuario = BDhelper.consultarUsuario(edtCarnet.toUpperCase());
		BDhelper.cerrar();
		if(usuario == null){
		  Toast.makeText(this, "El usuario NO existe", Toast.LENGTH_LONG).show();
		}
		else{
			String pass = usuario.getContrasenia();/* de la BASE */
			edtContrasenia = editTextPass.getText().toString();/* del Edit ingresado para comparar el de la BASE */
			if(edtContrasenia.equals(pass)){
				//Toast.makeText(this, "Si coincide", Toast.LENGTH_LONG).show();
				String tipoUser = usuario.getTipo();
				if(tipoUser.equals("alumno")){ /*Abriendo el Menu Alumno*/
					try {
						Intent inte = new Intent(this, MenuUsuarioActivity.class);
						inte.putExtra("NombreAlu", usuario.getNombreUsuario()+" "+usuario.getApellidoUsuario());
						inte.putExtra("idUser", usuario.getIdUsuario());
						this.startActivity(inte);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else{ /*Abriendo el Menu Secretaria*/
					try {
						Intent inte = new Intent(this, MenuSecretariaActivity.class);
						inte.putExtra("NombreSecre", usuario.getNombreUsuario()+" "+usuario.getApellidoUsuario());
						this.startActivity(inte);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				//onPause();
				
				editTextUser.setText("");
				editTextPass.setText("");
			}else{
				Toast.makeText(this, "La contraseña no coincide.\nVuelva a ingresarla", Toast.LENGTH_LONG).show();
			}
		}
		
		}else{
			Toast.makeText(this, "Campos vacios. Favor llenar todos los campos.", Toast.LENGTH_LONG).show();
		}
	}
	
	protected void onPause() {
		super.onPause();
		System.out.println("Salio");
		finish(); // termina la actividad
	}
}
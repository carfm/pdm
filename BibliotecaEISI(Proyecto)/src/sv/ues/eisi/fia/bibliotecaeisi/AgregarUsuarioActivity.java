package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Usuario;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;

public class AgregarUsuarioActivity extends Activity implements OnItemSelectedListener{

	EditText uIdUsuario;
	EditText uNombre;
	EditText uApellido;
	EditText uContrasenia;
	RadioGroup rbg;
	RadioButton radio1;
	Spinner spPrivilegios;
	Button btnLimpiar;
	Button btnAgregar;

	int intActivo;
	String strPrivilegio;
	ArrayAdapter<CharSequence> adapter;
	
	ControlBaseDatos BDhelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_usuario);
		BDhelper = new ControlBaseDatos (this);
		uIdUsuario = (EditText)findViewById(R.id.edAgregarIdUsuario);
		uNombre = (EditText)findViewById(R.id.edAgregarNombre);
		uApellido = (EditText)findViewById(R.id.edAgregarApeliido);
		uContrasenia = (EditText)findViewById(R.id.edAgregarContrasenia);
		radio1 = (RadioButton)findViewById(R.id.radioButton1);
		radio1.setChecked(true);
		intActivo = 1;
		rbg = (RadioGroup)findViewById(R.id.grupo);
		spPrivilegios = (Spinner) findViewById(R.id.spinner1);
        adapter =ArrayAdapter.createFromResource(this, R.array.privilegios_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPrivilegios.setAdapter(adapter);
        spPrivilegios.setOnItemSelectedListener(this);
	}
	
	public void UsuarioActivoCliked(View v){
		switch(rbg.getCheckedRadioButtonId()){
		case R.id.radioButton1:
			intActivo = 1; break;
		case R.id.radioButton2:
			intActivo = 0; break;
		}
	}
	
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		//Toast.makeText(parent.getContext(),"Privilegio: "+parent.getItemAtPosition(pos).toString(),Toast.LENGTH_LONG).show();
		strPrivilegio = parent.getItemAtPosition(pos).toString();
	}
	public void onNothingSelected(AdapterView<?> arg0) {
		// No se Utiliza
	}
	
	@SuppressLint("DefaultLocale")
	public void AgregarUsuario(View v){
		//Toast.makeText(this, "Privilegio: "+strPrivilegio +"\nActivo: "+intActivo, Toast.LENGTH_LONG).show();
		String regInsertados;
		String user = uIdUsuario.getText().toString().toUpperCase();
		String nombre = uNombre.getText().toString();
		String apellido = uApellido.getText().toString();
		String pass = uContrasenia.getText().toString();
		int activoUsuario = intActivo;
		String tipoUsuario = strPrivilegio.toLowerCase();		
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(user);
		usuario.setNombreUsuario(nombre);
		usuario.setApellidoUsuario(apellido);
		usuario.setContrasenia(pass);
		usuario.setActivo(activoUsuario);
		usuario.setTipo(tipoUsuario);
		
		BDhelper.abrir();
		regInsertados=BDhelper.insertar(usuario);
		BDhelper.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
	}
	public void LimpiarFields(View v){
		
		uIdUsuario.setText("");
		uNombre.setText("");
		uApellido.setText("");
		uContrasenia.setText("");
		radio1.setChecked(true);
		intActivo = 1;
	}
}

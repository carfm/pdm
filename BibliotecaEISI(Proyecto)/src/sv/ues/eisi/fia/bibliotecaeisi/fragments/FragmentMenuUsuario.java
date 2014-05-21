package sv.ues.eisi.fia.bibliotecaeisi.fragments;

import sv.ues.eisi.fia.bibliotecaeisi.R;
import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentMenuUsuario extends Fragment {

	public static final String OPCION = "opcion_number";
	public static final String USER = "nombre_user";
	public static final String IDUSER = "id_user";
	final String matriz[][] =new String[][] {
			{"PrestarDocumentoActivity","ConsultarDocumentoPrestamoActivity","HistorialPrestamoActivity"},
			{"EntregarDocumentoActivity","HistorialEntregaActivity",""}};
	private String[] datos;
	private String[] datos1;
	private ListView lstListado;
	private TextView txtVnombre;
	Activity context;
	

	public FragmentMenuUsuario() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		int i = getArguments().getInt(OPCION);
		if (i != 0) {
			datos= getResources().getStringArray(
					R.array.opciones_prestamos_usuario);
			datos1= getResources().getStringArray(
					R.array.opciones_entregas_usuario);
			return inflater.inflate(R.layout.fragment_menu_usuario,
					container, false);
		} else {
			return inflater.inflate(R.layout.fragment_inicio,
					container, false);
		}
	}

	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);
		context = super.getActivity();
		int i = getArguments().getInt(OPCION);
		if (i != 0) {
			String[] datos = new String[] {};
			switch (i) {
			case 1:
				datos = this.datos;
				break;
			case 2:
				datos = this.datos1;
				break;
			}
			ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
					this.getActivity(), android.R.layout.simple_list_item_1,
					datos);
			lstListado = (ListView) getView().findViewById(
					R.id.ListadoUsuario);
			lstListado.setAdapter(adaptador);
			lstListado.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> list, View view,
						int pos, long id) {
					int i = getArguments().getInt(OPCION);
					try {
						Class<?> clase = Class
								.forName("sv.ues.eisi.fia.bibliotecaeisi."
										+ matriz[i-1][pos]);
						Intent inte = new Intent(context, clase);
						
						inte.putExtra("idUsuario", getArguments().getString(IDUSER));
						startActivity(inte);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			});
		}else{
			txtVnombre = (TextView)getView().findViewById(R.id.textViewNoResultados);
			txtVnombre.setText(getArguments().getString(USER));
		}
	}
}

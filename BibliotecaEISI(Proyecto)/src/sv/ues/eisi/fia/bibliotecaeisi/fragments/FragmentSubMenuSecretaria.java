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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentSubMenuSecretaria extends Fragment {

	final String[] activities = new String[] { "AreaActivity",
			"DocumentoActivity", "EditorialActivity", "AutorActivity",
			"PaisActivity" };
	public static final String OPCION_SUBMENU = "opcion_number_submenu";
	private String[] datos;
	private String[] titulos;
	private ListView lstListado;
	

	Activity context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		datos= getResources().getStringArray(
				R.array.opciones_crud);
		titulos= getResources().getStringArray(
				R.array.opciones_bibloteca);
		return inflater.inflate(R.layout.fragment_submenu_secretaria,
				container, false);
	}

	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);
		context = super.getActivity();
		super.getActivity().setTitle(
				titulos[getArguments().getInt(OPCION_SUBMENU)]);
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
				this.getActivity(), android.R.layout.simple_list_item_1, datos);
		lstListado = (ListView) getView().findViewById(
				R.id.ListadoSubMenuSecretaria);
		lstListado.setAdapter(adaptador);
		lstListado.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list, View view, int pos,
					long id) {
				int i = getArguments().getInt(OPCION_SUBMENU);
				try {

					Class<?> clase = Class
							.forName("sv.ues.eisi.fia.bibliotecaeisi."
									+ datos[pos] + activities[i]);
					System.out.println(datos[pos] + activities[i]);
					Intent inte = new Intent(context, clase);
					context.startActivity(inte);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}
}

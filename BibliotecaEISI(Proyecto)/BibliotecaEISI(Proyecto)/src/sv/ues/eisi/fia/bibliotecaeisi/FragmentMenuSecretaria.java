package sv.ues.eisi.fia.bibliotecaeisi;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentMenuSecretaria extends Fragment {

	final String matriz[][] =new String[][] {{"AprobarPrestamosActivity", "ConsultarPrestamosActivity"},{"AgregarUsuarioActivity",""}};
	final String[] datos = new String[] { "Areas de aplicacion", "Documentos",
			"Editoriales", "Autores", "Paises" };
	final String[] datos1 = new String[] { "Aprobar Prestamos",
			"Consultar Prestamos" };
	final String[] datos2 = new String[] { "Agregar Usuario" };
	private ListView lstListado;
	Activity context;
	public static final String OPCION = "opcion_number";

	public FragmentMenuSecretaria() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		int i = getArguments().getInt(OPCION);
		if (i != 0) {
			return inflater.inflate(R.layout.fragment_menu_secretaria,
					container, false);
		} else {
			return inflater.inflate(R.layout.activity_aprobar_prestamos,
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
			case 3:
				datos = this.datos2;
				break;
			}
			System.out.println(i);
			ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
					this.getActivity(), android.R.layout.simple_list_item_1,
					datos);
			lstListado = (ListView) getView().findViewById(
					R.id.ListadoSecretaria);
			lstListado.setAdapter(adaptador);
			lstListado.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> list, View view,
						int pos, long id) {
					int i = getArguments().getInt(OPCION);
					if (i == 1) { // MENU BIBLIOTECA
						Fragment fragment = null;
						Bundle args = new Bundle();
						args.putInt(FragmentSubMenuSecretaria.OPCION_SUBMENU, pos);
						fragment = new FragmentSubMenuSecretaria();
						fragment.setArguments(args);
						FragmentManager fragmentManager = getFragmentManager();
						fragmentManager.beginTransaction()
								.replace(R.id.content_frame, fragment).commit();
					} else { // MENU USUARIO PRESTAMOS						
						//System.out.println(pos+" "+i+" "+matriz[i-2][pos]);
						try {
							Class<?> clase = Class
									.forName("sv.ues.eisi.fia.bibliotecaeisi."
											+ matriz[i-2][pos]);
							Intent inte = new Intent(context, clase);
							context.startActivity(inte);
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
					// TODO Auto-generated method stub
					// System.out.println(list.getSelectedItem());
					// System.out.println(pos);
					// System.out.println(view);
				}
			});
		}
	}
}

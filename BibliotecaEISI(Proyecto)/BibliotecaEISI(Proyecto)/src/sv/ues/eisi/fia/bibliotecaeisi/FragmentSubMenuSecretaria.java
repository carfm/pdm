package sv.ues.eisi.fia.bibliotecaeisi;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentSubMenuSecretaria extends Fragment {

	public static final String OPCION_SUBMENU = "opcion_number_submenu";
	final String[] datos = new String[] { "Insertar", "Eliminar", "Actualizar",
			"Consultar" };
	final String[] titulos = new String[] { "Areas de aplicacion", "Documentos",
			"Editoriales", "Autores", "Paises" };
	private ListView lstListado;
	Activity context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_submenu_secretaria,
				container, false);
	}

	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);
		super.getActivity().setTitle(titulos[getArguments().getInt(OPCION_SUBMENU)]);
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
				this.getActivity(), android.R.layout.simple_list_item_1, datos);
		lstListado = (ListView) getView().findViewById(
				R.id.ListadoSubMenuSecretaria);
		lstListado.setAdapter(adaptador);
	}
}

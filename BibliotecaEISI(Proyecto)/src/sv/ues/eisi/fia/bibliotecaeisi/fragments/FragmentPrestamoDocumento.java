package sv.ues.eisi.fia.bibliotecaeisi.fragments;

import sv.ues.eisi.fia.bibliotecaeisi.R;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Documento;
import sv.ues.eisi.fia.bibliotecaeisi.controlbase.ControlBaseDatos;
import android.support.v4.app.*;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentPrestamoDocumento extends Fragment {

	public static final String cadena = "cadena_busqueda";
	public static final String fragment = "opcion_fragment";
	public static final String documento = "documento_seleccionado";
	ControlBaseDatos control;
	private TextView detalle;
	int seleccion;
	Button buscar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		control = new ControlBaseDatos(getActivity());
		int i = getArguments().getInt(fragment);
		if (i == 0) {
			return inflater.inflate(R.layout.fragment_busqueda_prestamo,
					container, false);
		} else {
			return inflater.inflate(R.layout.fragment_detalle_documento,
					container, false);
		}
	}

	@Override
	public void onActivityCreated(Bundle state) {
		// TODO Auto-generated method stub
		super.onActivityCreated(state);
		int i = getArguments().getInt(fragment);
		if (i == 0) {
			
		} else {
			// codigo para segundo fragment
			detalle = (TextView) getView().findViewById(R.id.DetalleDocumento);
			control.abrir();
			Documento d = control.consultarDocumento(Integer
					.toString(getArguments().getInt(documento)));
			control.cerrar();
			String info = "";
			info = info + "\nTema o titulo : " + d.getTema();
			// AUTORES
			if (d.getIdTipoDocumento() == 1) {
				info = info + "\nTipo de documento : LIBRO";
			} else {
				info = info + "\nTipo de documento : TESIS";
			}
			info = info + "\nDescripcion : " + d.getDescripcion();
			// info = info + "\n : "+d.getIdEditorial();
			info = info + "\nEdicion : " + d.getEdicion();
			info = info + "\nAño : " + d.getAnio();
			info = info + "\nNumero de paginas : " + d.getNumeroPagina();
			info = info + "\nCantidad disponible : "
					+ d.getCantidadDisponible();
			detalle.setText(info);
		}
	}
}

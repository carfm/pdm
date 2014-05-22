package sv.ues.eisi.fia.bibliotecaeisi.fragments;

import android.app.*;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import android.util.Log;

public class FragmentDialogConfirmarLibros extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setMessage(
				"Regrese a las opciones de prestamo para hacer uno diferente")
				.setTitle("Advertencia")
				.setPositiveButton("Aceptar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Log.i("Dialogos", "Confirmacion Aceptada.");
								dialog.cancel();
							}
						});
		return builder.create();

	}
}

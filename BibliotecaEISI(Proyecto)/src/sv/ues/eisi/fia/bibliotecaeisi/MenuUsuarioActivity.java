package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.fragments.FragmentMenuUsuario;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuUsuarioActivity extends FragmentActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mTitle;
	private String[] titulosOpcionesUsuario;
	String NombreRecibido;
	String Carnet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_usuario);
		Bundle getDatos = getIntent().getExtras();
		Bundle args = new Bundle();
		args.putInt(FragmentMenuUsuario.OPCION, 0);
		NombreRecibido = getDatos.getString("NombreAlu");
		Carnet = getDatos.getString("idUser");
		args.putString(FragmentMenuUsuario.USER, NombreRecibido);
		args.putString(FragmentMenuUsuario.IDUSER, Carnet);
		Fragment fragment = new FragmentMenuUsuario();
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame2, fragment).commit();
		mTitle = getTitle();
		titulosOpcionesUsuario = getResources().getStringArray(
				R.array.opciones_usuario);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout2);
		mDrawerList = (ListView) findViewById(R.id.left_drawer2);
		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, titulosOpcionesUsuario));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle("Opciones");
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if (savedInstanceState == null) {
			// selectItem(0);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_usuario, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		// boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		return super.onOptionsItemSelected(item);
	}

	/* The click listener for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		// update the main content by replacing fragments
		if (position < 3) {
			Bundle args = new Bundle();
			args.putInt(FragmentMenuUsuario.OPCION, position);
			args.putString(FragmentMenuUsuario.USER, NombreRecibido);
			args.putString(FragmentMenuUsuario.IDUSER, Carnet);
			Fragment fragment = new FragmentMenuUsuario();
			fragment.setArguments(args);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame2, fragment).commit();
			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			setTitle(titulosOpcionesUsuario[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
			dialogo1.setTitle("Cerrar Sesion");
			dialogo1.setMessage("� De verdad desea Cerrar Sesion ?");
			dialogo1.setCancelable(false);
			dialogo1.setPositiveButton("Confirmar",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialogo1, int id) {
							aceptar();
						}
					});
			dialogo1.setNegativeButton("Cancelar",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialogo1, int id) {
							//return;
						}
					});
			dialogo1.show();
		}
	}

	public void aceptar() {
		super.onPause();
		System.out.println("Salio");
		finish(); // termina la actividad
		try {
			Intent inte = new Intent(this, InicioSesionActivity.class);
			this.startActivity(inte);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
}

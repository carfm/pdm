package sv.ues.eisi.fia.bibliotecaeisi;

import sv.ues.eisi.fia.bibliotecaeisi.fragments.FragmentMenuSecretaria;
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
import android.widget.TextView;

public class MenuSecretariaActivity extends FragmentActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mTitle;
	private String[] titulosOpcionesSecretaria;

	/*Sirve para inicio de Session*/
	TextView txtVnombre;
	String NombreRecibido;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_secretaria);
		Bundle getDatos = getIntent().getExtras();
		Bundle args = new Bundle();
		args.putInt(FragmentMenuSecretaria.OPCION,0);
		NombreRecibido = getDatos.getString("NombreSecre");
		args.putString(FragmentMenuSecretaria.USER,NombreRecibido);
		Fragment fragment = new FragmentMenuSecretaria();
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment)
				.commit();
		/*Inicio de Session*/
		//txtVnombre = (TextView)findViewById(R.id.textView2);
/*		Bundle getDatos = getIntent().getExtras();
		NombreRecibido = getDatos.getString("NombreSecre");
		txtVnombre.setText(NombreRecibido);*/
		mTitle = getTitle();
		titulosOpcionesSecretaria = getResources().getStringArray(
				R.array.opciones_secretaria);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, titulosOpcionesSecretaria));
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
		inflater.inflate(R.menu.menu_secretaria, menu);
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

	/* The click listner for ListView in the navigation drawer */
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
		Bundle args = new Bundle();
		args.putInt(FragmentMenuSecretaria.OPCION, position);
		args.putString(FragmentMenuSecretaria.USER,NombreRecibido);
		Fragment fragment = new FragmentMenuSecretaria();
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment)
				.commit();
		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(titulosOpcionesSecretaria[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
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

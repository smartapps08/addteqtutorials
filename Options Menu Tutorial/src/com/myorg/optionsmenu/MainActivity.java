package com.myorg.optionsmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView tv;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tv=(TextView)findViewById(R.id.txtOutput);
		registerForContextMenu(tv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.game_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1:
			Toast.makeText(getApplicationContext(), "New Game Created", Toast.LENGTH_LONG).show();
			return true;
		case R.id.item2:
			Toast.makeText(getApplicationContext(), "Game Saved", Toast.LENGTH_LONG).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.game_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1:
			Toast.makeText(getApplicationContext(), "New Game Created", Toast.LENGTH_LONG).show();
			return true;
		case R.id.item2:
			Toast.makeText(getApplicationContext(), "Game Saved", Toast.LENGTH_LONG).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
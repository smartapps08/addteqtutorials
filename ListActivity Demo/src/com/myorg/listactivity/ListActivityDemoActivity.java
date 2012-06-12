package com.myorg.listactivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListActivityDemoActivity extends ListActivity {
	private String[] countryList = { "Bahama", "Bangladesh", "Belgium",
			"Bugeria", "United States", "United Kingdom", "countri 1", "countri 1","countri 1","countri 1" };

	private ArrayAdapter<String> listAdapter; 
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		listAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countryList);
		setListAdapter(listAdapter);

	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Toast.makeText(this, "Item Selected: "+l.getItemIdAtPosition(position), Toast.LENGTH_LONG).show();
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
}
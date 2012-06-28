package com.myorg.actionbartutes;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ActionBarTutorialActivity extends Activity {
	/** Called when the activity is first created. */
	ActionBar actionBar;
	Object mActionMode;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		actionBar = getActionBar();
		actionBar
				.setTitle("This is a very long title so that items go elsewhere");

		// Define the contextual action mode
		View view = findViewById(R.id.myView);
		view.setOnLongClickListener(new View.OnLongClickListener() {
			// Called when the user long-clicks on someView
			public boolean onLongClick(View view) {
				if (mActionMode != null) {
					return false;
				}

				// Start the CAB using the ActionMode.Callback defined above
				mActionMode = ActionBarTutorialActivity.this
						.startActionMode(mActionModeCallback);
				//view.setSelected(true);
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuitem1:
			Toast.makeText(this, "Menu Item 1 selected", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.menuitem2:
			Toast.makeText(this, "Menu item 2 selected", Toast.LENGTH_SHORT)
					.show();
			break;

		default:
			break;
		}

		return true;
	}
	
	ActionMode.Callback mActionModeCallback=new ActionMode.Callback() {
		
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			
			return false;
		}
		
		// Called when the user exits the action mode
		public void onDestroyActionMode(ActionMode mode) {
			mActionMode = null;
			
		}
		
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater inflater = mode.getMenuInflater();
			// Assumes that you have "contexual.xml" menu resources
			inflater.inflate(R.menu.contextual, menu);
			return true;
		}
		
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.toast:
				Toast.makeText(ActionBarTutorialActivity.this, "Selected menu",
						Toast.LENGTH_LONG).show();
				mode.finish(); // Action picked, so close the CAB
				return true;
			default:
				return false;
			}
		}
	};
}
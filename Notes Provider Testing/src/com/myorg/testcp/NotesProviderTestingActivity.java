package com.myorg.testcp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NotesProviderTestingActivity extends Activity {
	EditText etTitle, etBody;
	Button btnInsert, btnShow;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		etTitle = (EditText) findViewById(R.id.etTitle);
		etBody = (EditText) findViewById(R.id.etBody);
		btnInsert = (Button) findViewById(R.id.btnSave);
		btnShow = (Button) findViewById(R.id.btnShow);

		btnInsert.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String title = etTitle.getText().toString();
				String body = etBody.getText().toString();

				if (!title.equals("") && !body.equals("")) {
					ContentResolver cr = getContentResolver();
					ContentValues values = new ContentValues();
					values.put("title", title);
					values.put("body", body);

					Uri insertedUri = cr.insert(
							Uri.parse("content://com.myorg.notescontentprovider.provider.notescp/notescp"),
							values);
					if (insertedUri != null) {
						Toast.makeText(
								getApplicationContext(),
								"Insertion Successful: "
										+ insertedUri.toString(),
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getApplicationContext(),
								"Insertion Failed", Toast.LENGTH_LONG).show();
					}
				}
			}
		});

		btnShow.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				ContentResolver cr = getContentResolver();
				Cursor cursor = cr.query(
						Uri.parse("content://com.myorg.notescontentprovider.provider.notescp/notescp"),
						null, null, null, null);
				if (cursor != null && cursor.getCount() > 0) {
					while (cursor.moveToNext()) {
						int id = cursor.getInt(cursor.getColumnIndex("_id"));
						String title = cursor.getString(cursor
								.getColumnIndex("title"));
						String body = cursor.getString(cursor
								.getColumnIndex("body"));

						Toast.makeText(getApplicationContext(),
								id + " " + title + " " + body,
								Toast.LENGTH_LONG).show();
					}
				}

			}
		});

	}
}
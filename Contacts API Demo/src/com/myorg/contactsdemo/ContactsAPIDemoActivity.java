package com.myorg.contactsdemo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Browser;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ContactsAPIDemoActivity extends Activity implements
		OnClickListener {
	Button show, add, update, delete;
	Button history;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		show = (Button) findViewById(R.id.btnShow);
		add = (Button) findViewById(R.id.btnAdd);
		update = (Button) findViewById(R.id.btnUpdate);
		delete = (Button) findViewById(R.id.btnDelete);

		history = (Button) findViewById(R.id.btnBrowserHistory);

		show.setOnClickListener(this);
		add.setOnClickListener(this);
		update.setOnClickListener(this);
		delete.setOnClickListener(this);

		history.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnShow:
			// show the existing contacts
			displayContacts();
			break;
		case R.id.btnAdd:
			addContact("Alex", "019385763524");
			break;

		case R.id.btnUpdate:
			updateContact("Ahsan", "5556");
			break;
		case R.id.btnDelete:
			deleteContact("Ahsan");
			break;
		case R.id.btnBrowserHistory:
			showHistory();
			break;
		default:
			break;
		}

	}

	// content://sms-mms/sms
	private void showHistory() {
		ContentResolver cr = getContentResolver();
		Cursor historyCursor = cr.query(Browser.BOOKMARKS_URI, null, null,
				null, null);

		if (historyCursor != null && historyCursor.getCount() > 0) {
			while (historyCursor.moveToNext()) {
				String url = historyCursor.getString(historyCursor
						.getColumnIndex(Browser.BookmarkColumns.URL));
				String time = historyCursor.getString(historyCursor
						.getColumnIndex(Browser.BookmarkColumns.DATE));

				Toast.makeText(getApplicationContext(), url + " " + time,
						Toast.LENGTH_LONG).show();
			}
			historyCursor.close();
		}

	}

	private void showCallLog() {
		ContentResolver cr = getContentResolver();
		Cursor callLogCursor = cr.query(CallLog.CONTENT_URI, null, null, null,
				null);

		if (callLogCursor != null && callLogCursor.getCount() > 0) {
			while (callLogCursor.moveToNext()) {

			}
			callLogCursor.close();
		}

	}

	private void addContact(String name, String phoneNumber) {
		ContentResolver cr = getContentResolver();
		Cursor contactCursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
				null, null, null, null);

		if (contactCursor != null && contactCursor.getCount() > 0) {
			while (contactCursor.moveToNext()) {
				String existName = contactCursor
						.getString(contactCursor
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				if (existName.contains(name)) {
					Toast.makeText(getApplicationContext(),
							"The contact name: " + name + " already exists",
							Toast.LENGTH_LONG).show();
					return;
				}
			}
		}

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ContentProviderOperation operation = ContentProviderOperation
				.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE,
						"accountname@gmail.com")
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME,
						"com.google").build();

		ops.add(operation);
		operation = ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				.withValue(
						ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
						name).build();
		ops.add(operation);
		operation = ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
						phoneNumber).build();
		ops.add(operation);

		try {
			cr.applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			e.printStackTrace();
		}

		Toast.makeText(getApplicationContext(),
				"Created new contact: " + name + " " + phoneNumber,
				Toast.LENGTH_LONG).show();

	}

	private void displayContacts() {
		ContentResolver cr = getContentResolver();
		// content://authority/data-path/id
		Cursor contactCursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
				null, null, null, null);

		if (contactCursor != null && contactCursor.getCount() > 0) {
			while (contactCursor.moveToNext()) {
				String id = contactCursor.getString(contactCursor
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = contactCursor
						.getString(contactCursor
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				int hasPhoneNumber = Integer
						.parseInt(contactCursor.getString(contactCursor
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
				if (hasPhoneNumber > 0) {
					// this contact
					Cursor phoneCursor = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ "=?", new String[] { id }, null);
					while (phoneCursor.moveToNext()) {
						String phoneNumber = phoneCursor
								.getString(phoneCursor
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						Toast.makeText(getApplicationContext(),
								id + " " + name + " " + phoneNumber,
								Toast.LENGTH_LONG).show();
					}
					phoneCursor.close();
				}

			}
		}
		contactCursor.close();
	}

	private void updateContact(String name, String phone) {
		ContentResolver cr = getContentResolver();

		String where = ContactsContract.Data.DISPLAY_NAME + " = ? AND "
				+ ContactsContract.Data.MIMETYPE + " = ? AND "
				+ String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE)
				+ " = ? ";
		String[] params = new String[] {
				name,
				ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
				String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_HOME) };

		Cursor phoneCur = managedQuery(ContactsContract.Data.CONTENT_URI, null,
				where, params, null);

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

		if ((null == phoneCur)) {
			addContact(name, phone);
		} else {
			ops.add(ContentProviderOperation
					.newUpdate(ContactsContract.Data.CONTENT_URI)
					.withSelection(where, params)
					.withValue(ContactsContract.CommonDataKinds.Phone.DATA,
							phone).build());
		}

		phoneCur.close();

		try {
			cr.applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Toast.makeText(this,
				"Updated the phone number of 'Sample Name' to: " + phone,
				Toast.LENGTH_SHORT).show();
	}

	private void deleteContact(String name) {

		ContentResolver cr = getContentResolver();
		String where = ContactsContract.Data.DISPLAY_NAME + " = ? ";
		String[] params = new String[] { name };

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation
				.newDelete(ContactsContract.RawContacts.CONTENT_URI)
				.withSelection(where, params).build());
		try {
			cr.applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Toast.makeText(this, "Deleted the contact with name '" + name + "'",
				Toast.LENGTH_SHORT).show();

	}
}

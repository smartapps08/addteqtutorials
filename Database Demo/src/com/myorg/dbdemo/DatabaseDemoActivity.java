package com.myorg.dbdemo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DatabaseDemoActivity extends Activity {
	private EditText etTitle, etBody;
	private Button btnShow, btnSave;
	private DBAdapter dbAdapter;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        dbAdapter=new DBAdapter(getApplicationContext());
        
        etTitle=(EditText)findViewById(R.id.etTitle);
        etBody=(EditText)findViewById(R.id.etBody);
        
        btnShow=(Button)findViewById(R.id.btnShow);
        btnSave=(Button)findViewById(R.id.btnSave);
        
        btnSave.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String title=etTitle.getText().toString();
				String body=etBody.getText().toString();
				
				if(title.equals("") || body.equals(""))
				{
					Toast.makeText(getApplicationContext(), "Please input all the fields!", Toast.LENGTH_LONG).show();
				}
				else
				{
					Note note=new Note(title, body);
					long inserted=dbAdapter.insertNote(note);
					if(inserted>-1)
					{
						Toast.makeText(getApplicationContext(), "Note inserted successfully!", Toast.LENGTH_LONG).show();
					}
					
				}
				
			}
		});
        
        btnShow.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				ArrayList<Note> notes=dbAdapter.getAllNotes();
				for (Note note : notes) {
					Toast.makeText(getApplicationContext(), "Title: "+note.getTitle()+" Body: "+note.getBody(), Toast.LENGTH_LONG).show();
				}
				
				
			}
		});
        
        
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	dbAdapter.open();
    	
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	dbAdapter.close();
    	
    }
}
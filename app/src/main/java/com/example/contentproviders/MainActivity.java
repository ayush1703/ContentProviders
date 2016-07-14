package com.example.contentproviders;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.system.StructStatVfs;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.provider.ContactsContract;
import android.database.Cursor;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button queryButton = (Button)findViewById(R.id.button1);
        queryButton.setOnClickListener(new OnClickListener(){
            public void onClick(View arg0)
            {
                queryContact();
            }
        });
    Button addButton = (Button)findViewById(R.id.button2);
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact("Steve");
            }
        });
    }
    private void addContact(String newName)
    {
        ContentValues myContact = new ContentValues();
        myContact.put(ContactsContract.RawContacts.ACCOUNT_NAME, newName);
        myContact.put(ContactsContract.RawContacts.ACCOUNT_TYPE, newName);
        Uri addUri= getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, myContact);
        long rawContactId= ContentUris.parseId(addUri);
        myContact.clear();
        myContact.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        myContact.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        myContact.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, newName);
        getContentResolver().insert(ContactsContract.Data.CONTENT_URI, myContact);
        Toast.makeText(this,"New Contact:"+newName, Toast.LENGTH_SHORT).show();
    }

    private void queryContact()
    {
        Cursor nameCursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        while(nameCursor.moveToNext()){
            String contactName = nameCursor.getString(nameCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
            Toast.makeText(this, contactName, Toast.LENGTH_SHORT).show();
        }
        nameCursor.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

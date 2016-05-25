package com.tom.contactapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CONTACTS = 1;
    private ListView list;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode==REQUEST_CONTACTS){
            if (grantResults.length>0 &&
                    grantResults[0]==PackageManager.PERMISSION_GRANTED){
                readContacts();
            }else{

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.listView);
        //
        int permission =
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.READ_CONTACTS);
        if (permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_CONTACTS);
        }else{
            readContacts();
        }
    }

    private void readContacts() {
        Log.d("Contacts", "readContacts");
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        String[] from = {ContactsContract.Contacts.DISPLAY_NAME};
        int[] to = {android.R.id.text1};
        SimpleCursorAdapter adapter =
                new SimpleCursorAdapter(this,
                        android.R.layout.simple_list_item_1,
                        c, from, to , 0);


        list.setAdapter(adapter);
        /*
        while(c.moveToNext()) {
            int index = c.getColumnIndex(ContactsContract.Contacts._ID);
            int id = c.getInt(index);
            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            Log.d("Phone", id + "/" + name);
        }*/
    }
}




package com.tom.contactapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
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
        Cursor c = cr.query(Phone.CONTENT_URI,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME,
                        Phone.NUMBER, ContactsContract.Contacts._ID},
                null, null, null);

        String[] from = {ContactsContract.Contacts.DISPLAY_NAME,
                        Phone.NUMBER};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleCursorAdapter adapter =
                new SimpleCursorAdapter(this,
                        android.R.layout.simple_list_item_2,
                        c, from, to , 0);
        list.setAdapter(adapter);
        /*
        while(c.moveToNext()) {
            int index = c.getColumnIndex(ContactsContract.Contacts._ID);
            int id = c.getInt(index);
            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            int hasPhone = c.getInt(c.getColumnIndex(
                    ContactsContract.Contacts.HAS_PHONE_NUMBER));
            Log.d("Phone", id + "/" + name + "/"+ hasPhone);
            if (hasPhone==1){
                Cursor c2 = cr.query(Phone.CONTENT_URI,
                        null,
                        Phone.CONTACT_ID+"=?",
                        new String[]{id+""},
                        null);
                while(c2.moveToNext()){
                    String phone = c2.getString(c2.getColumnIndex(Phone.NUMBER));
                    Log.d("NUMBER", phone);
                }
            }*/

    }
}




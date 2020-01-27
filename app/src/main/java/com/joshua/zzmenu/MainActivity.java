package com.joshua.zzmenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.ACTION_WEB_SEARCH;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PICTURE_CAPTURE =3 ;
    ImageView img;
    private static final int REQUEST_CODE = 1;
    private static final int PICK_IMAGE_CODE=2;
    TextView tv,tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img=(ImageView)findViewById(R.id.imageView);
        tv=(TextView)findViewById(R.id.textView);
        tv2=(TextView)findViewById(R.id.textView2);
        registerForContextMenu(img);
        tv2.setText("Hello "+getIntent().getExtras().getString("name").toString());
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem items)
    {
        switch(items.getItemId())
        {
            case R.id.help:
                Toast.makeText(getApplicationContext(),"Help",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY,"https://developer.android.com/");
                startActivity(intent);

                return true;
            case R.id.exit:
                Process.killProcess(Process.myPid());
                System.exit(1);
                return true;
            case R.id.home:
                Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,LoginActivity.class));
                return true;
            case R.id.contact:
                Toast.makeText(getApplicationContext(),"Contacts",Toast.LENGTH_SHORT).show();
               // startActivity(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI));
                Uri uri = Uri.parse("content://contacts");
                Intent intent1 = new Intent(Intent.ACTION_PICK, uri);
                intent1.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent1, REQUEST_CODE);
                return true;
            case R.id.search:
                Toast.makeText(getApplicationContext(),"Search",Toast.LENGTH_SHORT).show();

                Uri u=Uri.parse("");
                Intent i=new Intent(ACTION_WEB_SEARCH,u);
            default:
                return super.onOptionsItemSelected(items);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                Uri uri = intent.getData();
                String[] projection = { ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME };

                Cursor cursor = getContentResolver().query(uri, projection,
                        null, null, null);
                cursor.moveToFirst();

                int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberColumnIndex);

                int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(nameColumnIndex);

                tv.setText("Name : "+name+"\nNumber : " + number );

            }

        }


    }


    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.camera:
                Toast.makeText(getApplicationContext(),"Opening camera",Toast.LENGTH_SHORT).show();
                    //
                     startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
                //
                return true;
            case R.id.sharepic:

                Toast.makeText(getApplicationContext(),"Picture Shared in mail",Toast.LENGTH_SHORT).show();
                Intent j=new Intent(Intent.ACTION_SEND);
                j.setType("image/*");
                startActivity(j);
                return true;

            case R.id.change:
                Toast.makeText(getApplicationContext(),"Opening Gallery",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select an Image"),PICK_IMAGE_CODE);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }




}

package com.joshua.zzmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText name,pass;

    String username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name=(EditText)findViewById(R.id.editText3);
        pass=(EditText)findViewById(R.id.editText4);

    }

    public void submit(View view)
    {
        username=name.getText().toString();
        password=pass.getText().toString();

        if(username.equals("1947105") && password.equals("12345"))
        {
            Toast.makeText(getApplicationContext(),"Login successful",Toast.LENGTH_LONG).show();
            Intent i=new Intent(this,MainActivity.class);
            i.putExtra("name","Joshua");
            startActivity(i);
            finish();
        }
        else
                Toast.makeText(this,"Wrong username or password",Toast.LENGTH_LONG).show();
    }

    public void clear(View view)
    {
     startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
}

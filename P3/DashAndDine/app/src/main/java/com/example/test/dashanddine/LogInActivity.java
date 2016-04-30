package com.example.test.dashanddine;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LogInActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button b=(Button)findViewById(R.id.login);
        b.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v)
            {
                EditText et1=(EditText)findViewById(R.id.username);
                EditText et2=(EditText)findViewById(R.id.password);
                String username=et1.getText().toString();
                String password=et2.getText().toString();
                //Here would be a check for valid login and password
                Intent i = new Intent(LogInActivity.this, StartActivity.class);
                i.putExtra("username",username);
                i.putExtra("password",password);
                startActivity(i);
            }
        });
    }



}

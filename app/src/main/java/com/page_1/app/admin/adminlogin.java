package com.page_1.app.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.page_1.app.R;

public class adminlogin extends AppCompatActivity {
public Button button7;
private EditText username;
private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        getSupportActionBar().hide();
        button7=(Button) findViewById(R.id.button7);
        username=(EditText) findViewById(R.id.tf_admin_username);
        password=(EditText) findViewById(R.id.tf_admin_password);
         final String tf_userid= username.getText().toString();
         final String tf_userpass= password.getText().toString();
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((username.getText().toString() .equals("admin") ) && (password.getText().toString().equals("password"))) {
                    openadmin_menu();
                    Toast.makeText( adminlogin. this, "Logged in Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText( adminlogin. this, "Login Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void openadmin_menu()
    {
        Intent intent = new Intent(this,admin_menu.class);
        startActivity(intent);
    }
}

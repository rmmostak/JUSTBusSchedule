package com.rmproduct.bus.busschedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText userName=findViewById(R.id.userName);
        final EditText password=findViewById(R.id.userPassword);
        Button login=findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_Name=userName.getText().toString().trim();
                String pass_word=password.getText().toString().trim();

                if (user_Name.equals("admin@just.edu.bd")) {
                    if (pass_word.equals("adminpanel")) {
                        startActivity(new Intent(Login.this, MainActivity.class));
                    } else {
                        password.setError("Please enter correct password!");
                        password.requestFocus();
                    }

                } else {
                    userName.setError("Please enter correct username!");
                    userName.requestFocus();
                }
            }
        });
    }
}

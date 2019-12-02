package com.aron.patientmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    RelativeLayout relay1;
    Handler handler =new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
          relay1.setVisibility(View.VISIBLE);

        }
    };
    EditText username;
    EditText password;
    String username1;
    String password1;
   Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (pref.getBoolean("activity_executed", false)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        relay1=(RelativeLayout)findViewById(R.id.rllogin);
        handler.postDelayed(runnable,2000);


        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);

        System.out.println(password1);
        button=(Button)findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("admin")&& password.getText().toString().equals("admin"))
                {
                    Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Wrong Credentials",Toast.LENGTH_SHORT).show();
                }

            }
        });
         pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        edt.putBoolean("activity_executed", true);
        edt.commit();

    }
}

package com.aron.patientmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

public class LoginActivity extends AppCompatActivity {
    RelativeLayout relay1;
    Handler handler =new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
          relay1.setVisibility(View.VISIBLE);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        relay1=(RelativeLayout)findViewById(R.id.rllogin);
        handler.postDelayed(runnable,2000);

    }
}

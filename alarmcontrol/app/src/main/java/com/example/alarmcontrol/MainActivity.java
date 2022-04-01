package com.example.alarmcontrol;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnConnect, btnState, btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConnect = (Button) findViewById(R.id.btn_connect);
        btnState = (Button) findViewById(R.id.btn_state);
        btnSetting = (Button) findViewById(R.id.btn_setting);
        
        initLayouts();
        setLayouts();
    }

    void initLayouts() {
        btnConnect = (Button) findViewById(R.id.btn_connect);
        btnState = (Button) findViewById(R.id.btn_state);
        btnSetting = (Button) findViewById(R.id.btn_setting);
    }

    void setLayouts() {
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("연결 및 블루투스 페어링")
                        .setMessage("내용");
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btnState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
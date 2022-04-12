package com.example.alarmcontrol;

import android.bluetooth.BluetoothAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class MainActivity extends AppCompatActivity {

    final int REQUEST_ENABLE_BLUETOOTH = 0;
    final int REQUEST_DISCOVERABLE = 1;

    BluetoothSPP bluetoothSPP;
    BluetoothAdapter bluetoothAdapter;

    Button btnConnect, btnState, btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayouts();
        initBluetooth();
        setLayouts();
    }

    void initLayouts() {
        btnConnect = (Button) findViewById(R.id.btn_connect);
        btnState = (Button) findViewById(R.id.btn_state);
        btnSetting = (Button) findViewById(R.id.btn_setting);
    }

    void initBluetooth() {
        bluetoothSPP= new BluetoothSPP(this);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    void setLayouts() {
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("연결 및 블루투스 페어링")
                        .setMessage("내용"); //아직 안만든 부분
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btnState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!MainActivity.this.bluetoothSPP.isBluetoothAvailable()) {
                    Toast.makeText(MainActivity.this, "지원하지 않는 기기입니다.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "블루투스 쌉가능", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    void connectBluetooth() {
        //블루투스 연결하는 부분
    }
}
package com.example.alarmcontrol;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class MainActivity extends AppCompatActivity {
    final int REQUEST_ENABLE_BLUETOOTH = 0;
    final int REQUEST_DISCOVERABLE = 1;

    BluetoothSPP bluetoothSPP;
    BluetoothAdapter bluetoothAdapter;
    boolean isConnected = false;

    Button btnConnect, btnState, btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothSPP = new BluetoothSPP(this);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        checkPermissions();
        initLayouts();
        setLayouts();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            switch (requestCode) {
                case REQUEST_ENABLE_BLUETOOTH:
                    Toast.makeText(this, "블루투스 기능을 활성화해야 합니다.", Toast.LENGTH_LONG).show();
                    enableBluetooth();
                    break;
                case REQUEST_DISCOVERABLE:
                    Toast.makeText(this, "장치를 탐색하기 위해선 사용자의 동의가 필요합니다.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
                    break;
            }
        } else {
            switch (requestCode) {
                case BluetoothState.REQUEST_CONNECT_DEVICE:
                    assert data != null;
                    bluetoothSPP.connect(data);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
                if (!isConnected) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("장치와 연결되지 않은 상태입니다. 연결하시겠습니까?")
                            .setPositiveButton("연결", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    connectBluetooth();
                                }
                            })
                            .setNegativeButton("취소", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("이미 장치와 연결되어 있습니다. 연결을 끊을까요?")
                            .setPositiveButton("연결 해제", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //연결을 끊는 기능
                                    Toast.makeText(MainActivity.this, "장치와 연결이 해제되었습니다. 다시 장치와 연결하세요.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("취소", null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
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
                if (isConnected) {
                    //알람 설정하기
                } else {
                    
                }
            }
        });
    }

    void checkPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            permissions = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.BLUETOOTH_CONNECT
            };
        }
        ActivityCompat.requestPermissions(this, permissions, 1);
    }

    void enableBluetooth() {
        if (!bluetoothAdapter.isEnabled()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                checkPermissions();
            }
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BLUETOOTH);
        }
    }

    boolean isBluetoothReady() {
        if (!bluetoothSPP.isBluetoothAvailable()) {
            Toast.makeText(this, "블루투스를 지원하지 않는 장치입니다.", Toast.LENGTH_LONG).show();
            return false;
        } else if (!bluetoothSPP.isBluetoothEnabled()) {
            Toast.makeText(this, "블루투스 기능을 활성화해야 합니다.", Toast.LENGTH_LONG).show();
            enableBluetooth();
            return false;
        } else if (!bluetoothSPP.isServiceAvailable()) {
            bluetoothSPP.setupService();
            bluetoothSPP.startService(BluetoothState.DEVICE_OTHER);
            return false;
        } else {
            Toast.makeText(this, "블루투스 상태를 확인하였습니다. 문제가 없습니다.", Toast.LENGTH_LONG).show();
            return true;
        }
    }

    void connectBluetooth() {
        if (isBluetoothReady()) {
            if (bluetoothSPP.getServiceState() != BluetoothState.STATE_CONNECTED) {
                startActivityForResult(new Intent(getApplicationContext(), DeviceList.class), BluetoothState.REQUEST_CONNECT_DEVICE);
                Toast.makeText(this, "연결할 기기를 선택하세요.", Toast.LENGTH_LONG).show();
                bluetoothSPP.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
                    @Override
                    public void onDeviceConnected(String name, String address) {
                        isConnected = true;
                        Toast.makeText(MainActivity.this, "장치와 연결되었습니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDeviceDisconnected() {
                        isConnected = false;
                        Toast.makeText(MainActivity.this, "장치와 연결이 끊겼습니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDeviceConnectionFailed() {
                        isConnected = false;
                        Toast.makeText(MainActivity.this, "장치와 연결이 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
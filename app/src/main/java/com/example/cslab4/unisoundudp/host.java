package com.example.cslab4.unisoundudp;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class host extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        WifiManager wifiManager = (WifiManager)getSystemService(WIFI_SERVICE);

        String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        TextView ipText = (TextView)findViewById(R.id.iptext);
        ipText.setText(ip);

        Button stopStream = (Button)findViewById(R.id.stopButton);
        stopStream.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        MainActivity ma = new MainActivity();
                        ma.status=false;
                        ma.recorder.release();
                        ma.socket.close();
                    }
                }
        );
    }
}

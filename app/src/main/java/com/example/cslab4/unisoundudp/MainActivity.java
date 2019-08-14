package com.example.cslab4.unisoundudp;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.DatagramSocket;


public class MainActivity extends AppCompatActivity {

    static DatagramSocket socket;
    static  AudioRecord recorder;
    private int sampleRate  = 16000;
    private int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    int minBufSize = 20000;
    static boolean status = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WifiManager wifiManager = (WifiManager)getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        TextView ipText = (TextView)findViewById(R.id.ipText);
        ipText.setText(ip);

        final Button hostButton = (Button)findViewById(R.id.hostButton);
        hostButton.setOnClickListener(
            new Button.OnClickListener(){
            public void onClick(View v){
                MyTask myTask = new MyTask();
                myTask.execute();
                startActivity(new Intent(MainActivity.this,host.class));

            }
        }
        );
        final Button clientButton = (Button)findViewById(R.id.button2);
        clientButton.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Speaker speak = new Speaker();
                        speak.execute();
                        startActivity(new Intent(MainActivity.this,host.class));
                    }
                }
        );
    }

//    public void  startStreaming(){
//        Thread  streamThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    DatagramPacket packet;
//                    socket = new DatagramSocket();
//                    byte buffer[] = new byte[minBufSize];
//                    InetAddress destination = InetAddress.getByName("192.168.137.67");
//                    recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,sampleRate,channelConfig,audioFormat,minBufSize*10);
//                    recorder.startRecording();
//                    while (status == true){
//
//                        minBufSize = recorder.read(buffer,0,buffer.length);
//                        packet = new DatagramPacket(buffer, buffer.length,destination,8888);
//                        socket.send(packet);
//                        System.out.println("hello");
//                    }
//
//                }catch (Exception e){
//
//                }
//            }
//        });
//        streamThread.start();
//    }

}

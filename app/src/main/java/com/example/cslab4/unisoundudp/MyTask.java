package com.example.cslab4.unisoundudp;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;

import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by CS LAB 4 on 1/24/2019.
 */

public class MyTask extends AsyncTask<Void, Void, Void> {


    static Socket socket;
    MediaRecorder mRecorder;
    AudioRecord recorder;
    private static int sampleRate  = 44100;
    private static int channelConfig = AudioFormat.CHANNEL_IN_MONO;
    private static int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    static int minBufSize = AudioRecord.getMinBufferSize(sampleRate,channelConfig,audioFormat);
    static boolean status = true;

    @Override
    protected Void doInBackground(Void... voids) {
        Thread  streamThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    InetAddress destination = InetAddress.getByName("192.168.173.1");
                    DatagramPacket packet;
                    DatagramSocket socket = new DatagramSocket();

                    byte buffer[] = new byte[2048];

                    recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,sampleRate,channelConfig,audioFormat,minBufSize);
                    recorder.startRecording();
                    while (status == true){

                        minBufSize = recorder.read(buffer,0,buffer.length);
                        packet = new DatagramPacket(buffer,buffer.length,destination,8888);
                        socket.send(packet);
                        

                        System.out.println(buffer.length + "min buffer size is   "+minBufSize);
                    }

                }catch (Exception e){
                    System.out.println(e);
                }
            }
        });
        streamThread.start();

        return null;
    }
}

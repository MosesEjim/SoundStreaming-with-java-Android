package com.example.cslab4.unisoundudp;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by CS LAB 4 on 1/31/2019.
 */

public class Speaker extends AsyncTask<Void, Void, Void> {InetAddress destination;
    DatagramPacket packet;
    DatagramSocket socket;
    @Override
    protected Void doInBackground(Void... voids) {
        Thread  streamThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{


                    socket = new DatagramSocket(8888);
                    byte data[] = new byte[2048];
                    packet = new DatagramPacket(data,data.length);


                    int sampleRate = 44100;
                    int bufferSize = AudioTrack.getMinBufferSize(sampleRate,
                            AudioFormat.CHANNEL_CONFIGURATION_MONO,
                            AudioFormat.ENCODING_PCM_16BIT);

                    AudioTrack mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                            sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                            AudioFormat.ENCODING_PCM_16BIT, bufferSize,
                            AudioTrack.MODE_STREAM);
                    while (true){
                        socket.receive(packet);
                        mAudioTrack.write(data, 0, data.length);
                        mAudioTrack.play();

                    }
                }catch (Exception e){
                    System.out.println(e);
                }
                finally {
                    {
                        socket.close();
                    }
                }
            }
        });
        streamThread.start();
        return null;
    }
}

package com.OBDMachine.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;
import com.OBDMachine.Application;
import com.OBDMachine.machines.SerialPort;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.security.InvalidParameterException;

/**
 * Created by loki on 5/18/15.
 */
public abstract class SerialPortActivity extends Activity{
    protected Application mApplication;
    protected SerialPort mSerialPort;
    protected OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    private WriteThread mWriteThread;

    private String testmessage="原来是这样";
    private com.github.nkzawa.socketio.client.Socket mSocket;

    {
        try{
            mSocket = IO.socket("http://192.168.0.150:3000");
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
    }
    private class WriteThread extends Thread{
        String outPut = "test\n";

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()){
                try{
                    //发送obd数据

                    while(true){

                        sleep(2000);
                        mOutputStream.write(outPut.getBytes());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while(!isInterrupted()) {
                try {
                    int count = 10;
                    byte[] buffer = new byte[count];
                    int readCount = 0; // 已经成功读取的字节的个数
                    if (mInputStream == null) return;
                    while (readCount < count) {
                        readCount += mInputStream.read(buffer, readCount, count - readCount);
//						if((int)(char)buffer[readCount-1] ==13 )break;
                        System.out.println((int)(char)buffer[readCount-1]);

                    }
                    if (readCount > 0) {
                        onDataReceived(buffer, readCount);
                        mSocket.emit("chat message", new String(buffer));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    private void DisplayError(int resourceId) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Error");
        b.setMessage(resourceId);
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SerialPortActivity.this.finish();
            }
        });
        b.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (Application) getApplication();
        try {
            mSerialPort = mApplication.getSerialPort();
            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();


            mSocket.on(Socket.EVENT_CONNECT_ERROR,onConnectError);
            mSocket.on(Socket.EVENT_CONNECT_TIMEOUT,onConnectError);
            mSocket.emit("test emit", testmessage);
            mSocket.connect();

			/* Create a receiving thread */
            mReadThread = new ReadThread();
            mReadThread.start();

//			mWriteThread = new WriteThread();
//			mWriteThread.start();

        } catch (SecurityException e) {
            DisplayError(R.string.error_security);
        } catch (IOException e) {
            DisplayError(R.string.error_unknown);
        } catch (InvalidParameterException e) {
            DisplayError(R.string.error_configuration);
        }
    }

    protected abstract void onDataReceived(final byte[] buffer, final int size);

    @Override
    protected void onDestroy() {
        if (mReadThread != null)
            mReadThread.interrupt();
        mApplication.closeSerialPort();
        mSerialPort = null;
        super.onDestroy();
    }

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            R.string.error_connect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

//    protected Emitter.Listener onMessage = new Emitter.Listener() {
//        public void call(final Object... objects) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//                    String tmpString;
//                    tmpString = (String) objects[0];
////                    text1.setText(tmpString);
//
//                }
//            });
//        }
//    };
}

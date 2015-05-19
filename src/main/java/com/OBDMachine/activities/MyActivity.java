package com.OBDMachine.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MyActivity extends Activity {


    private TextView text1;
//    private String testmessage="原来是这样";
//    private com.github.nkzawa.socketio.client.Socket mSocket;
//
//    {
//        try{
//            mSocket = IO.socket("http://192.168.0.145:3000");
//        }catch (URISyntaxException e){
//            e.printStackTrace();
//        }
//    }



    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        text1 = (TextView) findViewById(R.id.text1);

        System.out.println("test");

//        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
//        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT,onConnectError);
//        mSocket.emit("test emit", testmessage);
//        mSocket.on("chat message", onMessage);
//        mSocket.connect();

        final Button buttonTest = (Button)findViewById(R.id.TestActivityButton);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MyActivity.this, TestPort.class));
            }
        });
    }

//    private Emitter.Listener onConnectError = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(getApplicationContext(),
//                            R.string.error_connect, Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//    };
//
//    protected Emitter.Listener onMessage = new Emitter.Listener() {
//        public void call(final Object... objects) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//                    String tmpString;
//                    tmpString = (String) objects[0];
//                    text1.setText(tmpString);
//
//                }
//            });
//        }
//    };



}

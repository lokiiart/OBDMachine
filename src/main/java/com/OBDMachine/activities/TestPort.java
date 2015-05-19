package com.OBDMachine.activities;

import android.os.Bundle;
import android.widget.TextView;
import com.OBDMachine.views.GuageView;

/**
 * Created by loki on 5/18/15.
 */
public class TestPort extends SerialPortActivity {

    private TextView textView;
    private GuageView guageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        textView = (TextView) findViewById(R.id.textView);
        guageView = (GuageView) findViewById(R.id.guageView);


    }

    @Override
    protected void onDataReceived(byte[] buffer, int size) {
        final String TestString = new String(buffer);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    textView.setText(TestString);
                    guageView.setProgress(Integer.parseInt(TestString));
                }catch (InternalError e){
                    e.printStackTrace();
                }
            }
        });

    }
}

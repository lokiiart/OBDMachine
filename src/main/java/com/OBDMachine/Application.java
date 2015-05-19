package com.OBDMachine;

import com.OBDMachine.machines.SerialPort;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * Created by loki on 5/18/15.
 */
public class Application extends android.app.Application {

    private SerialPort mSerialPort = null;

    public SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
        if (mSerialPort == null) {
			/* Read serial port parameters */
//            SharedPreferences sp = getSharedPreferences("android_serialport_api.sample_preferences", MODE_PRIVATE);
            String path = "/dev/ttyMT1"; //串口
            int baudrate = Integer.decode("9600"); //波特率

			/* Check parameters */
            if ( (path.length() == 0) || (baudrate == -1)) {
                throw new InvalidParameterException();
            }

			/* Open the serial port */
            mSerialPort = new SerialPort(new File(path), baudrate, 0);
        }
        return mSerialPort;
    }

    public void closeSerialPort() {
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }
}

package com.example.loginservices;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class MainService extends Service {
    public MainService() {

    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //Constant for file for current services
    final static int SAVE_TO_FILE = 1;
    //string which is going to be written in file for current service
    String passedString;

    class IcomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SAVE_TO_FILE:
                    Bundle bundle = (Bundle) msg.obj;
                    passedString = bundle.getString("msg");
                    SaveInputTOFile(passedString);

                    break;
              default:
                    super.handleMessage(msg);
                    break;
            }

        }

        private void SaveInputTOFile(String timeStampedInput) {
            String filename = "LoggingServiceLog.txt";
            File externalFile;

            externalFile = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), filename);
            try {
                FileOutputStream fOutput = new FileOutputStream(externalFile, true);
                OutputStreamWriter wOutput = new OutputStreamWriter(fOutput);
                wOutput.append(timeStampedInput + "\n");
                wOutput.flush();
                wOutput.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Target we publish for clients to send to IncomingHandler.
        //final Messenger messenger=new Messenger(new IncomingHandler());

    }
}
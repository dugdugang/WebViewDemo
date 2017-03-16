package com.jpyl.webviewdemo;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dg on 2017/3/15.
 */

public class DownThread extends Thread {
    String mUrl;

    public DownThread(String url) {
        this.mUrl = url;
    }

    @Override
    public void run() {
        Log.i("M-TAG", "" + mUrl);

        Log.i("M-TAG", "download start!");
        String[] names = mUrl.split("\\?")[0].split("/");

        String name = names[names.length - 1];
        Log.i("M-TAG", "" + name);
        try {
            URL url = new URL(mUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            File downFile;
            File sdFile;
            Log.i("M-TAG", "5");
            int code=connection.getResponseCode();
            Log.i("M-TAG",""+code);
            InputStream is = connection.getInputStream();

            OutputStream os = null;

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                downFile = Environment.getExternalStorageDirectory();
                sdFile = new File(downFile, name);
                os = new FileOutputStream(sdFile);
            }

            byte[] buff = new byte[1024];
            int length;
            while ((length = is.read(buff)) != -1) {
                if (os != null)
                    os.write(buff, 0, length);
            }
            if (is != null)
                is.close();
            if (os != null)
                os.close();
            Log.i("M-TAG", "download success!");

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("M-TAG", "异常");
        }
    }
}

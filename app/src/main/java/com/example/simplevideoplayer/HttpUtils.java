package com.example.simplevideoplayer;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @PackageName: com.example.simplevideoplayer
 * @ClassName: HttpUtils
 * @Author: winwa
 * @Date: 2023/3/24 12:21
 * @Description:
 **/
public class HttpUtils {
    public static String get(String url) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            URL urlObject = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) urlObject.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toString();
    }
}

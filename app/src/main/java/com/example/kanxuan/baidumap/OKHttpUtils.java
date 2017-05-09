package com.example.kanxuan.baidumap;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

/**
 * Created by kanxuan on 2017/5/4.
 */

public class OKHttpUtils {

    final static  private  String TAG = "Http Request";
    static public String url = "http://192.168.1.110:8080";
    static public void getMap(final Context context, String mapid){

        OkHttpClient client = new OkHttpClient();
        String tempUrl = url  + "/maps/" + mapid;
        Request request = new Request.Builder().url("http://192.168.1.110:8080/map/maps/id?mapId=12").get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.toString());

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.e("TAG", "Sucess");
                    if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                        Log.d(TAG, "Main Thread");
                    } else {
                        Log.d(TAG, "Not Main Thread");
                    }
                    Log.i(TAG, response.body().string());

                    ((MainActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "code: ");
                            Toast.makeText(context, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
                else {
                    Log.e(TAG, response.toString());
                }

            }
        });


    }



}

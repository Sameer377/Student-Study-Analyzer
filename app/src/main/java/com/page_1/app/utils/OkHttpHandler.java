package com.page_1.app.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class OkHttpHandler extends AsyncTask {
    private OnTaskCompleted listener;
    private HashMap<String, String> postDataParams;
    private String TAG, dup_url = "";
    private Context mContext;

    public OkHttpHandler(Context context, OnTaskCompleted listener, HashMap<String, String> postDataParams, String TAG) {
        this.listener = listener;
        this.TAG = TAG;
        this.mContext = context;
        this.postDataParams = postDataParams;
//        preferencesManager = PreferencesManager.getInstance(mContext);
    }

    public OkHttpHandler(OnTaskCompleted listener, String TAG, Activity baseActivity) {
        this.listener = listener;
        this.TAG = TAG;
    }


    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }


    private String uploadData(String requestURL, HashMap<String, String> postDataParams, String TAG) {
        InputStream is = null;
        URL url;
        String response = "";

        try {
                url = new URL(requestURL.replaceAll(" ", "%20"));
            Log.v(TAG + ":API", url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);

            if (postDataParams != null) {
                    conn.setRequestMethod("POST");
//                    conn.addRequestProperty("Authorization", "Basic " + auth);
                    conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    OutputStream os = conn.getOutputStream();
                    Log.v(TAG + " Request", postDataParams.toString());
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getPostDataString(postDataParams));
                    writer.flush();
                    writer.close();
                    os.close();

            } else {
                if(TAG.equals("OTPSend"))
                {
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                    conn.setRequestProperty("cache-control", "no-cache");
                }
                else
                conn.setRequestMethod("GET");
            }
            String line;
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }

            }


            Log.v("Response:" + TAG, response);
        } catch (SocketTimeoutException s) {

            s.printStackTrace();
            return response;


        } catch (Exception e) {
            e.printStackTrace();
            return response;
        }
        //    Log.v(TAG, "Response: " + response);
        return response;
    }

    @Override
    protected String doInBackground(Object[] objects) {
        dup_url = objects[0].toString();
        return uploadData(objects[0].toString(), postDataParams, TAG);
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        try {
            if(progress!=null)
            {
                if(progress.isShowing())
                    progress.dismiss();
            }

            listener.onTaskCompleted(o.toString(), TAG);
            cancel(true);

        } catch (Exception e) {
            Log.e("ReadJSONFeedTask", e.getLocalizedMessage() + "");
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//            openProgressDialog();


    }
    ProgressDialog progress;
    public void openProgressDialog() {
        progress = new ProgressDialog(mContext );
        progress.setMessage("Please wait.......");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }


}

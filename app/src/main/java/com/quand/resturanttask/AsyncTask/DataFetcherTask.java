package com.quand.resturanttask.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.quand.resturanttask.activities.SplashActivity;
import com.quand.resturanttask.model.Customer;
import com.quand.resturanttask.model.Table;
import com.quand.resturanttask.utilities.Utility;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by sahar on 06-Sep-16.
 */
public class DataFetcherTask extends AsyncTask<String, Void, String> {

    private Context context;
    private boolean isLoadingTablesData = false;
    private HttpURLConnection urlConnection;

    public DataFetcherTask(Context context, boolean isLoadingTablesData) {
        this.context = context;
        this.isLoadingTablesData = isLoadingTablesData;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {

        String res = "";// String object to store fetched data from server

        try {
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            // Responses from the server (code and message)
            int serverResponseCode = urlConnection.getResponseCode();

            if (serverResponseCode == HttpsURLConnection.HTTP_OK) {
                while ((line = reader.readLine()) != null) {
                    res += line;
                }
            }
            if (isLoadingTablesData) {
                //loading Tables data
                Table.parseAndStoreTablesData(context, res);
            } else {
                //loading customers data
                Customer.loadAndStoreFromJson(context, res);
            }
            Log.d("result:", res);
        } catch (Exception e) {
            res = null;
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }
        return res;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (!isLoadingTablesData)
            ((SplashActivity) context).openCustomersActivity();

    }
}

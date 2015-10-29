package edu.temple.mikem.basicwebbrowser;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mikem on 10/28/2015.
 */
public class UrlDownloader extends AsyncTask<String, Void, String> {


    private WebView webView;

    public UrlDownloader(WebView webView){
        this.webView = webView;
    }

    @Override
    protected String doInBackground(String... params) {

        Log.d("AsyncTask", "Started");

            String pageData = "";
            String dataIn;

        try {
            String newUrl = params[0];

            if(!newUrl.startsWith("http://")){
                newUrl = "http://" + newUrl;
            }

            URL url = new URL(newUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            while ((dataIn = reader.readLine()) != null) {
                pageData += dataIn + "\n";
            }
            return pageData;

        }catch (MalformedURLException e){
            Log.d("MalformedURLException", e.toString());
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;

    }

    @Override
    protected void onPostExecute(String result){

        if(webView != null && result!=null){
            webView.loadData(result,"text/html","UTF-8");
            Log.d("AsyncTask", "Finished");
            Log.d("Page", result);
        }
    }




}

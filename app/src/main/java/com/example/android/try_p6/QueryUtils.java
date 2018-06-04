package com.example.android.try_p6;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static ArrayList<News> extraxtResultFromJson(String jsonString){
        ArrayList<News> newsInfo = new ArrayList<>();
        String sectionName, webTitle, webUrl, webPublicationDate, type, author;
        JSONObject row,fields;
        try{
            JSONObject root = new JSONObject(jsonString);
            JSONObject response = root.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");

            for (int i=0; i<results.length(); i++){

                row = results.getJSONObject(i);

                sectionName = row.getString("sectionName");

                webTitle = row.getString("webTitle");

                webUrl = row.getString("webUrl");

                webPublicationDate = row.getString("webPublicationDate");

                type = row.getString("type");

                fields = row.getJSONObject("fields");
                author = fields.getString("byline");

                // add current news object to the news info list depending if the author name is found or not
                if(fields != null){
                    newsInfo.add(new News(sectionName, webTitle, webUrl, webPublicationDate, type, author));
                }
                else{
                    newsInfo.add(new News(sectionName, webTitle, webUrl, webPublicationDate, type));
                }

            }

        } catch (JSONException e){
            e.printStackTrace();
        }
        return newsInfo;
    }

    public static ArrayList<News> getUrlData (URL requestUrl){
        // get string json
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(requestUrl);
        } catch (IOException e){
            Log.e(LOG_TAG, e.getMessage());
        }
        // convert json string to json object
        ArrayList<News> newsInfo = extraxtResultFromJson(jsonResponse);

        // return json object
        return newsInfo;
    }

    private static String makeHttpRequest(URL requestUrl) throws IOException {

        String jsonResponse = "";

        // open url connection
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) { // connected
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        // from bits to string
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader); // read from input stream
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


}

package com.example.dre.emergencybutton;

import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dre on 22-Nov-17.
 */

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    String googlePlacesData;
    String url;
    Double lat1, lng1;
    List<HashMap<String, String>> nearbyPlacesList;

    GetNearbyPlacesData(Double lat, Double lng) {
        this.lat1 = lat;
        this.lng1 = lng;

    }


    @Override
    protected String doInBackground(Object... params) {
        try {
            Log.d("GetNearbyPlacesData", "doInBackground entered");
            url = (String) params[0];
            DownloadUrl downloadUrl = new DownloadUrl();
            googlePlacesData = downloadUrl.readUrl(url);
            Log.d("GooglePlacesReadTask", "doInBackground Exit");
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("GooglePlacesReadTask", "onPostExecute Entered");
        List<HashMap<String, String>> nearbyPlacesList = null;
        DataParserPlace dataParserPlace = new DataParserPlace();
        nearbyPlacesList = dataParserPlace.parse(result);

        for(int i=0; i<nearbyPlacesList.size(); i++){
            String placeName = nearbyPlacesList.get(i).get("placeName");
            String phoneNumber = nearbyPlacesList.get(i).get("phoneNumber");
        }

        Log.d("GooglePlacesReadTask", "onPostExecute Exit" + result);
    }
}

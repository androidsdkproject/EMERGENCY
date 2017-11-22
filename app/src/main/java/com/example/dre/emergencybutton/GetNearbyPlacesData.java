package com.example.dre.emergencybutton;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;

/**
 * Created by Dre on 22-Nov-17.
 */

public class GetNearbyPlacesData {
    String googlePlacesData;
    String url;
    Double lat1, lng1;

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
        Log.d("GooglePlacesReadTask", "onPostExecute Exit" + result);
    }

}

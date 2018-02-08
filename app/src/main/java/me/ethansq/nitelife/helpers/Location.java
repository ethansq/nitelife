package me.ethansq.nitelife.helpers;

import org.json.JSONException;
import org.json.JSONObject;

public class Location {
    private String name;
    private String placeId;
    private String photoRef;
    private int rating;

    public Location(JSONObject resultJSON) {
        try {
            name = resultJSON.getString("name");
            placeId = resultJSON.getString("place_id");
            rating = resultJSON.getInt("rating");

            photoRef = resultJSON.getJSONArray("photos").getJSONObject(0).getString("photo_reference");
        } catch (JSONException e) {
            // ...
        }
    }

    public String getName() {
        return name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getPhotoRef() {
        return photoRef;
    }

    public String getPhotoUrl() {
        String url = "https://maps.googleapis.com/maps/api/place/photo" +
                "?maxwidth=1600" +
                "&photoreference=" + photoRef +
                "&key=" + Constants.GOOGLE_PLACES_API_KEY;
        return url;
    }

    public int getRating() {
        return rating;
    }
}

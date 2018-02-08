package me.ethansq.nitelife.fragments;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.ethansq.nitelife.R;
import me.ethansq.nitelife.helpers.Constants;
import me.ethansq.nitelife.helpers.Utils;
import me.ethansq.nitelife.views.LocationCardView;

public class FragmentExplore extends Fragment {
    private final String TAG = "FragmentExplore";

    private View mView;
    private SwipePlaceHolderView mSwipeView;
    private RequestQueue mRequestQueue;

    public FragmentExplore() {} // Required empty public constructor

    public static FragmentExplore newInstance() {
        FragmentExplore fragment = new FragmentExplore();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRequestQueue = Volley.newRequestQueue(getContext());
        mView = inflater.inflate(R.layout.fragment_explore, container, false);

        // Inflate the layout for this fragment
        mSwipeView = (SwipePlaceHolderView) mView.findViewById(R.id.swipeView);
        mSwipeView.getBuilder()
                .setDisplayViewCount(3);
//                .setSwipeDecor(new SwipeDecor()
//                        .setPaddingTop(20)
//                        .setRelativeScale(0.01f);
//                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
//                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));
//
//        for (Profile profile : Utils.loadProfiles(getContext())){
//            mSwipeView.addView(new ProfileCardView(getContext(), profile, mSwipeView));
//        }

//        mView.findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSwipeView.doSwipe(false);
//            }
//        });
//
//        mView.findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSwipeView.doSwipe(true);
//            }
//        });

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();

        /**
         * In order to populate our locations, we send a GET request to Google's
         * Nearby Places API
         */

        try {
            // Get current location coordinates
            LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

            final LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();

                    final String REQUEST_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                            "?location=" + latitude + "," + longitude +
                            "&key=" + Constants.GOOGLE_PLACES_API_KEY +
                            "&rankby=" + "prominence" +
                            "&radius=" + "20000" +
                            "&type=" + "restaurant";

                    Log.e(TAG, REQUEST_URL);

                    /**
                     * Finally, make the request to get some search results
                     */
                    JsonObjectRequest jsonRequest = new JsonObjectRequest(
                            Request.Method.GET,
                            REQUEST_URL,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        // Parse through response and get all individual results
                                        JSONArray resultsArray = response.getJSONArray("results");

                                        int n = resultsArray.length();
                                        for (int i=0; i<n; i++) {
                                            me.ethansq.nitelife.helpers.Location loc = new me.ethansq.nitelife.helpers.Location((JSONObject) resultsArray.get(i));
                                            mSwipeView.addView(new LocationCardView(getContext(), loc, mSwipeView));
                                        }

                                    } catch (JSONException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e(TAG, error.toString());
                                }
                            }
                    );
                    // lastly, add the request to queue
                    mRequestQueue.add(jsonRequest);

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {}

                @Override
                public void onProviderEnabled(String provider) {}

                @Override
                public void onProviderDisabled(String provider) {}
            };

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);

        } catch (SecurityException e) {
            // ...
        }
    }
}

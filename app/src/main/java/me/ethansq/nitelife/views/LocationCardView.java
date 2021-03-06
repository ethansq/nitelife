package me.ethansq.nitelife.views;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

import me.ethansq.nitelife.R;
import me.ethansq.nitelife.helpers.Location;

@Layout(R.layout.view_location_card)
public class LocationCardView {
    private final String TAG = "LocationCardView";

    @View(R.id.mainImageView)
    private ImageView mainImageView;

    @View(R.id.infoText)
    private TextView infoTextView;

    private Location mLocation;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public LocationCardView(Context context, Location location, SwipePlaceHolderView swipeView) {
        mContext = context;
        mLocation = location;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved() {
        if (mainImageView == null) {
            Log.e(TAG, "mainImageView null");
        } else if (infoTextView == null) {
            Log.e(TAG, "infoTextView null");
        }

        Log.e(TAG, mLocation.getPhotoUrl());
        Glide.with(mContext).load(mLocation.getPhotoUrl()).into(mainImageView);
        infoTextView.setText(mLocation.getName());
    }

    @SwipeOut
    private void onSwipedOut() {
        Log.d("EVENT", "onSwipedOut");
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState() {
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn() {
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState() {
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState() {
        Log.d("EVENT", "onSwipeOutState");
    }
}
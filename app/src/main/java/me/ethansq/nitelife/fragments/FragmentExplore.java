package me.ethansq.nitelife.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import me.ethansq.nitelife.R;
import me.ethansq.nitelife.helpers.Profile;
import me.ethansq.nitelife.helpers.Utils;
import me.ethansq.nitelife.views.ProfileCardView;

public class FragmentExplore extends Fragment {
    private final String TAG = "FragmentExplore";
    private View mView;
    private SwipePlaceHolderView mSwipeView;

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

        for (Profile profile : Utils.loadProfiles(getContext())){
            mSwipeView.addView(new ProfileCardView(getContext(), profile, mSwipeView));
        }

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
}

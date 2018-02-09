package me.ethansq.nitelife.activities;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.mindorks.placeholderview.Utils;

import me.ethansq.nitelife.R;
import me.ethansq.nitelife.fragments.FragmentExplore;

public class ActivityMain extends AppCompatActivity {
    private final String TAG = "ActivityMain";
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 6001;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private boolean mLocationPermissionGranted;
    private View mLocationPermissionMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        mLocationPermissionMessage = findViewById(R.id.locationPermissionMessage);
        mViewPager = (ViewPager) findViewById(R.id.container);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // When a new page is selected, update our TabItems manually
                Log.e(TAG, mViewPager.getCurrentItem()+"");
                updateTabs(false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        updateTabs(true);
    }

    /**
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
    public void getLocationPermission(View v) {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            updateUI();
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {

//        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }

        updateUI();
    }

    /**
     * Primary function of updateUI is to show/hide a message indicating that location permission(s)
     * are required to use this application
     */
    private void updateUI() {
        mLocationPermissionMessage.setVisibility(View.GONE); // Would rather delete View altogether,
        // but this should suffice
    }

    final int inactiveTabs[] = {
            R.drawable.ic_menu_inactive,
            R.drawable.ic_explore_inactive,
            R.drawable.ic_add_inactive
    };

    final int activeTabs[] = {
            R.drawable.ic_menu,
            R.drawable.ic_explore,
            R.drawable.ic_add
    };

    /**
     * When we select a new page, we need to update our TabItems to reflect the currently
     * selected tab
     */
    private void updateTabs(boolean init) {
        int active = mViewPager.getCurrentItem();

        // Set the initial icons for our TabItems
        for (int i = 0; i < 3; i++) {
            if (init) {
                // Depending on the active Fragment, our tab icon might look different
                View v = LayoutInflater.from(this).inflate(
                        R.layout.view_tab,
                        null
                );
                mTabLayout.getTabAt(i).setCustomView(v);
            }

            View v = mTabLayout.getTabAt(i).getCustomView();

            int padding = (i == active) ? Utils.dpToPx(2) : Utils.dpToPx(6);
            v.findViewById(R.id.tabIconWrapper).setPadding(padding, padding, padding, padding);
            ((ImageView) v.findViewById(R.id.tabIcon)).setImageResource(
                    (i == active) ? activeTabs[i] : inactiveTabs[i]
            );
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getLocationPermission(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private Context mContext;

        public SectionsPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentExplore.newInstance();
        }

        @Override
        public int getCount() {
            /**
             * We show a total of three pages: LIST, EXPLORE, and CREATE
             */
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}

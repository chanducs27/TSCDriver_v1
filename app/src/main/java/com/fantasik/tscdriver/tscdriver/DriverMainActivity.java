package com.fantasik.tscdriver.tscdriver;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import layout.EarningsTodayFragment;
import layout.MapsFragment;

public class DriverMainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        //getActionBar().hide();
        getSupportActionBar().hide();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mSectionsPagerAdapter.addFrag(new MapsFragment(), "HOME");
        mSectionsPagerAdapter.addFrag(new EarningsTodayFragment(), "EARNINGS");
        mSectionsPagerAdapter.addFrag(new DriverRatingFragment(), "RATINGS");
        mSectionsPagerAdapter.addFrag(new AccountsFragment(), "ACCOUNT");
        mSectionsPagerAdapter.addFrag(new Fragment(), "ONLINE");
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        createTabIcons() ;
     /*   // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.pickup_request);
        dialog.setTitle("Title...");
        dialog.show();*/
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                // When on page selected if position is not spesific for you
                // you can find your fragment or you can switch position.
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    private void createTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("HOME");

        Drawable dr = this.getResources().getDrawable(R.drawable.imghome);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable dre = new BitmapDrawable(this.getResources(), Bitmap.createScaledBitmap(bitmap, 60, 60, true));

        tabOne.setCompoundDrawablesWithIntrinsicBounds(null, dre, null, null);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tab2 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab2.setText("EARNINGS");
         dr = this.getResources().getDrawable(R.drawable.imgearnings);
         bitmap = ((BitmapDrawable) dr).getBitmap();
         dre = new BitmapDrawable(this.getResources(), Bitmap.createScaledBitmap(bitmap, 60, 60, true));
        tab2.setCompoundDrawablesWithIntrinsicBounds(null, dre, null, null);
        tabLayout.getTabAt(1).setCustomView(tab2);

        TextView tab3 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab3.setText("RATINGS");
        dr = this.getResources().getDrawable(R.drawable.imgrating);
        bitmap = ((BitmapDrawable) dr).getBitmap();
        dre = new BitmapDrawable(this.getResources(), Bitmap.createScaledBitmap(bitmap, 60, 60, true));
        tab3.setCompoundDrawablesWithIntrinsicBounds(null, dre, null, null);
        tabLayout.getTabAt(2).setCustomView(tab3);

        TextView tab4 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab4.setText("ACCOUNT");
        dr = this.getResources().getDrawable(R.drawable.imgaccount);
        bitmap = ((BitmapDrawable) dr).getBitmap();
        dre = new BitmapDrawable(this.getResources(), Bitmap.createScaledBitmap(bitmap, 60, 60, true));
        tab4.setCompoundDrawablesWithIntrinsicBounds(null, dre, null, null);
        tabLayout.getTabAt(3).setCustomView(tab4);

        TextView tab5 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab5.setText("ONLINE");
        dr = this.getResources().getDrawable(R.drawable.imgonline);
        bitmap = ((BitmapDrawable) dr).getBitmap();
        dre = new BitmapDrawable(this.getResources(), Bitmap.createScaledBitmap(bitmap, 90, 60, true));
        tab5.setCompoundDrawablesWithIntrinsicBounds(null, dre, null, null);
        tabLayout.getTabAt(4).setCustomView(tab5);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_driver_main, menu);
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


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}


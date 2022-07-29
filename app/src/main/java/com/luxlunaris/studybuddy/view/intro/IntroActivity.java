package com.luxlunaris.studybuddy.view.intro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.model.utils.FileManager;

public class IntroActivity extends FragmentActivity {

    private static final int NUM_PAGES = 5;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private CheckBox doNotShowIntroAgainCheckBox;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        viewPager = (ViewPager) findViewById(R.id.introViewPager);
        pagerAdapter = new FAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        doNotShowIntroAgainCheckBox = findViewById(R.id.dontShowIntroAgaincheckBox);
        progressBar = findViewById(R.id.introProgress);
        progressBar.setMax(NUM_PAGES-1);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("IntroActivity", "onPageScrolled: "+position);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("IntroActivity", "onPageSelected: "+position);
                progressBar.setProgress(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("IntroActivity", "onPageScrollStateChanged: "+state);
            }
        });



    }

    @Override
    public void onBackPressed() {

        if(viewPager.getCurrentItem()==0){
            super.onBackPressed();
        }else{
            viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
        }

    }

    @Override
    public void finish() {


        if(doNotShowIntroAgainCheckBox.isChecked()){
            FileManager.setIntroSeen();
        }

        super.finish();

    }

    class FAdapter extends FragmentStatePagerAdapter{

        public FAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return new IntroFragment(R.layout.fragment_intro);
        }


        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }



}
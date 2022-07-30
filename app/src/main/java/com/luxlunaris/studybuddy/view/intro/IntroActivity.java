package com.luxlunaris.studybuddy.view.intro;

import androidx.annotation.NonNull;
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

    private  static int[] PAGES = new int[]{R.layout.fragment_intro_0, R.layout.fragment_intro_2, R.layout.fragment_intro_1, R.layout.fragment_intro_3};

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        viewPager = (ViewPager) findViewById(R.id.introViewPager);
        pagerAdapter = new FAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        progressBar = findViewById(R.id.introProgress);
        progressBar.setMax(PAGES.length-1);

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

        FileManager.setIntroSeen();
        super.finish();

    }

    class FAdapter extends FragmentStatePagerAdapter{


        public FAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return new IntroFragment(PAGES[position]);
        }


        @Override
        public int getCount() {
            return PAGES.length;
        }
    }



}
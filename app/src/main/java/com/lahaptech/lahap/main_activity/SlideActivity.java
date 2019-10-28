package com.lahaptech.lahap.main_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.lahaptech.lahap.R;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class SlideActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button next;
    private Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.

        setContentView(R.layout.activity_slide);
        viewPager = findViewById(R.id.viewPager);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);

        back.setVisibility(View.INVISIBLE);

        WormDotsIndicator indicators = findViewById(R.id.indicator);
        FadeOut transformation = new FadeOut();
        WalkThroughAdapter viewPagerAdapter = new WalkThroughAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        indicators.setViewPager(viewPager);
        viewPager.setPageTransformer(true,transformation);
    }

    @Override
    protected void onStart() {
        super.onStart();

        next.setOnClickListener(view -> {
            if (viewPager.getCurrentItem() + 1 < 4) {
                viewPager.setCurrentItem(nextitem(), true); //getItem(-1) for previous
            }
            else {
                Intent intent = new Intent(SlideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(view -> {
            if (viewPager.getCurrentItem() - 1 >= 0) {
                viewPager.setCurrentItem(previtem(), true); //getItem(-1) for previous
            }
        });
    }

    private int nextitem() {

        return viewPager.getCurrentItem()+1;

    }

    private int previtem() {

        return viewPager.getCurrentItem() - 1;

    }

    private class WalkThroughAdapter extends FragmentPagerAdapter {

        WalkThroughAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int i) {

            switch (i){
                case 0:
                    return new Walkthrough1();
                case 1:
                    return new Walkthrough2();
                case 2:
                    return new Walkthrough3();
                case 3:
                    return new Walkthrough4();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    private class FadeOut implements ViewPager.PageTransformer{
        @Override
        public void transformPage(View page, float position) {

            page.setTranslationX(-position*page.getWidth());

            page.setAlpha(1-Math.abs(position));


        }
    }
}

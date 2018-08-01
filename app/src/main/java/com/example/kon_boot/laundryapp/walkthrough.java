package com.example.kon_boot.laundryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class walkthrough extends AppCompatActivity {

    ViewPager slideViewPager;
    LinearLayout dotLayout;
    slideAdapter mslideAdapter;
    private TextView[] dots;
    private Button leftbtn, rightbtn;
    int curPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);
        leftbtn = (Button) findViewById(R.id.leftbtn);
        rightbtn = (Button) findViewById(R.id.rightbtn);

        slideViewPager = (ViewPager) findViewById(R.id.viewPager1);
        dotLayout = (LinearLayout) findViewById(R.id.linearLayout1);

        mslideAdapter = new slideAdapter(this);
        slideViewPager.setAdapter(mslideAdapter);
        slideViewPager.addOnPageChangeListener(viewListener);
        indicate(0);

        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideViewPager.setCurrentItem(curPage - 1);

            }
        });
        rightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideViewPager.setCurrentItem(curPage + 1);
                trans();
            }
        });

    }

    public void indicate(int position) {
        dots = new TextView[3];
        dotLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorTransparent));
            dotLayout.addView(dots[i]);

        }
        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.colorAccent));

        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int i) {
            indicate(i);
            curPage = i;
            if (i == 0) {
                leftbtn.setEnabled(false);
                rightbtn.setEnabled(true);
                leftbtn.setVisibility(View.INVISIBLE);
                rightbtn.setText("Next");
            } else if (i == dots.length - 1) {
                leftbtn.setEnabled(true);
                rightbtn.setEnabled(true);
                leftbtn.setVisibility(View.VISIBLE);
                rightbtn.setText("Finish");

                leftbtn.setText("Back");
            } else {
                leftbtn.setEnabled(true);
                rightbtn.setEnabled(true);
                leftbtn.setVisibility(View.VISIBLE);
                rightbtn.setText("Next");
                leftbtn.setText("Back");
            }
        }


        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void trans() {
        if (slideViewPager.getCurrentItem() == dots.length - 1) {
            startActivity(new Intent(walkthrough.this, GoogleLogin.class));
            finish();
        }
    }
}


package com.example.android.introslider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView textView;
    Button btnSkip,btnNext;
    int[] layouts;
    ViewPager viewPager;
    MyViewPagerAdapter myViewPagerAdapter;

    boolean isFirstTime()
    {
        return sharedPreferences.getBoolean("isFirstTime",true);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    void launchWelcomeActivity()
    {
        setContentView(R.layout.activity_main);
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                };
        btnNext = (Button)findViewById(R.id.btn_next);
        btnSkip = (Button)findViewById(R.id.btn_skip);
        myViewPagerAdapter = new MyViewPagerAdapter();

        viewPager.setAdapter(myViewPagerAdapter);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });
    }

    public void  launchHomeScreen()
    {
        startActivity(new Intent(getApplicationContext(),Main2Activity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.example.android.introslider",MODE_PRIVATE);
        textView = (TextView)findViewById(R.id.textView);
        if(isFirstTime()){
            launchWelcomeActivity();

            sharedPreferences.edit().putBoolean("isFirstTime",false).apply();
        }else{
            launchHomeScreen();
            startActivity(new Intent(getApplicationContext(),Main2Activity.class));
            finish();
        }

    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}

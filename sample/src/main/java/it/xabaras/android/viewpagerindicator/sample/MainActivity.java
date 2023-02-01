package it.xabaras.android.viewpagerindicator.sample;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.viewpager2.widget.ViewPager2;

import it.xabaras.android.viewpagerindicator.sample.adapter.ViewPagerAdapter;
import it.xabaras.android.viewpagerindicator.widget.ViewPagerIndicator2;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private ViewPagerIndicator2 viewPagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager2) findViewById(R.id.viewPager);
        viewPagerIndicator = (ViewPagerIndicator2) findViewById(R.id.viewPagerIndicator);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPagerIndicator.initWithViewPager(viewPager);
    }
}

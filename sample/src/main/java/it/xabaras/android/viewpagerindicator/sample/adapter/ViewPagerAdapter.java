package it.xabaras.android.viewpagerindicator.sample.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Paolo Montalto on 19/07/16.
 * Copyright (c) 2016 TwoMenStudio. All rights reserved.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private final Context context;

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView view = new TextView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        view.setGravity(Gravity.CENTER);
        view.setText("View " + (position + 1));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
        object = null;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals((View)object);
    }
}

package com.mp.neetjeeiitprep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mp.neetjeeiitprep.R;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class IntroPagerAdapter extends PagerAdapter {

    private int tabCount;
    private Context context;

    public IntroPagerAdapter(int tabCount, Context context) {
        this.tabCount = tabCount;
        this.context = context;
    }

    public int getCount() {
        return this.tabCount;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Using different layouts in the view pager instead of images.

        int resId = -1;

        //Getting my layout's in my adapter. Three layouts defined.
        switch (position) {
            case 0:
                resId = R.layout.intro_page1;
                break;
            case 1:
                resId = R.layout.intro_page2;
                break;
        }

        View view = inflater.inflate(resId, container, false);
        if (resId == R.layout.intro_page2) {
            ImageView imgLogo = view.findViewById(R.id.imgLogo);
            ImageView imgOurServices = view.findViewById(R.id.imgOurServices);
            Glide.with(context).load(R.drawable.colour_logo).into(imgLogo);
            Glide.with(context).load(R.drawable.our_services).into(imgOurServices);

        }else if (resId == R.layout.intro_page1) {
            ImageView imgPg1Logo = view.findViewById(R.id.imgPg1Logo);
            Glide.with(context).load(R.drawable.colour_logo).into(imgPg1Logo);
        }

        ((ViewPager) container).addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}


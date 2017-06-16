package com.example.teerayutk.tsr_demo.activity.catalog.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.teerayutk.tsr_demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teerayut.k on 12/6/2560.
 */

public class ImageAdapter extends PagerAdapter {

    private Context context;
    private List<String> stringList = new ArrayList<String>();
    public ImageAdapter(Context context, List<String> stringList) {
        this.context = context;
        this.stringList = stringList;
    }
    @Override
    public int getCount() {
        return stringList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.image_slider, null);
        ImageView view_image = (ImageView) view.findViewById(R.id.imageSlider);

        Glide.with(context).load(stringList.get(position))
                .placeholder(R.drawable.image_holder)
                .into(view_image);

        ((ViewPager) container).addView(view, 0);

        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ViewGroup) object);
    }
}
